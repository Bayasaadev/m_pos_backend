package com.bank.pos.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransactionResponseDetail {

    @JsonProperty("tran_no")
    private Long tranNo;

    @JsonProperty("tran_date")
    private OffsetDateTime tranDate;

    @JsonProperty("serial_no")
    private String serialNo;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("currency")
    private Integer currency;

    @JsonProperty("trace_no")
    private String traceNo;

    @JsonProperty("entry_mode")
    private String entryMode;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("terminal_id")
    private String terminalId;

    @JsonProperty("masked_pan")
    private String maskedPan;

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

    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("description")
    private String description;

    public Long getTranNo() {
        return tranNo;
    }

    public void setTranNo(Long tranNo) {
        this.tranNo = tranNo;
    }

    public OffsetDateTime getTranDate() {
        return tranDate;
    }

    public void setTranDate(OffsetDateTime tranDate) {
        this.tranDate = tranDate;
    }

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

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


