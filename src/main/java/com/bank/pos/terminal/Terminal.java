package com.bank.pos.terminal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "terminals",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_terminals_terminal_id", columnNames = {"terminal_id"})
        }
)
public class Terminal {

    @Id
    @Column(name = "serial_no", nullable = false, updatable = false, length = 64)
    private String serialNo;

    @Column(name = "terminal_id", nullable = false, length = 64, unique = true)
    private String terminalId;

    @Column(name = "merchant_id", nullable = false, length = 64)
    private String merchantId;

    @Column(name = "merchant_name", nullable = false, length = 128)
    private String merchantName;

    @Column(name = "type", nullable = false, length = 32)
    private String type;

    @Column(name = "zone_key", nullable = false, length = 128)
    private String zoneKey;

    @Column(name = "pin_key", nullable = false, length = 128)
    private String pinKey;

    @Column(name = "mac_key", nullable = false, length = 128)
    private String macKey;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


