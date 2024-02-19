package com.example.payment.dao;

import com.example.payment.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber (String debitAccountNumber);
}
