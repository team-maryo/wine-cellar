package com.teamMaryo.wineCellar.controller;

import java.util.List;

import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.models.OriginModel;
import com.teamMaryo.wineCellar.services.OriginService;
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
public class OriginController {

    @Autowired
    private OriginService denominacionService;

    @Autowired
    private UserService userService;

    @GetMapping("/origins")
    public ResponseEntity<Iterable<OriginModel>> retrieveDenominaciones(@AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Iterable<OriginModel> denominaciones = denominacionService.retrieveAll(userId);
        return ResponseEntity.ok().body(denominaciones);
    }

    @PostMapping("/origins")
    public ResponseEntity<OriginModel> createDenominacion(@RequestBody OriginModel denominacion, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        OriginModel newDenominacion = denominacionService.create(userId, denominacion);
        return ResponseEntity.ok().body(newDenominacion);
    }    
    
    @GetMapping("/denominaciones/{denominacionId}")
    public ResponseEntity<OriginModel> retrieveWine(@PathVariable("denominacionId") Long denominacionId, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        OriginModel denominacion = denominacionService.retrieve(userId,denominacionId);
        return ResponseEntity.ok().body(denominacion);
    }

    @PutMapping("/denominaciones/{denominacionId}")
    public ResponseEntity<OriginModel> updateWine(
        @PathVariable("denominacionId") Long denominacionId, 
        @RequestBody OriginModel newDenominacion,
        @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        OriginModel denominacion = denominacionService.update(userId,denominacionId,newDenominacion);
        return ResponseEntity.ok().body(denominacion);
    }

    @DeleteMapping("/denominaciones/{denominacionId}")
    public ResponseEntity<WineModel> deleteWine(
        @PathVariable("denominacionId") Long denominacionId,
        @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        denominacionService.destroy(userId, denominacionId);
        return ResponseEntity.noContent().build();
    }
}


