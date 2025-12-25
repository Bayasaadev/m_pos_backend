package com.bank.pos.terminal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TerminalRepository extends JpaRepository<Terminal, String> {
    Optional<Terminal> findByTerminalId(String terminalId);
    boolean existsByTerminalId(String terminalId);
}


