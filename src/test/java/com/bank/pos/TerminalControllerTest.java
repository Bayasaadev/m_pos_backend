package com.bank.pos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TerminalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void register_get_update_terminal() throws Exception {
        String serialNo = "SN-001";

        String createJson = """
                {
                  "serial_no": "SN-001",
                  "terminal_id": "T-001",
                  "merchant_id": "M-001",
                  "merchant_name": "Demo Merchant",
                  "type": "MPOS",
                  "zone_key": "ZK",
                  "pin_key": "PK",
                  "mac_key": "MK"
                }
                """;

        mockMvc.perform(post("/terminals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serial_no").value("SN-001"))
                .andExpect(jsonPath("$.terminal_id").value("T-001"))
                .andExpect(jsonPath("$.created_at").exists());

        mockMvc.perform(get("/terminals/{serial_no}", serialNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchant_name").value("Demo Merchant"));

        String updateJson = """
                {
                  "merchant_name": "Demo Merchant Updated"
                }
                """;

        mockMvc.perform(put("/terminals/{serial_no}", serialNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchant_name").value("Demo Merchant Updated"));
    }
}


