package com.inditex.prices.service;

import com.inditex.prices.model.Price;
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
            return null;
        }

        // Selecciona el precio con mayor prioridad
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElse(null);
    }
}
