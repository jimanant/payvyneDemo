package com.payvyne.demoApi.repository;

import com.payvyne.demoApi.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionReposirotyCustom {

    List<Transaction> findByDateStatusAmountCurrency(LocalDate dateFrom, LocalDate dateTo, String status, BigDecimal amountFrom, BigDecimal amountTo, String currency);
}
