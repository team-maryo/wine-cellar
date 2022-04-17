package com.teamMaryo.wineCellar.controller;

import java.util.List;

import com.teamMaryo.wineCellar.joins.DenominacionWineJoin;
import com.teamMaryo.wineCellar.joins.TipoWineJoin;
import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.services.WineService;

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
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping("/join/wines/tipos")
    public ResponseEntity<List<TipoWineJoin>> retrieveTipoWines() {
        List<TipoWineJoin> tipoWines = wineService.retreiveTipoWines();
        return ResponseEntity.ok().body(tipoWines);
    }

    @GetMapping("/join/wines/denominaciones")
    public ResponseEntity<List<DenominacionWineJoin>> retrieveDenominacionWines() {
        List<DenominacionWineJoin> tipoWines = wineService.retreiveDenominacionWines();
        return ResponseEntity.ok().body(tipoWines);
    }


    @GetMapping("/wines")
    public ResponseEntity<List<WineModel>> retrieveWines() {
        Long clientId = 1L;
        List<WineModel> wines = wineService.retrieveAll(clientId);
        return ResponseEntity.ok().body(wines);
    }

    @PostMapping("/wines")
    public ResponseEntity<WineModel> createWine(@RequestBody WineModel wine) {
        Long clientId = 1L;
        WineModel newWine = wineService.create(clientId, wine);
        return ResponseEntity.ok().body(newWine);
    }    
    
    @GetMapping("/wines/{wineId}")
    public ResponseEntity<WineModel> retrieveWine(@PathVariable("wineId") Long wineId) {
        Long clientId = 1L;
        WineModel wine = wineService.retrieve(clientId,wineId);
        return ResponseEntity.ok().body(wine);
    }

    @PutMapping("/wines/{wineId}")
    public ResponseEntity<WineModel> updateWine(@PathVariable("wineId") Long wineId, @RequestBody WineModel newWine) {
        Long clientId = 1L;
        WineModel wine = wineService.update(clientId,wineId,newWine);
        return ResponseEntity.ok().body(wine);
    }

    @DeleteMapping("/wines/{wineId}")
    public ResponseEntity<WineModel> deleteWine(@PathVariable("wineId") Long wineId) {
        Long clientId = 1L;
        wineService.destroy(clientId, wineId);
        return ResponseEntity.noContent().build();
    }
}


