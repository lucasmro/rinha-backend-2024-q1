package com.github.lucasmro.rinhabackend2024.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionResult createTransaction(final int accountId, final TransactionRequest request) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM process_transaction(?, ?, ?)",
                new Object[]{ accountId, request.amount(), request.description() },
                (rs, rowNum) -> new TransactionResult(
                        rs.getInt("account_balance"),
                        rs.getInt("account_credit_limit"),
                        rs.getInt("error_code")
                )
        );
    }
}
