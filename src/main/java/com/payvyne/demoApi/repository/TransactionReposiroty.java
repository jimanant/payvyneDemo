package com.payvyne.demoApi.repository;

import com.payvyne.demoApi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionReposiroty extends JpaRepository<Transaction, Long>, TransactionReposirotyCustom {

}
