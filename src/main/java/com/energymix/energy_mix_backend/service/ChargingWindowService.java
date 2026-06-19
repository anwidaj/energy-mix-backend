package com.energymix.energy_mix_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.energymix.energy_mix_backend.dto.CarbonApiResponse;
import com.energymix.energy_mix_backend.dto.ChargingWindowResponse;
import com.energymix.energy_mix_backend.dto.FuelSource;
import com.energymix.energy_mix_backend.utils.CleanEnergyUtil;

@Service
public class ChargingWindowService {

    private final CarbonIntensityService carbonIntensityService;

    public ChargingWindowService(CarbonIntensityService carbonIntensityService) {
        this.carbonIntensityService = carbonIntensityService;
    }

    public ChargingWindowResponse findBestWindow(double hours) {
        // Fetches data from the CarbonIntensity API and calculates the best window to
        // charge based on the higest clean energy percentage
        CarbonApiResponse response = carbonIntensityService.fetch();

        // Get the data from the response
        var intervals = response.data();

        double maxCleanEnergySum = -1.0;
        String bestWindowStart = "";
        String bestWindowEnd = "";
        int windowSize = (int) (hours * 2);

        for (int i = 0; i <= intervals.size() - windowSize; i++) {
            var currentWindow = intervals.subList(i, i + windowSize);

            double cleanEnergySum = 0;
            for (var item : currentWindow) {
                cleanEnergySum += CleanEnergyPerc(item.generationmix());
            }

            cleanEnergySum = cleanEnergySum / windowSize;

            if (cleanEnergySum > maxCleanEnergySum) {
                maxCleanEnergySum = cleanEnergySum;
                bestWindowStart = currentWindow.get(0).from();
                bestWindowEnd = currentWindow.get(currentWindow.size() - 1).to();
            }
        }

        return new ChargingWindowResponse(bestWindowStart, bestWindowEnd, maxCleanEnergySum);
    }

    private double CleanEnergyPerc(List<FuelSource> generationmix) {
        // Helper method to calculate clean energy percentage for a given generation mix
        return generationmix.stream()
                .filter(fuel -> CleanEnergyUtil.isCleanEnergy(fuel.fuel()))
                .mapToDouble(FuelSource::perc)
                .sum();
    }

}