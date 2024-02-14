package com.github.lucasmro.rinhabackend2024.statement;

import com.github.lucasmro.rinhabackend2024.account.Account;
import com.github.lucasmro.rinhabackend2024.account.AccountRepository;
import com.github.lucasmro.rinhabackend2024.transaction.Transaction;
import com.github.lucasmro.rinhabackend2024.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatementService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public StatementResponse getStatement(final int accountId) {
        final Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            return new StatementResponse(
                    null,
                    null,
                    "Cliente não encontrado"
            );
        }

        final Account account = accountOptional.get();

        final StatementResponse.StatementBalanceResponse balance = new StatementResponse.StatementBalanceResponse(
                account.getBalance(),
                LocalDateTime.now(),
                account.getCreditLimit()
        );

        final List<StatementResponse.StatementTransactionResponse> transactions = transactionRepository.findTop10ByAccountIdOrderByIdDesc(accountId)
                .stream()
                .map(StatementService::getStatementTransactionResponse)
                .toList();

        return new StatementResponse(balance, transactions, null);
    }

    private static StatementResponse.StatementTransactionResponse getStatementTransactionResponse(final Transaction transaction) {
        return new StatementResponse.StatementTransactionResponse(
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getCreatedAt());
    }
}
