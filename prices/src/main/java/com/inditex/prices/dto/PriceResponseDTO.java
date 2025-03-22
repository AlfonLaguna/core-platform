package com.inditex.prices.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponseDTO {
    private Long productId;
    private Long brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;

    // Constructor
    public PriceResponseDTO(Long productId, Long brandId, Integer priceList,
                            LocalDateTime startDate, LocalDateTime endDate, BigDecimal price) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    // Getters
    public Long getProductId() { return productId; }
    public Long getBrandId() { return brandId; }
    public Integer getPriceList() { return priceList; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public BigDecimal getPrice() { return price; }

    // Setters
    public void setProductId(Long productId) { this.productId = productId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }
    public void setPriceList(Integer priceList) { this.priceList = priceList; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setPrice(BigDecimal price) { this.price = price; }
}