package com.github.lucasmro.rinhabackend2024.transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    List<Transaction> findTop10ByAccountIdOrderByIdDesc(Integer accountId);
}
