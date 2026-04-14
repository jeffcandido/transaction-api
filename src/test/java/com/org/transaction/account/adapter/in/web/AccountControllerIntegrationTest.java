package com.org.transaction.account.adapter.in.web;

import com.org.transaction.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

class AccountControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM accounts");
    }

    @Test
    void shouldCreateAccountAndReturn201() {
        var result = mvc.post().uri("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"document_number":"12345678900"}
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson()
                .extractingPath("$.document_number").isEqualTo("12345678900");
        assertThat(result).bodyJson()
                .extractingPath("$.account_id").isNotNull();
    }

    @Test
    void shouldReturn409WhenDocumentNumberAlreadyExists() {
        String body = """
                {"document_number":"99988877766"}
                """;
        mvc.post().uri("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .exchange();

        var result = mvc.post().uri("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CONFLICT);
    }

    @Test
    void shouldReturn400WhenDocumentNumberIsBlank() {
        var result = mvc.post().uri("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"document_number":""}
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }
}
