package com.github.lucasmro.rinhabackend2024.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("account")
public class Account {

    @Id
    private Integer id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("limite")
    private Integer creditLimit;

    @JsonProperty("saldo")
    private Integer balance;
}
