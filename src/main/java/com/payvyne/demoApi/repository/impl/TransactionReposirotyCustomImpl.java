package com.payvyne.demoApi.repository.impl;


import com.payvyne.demoApi.model.Transaction;
import com.payvyne.demoApi.repository.TransactionReposirotyCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
class TransactionReposirotyCustomImpl implements TransactionReposirotyCustom {

    @Autowired
    EntityManager em;

    @Override
    public List<Transaction> findByDateStatusAmountCurrency(LocalDate dateFrom, LocalDate dateTo, String status, BigDecimal amountFrom, BigDecimal amountTo, String currency) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);

        Root<Transaction> transaction = cq.from(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();
        if (dateFrom != null && dateTo != null) {
            predicates.add(
                    cb.and(
                            cb.greaterThanOrEqualTo(transaction.get("date"), dateFrom),
                            cb.lessThanOrEqualTo(transaction.get("date"), dateTo)
                    )
            );
        }

        if (status != null) {
            predicates.add(cb.equal(transaction.get("status"), status));
        }

        if (amountFrom != null && amountTo != null) {
            predicates.add(
                    cb.and(
                            cb.greaterThanOrEqualTo(transaction.get("amount"), amountFrom),
                            cb.lessThanOrEqualTo(transaction.get("amount"), amountTo)
                    )
            );
        }

        if (currency != null) {
            predicates.add(cb.equal(transaction.get("currency"), currency));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return em.createQuery(cq).getResultList();
    }

}