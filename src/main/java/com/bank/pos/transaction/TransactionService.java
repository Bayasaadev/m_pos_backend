package com.bank.pos.transaction;

import com.bank.pos.common.BadRequestException;
import com.bank.pos.common.NotFoundException;
import com.bank.pos.terminal.Terminal;
import com.bank.pos.terminal.TerminalRepository;
import com.bank.pos.transaction.dto.TransactionRequest;
import com.bank.pos.transaction.dto.TransactionResponse;
import com.bank.pos.transaction.dto.TransactionResponseDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TerminalRepository terminalRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(TerminalRepository terminalRepository, TransactionRepository transactionRepository) {
        this.terminalRepository = terminalRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDetail> getAll() {
        return transactionRepository.findAll().stream().map(TransactionMapper::toDetail).toList();
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDetail> getByTerminalId(String terminalId) {
        if (!terminalRepository.existsByTerminalId(terminalId)) {
            throw new NotFoundException("Terminal not found for terminal_id=" + terminalId);
        }
        return transactionRepository.findByTerminal_TerminalIdOrderByTranDateDesc(terminalId)
                .stream()
                .map(TransactionMapper::toDetail)
                .toList();
    }

    @Transactional(noRollbackFor = BadRequestException.class)
    public TransactionResponse process(TransactionRequest req) {
        Terminal terminal = terminalRepository.findByTerminalId(req.getTerminalId())
                .orElseThrow(() -> new NotFoundException("Terminal not registered for terminal_id=" + req.getTerminalId()));

        if (!req.getSerialNo().equals(terminal.getSerialNo())) {
            throw new BadRequestException("serial_no does not match terminal_id");
        }
        if (!req.getMerchantId().equals(terminal.getMerchantId())) {
            throw new BadRequestException("merchant_id does not match terminal_id");
        }

        // Check for duplicate trace_no for this terminal
        if (transactionRepository.findByTerminal_TerminalIdAndTraceNo(req.getTerminalId(), req.getTraceNo()).isPresent()) {
            throw new BadRequestException("Duplicate trace_no", "14");
        }

        boolean keysMatch = safeEquals(req.getZoneKey(), terminal.getZoneKey())
                && safeEquals(req.getPinKey(), terminal.getPinKey())
                && safeEquals(req.getMacKey(), terminal.getMacKey());

        BigDecimal normalizedAmount = normalizeAmount(req.getAmount());

        Transaction tx = new Transaction();
        tx.setTerminal(terminal);
        tx.setTranDate(OffsetDateTime.now());
        tx.setSerialNo(req.getSerialNo());
        tx.setMerchantId(req.getMerchantId());
        tx.setCurrency(req.getCurrency());
        tx.setTraceNo(req.getTraceNo());
        tx.setEntryMode(req.getEntryMode());
        tx.setAmount(normalizedAmount);
        tx.setMaskedPan(req.getMaskedPan());
        tx.setExpiryDate(req.getExpiryDate());
        tx.setTrack2(req.getTrack2());
        tx.setTlvData(req.getTlvData());
        tx.setPinBlock(req.getPinBlock());
        tx.setPinKsn(req.getPinKsn());
        tx.setRemark(req.getRemark());

        if (!keysMatch) {
            tx.setSuccess(false);
            tx.setDescription("Key mismatch");
            Transaction saved = transactionRepository.save(tx);
            throw new BadRequestException(saved.getDescription(), "05");
        }

        String amountStr = normalizedAmount.toPlainString();
        if (amountStr.endsWith(".51")) {
            tx.setSuccess(false);
            tx.setDescription("Insufficient funds");
            Transaction saved = transactionRepository.save(tx);
            throw new BadRequestException(saved.getDescription(), "51");
        }
        if (amountStr.endsWith(".52")) {
            tx.setSuccess(false);
            tx.setDescription("System error");
            Transaction saved = transactionRepository.save(tx);
            throw new BadRequestException(saved.getDescription(), "91");
        }

        tx.setSuccess(true);
        tx.setDescription("Approved");

        Transaction saved = transactionRepository.save(tx);
        
        // Generate approval code (6 digits)
        String approvalCode = String.format("%06d", saved.getTranNo() % 1000000);
        
        // Generate issuer_response_tlv: required for ICC (CHIP), null for NFC
        String issuerResponseTlv = null;
        if ("CHIP".equalsIgnoreCase(req.getEntryMode()) || "ICC".equalsIgnoreCase(req.getEntryMode())) {
            // Example TLV format: 910A... (simplified for demo)
            issuerResponseTlv = "910A" + String.format("%08X", saved.getTranNo().intValue());
        }
        
        return new TransactionResponse(saved.getTranNo(), saved.getDescription(), "00", approvalCode, issuerResponseTlv);
    }

    private boolean safeEquals(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }

    private BigDecimal normalizeAmount(BigDecimal amount) {
        if (amount == null) {
            throw new BadRequestException("amount is required");
        }
        if (amount.signum() <= 0) {
            throw new BadRequestException("amount must be positive");
        }
        try {
            return amount.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException ex) {
            throw new BadRequestException("amount must have 2 decimal places");
        }
    }
}


