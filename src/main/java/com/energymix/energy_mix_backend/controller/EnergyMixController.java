package com.energymix.energy_mix_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.energymix.energy_mix_backend.service.ChargingWindowService;
import com.energymix.energy_mix_backend.service.EnergyMixService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows the frontend to make requests to the backend
public class EnergyMixController {

    // EnergyMixService handles the energy mix data from the API
    private final EnergyMixService energyMixService;
    // ChargingWindowService handles the charging window data from the API
    private final ChargingWindowService chargingWindowService;

    public EnergyMixController(EnergyMixService energyMixService, ChargingWindowService chargingWindowService) {
        this.energyMixService = energyMixService;
        this.chargingWindowService = chargingWindowService;
    }

    @GetMapping("") // Test endpoint
    public String test() {
        return "API is running";
    }

    @GetMapping("/energy-mix") // Endpoint to get the energy mix
    public com.energymix.energy_mix_backend.dto.EnergyMixResponse getEnergyMix() {
        return energyMixService.getEnergyMix();
    }

    @GetMapping("/charging-window") // Endpoint to get the best charging window
    public com.energymix.energy_mix_backend.dto.ChargingWindowResponse getChargingWindow(
            @org.springframework.web.bind.annotation.RequestParam(defaultValue = "2", name = "hours") int hours) {

        if (hours < 1 || hours > 6) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Charging hours must be between 1 and 6");
        }

        return chargingWindowService.findBestWindow(hours);
    }

}
