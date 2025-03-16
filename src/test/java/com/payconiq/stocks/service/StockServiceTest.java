package com.payconiq.stocks.service;

import com.payconiq.stocks.model.StockDTO;
import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.repository.StockRepository;
import com.payconiq.stocks.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for StockService.
 */
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Stock stock;
    private StockDTO stockDTO;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        stock = new Stock();
        stock.setId(1L);
        stock.setName("Apple Inc.");
        stock.setCurrentPrice(BigDecimal.valueOf(150.00));
        stock.setLastUpdate(Instant.now());

        stockDTO = new StockDTO(stock.getId(), stock.getName(), stock.getCurrentPrice(), stock.getLastUpdate());
    }

    /**
     * Test method for {@link StockServiceImpl#getAllStocks(int, int)}.
     */
    @Test
    void testGetAllStocks() {
        Page<Stock> stockPage = new PageImpl<>(List.of(stock));
        when(stockRepository.findAll(any(PageRequest.class))).thenReturn(stockPage);

        List<StockDTO> result = stockService.getAllStocks(0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(stock.getName(), result.get(0).name());
    }

    /**
     * Test method for {@link StockServiceImpl#getStockById(Long)}.
     */
    @Test
    void testGetStockById() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        StockDTO result = stockService.getStockById(1L);

        assertNotNull(result);
        assertEquals(stock.getName(), result.name());
    }

    /**
     * Test method for {@link StockServiceImpl#createStock(StockDTO)}.
     */
    @Test
    void testCreateStock() {
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        StockDTO result = stockService.createStock(stockDTO);

        assertNotNull(result);

        assertEquals(stock.getName(), result.name());
    }

    /**
     * Test method for {@link StockServiceImpl#updateStockPrice(Long, StockDTO)}.
     */
    @Test
    void testUpdateStockPrice() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        StockDTO updatedStock = new StockDTO(stock.getId(), stock.getName(), BigDecimal.valueOf(200.00), Instant.now());
        StockDTO result = stockService.updateStockPrice(1L, updatedStock);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200.00), result.currentPrice());
    }

    /**
     * Test method for {@link StockServiceImpl#deleteStock(Long)}.
     */
    @Test
    void testDeleteStock() {
        when(stockRepository.existsById(1L)).thenReturn(true);
        doNothing().when(stockRepository).deleteById(1L);

        assertDoesNotThrow(() -> stockService.deleteStock(1L));
        verify(stockRepository, times(1)).deleteById(1L);
    }
}
