package com.github.lucasmro.rinhabackend2024.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionRequest(
        @NotNull(message = "Valor é obrigatório")
        @Min(value = 1, message = "Valor deve ser um número inteiro positivo")
        @JsonProperty("valor")
        Integer amount,
        @NotBlank(message = "TIpo é obrigatório type")
        @Pattern(regexp = "^[cd]$", message = "Tipo deve ser 'c' ou 'd'")
        @JsonProperty("tipo")
        String type,
        @NotBlank(message = "Descrição é obrigatório")
        @Size(max = 10, message = "Descrição deve ter no máximo 10 caracteres")
        @JsonProperty("descricao")
        String description) {

    public Integer amount() {
        if ("d".equalsIgnoreCase(type)) {
            return -Math.abs(amount);
        }
        return Math.abs(amount);
    }
}
