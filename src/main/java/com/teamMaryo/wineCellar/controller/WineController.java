package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.joins.WineExtendedJoin;
import com.teamMaryo.wineCellar.models.WineExtendedModel;
import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.services.UserService;
import com.teamMaryo.wineCellar.services.WineService;

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
public class WineController {

    @Autowired
    private WineService wineService;

    @Autowired
    private UserService userService;

    @GetMapping("/extended/wines")
    public ResponseEntity<Iterable<WineExtendedJoin>> retrieveAllInfo(@AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Iterable<WineExtendedJoin> tipoWines = wineService.retrieveAllExtended(userId);
        return ResponseEntity.ok().body(tipoWines);
    }

    @GetMapping("/wines")
    public ResponseEntity<Iterable<WineExtendedModel>> retrieveWines(@AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Iterable<WineExtendedModel> wines = wineService.retrieveAll(userId);
        return ResponseEntity.ok().body(wines);
    }

    @PostMapping("/wines")
    public ResponseEntity<WineModel> createWine(@RequestBody WineModel wine, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        WineModel newWine = wineService.create(userId, wine);
        return ResponseEntity.ok().body(newWine);
    }    
    
    @GetMapping("/wines/{wineId}")
    public ResponseEntity<WineExtendedModel> retrieveWine(@PathVariable("wineId") Long wineId, @AuthenticationPrincipal User user)
    {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        WineExtendedModel wine = wineService.retrieve(userId, wineId);

        return ResponseEntity.ok().body(wine);
    }

    @PutMapping("/wines/{wineId}")
    public ResponseEntity<WineModel> updateWine(
        @PathVariable("wineId") Long wineId, 
        @RequestBody WineModel newWine,
        @AuthenticationPrincipal User user) 
    {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        WineModel wine = wineService.update(userId,wineId,newWine);
        return ResponseEntity.ok().body(wine);
    }

    @DeleteMapping("/wines/{wineId}")
    public ResponseEntity<WineModel> deleteWine(
        @PathVariable("wineId") Long wineId, @AuthenticationPrincipal User user) 
    {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        wineService.destroy(userId, wineId);
        return ResponseEntity.noContent().build();
    }
}


