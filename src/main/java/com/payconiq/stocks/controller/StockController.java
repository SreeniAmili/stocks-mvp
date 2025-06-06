package com.payconiq.stocks.controller;

import com.payconiq.stocks.model.StockDTO;
import com.payconiq.stocks.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Stock controller.
 */
@Tag(name = "Stock Management", description = "Stock Management APIs")
@RestController
@RequestMapping("/api/stocks")
public class StockController {


    /**
     * Stock service.
     */
    @Autowired
    private StockService stockService;

    /**
     * Get paginated list of stocks.
     *
     * @param page page number
     * @param size page size
     * @return list of stocks
     */
    @GetMapping
    @Operation(summary = "Get paginated list of stocks")
    public ResponseEntity<List<StockDTO>> getAllStocks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(stockService.getAllStocks(page, size));
    }


    /**
     * Get stock by id.
     *
     * @param id stock id
     * @return stock
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get stock by id")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {

        return ResponseEntity.ok(stockService.getStockById(id));
    }


    /**
     * Create a new stock.
     *
     * @param stockDTO stock data
     * @return created stock
     */
    @PostMapping
    @Operation(summary = "Create a new stock")
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) {
        return ResponseEntity.status(201).body(stockService.createStock(stockDTO));
    }


    /**
     * Update stock price.
     *
     * @param id stock id
     * @param stockDTO stock data
     * @return updated stock
     */
    @PatchMapping("/{id}")
    public ResponseEntity<StockDTO>  updateStockPrice(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(stockService.updateStockPrice(id, stockDTO));
    }

    /**
     * Delete stock by id.
     *
     * @param id stock id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
