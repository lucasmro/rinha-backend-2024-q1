package com.github.lucasmro.rinhabackend2024.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("account_transaction")
public class Transaction {

    @Id
    private Integer id;

    @JsonProperty("clientId")
    private Integer accountId;

    @JsonProperty("valor")
    private Integer amount;

    @JsonProperty("tipo")
    private String type;

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("realizadaEm")
    private LocalDateTime createdAt;
}
