package com.github.lucasmro.rinhabackend2024.account;

import com.github.lucasmro.rinhabackend2024.statement.StatementResponse;
import com.github.lucasmro.rinhabackend2024.statement.StatementService;
import com.github.lucasmro.rinhabackend2024.transaction.TransactionRequest;
import com.github.lucasmro.rinhabackend2024.transaction.TransactionResponse;
import com.github.lucasmro.rinhabackend2024.transaction.TransactionResult;
import com.github.lucasmro.rinhabackend2024.transaction.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class AccountController {

    private final StatementService statementService;
    private final TransactionService transactionService;

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<TransactionResponse> criarTransacao(
            @Min(value = 1, message = "O 'id' do cliente é obrigatório e deve ser um número positivo")
            @PathVariable(name = "id") final Integer clienteId,
            @Valid @RequestBody final TransactionRequest requisicao) {

        final TransactionResult transactionResult = transactionService.createTransaction(clienteId, requisicao);

        if (transactionResult.accountNotFound()) {
            final TransactionResponse transactionResponse = new TransactionResponse(
                    transactionResult.creditLimit(),
                    transactionResult.balance(),
                    "Cliente não encontrado"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionResponse);
        }

        if (transactionResult.insufficientBalance()) {
            final TransactionResponse transactionResponse = new TransactionResponse(
                    transactionResult.creditLimit(),
                    transactionResult.balance(),
                    "Saldo insuficiente"
            );
            return ResponseEntity.unprocessableEntity().body(transactionResponse);
        }

        final TransactionResponse transactionResponse = new TransactionResponse(
                transactionResult.creditLimit(),
                transactionResult.balance(),
                null
        );

        return ResponseEntity.ok(transactionResponse);
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<StatementResponse> obterExtrato(
            @Min(value = 1, message = "O 'id' do cliente é obrigatório e deve ser um número positivo")
            @PathVariable(name = "id") final Integer clienteId) {

        final StatementResponse statementResponse = statementService.getStatement(clienteId);

        if (statementResponse.accountNotFound()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statementResponse);
        }

        return ResponseEntity.ok(statementResponse);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Map<String, String>> globalExceptionHandler(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Map.of("erro", ex.getMessage()));
    }
}
