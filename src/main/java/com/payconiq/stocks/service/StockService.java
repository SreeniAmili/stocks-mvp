package com.payconiq.stocks.service;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.model.StockDTO;

import java.util.List;

/**
 * Service interface for Stock entity.
 */
public interface StockService {
    /**
     * Get all stocks.
     *
     * @param page page number
     * @param size page size
     * @return list of stocks
     */
    List<StockDTO> getAllStocks(int page, int size);
    /**
     * Get stock by id.
     *
     * @param id stock id
     * @return stock
     */
    StockDTO getStockById(Long id);
    /**
     * Create stock.
     *
     * @param stockDTO stock data
     * @return created stock
     */
    StockDTO createStock(StockDTO stockDTO);
    /**
     * Update stock price.
     *
     * @param id stock id
     * @param stockDTO stock data
     * @return updated stock
     */
    StockDTO updateStockPrice(Long id, StockDTO stockDTO);
    /**
     * Delete stock.
     *
     * @param id stock id
     */
    void deleteStock(Long id);
}
