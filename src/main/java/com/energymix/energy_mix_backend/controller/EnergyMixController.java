package com.energymix.energy_mix_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.energymix.energy_mix_backend.service.ChargingWindowService;
import com.energymix.energy_mix_backend.service.EnergyMixService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class EnergyMixController {

    private final EnergyMixService energyMixService;
    private final ChargingWindowService chargingWindowService;

    public EnergyMixController(EnergyMixService energyMixService, ChargingWindowService chargingWindowService) {
        this.energyMixService = energyMixService;
        this.chargingWindowService = chargingWindowService;
    }

    @GetMapping("")
    public String test() {
        return "API is running";
    }

    @GetMapping("/energy-mix")
    public com.energymix.energy_mix_backend.dto.EnergyMixResponse getEnergyMix() {
        return energyMixService.getEnergyMix();
    }

    @GetMapping("/charging-window")
    public com.energymix.energy_mix_backend.dto.ChargingWindowResponse getChargingWindow() {
        return chargingWindowService.findBestWindow(2);
    }

}
