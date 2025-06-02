package com.tingeso.ms_3.services;

import com.tingeso.ms_3.entities.FidelityDiscountEntity;
import com.tingeso.ms_3.repositories.FidelityDiscountRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FidelityDiscountService {

    private final FidelityDiscountRepository repository;

    public FidelityDiscountService(FidelityDiscountRepository repository) {
        this.repository = repository;
    }

    public List<FidelityDiscountEntity> findAll() {
        return repository.findAll();
    }

    public FidelityDiscountEntity save(FidelityDiscountEntity discount) {
        return repository.save(discount);
    }

    public int getBestDiscountForPoints(int points) {
        return repository.findAllByMinPointsLessThanEqual(points).stream()
                .max(Comparator.comparingInt(FidelityDiscountEntity::getDiscountPercentage))
                .map(FidelityDiscountEntity::getDiscountPercentage)
                .orElse(0);
    }
}