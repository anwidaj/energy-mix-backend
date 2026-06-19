package com.energymix.energy_mix_backend.dto;

import java.util.List;

public record CarbonApiResponse(List<CarbonApiData> data) {
    // Record for the response from the Carbon Intensity API

    public record CarbonApiData(
            String from,
            String to,
            List<FuelSource> generationmix) {
        // Record for a single data entry from the Carbon Intensity API
    }
}