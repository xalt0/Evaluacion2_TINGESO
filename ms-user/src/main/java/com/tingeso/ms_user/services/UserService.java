package com.tingeso.ms_user.services;

import com.tingeso.ms_user.entities.UserEntity;
import com.tingeso.ms_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserEntity> getUsers() {
        return userRepo.findAll();
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepo.save(user);
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public Optional<UserEntity> getUserByRut(String rut) {
        return userRepo.findByRut(rut);
    }

    public UserEntity updateUser(UserEntity user) {
        return userRepo.save(user);
    }

    public boolean deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addFidelity(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setFidelity(user.getFidelity() + 1);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
