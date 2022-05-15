package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.models.TipoModel;
import com.teamMaryo.wineCellar.services.TipoService;
import com.teamMaryo.wineCellar.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @Autowired
    private UserService userService;


    @GetMapping("/tipos")
    public ResponseEntity<Iterable<TipoModel>> retrieveTipos(@AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Iterable<TipoModel> tipos = tipoService.retrieveAll(userId);
        return ResponseEntity.ok().body(tipos);
    }

    @PostMapping("/tipos")
    public ResponseEntity<TipoModel> createTipo(@RequestBody TipoModel tipo, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        TipoModel newTipo = tipoService.create(userId, tipo);
        return ResponseEntity.ok().body(newTipo);
    }    
    
    @GetMapping("/tipos/{tipoId}")
    public ResponseEntity<TipoModel> retrieveTipo(
        @PathVariable("tipoId") Long tipoId, 
        @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        TipoModel tipo = tipoService.retrieve(userId,tipoId);
        return ResponseEntity.ok().body(tipo);
    }

    @PutMapping("/tipos/{tipoId}")
    public ResponseEntity<TipoModel> updateWine(
        @PathVariable("tipoId") Long tipoId, 
        @RequestBody TipoModel newTipo, 
        @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        TipoModel tipo = tipoService.update(userId,tipoId,newTipo);
        return ResponseEntity.ok().body(tipo);
    }

    @DeleteMapping("/tipos/{tipoId}")
    public ResponseEntity<TipoModel> deleteTipo(
        @PathVariable("tipoId") Long tipoId, 
        @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        tipoService.destroy(userId, tipoId);
        return ResponseEntity.noContent().build();
    }
}

