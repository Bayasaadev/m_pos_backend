package com.bank.pos.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTerminal_TerminalIdOrderByTranDateDesc(String terminalId);
    
    Optional<Transaction> findByTerminal_TerminalIdAndTraceNo(String terminalId, String traceNo);
}


