package com.github.lucasmro.rinhabackend2024.transaction;

public record TransactionResult(Integer balance, Integer creditLimit, Integer errorCode) {

    private static final Integer ACCOUNT_NOT_FOUND = -1;
    private static final Integer INSUFFICIENT_BALANCE = -2;

    public boolean accountNotFound() {
        return ACCOUNT_NOT_FOUND.equals(errorCode);
    }

    public boolean insufficientBalance() {
        return INSUFFICIENT_BALANCE.equals(errorCode);
    }

    public boolean ok() {
        return null == errorCode;
    }
}
