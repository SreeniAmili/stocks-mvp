package com.payconiq.stocks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.payconiq.stocks.model.StockDTO;
import com.payconiq.stocks.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for StockController.
 */
@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    private StockDTO stockDTO;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();

        stockDTO = new StockDTO(1L, "Apple Inc.", BigDecimal.valueOf(150.00), Instant.now());
    }

    /**
     * Test method for {@link StockController#getAllStocks(int, int)}.
     */
    @Test
    void testGetAllStocks() throws Exception {
        when(stockService.getAllStocks(0, 10)).thenReturn(List.of(stockDTO));

        mockMvc.perform(get("/api/stocks")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Apple Inc."));
    }

    /**
     * Test method for {@link StockController#getStockById(Long)}.
     */
    @Test
    void testGetStockById() throws Exception {
        when(stockService.getStockById(1L)).thenReturn(stockDTO);

        mockMvc.perform(get("/api/stocks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple Inc."));
    }

    /**
     * Test method for {@link StockController#createStock(StockDTO)}.
     */
    @Test
    void testCreateStock() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // ✅ Fix Instant serialization

        when(stockService.createStock(any(StockDTO.class))).thenReturn(stockDTO);

        mockMvc.perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockDTO)))  // ✅ Use updated ObjectMapper
                .andExpect(status().isCreated());
    }

    /**
     * Test method for {@link StockController#updateStockPrice(Long, StockDTO)}.
     */
    @Test
    void testUpdateStockPrice() throws Exception {
        StockDTO updatedStock = new StockDTO(1L, "Apple Inc.", BigDecimal.valueOf(200.00), Instant.now());
        when(stockService.updateStockPrice(eq(1L), any(StockDTO.class))).thenReturn(updatedStock);

        mockMvc.perform(patch("/api/stocks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"currentPrice\": 200.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPrice").value(200.00));
    }

    /**
     * Test method for {@link StockController#deleteStock(Long)}.
     */
    @Test
    void testDeleteStock() throws Exception {
        mockMvc.perform(delete("/api/stocks/{id}", 1L))
                .andExpect(status().isNoContent());  // ✅ Expect 204 No Content
    }
}
