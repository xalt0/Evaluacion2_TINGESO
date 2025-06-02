package com.tingeso.ms_3.controllers;

import com.tingeso.ms_3.entities.FidelityDiscountEntity;
import com.tingeso.ms_3.services.FidelityDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fidelity-discounts")
public class FidelityDiscountController {

    @Autowired
    FidelityDiscountService service;

    @GetMapping("/get")
    public List<FidelityDiscountEntity> getAll() {
        return service.findAll();
    }

    @PostMapping("/save")
    public FidelityDiscountEntity create(@RequestBody FidelityDiscountEntity discount) {
        return service.save(discount);
    }

    @GetMapping("/best/{points}")
    public ResponseEntity<Integer> getBestDiscount(@PathVariable int points) {
        int discount = service.getBestDiscountForPoints(points);
        return ResponseEntity.ok(discount);
    }
}