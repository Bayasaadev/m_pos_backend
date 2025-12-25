package com.bank.pos.transaction;

import com.bank.pos.transaction.dto.TransactionRequest;
import com.bank.pos.transaction.dto.TransactionResponse;
import com.bank.pos.transaction.dto.TransactionResponseDetail;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionResponseDetail> getAll() {
        return transactionService.getAll();
    }

    @PostMapping
    public TransactionResponse process(@Valid @RequestBody TransactionRequest req) {
        return transactionService.process(req);
    }

    @GetMapping("/{terminal_id}")
    public List<TransactionResponseDetail> getByTerminalId(@PathVariable("terminal_id") String terminalId) {
        return transactionService.getByTerminalId(terminalId);
    }
}


