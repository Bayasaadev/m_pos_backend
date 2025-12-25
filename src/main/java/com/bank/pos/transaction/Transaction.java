package com.bank.pos.transaction;

import com.bank.pos.terminal.Terminal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tran_no", nullable = false)
    private Long tranNo;

    @Column(name = "tran_date", nullable = false)
    private OffsetDateTime tranDate;

    @Column(name = "serial_no", nullable = false, length = 64)
    private String serialNo;

    @Column(name = "merchant_id", nullable = false, length = 64)
    private String merchantId;

    @Column(name = "currency", nullable = false)
    private Integer currency;

    @Column(name = "trace_no", nullable = false, length = 64)
    private String traceNo;

    @Column(name = "entry_mode", nullable = false, length = 32)
    private String entryMode;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "masked_pan", nullable = false, length = 32)
    private String maskedPan;

    @Column(name = "expiry_date", nullable = false, length = 8)
    private String expiryDate;

    @Column(name = "track2", length = 512)
    private String track2;

    @Column(name = "tlv_data", length = 4000)
    private String tlvData;

    @Column(name = "pin_block", length = 32)
    private String pinBlock;

    @Column(name = "pin_ksn", length = 32)
    private String pinKsn;

    @Column(name = "remark", length = 512)
    private String remark;

    @Column(name = "is_success", nullable = false)
    private boolean isSuccess;

    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terminal_serial_no", nullable = false)
    private Terminal terminal;

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

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
}


