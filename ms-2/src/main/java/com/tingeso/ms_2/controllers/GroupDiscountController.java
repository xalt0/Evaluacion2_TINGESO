package com.tingeso.ms_2.controllers;

import com.tingeso.ms_2.entities.GroupDiscountEntity;
import com.tingeso.ms_2.services.GroupDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group-discounts")
public class GroupDiscountController {

    @Autowired
    GroupDiscountService service;

    @GetMapping("/get")
    public List<GroupDiscountEntity> getAll() {
        return service.findAll();
    }

    @PostMapping("/save")
    public GroupDiscountEntity create(@RequestBody GroupDiscountEntity discount) {
        return service.save(discount);
    }

    @GetMapping("/best/{groupSize}")
    public ResponseEntity<Integer> getBestDiscount(@PathVariable int groupSize) {
        int discount = service.getBestDiscountForGroupSize(groupSize);
        return ResponseEntity.ok(discount);
    }
}