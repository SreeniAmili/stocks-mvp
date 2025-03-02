package com.payconiq.stocks.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Data Transfer Object for Stock entity.
 */
public record StockDTO(Long id, String name, BigDecimal currentPrice, Instant lastUpdate) {

}
