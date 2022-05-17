package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.services.PurchaseService;
import com.teamMaryo.wineCellar.services.UserService;
import com.teamMaryo.wineCellar.services.WineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    
    @Autowired
    private WineService wineService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getIndex() {
        return "inventory";
    }

    @GetMapping("/running-low")
    public String getRunningLow() {
        return "running-low";
    }

    @GetMapping("/settings")
    public String getSettings() {
        return "settings";
    }

    @GetMapping("/purchases")
    public String getPurchases() {
        return "purchases";
    }

    @GetMapping("/purchases/{purchaseId}")
    public String getPurchase(@AuthenticationPrincipal User user, @PathVariable("purchaseId") Long purchaseId)
    {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        if (purchaseService.exists(userId, purchaseId)) {
            return "purchase";
        } else {
            return "error";
        }
    }

    @GetMapping("/wine/new")
    public String getWinesNew() {
        return "wine-new";
    }

    @GetMapping("/wine/{wineId}")
    public String getWine(@AuthenticationPrincipal User user, @PathVariable("wineId") Long wineId)
    {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        if (wineService.exists(userId, wineId)) {
            return "wine";
        } else {
            return "error";
        }
    }
    
    @GetMapping("/wine/{wineId}/edit/")
    public String getWineEdit(@AuthenticationPrincipal User user, @PathVariable("wineId") Long wineId) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        if (wineService.exists(userId, wineId)) {
            return "wine-edit";
        } else {
            return "error";
        }
    }

    @GetMapping("/error") 
    public String get404() {
        return "error";
    }
}
