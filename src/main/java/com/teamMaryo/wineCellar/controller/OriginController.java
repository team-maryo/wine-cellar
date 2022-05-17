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
    private OriginService service;

    @Autowired
    private UserService userService;

    @GetMapping("/origins")
    public ResponseEntity<Iterable<OriginModel>> retrieveDenominaciones(@AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Iterable<OriginModel> denominaciones = service.retrieveAll(userId);
        return ResponseEntity.ok().body(denominaciones);
    }

    @PostMapping("/origins")
    public ResponseEntity<OriginModel> createDenominacion(@RequestBody OriginModel denominacion, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        OriginModel newDenominacion = service.create(userId, denominacion);
        return ResponseEntity.ok().body(newDenominacion);
    }    
    
    @GetMapping("/origins/{originId}")
    public ResponseEntity<OriginModel> retrieveWine(@PathVariable("originId") Long originId, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        OriginModel denominacion = service.retrieve(userId,originId);
        return ResponseEntity.ok().body(denominacion);
    }

    @PutMapping("/origins/{originId}")
    public ResponseEntity<OriginModel> updateWine(
        @PathVariable("originId") Long originId, 
        @RequestBody OriginModel newDenominacion,
        @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        OriginModel denominacion = service.update(userId,originId,newDenominacion);
        return ResponseEntity.ok().body(denominacion);
    }

    @DeleteMapping("/origins/{originId}")
    public ResponseEntity<WineModel> deleteWine(
        @PathVariable("originId") Long originId,
        @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        service.destroy(userId, originId);
        return ResponseEntity.noContent().build();
    }
}


