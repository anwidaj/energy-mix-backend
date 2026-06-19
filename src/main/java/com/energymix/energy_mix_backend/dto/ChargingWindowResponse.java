package com.energymix.energy_mix_backend.dto;

public record ChargingWindowResponse(String start, String end, double cleanEnergyPerc) {
    // Record for a charging window with start time, end time, and clean energy
    // percentage
}