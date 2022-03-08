package com.payvyne.demoApi.service.impl;

import com.payvyne.demoApi.dto.TransactionDTO;
import com.payvyne.demoApi.exception.ValidationExeption;
import com.payvyne.demoApi.model.Transaction;
import com.payvyne.demoApi.repository.TransactionReposiroty;
import com.payvyne.demoApi.service.TransactionService;
import com.payvyne.demoApi.service.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionReposiroty repository;
    private final TransactionMapper mapper;

    public TransactionServiceImpl(TransactionReposiroty repository, TransactionMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TransactionDTO> findAll(LocalDate dateFrom, LocalDate dateTo, String status, BigDecimal amountFrom, BigDecimal amountTo, String currency) {

        validateRequest(dateFrom, dateTo, amountFrom, amountTo, currency);
        return mapper.toTransactionDTO(repository.findByDateStatusAmountCurrency(dateFrom, dateTo, status, amountFrom, amountTo, currency));
    }

    @Override
    public TransactionDTO findById(Long id) {

        Optional<Transaction> result = repository.findById(id);
        if (result.isEmpty()) throw new NoSuchElementException("Transaction not found");
        return mapper.toTransactionDTO(result.get());
    }

    @Override
    public TransactionDTO create(TransactionDTO transaction) {

        return mapper.toTransactionDTO(repository.save(mapper.toTransaction(transaction)));
    }

    @Override
    public TransactionDTO update(Long id, TransactionDTO transaction) {

        if (id != transaction.getId()) throw new ValidationExeption("Path id does not match body id.");
        findById(id);
        return mapper.toTransactionDTO(repository.save(mapper.toTransaction(transaction)));
    }

    @Override
    public void deleteById(Long id) {

        repository.deleteById(id);
    }

    void validateRequest(LocalDate dateFrom, LocalDate dateTo, BigDecimal amountFrom, BigDecimal amountTo, String currency) {

        if (dateFrom != null) {
            if (dateTo != null) {
                if (dateFrom.isAfter(dateTo)) throw new ValidationExeption("dateFrom should not be after dateTo");
            }
            if (dateTo == null) throw new ValidationExeption("dateTo value is missing");
        }
        if (dateFrom == null && dateTo != null) throw new ValidationExeption("dateFrom value is missing");

        if (amountFrom != null) {
            if (amountTo != null) {
                if (amountFrom.compareTo(amountTo) > 0)
                    throw new ValidationExeption("amountFrom should not be greater than amountTo");
            }
            if (amountTo == null) throw new ValidationExeption("amountTo value is missing");
        }
        if (amountFrom == null && amountTo != null) throw new ValidationExeption("amountFrom value is missing");

        if (currency!= null && currency.length() != 3) throw new ValidationExeption("currency should be 3 characters long");
    }
}
