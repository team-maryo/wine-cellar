package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.services.UserService;
import com.teamMaryo.wineCellar.services.WineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    
    @Autowired
    private WineService wineService;

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

    @GetMapping("/wine/new")
    public String getWinesNew() {
        return "wine-new";
    }

    @GetMapping("/wine/{wineId}")
    public String getWine(@PathVariable("wineId") Long wineId)
    {
        // Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Long userId = 1L;
        if (wineService.exists(userId, wineId)) {
            return "wine";
        } else {
            return "components";
        }
    }
    
    @GetMapping("/wine/{wineId}/edit/")
    public String getWineEdit(@PathVariable("wineId") Long wineId) {
        // Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Long userId = 1L;
        if (wineService.exists(userId, wineId)) {
            return "wine-edit";
        } else {
            return "components";
        }
    }

    @GetMapping("/error") 
    public String get404() {
        return "error";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }

}
