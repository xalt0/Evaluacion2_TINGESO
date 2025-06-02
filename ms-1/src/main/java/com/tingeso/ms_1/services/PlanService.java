package com.tingeso.ms_1.services;

import com.tingeso.ms_1.entities.PlanEntity;
import com.tingeso.ms_1.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    public List<PlanEntity> getAllPlans() {
        return planRepository.findAll();
    }

    public Optional<PlanEntity> getPlanByName(String name) {
        return planRepository.findByName(name.toUpperCase());
    }

    public PlanEntity savePlan(PlanEntity plan) {
        return planRepository.save(plan);
    }

    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }
}

