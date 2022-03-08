package com.payvyne.demoApi.service;

import com.payvyne.demoApi.dto.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    List<TransactionDTO> findAll(LocalDate dateFrom, LocalDate dateTo, String status, BigDecimal amountFrom, BigDecimal amountTo, String currency);

    TransactionDTO findById(Long id);

    TransactionDTO create(TransactionDTO transaction);

    TransactionDTO update(Long id, TransactionDTO transaction);

    void deleteById(Long id);

}
