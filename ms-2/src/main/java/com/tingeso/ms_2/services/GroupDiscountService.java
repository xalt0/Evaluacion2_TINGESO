package com.tingeso.ms_2.services;

import com.tingeso.ms_2.entities.GroupDiscountEntity;
import com.tingeso.ms_2.repositories.GroupDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDiscountService {

    @Autowired
    GroupDiscountRepository repository;

    public List<GroupDiscountEntity> findAll() {
        return repository.findAll();
    }

    public Optional<GroupDiscountEntity> findById(Long id) {
        return repository.findById(id);
    }

    public GroupDiscountEntity save(GroupDiscountEntity discount) {
        return repository.save(discount);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public int getBestDiscountForGroupSize(int groupSize) {
        return repository.findAll().stream()
                .filter(d -> groupSize >= d.getMinUsers() && groupSize <= d.getMaxUsers())
                .mapToInt(GroupDiscountEntity::getDiscountPercentage)
                .max().orElse(0);
    }
}
