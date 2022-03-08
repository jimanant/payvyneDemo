package com.payvyne.demoApi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {

    private Long id;

    private LocalDate date;

    private String status;

    private BigDecimal amount;

    private String currency;

    private String description;

}
