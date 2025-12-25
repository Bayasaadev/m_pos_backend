package com.bank.pos.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class TerminalCreateRequest {

    @NotBlank
    @JsonProperty("serial_no")
    private String serialNo;

    @NotBlank
    @JsonProperty("terminal_id")
    private String terminalId;

    @NotBlank
    @JsonProperty("merchant_id")
    private String merchantId;

    @NotBlank
    @JsonProperty("merchant_name")
    private String merchantName;

    @NotBlank
    @JsonProperty("type")
    private String type;

    @NotBlank
    @JsonProperty("zone_key")
    private String zoneKey;

    @NotBlank
    @JsonProperty("pin_key")
    private String pinKey;

    @NotBlank
    @JsonProperty("mac_key")
    private String macKey;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZoneKey() {
        return zoneKey;
    }

    public void setZoneKey(String zoneKey) {
        this.zoneKey = zoneKey;
    }

    public String getPinKey() {
        return pinKey;
    }

    public void setPinKey(String pinKey) {
        this.pinKey = pinKey;
    }

    public String getMacKey() {
        return macKey;
    }

    public void setMacKey(String macKey) {
        this.macKey = macKey;
    }
}


