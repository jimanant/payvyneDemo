package com.payvyne.demoApi.service.mapper;

import com.payvyne.demoApi.dto.TransactionDTO;
import com.payvyne.demoApi.model.Transaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Named("toTransactionDTO")
    TransactionDTO toTransactionDTO(Transaction source);

    @IterableMapping(qualifiedByName = "toTransactionDTO")
    List<TransactionDTO> toTransactionDTO(List<Transaction> source);

    Transaction toTransaction(TransactionDTO destination);

}

