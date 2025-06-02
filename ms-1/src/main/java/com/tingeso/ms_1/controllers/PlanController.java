package com.tingeso.ms_1.controllers;

import com.tingeso.ms_1.entities.PlanEntity;
import com.tingeso.ms_1.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    PlanService planService;

    @GetMapping("/list")
    public List<PlanEntity> getAllPlans() {
        return planService.getAllPlans();
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<PlanEntity> getPlanByName(@PathVariable String name) {
        return planService.getPlanByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public PlanEntity savePlan(@RequestBody PlanEntity plan) {
        return planService.savePlan(plan);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
