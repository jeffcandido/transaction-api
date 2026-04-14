package com.org.transaction.transaction.adapter.in.web;

import com.org.transaction.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long accountId;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM transactions");
        jdbcTemplate.execute("DELETE FROM accounts");

        jdbcTemplate.update("INSERT INTO accounts (document_number) VALUES ('12345678900')");
        accountId = jdbcTemplate.queryForObject(
                "SELECT account_id FROM accounts WHERE document_number = '12345678900'",
                Long.class
        );
    }

    @Test
    void shouldCreateTransactionAndReturn201() {
        var result = mvc.post().uri("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "account_id": %d,
                          "operation_type_id": 1,
                          "amount": 100.00
                        }
                        """.formatted(accountId))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.transaction_id").isNotNull();
        assertThat(result).bodyJson().extractingPath("$.account_id").isEqualTo(accountId.intValue());
        assertThat(result).bodyJson().extractingPath("$.operation_type_id").isEqualTo(1);
        assertThat(result).bodyJson().extractingPath("$.amount").isEqualTo(-100.0);
        assertThat(result).bodyJson().extractingPath("$.event_date").isNotNull();
    }

    @Test
    void shouldCreatePaymentTransactionWithPositiveAmount() {
        var result = mvc.post().uri("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "account_id": %d,
                          "operation_type_id": 4,
                          "amount": 250.00
                        }
                        """.formatted(accountId))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.amount").isEqualTo(250.0);
    }

    @Test
    void shouldReturn404WhenAccountDoesNotExist() {
        var result = mvc.post().uri("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "account_id": 99999,
                          "operation_type_id": 1,
                          "amount": 50.00
                        }
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturn404WhenOperationTypeDoesNotExist() {
        var result = mvc.post().uri("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "account_id": %d,
                          "operation_type_id": 99999,
                          "amount": 50.00
                        }
                        """.formatted(accountId))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturn400WhenAmountIsNegative() {
        var result = mvc.post().uri("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "account_id": %d,
                          "operation_type_id": 1,
                          "amount": -50.00
                        }
                        """.formatted(accountId))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturn400WhenRequiredFieldsAreMissing() {
        var result = mvc.post().uri("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }
}
