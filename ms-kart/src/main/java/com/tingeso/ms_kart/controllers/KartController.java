package com.tingeso.ms_kart.controllers;

import com.tingeso.ms_kart.entities.KartEntity;
import com.tingeso.ms_kart.services.KartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca esta clase como un controlador que maneja solicitudes HTTP y devuelve respuestas JSON.
@RequestMapping("/karts") // Definición de la ruta base para todas las solicitudes que maneja el controlador.
public class KartController {
    @Autowired
    KartService kartService;

    //listKarts
    @GetMapping("/list")
    public ResponseEntity<List<KartEntity>> listKarts() {
        List<KartEntity> karts = kartService.getKarts();
        return new ResponseEntity<>(karts, HttpStatus.OK);
    }

    //getKarts
    @GetMapping("/get/{id}")
    public ResponseEntity<KartEntity> getUserById(@PathVariable Long id) {
        return kartService.getKartById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //saveKart
    @PostMapping("/save")
    public ResponseEntity<KartEntity> saveKart(@RequestBody KartEntity kart) {
        KartEntity kartNew = kartService.saveKart(kart);
        return new ResponseEntity<>(kartNew, HttpStatus.OK);
    }

    //updateKart
    @PutMapping("/update")
    public ResponseEntity<KartEntity> updateKart(@RequestBody KartEntity kart) {
        KartEntity kartUpdated = kartService.updateKart(kart);
        return ResponseEntity.ok(kartUpdated);
    }

    //deleteKart
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteKartById(@PathVariable Long id) throws Exception {
        var isDeleted = kartService.deleteKart(id);
        return ResponseEntity.noContent().build();
    }

    //getAvailableKarts
    @GetMapping("/available")
    public ResponseEntity<List<KartEntity>> getAvailableKarts() {
        return ResponseEntity.ok(kartService.getAvailableKarts());
    }
}