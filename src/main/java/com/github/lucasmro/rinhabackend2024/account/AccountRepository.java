package com.github.lucasmro.rinhabackend2024.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Transactional(readOnly = true)
    Optional<Account> findById(Integer id);
}
