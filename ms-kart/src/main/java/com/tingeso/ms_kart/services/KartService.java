package com.tingeso.ms_kart.services;

import com.tingeso.ms_kart.entities.KartEntity;
import com.tingeso.ms_kart.repositories.KartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KartService {
    @Autowired // Permite inyección de la dependencia.
    KartRepository kartRepository;

    // Metodo para retornar listado de Karts.
    public ArrayList<KartEntity> getKarts() {
        return (ArrayList<KartEntity>) kartRepository.findAll();
    }

    // Metodo para añadir un Kart.
    public KartEntity saveKart(KartEntity kart) {
        return kartRepository.save(kart);
    }

    // Metodo para retornar un Kart por ID.
    public Optional<KartEntity> getKartById(Long id) {
        return kartRepository.findById(id);
    }

    // Metodo para actualizar un Kart.
    public KartEntity updateKart(KartEntity kart) {
        return kartRepository.save(kart);
    }

    // Metodo para eliminar un Kart.
    public boolean deleteKart(Long id) throws Exception {
        try {
            kartRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Metodo para retornar Kart disponibles (available == true)
    public List<KartEntity> getAvailableKarts() {
        return kartRepository.findByAvailableTrue();
    }
}