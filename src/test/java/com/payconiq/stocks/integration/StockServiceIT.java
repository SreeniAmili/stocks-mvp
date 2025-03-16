package com.payconiq.stocks.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stocks.model.StockDTO;
import com.payconiq.stocks.service.StockService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;
import java.time.Instant;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
@Tag("integration")
public class StockServiceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockService stockService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test_stocks_db")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void startContainer() {
        postgres.start();
    }

    @AfterAll
    static void stopContainer() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

    }

    @Test
    void testCreateStock() throws Exception {
        StockDTO stockDTO = new StockDTO(null, "Tesla", BigDecimal.valueOf(900.50), Instant.now());

        mockMvc.perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin", "admin123"))
                        .content(objectMapper.writeValueAsString(stockDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tesla"))
                .andExpect(jsonPath("$.currentPrice").value(900.50));
    }

    @Test
    void testGetStockById() throws Exception {
        StockDTO stockDTO = new StockDTO(null, "Apple", BigDecimal.valueOf(150.00), Instant.now());
        StockDTO savedStock = stockService.createStock(stockDTO);

        mockMvc.perform(get("/api/stocks/" + savedStock.id()).with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    void testUpdateStockPrice() throws Exception {
        StockDTO stockDTO = new StockDTO(null, "Google", BigDecimal.valueOf(1000.00), Instant.now());
        StockDTO savedStock = stockService.createStock(stockDTO);

        mockMvc.perform(patch("/api/stocks/" + savedStock.id())
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPrice\": 1200.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPrice").value(1200.00));
    }

    @Test
    void testDeleteStock() throws Exception {
        StockDTO stockDTO = new StockDTO(null, "Amazon", BigDecimal.valueOf(3200.00), Instant.now());
        StockDTO savedStock = stockService.createStock(stockDTO);

        mockMvc.perform(delete("/api/stocks/" + savedStock.id()).with(httpBasic("admin", "admin123")))
                .andExpect(status().isNoContent());
    }
}
