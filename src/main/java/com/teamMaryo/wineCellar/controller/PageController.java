package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.services.WineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    
    @Autowired
    private WineService wineService;

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
        Long clientId = 1L;
        if (wineService.exists(clientId, wineId)) {
            return "wine";
        } else {
            return "components";
        }
    }

    @GetMapping("/wine/{wineId}/edit/")
    public String getWineEdit(@PathVariable("wineId") Long wineId) {
        Long clientId = 1L;
        if (wineService.exists(clientId, wineId)) {
            return "wine-edit";
        } else {
            return "components";
        }
    }
}
