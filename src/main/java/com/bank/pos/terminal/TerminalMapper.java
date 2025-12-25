package com.bank.pos.terminal;

import com.bank.pos.terminal.dto.TerminalCreateRequest;
import com.bank.pos.terminal.dto.TerminalResponse;

public final class TerminalMapper {
    private TerminalMapper() {
    }

    public static Terminal toEntity(TerminalCreateRequest req) {
        Terminal t = new Terminal();
        t.setSerialNo(req.getSerialNo());
        t.setTerminalId(req.getTerminalId());
        t.setMerchantId(req.getMerchantId());
        t.setMerchantName(req.getMerchantName());
        t.setType(req.getType());
        t.setZoneKey(req.getZoneKey());
        t.setPinKey(req.getPinKey());
        t.setMacKey(req.getMacKey());
        return t;
    }

    public static TerminalResponse toResponse(Terminal t) {
        TerminalResponse res = new TerminalResponse();
        res.setSerialNo(t.getSerialNo());
        res.setTerminalId(t.getTerminalId());
        res.setMerchantId(t.getMerchantId());
        res.setMerchantName(t.getMerchantName());
        res.setType(t.getType());
        res.setZoneKey(t.getZoneKey());
        res.setPinKey(t.getPinKey());
        res.setMacKey(t.getMacKey());
        res.setCreatedAt(t.getCreatedAt());
        return res;
    }
}


