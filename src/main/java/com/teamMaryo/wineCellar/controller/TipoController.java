package com.teamMaryo.wineCellar.controller;


import java.util.List;

import com.teamMaryo.wineCellar.models.TipoModel;
import com.teamMaryo.wineCellar.services.TipoService;

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
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping("/tipos")
    public ResponseEntity<List<TipoModel>> retrieveTipos() {
        Long clientId = 1L;
        List<TipoModel> tipos = tipoService.retrieveAll(clientId);
        return ResponseEntity.ok().body(tipos);
    }

    @PostMapping("/tipos")
    public ResponseEntity<TipoModel> createTipo(@RequestBody TipoModel tipo) {
        Long clientId = 1L;
        TipoModel newTipo = tipoService.create(clientId, tipo);
        return ResponseEntity.ok().body(newTipo);
    }    
    
    @GetMapping("/tipos/{tipoId}")
    public ResponseEntity<TipoModel> retrieveTipo(@PathVariable("tipoId") Long tipoId) {
        Long clientId = 1L;
        TipoModel tipo = tipoService.retrieve(clientId,tipoId);
        return ResponseEntity.ok().body(tipo);
    }

    @PutMapping("/tipos/{tipoId}")
    public ResponseEntity<TipoModel> updateWine(@PathVariable("tipoId") Long tipoId, @RequestBody TipoModel newTipo) {
        Long clientId = 1L;
        TipoModel tipo = tipoService.update(clientId,tipoId,newTipo);
        return ResponseEntity.ok().body(tipo);
    }

    @DeleteMapping("/tipos/{tipoId}")
    public ResponseEntity<TipoModel> deleteWine(@PathVariable("tipoId") Long tipoId) {
        Long clientId = 1L;
        tipoService.destroy(clientId, tipoId);
        return ResponseEntity.noContent().build();
    }
}

