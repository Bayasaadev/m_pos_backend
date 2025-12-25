package com.bank.pos.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransactionRequest {

    @NotBlank
    @JsonProperty("serial_no")
    private String serialNo;

    @NotBlank
    @JsonProperty("merchant_id")
    private String merchantId;

    @NotNull
    @JsonProperty("currency")
    private Integer currency;

    @NotBlank
    @JsonProperty("trace_no")
    private String traceNo;

    @NotBlank
    @JsonProperty("entry_mode")
    private String entryMode;

    @NotNull
    @Positive
    @JsonProperty("amount")
    private BigDecimal amount;

    @NotBlank
    @JsonProperty("terminal_id")
    private String terminalId;

    @NotBlank
    @JsonProperty("zone_key")
    private String zoneKey;

    @NotBlank
    @JsonProperty("pin_key")
    private String pinKey;

    @NotBlank
    @JsonProperty("mac_key")
    private String macKey;

    @NotBlank
    @JsonProperty("masked_pan")
    private String maskedPan;

    @NotBlank
    @JsonProperty("expiry_date")
    private String expiryDate;

    @JsonProperty("track2")
    private String track2;

    @JsonProperty("tlv_data")
    private String tlvData;

    @JsonProperty("pin_block")
    private String pinBlock;

    @JsonProperty("pin_ksn")
    private String pinKsn;

    @JsonProperty("remark")
    private String remark;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getEntryMode() {
        return entryMode;
    }

    public void setEntryMode(String entryMode) {
        this.entryMode = entryMode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getTlvData() {
        return tlvData;
    }

    public void setTlvData(String tlvData) {
        this.tlvData = tlvData;
    }

    public String getPinBlock() {
        return pinBlock;
    }

    public void setPinBlock(String pinBlock) {
        this.pinBlock = pinBlock;
    }

    public String getPinKsn() {
        return pinKsn;
    }

    public void setPinKsn(String pinKsn) {
        this.pinKsn = pinKsn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}


