package com.bank.pos.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse {

    @JsonProperty("tran_no")
    private Long tranNo;

    @JsonProperty("description")
    private String description;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("approval_code")
    private String approvalCode;

    @JsonProperty("issuer_response_tlv")
    private String issuerResponseTlv;

    public TransactionResponse() {
    }

    public TransactionResponse(Long tranNo, String description, String responseCode) {
        this.tranNo = tranNo;
        this.description = description;
        this.responseCode = responseCode;
    }

    public TransactionResponse(Long tranNo, String description, String responseCode, String approvalCode, String issuerResponseTlv) {
        this.tranNo = tranNo;
        this.description = description;
        this.responseCode = responseCode;
        this.approvalCode = approvalCode;
        this.issuerResponseTlv = issuerResponseTlv;
    }

    public Long getTranNo() {
        return tranNo;
    }

    public void setTranNo(Long tranNo) {
        this.tranNo = tranNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getIssuerResponseTlv() {
        return issuerResponseTlv;
    }

    public void setIssuerResponseTlv(String issuerResponseTlv) {
        this.issuerResponseTlv = issuerResponseTlv;
    }
}


