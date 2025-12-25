package com.bank.pos.terminal;

import com.bank.pos.common.ConflictException;
import com.bank.pos.common.NotFoundException;
import com.bank.pos.terminal.dto.TerminalCreateRequest;
import com.bank.pos.terminal.dto.TerminalResponse;
import com.bank.pos.terminal.dto.TerminalUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TerminalService {

    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    @Transactional(readOnly = true)
    public List<TerminalResponse> getAll() {
        return terminalRepository.findAll().stream().map(TerminalMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TerminalResponse getBySerialNo(String serialNo) {
        Terminal t = terminalRepository.findById(serialNo)
                .orElseThrow(() -> new NotFoundException("Terminal not found for serial_no=" + serialNo));
        return TerminalMapper.toResponse(t);
    }

    @Transactional
    public TerminalResponse register(TerminalCreateRequest req) {
        if (terminalRepository.existsById(req.getSerialNo())) {
            throw new ConflictException("serial_no already registered: " + req.getSerialNo());
        }
        if (terminalRepository.existsByTerminalId(req.getTerminalId())) {
            throw new ConflictException("terminal_id already registered: " + req.getTerminalId());
        }
        Terminal saved = terminalRepository.save(TerminalMapper.toEntity(req));
        return TerminalMapper.toResponse(saved);
    }

    @Transactional
    public TerminalResponse update(String serialNo, TerminalUpdateRequest req) {
        Terminal t = terminalRepository.findById(serialNo)
                .orElseThrow(() -> new NotFoundException("Terminal not found for serial_no=" + serialNo));

        if (req.getTerminalId() != null && !req.getTerminalId().equals(t.getTerminalId())) {
            if (terminalRepository.existsByTerminalId(req.getTerminalId())) {
                throw new ConflictException("terminal_id already registered: " + req.getTerminalId());
            }
            t.setTerminalId(req.getTerminalId());
        }

        if (req.getMerchantId() != null) t.setMerchantId(req.getMerchantId());
        if (req.getMerchantName() != null) t.setMerchantName(req.getMerchantName());
        if (req.getType() != null) t.setType(req.getType());
        if (req.getZoneKey() != null) t.setZoneKey(req.getZoneKey());
        if (req.getPinKey() != null) t.setPinKey(req.getPinKey());
        if (req.getMacKey() != null) t.setMacKey(req.getMacKey());

        Terminal saved = terminalRepository.save(t);
        return TerminalMapper.toResponse(saved);
    }
}


