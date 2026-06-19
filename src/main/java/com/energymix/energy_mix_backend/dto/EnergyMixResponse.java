package com.energymix.energy_mix_backend.dto;

import java.util.List;

public record EnergyMixResponse(List<DayMix> days) {
    // Record for the energy mix for all days
}
