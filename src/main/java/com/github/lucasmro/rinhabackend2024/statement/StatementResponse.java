package com.github.lucasmro.rinhabackend2024.statement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record StatementResponse(
        @JsonProperty("saldo")
        StatementBalanceResponse balance,
        @JsonProperty("ultimas_transacoes")
        List<StatementTransactionResponse> lastTransactions,
        @JsonProperty("mensagemErro")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String errorMessage) {

    public record StatementBalanceResponse(
            int total,
            @JsonProperty("data_extrato")
            @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'")
            LocalDateTime dateTime,
            @JsonProperty("limite")
            int creditLimit) {
    }

    public record StatementTransactionResponse(
            @JsonProperty("valor")
            int amount,
            @JsonProperty("tipo")
            String type,
            @JsonProperty("descricao")
            String description,
            @JsonProperty("realizada_em")
            @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'")
            LocalDateTime createdAt) {
    }

    public boolean accountNotFound() {
        return null == balance && null == lastTransactions;
    }
}
