package com.energymix.energy_mix_backend.dto;

import java.util.List;

public record DayMix(String date, List<FuelSource> sources, Double perc) {
    // Record for the energy mix for a single day
    // date: date of the day
    // sources: list of fuel sources with percentage
    // perc: percentage of clean energy

}