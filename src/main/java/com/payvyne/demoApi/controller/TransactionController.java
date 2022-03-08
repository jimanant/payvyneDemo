package com.payvyne.demoApi.controller;


import com.payvyne.demoApi.dto.TransactionDTO;
import com.payvyne.demoApi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController extends ControllerExceptionHandler {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<TransactionDTO> findAll(
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) BigDecimal amountFrom,
            @RequestParam(required = false) BigDecimal amountTo,
            @RequestParam(required = false) String currency

    ) {
        return service.findAll(dateFrom, dateTo, status, amountFrom, amountTo, currency);
    }

    @GetMapping(value = "/{id}")
    public TransactionDTO findById(@PathVariable("id") Long id) {

        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO create(@RequestBody TransactionDTO transaction) {

        return service.create(transaction);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO update(@PathVariable("id") Long id, @RequestBody TransactionDTO transaction) {

        return service.update(id, transaction);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {

        service.deleteById(id);
    }

}
