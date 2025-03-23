package com.inditex.prices.service;

import com.inditex.prices.domain.Price;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                brandId, productId, date, date);

        if (prices.isEmpty()) {
            throw new PriceNotFoundException("No applicable price found for productId: " + productId +
                    ", brandId: " + brandId + ", date: " + date);
        }

        // Selecciona el precio con mayor prioridad
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException("Unexpected error retrieving price"));
    }
}
