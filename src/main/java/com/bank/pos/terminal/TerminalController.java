package com.bank.pos.terminal;

import com.bank.pos.terminal.dto.TerminalCreateRequest;
import com.bank.pos.terminal.dto.TerminalResponse;
import com.bank.pos.terminal.dto.TerminalUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/terminals")
public class TerminalController {

    private final TerminalService terminalService;

    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @GetMapping
    public List<TerminalResponse> getAll() {
        return terminalService.getAll();
    }

    @GetMapping("/{serial_no}")
    public TerminalResponse getBySerialNo(@PathVariable("serial_no") String serialNo) {
        return terminalService.getBySerialNo(serialNo);
    }

    @PostMapping
    public ResponseEntity<TerminalResponse> register(@Valid @RequestBody TerminalCreateRequest req) {
        TerminalResponse created = terminalService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{serial_no}")
    public TerminalResponse update(
            @PathVariable("serial_no") String serialNo,
            @RequestBody TerminalUpdateRequest req
    ) {
        return terminalService.update(serialNo, req);
    }
}


