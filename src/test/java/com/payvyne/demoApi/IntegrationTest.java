package com.payvyne.demoApi;


import com.payvyne.demoApi.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private MockMvc mockMvc;

    private static RestTemplate restTemplate = null;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/transactions");
    }

    @Test
    @Sql(statements = "INSERT INTO transaction (id, date, status, amount, currency, description) VALUES (1,'2021-02-02', 'Authorised', 135.1, 'EUR', 'Desc');",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO transaction (id, date, status, amount, currency, description) VALUES (2,'2021-02-06', 'Authorised', 15.51, 'EUR', 'Desc');",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO transaction (id, date, status, amount, currency, description) VALUES (3,'2021-02-22', 'Authorised', 5.165, 'EUR', 'Desc');",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM transaction",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void returnATransactionWithinDateRange() {
        baseUrl = baseUrl.concat("?dateFrom=2021-02-01&dateTo=2021-02-11");


        ResponseEntity<TransactionDTO[]> responseEntity = restTemplate.getForEntity(baseUrl, TransactionDTO[].class);
        TransactionDTO[] result = responseEntity.getBody();
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.length),
                () -> assertEquals(true, result[0].getAmount().compareTo(BigDecimal.valueOf(135.10)) == 0),
                () -> assertEquals(2, result[1].getId())
        );
    }
}