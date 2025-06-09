package com.tingeso.ms_1.config;

import com.tingeso.ms_1.entities.PlanEntity;
import com.tingeso.ms_1.repositories.PlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final PlanRepository planRepository;

    public DataLoader(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public void run(String... args) {
        if (planRepository.count() == 0) {
            PlanEntity planA = new PlanEntity(null, "Plan $15.000", 10, 10, 30, 15000);
            PlanEntity planB = new PlanEntity(null, "Plan $20.000", 15, 15, 35, 20000);
            PlanEntity planC = new PlanEntity(null, "Plan $25.000", 20, 20, 40, 25000);

            planRepository.saveAll(List.of(planA, planB, planC));

            System.out.println("Planes iniciales insertados.");
        } else {
            System.out.println("Planes iniciales previamente cargados.");
        }
    }
}

