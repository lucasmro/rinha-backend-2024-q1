package com.github.lucasmro.rinhabackend2024.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionResponse(
        @JsonProperty("limite")
        int creditLimit,
        @JsonProperty("saldo")
        int balance,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("mensagemErro")
        String errorMessage) {
}
