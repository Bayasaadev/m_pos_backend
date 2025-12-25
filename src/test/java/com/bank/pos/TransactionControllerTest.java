package com.bank.pos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    private void registerTerminal(String serialNo, String terminalId, String merchantId) throws Exception {
        String createJson = """
                {
                  "serial_no": "%s",
                  "terminal_id": "%s",
                  "merchant_id": "%s",
                  "merchant_name": "Merchant 100",
                  "type": "POS",
                  "zone_key": "ZK100",
                  "pin_key": "PK100",
                  "mac_key": "MK100"
                }
                """.formatted(serialNo, terminalId, merchantId);

        mockMvc.perform(post("/terminals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated());
    }

    @Test
    void process_transaction_success_and_mismatch_and_terminal_not_registered() throws Exception {
        registerTerminal("SN-100", "T-100", "M-100");

        String okTxn = """
                {
                  "serial_no": "SN-100",
                  "merchant_id": "M-100",
                  "currency": 104,
                  "trace_no": "TR-001",
                  "entry_mode": "CHIP",
                  "amount": 1000.00,
                  "terminal_id": "T-100",
                  "zone_key": "ZK100",
                  "pin_key": "PK100",
                  "mac_key": "MK100",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512",
                  "track2": "track2data",
                  "tlv_data": "tlv",
                  "remark": "OK"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(okTxn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tran_no").exists())
                .andExpect(jsonPath("$.description").value("Approved"))
                .andExpect(jsonPath("$.response_code").value("00"))
                .andExpect(jsonPath("$.approval_code").exists())
                .andExpect(jsonPath("$.issuer_response_tlv").exists());

        String badKeyTxn = """
                {
                  "serial_no": "SN-100",
                  "merchant_id": "M-100",
                  "currency": 104,
                  "trace_no": "TR-002",
                  "entry_mode": "CHIP",
                  "amount": 2000.00,
                  "terminal_id": "T-100",
                  "zone_key": "BAD",
                  "pin_key": "PK100",
                  "mac_key": "MK100",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512",
                  "remark": "BADKEY"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badKeyTxn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Key mismatch"))
                .andExpect(jsonPath("$.response_code").value("05"));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        String unknownTerminalTxn = """
                {
                  "serial_no": "SN-999",
                  "merchant_id": "M-999",
                  "currency": 104,
                  "trace_no": "TR-003",
                  "entry_mode": "CHIP",
                  "amount": 3000.00,
                  "terminal_id": "T-UNKNOWN",
                  "zone_key": "ZK",
                  "pin_key": "PK",
                  "mac_key": "MK",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512",
                  "remark": "UNKNOWN_TERMINAL"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unknownTerminalTxn))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());

        // Ensure we did NOT save a transaction when terminal isn't registered
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void transaction_validation_error_returns_400() throws Exception {
        String invalidTxn = """
                {
                  "serial_no": "SN-100",
                  "merchant_id": "M-100",
                  "currency": 104,
                  "trace_no": "TR-004",
                  "entry_mode": "CHIP",
                  "amount": 1000.00,
                  "zone_key": "ZK",
                  "pin_key": "PK",
                  "mac_key": "MK",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTxn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void amount_suffix_rules_return_expected_errors_and_persist_failed_txn() throws Exception {
        registerTerminal("SN-101", "T-101", "M-101");

        String insufficientFundsTxn = """
                {
                  "serial_no": "SN-101",
                  "merchant_id": "M-101",
                  "currency": 104,
                  "trace_no": "TR-051",
                  "entry_mode": "CHIP",
                  "amount": 10.51,
                  "terminal_id": "T-101",
                  "zone_key": "ZK100",
                  "pin_key": "PK100",
                  "mac_key": "MK100",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512",
                  "remark": "TEST"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(insufficientFundsTxn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Insufficient funds"))
                .andExpect(jsonPath("$.response_code").value("51"));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        String systemErrorTxn = """
                {
                  "serial_no": "SN-101",
                  "merchant_id": "M-101",
                  "currency": 104,
                  "trace_no": "TR-052",
                  "entry_mode": "CHIP",
                  "amount": 10.52,
                  "terminal_id": "T-101",
                  "zone_key": "ZK100",
                  "pin_key": "PK100",
                  "mac_key": "MK100",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512",
                  "remark": "TEST"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(systemErrorTxn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("System error"))
                .andExpect(jsonPath("$.response_code").value("91"));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void duplicate_trace_no_returns_error() throws Exception {
        registerTerminal("SN-102", "T-102", "M-102");

        String firstTxn = """
                {
                  "serial_no": "SN-102",
                  "merchant_id": "M-102",
                  "currency": 104,
                  "trace_no": "TR-DUP-001",
                  "entry_mode": "CHIP",
                  "amount": 1000.00,
                  "terminal_id": "T-102",
                  "zone_key": "ZK100",
                  "pin_key": "PK100",
                  "mac_key": "MK100",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512",
                  "remark": "FIRST"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstTxn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response_code").value("00"));

        // Try to submit the same trace_no again
        String duplicateTxn = """
                {
                  "serial_no": "SN-102",
                  "merchant_id": "M-102",
                  "currency": 104,
                  "trace_no": "TR-DUP-001",
                  "entry_mode": "CHIP",
                  "amount": 2000.00,
                  "terminal_id": "T-102",
                  "zone_key": "ZK100",
                  "pin_key": "PK100",
                  "mac_key": "MK100",
                  "masked_pan": "411111******1111",
                  "expiry_date": "2512",
                  "remark": "DUPLICATE"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateTxn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Duplicate trace_no"))
                .andExpect(jsonPath("$.response_code").value("14"));

        // Verify only one transaction was saved
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}


