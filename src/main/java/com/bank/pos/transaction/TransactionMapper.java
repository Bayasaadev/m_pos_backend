package com.bank.pos.transaction;

import com.bank.pos.transaction.dto.TransactionResponseDetail;

public final class TransactionMapper {
    private TransactionMapper() {
    }

    public static TransactionResponseDetail toDetail(Transaction tx) {
        TransactionResponseDetail d = new TransactionResponseDetail();
        d.setTranNo(tx.getTranNo());
        d.setTranDate(tx.getTranDate());
        d.setSerialNo(tx.getSerialNo());
        d.setMerchantId(tx.getMerchantId());
        d.setCurrency(tx.getCurrency());
        d.setTraceNo(tx.getTraceNo());
        d.setEntryMode(tx.getEntryMode());
        d.setAmount(tx.getAmount());
        d.setTerminalId(tx.getTerminal().getTerminalId());
        d.setMaskedPan(tx.getMaskedPan());
        d.setExpiryDate(tx.getExpiryDate());
        d.setTrack2(tx.getTrack2());
        d.setTlvData(tx.getTlvData());
        d.setPinBlock(tx.getPinBlock());
        d.setPinKsn(tx.getPinKsn());
        d.setRemark(tx.getRemark());
        d.setSuccess(tx.isSuccess());
        d.setDescription(tx.getDescription());
        return d;
    }
}


