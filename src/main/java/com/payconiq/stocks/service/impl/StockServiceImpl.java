package com.payconiq.stocks.service.impl;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.model.StockDTO;
import com.payconiq.stocks.repository.StockRepository;
import com.payconiq.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Stock service implementation.
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    /**
     * Get all stocks.
     *
     * @param page page number
     * @param size page size
     * @return list of stocks
     */
    @Override
    public List<StockDTO> getAllStocks(int page, int size) {
        return stockRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(stock -> new StockDTO(stock.getId(), stock.getName(), stock.getCurrentPrice(), stock.getLastUpdate()))
                .collect(Collectors.toList());
    }

    /**
     * Get stock by id.
     *
     * @param id stock id
     * @return stock
     */
    @Override
    public StockDTO getStockById(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        return new StockDTO(stock.getId(), stock.getName(), stock.getCurrentPrice(), stock.getLastUpdate());
    }

    /**
     * Create stock.
     *
     * @param stockDTO stock data
     * @return created stock
     */
    @Override
    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setName(stockDTO.name());
        stock.setCurrentPrice(stockDTO.currentPrice());
        stock.setLastUpdate(stockDTO.lastUpdate());

        Stock savedStock = stockRepository.save(stock);
        return new StockDTO(savedStock.getId(), savedStock.getName(), savedStock.getCurrentPrice(), savedStock.getLastUpdate());
    }

    /**
     * Update stock price.
     *
     * @param id stock id
     * @param stockDTO stock data
     * @return updated stock
     */
    @Override
    public StockDTO updateStockPrice(Long id, StockDTO stockDTO) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setCurrentPrice(stockDTO.currentPrice());
        stock.setLastUpdate(stockDTO.lastUpdate());

        Stock updatedStock = stockRepository.save(stock);
        return new StockDTO(updatedStock.getId(), updatedStock.getName(), updatedStock.getCurrentPrice(), updatedStock.getLastUpdate());
    }

    /**
     * Delete stock.
     *
     * @param id stock id
     */
    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}
