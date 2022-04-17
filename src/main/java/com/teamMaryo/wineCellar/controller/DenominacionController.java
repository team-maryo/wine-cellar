package com.teamMaryo.wineCellar.controller;

import java.util.List;

import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.models.DenominacionModel;
import com.teamMaryo.wineCellar.services.DenominacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DenominacionController {

    @Autowired
    private DenominacionService denominacionService;

    @GetMapping("/denominaciones")
    public ResponseEntity<List<DenominacionModel>> retrieveDenominaciones() {
        Long clientId = 1L;
        List<DenominacionModel> denominaciones = denominacionService.retrieveAll(clientId);
        return ResponseEntity.ok().body(denominaciones);
    }

    @PostMapping("/denominaciones")
    public ResponseEntity<DenominacionModel> createDenominacion(@RequestBody DenominacionModel denominacion) {
        Long clientId = 1L;
        DenominacionModel newDenominacion = denominacionService.create(clientId, denominacion);
        return ResponseEntity.ok().body(newDenominacion);
    }    
    
    @GetMapping("/denominaciones/{denominacionId}")
    public ResponseEntity<DenominacionModel> retrieveWine(@PathVariable("denominacionId") Long denominacionId) {
        Long clientId = 1L;
        DenominacionModel denominacion = denominacionService.retrieve(clientId,denominacionId);
        return ResponseEntity.ok().body(denominacion);
    }

    @PutMapping("/denominaciones/{denominacionId}")
    public ResponseEntity<DenominacionModel> updateWine(@PathVariable("denominacionId") Long denominacionId, @RequestBody DenominacionModel newDenominacion) {
        Long clientId = 1L;
        DenominacionModel denominacion = denominacionService.update(clientId,denominacionId,newDenominacion);
        return ResponseEntity.ok().body(denominacion);
    }

    @DeleteMapping("/denominaciones/{denominacionId}")
    public ResponseEntity<WineModel> deleteWine(@PathVariable("denominacionId") Long denominacionId) {
        Long clientId = 1L;
        denominacionService.destroy(clientId, denominacionId);
        return ResponseEntity.noContent().build();
    }
}


