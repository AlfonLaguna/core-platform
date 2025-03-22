package com.inditex.prices.controller;

import com.inditex.prices.dto.PriceResponseDTO;
import com.inditex.prices.model.Price;
import com.inditex.prices.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping
    public ResponseEntity<PriceResponseDTO> getPrice(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Long brandId) {

        Price price = priceService.getApplicablePrice(date, productId, brandId);
        if (price == null) {
            return ResponseEntity.notFound().build();
        }

        PriceResponseDTO response = new PriceResponseDTO(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice()
        );
        return ResponseEntity.ok(response);
    }
}