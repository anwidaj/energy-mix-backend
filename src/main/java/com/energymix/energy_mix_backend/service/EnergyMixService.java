package com.energymix.energy_mix_backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.energymix.energy_mix_backend.dto.CarbonApiResponse;
import com.energymix.energy_mix_backend.dto.DayMix;
import com.energymix.energy_mix_backend.dto.EnergyMixResponse;
import com.energymix.energy_mix_backend.dto.FuelSource;
import com.energymix.energy_mix_backend.utils.CleanEnergyUtil;

@Service
public class EnergyMixService {

    private final CarbonIntensityService carbonIntensityService;

    public EnergyMixService(CarbonIntensityService carbonIntensityService) {
        this.carbonIntensityService = carbonIntensityService;
    }

    public EnergyMixResponse getEnergyMix() {
        // Fetches data from the Carbon Intensity API and processes it
        // to seperate by day and calculate the average percentage of each fuel source
        CarbonApiResponse response = carbonIntensityService.fetch();
        var intervals = response.data();

        // Get the current date and the next two days
        String day0 = LocalDate.now().toString();
        String day1 = LocalDate.now().plusDays(1).toString();
        String day2 = LocalDate.now().plusDays(2).toString();

        // Filter the intervals by day
        var day0Intervals = intervals.stream()
                .filter(i -> i.from().startsWith(day0))
                .toList();
        var day1Intervals = intervals.stream()
                .filter(i -> i.from().startsWith(day1))
                .toList();
        var day2Intervals = intervals.stream()
                .filter(i -> i.from().startsWith(day2))
                .toList();

        // Calculate the energy mix for each day
        var day0Mix = calculateDayMix(day0, day0Intervals);
        var day1Mix = calculateDayMix(day1, day1Intervals);
        var day2Mix = calculateDayMix(day2, day2Intervals);

        // Return the energy mix for all three days
        return new EnergyMixResponse(List.of(day0Mix, day1Mix, day2Mix));
    }

    private DayMix calculateDayMix(String date, List<CarbonApiResponse.CarbonApiData> dayIntervals) {
        // Calculate the energy mix for a single day
        List<FuelSource> avgSources = calculateAverageFuels(dayIntervals);
        double cleanPerc = calculateCleanEnergyPerc(avgSources);

        return new DayMix(date, avgSources, cleanPerc);
    }

    private double calculateCleanEnergyPerc(List<FuelSource> sources) {
        // Method to calculate the clean energy percentage
        return sources.stream()
                .filter(fuel -> CleanEnergyUtil.isCleanEnergy(fuel.fuel()))
                .mapToDouble(FuelSource::perc)
                .sum();
    }

    private List<FuelSource> calculateAverageFuels(List<CarbonApiResponse.CarbonApiData> dayIntervals) {
        // Method to calculate the average energy mix for a single day

        List<String> fuelNames = List.of("biomass", "coal", "imports", "gas", "nuclear", "other", "hydro", "solar",
                "wind");
        List<FuelSource> avgSources = new ArrayList<>();

        // Iterate through each fuel type and calculate the average percentage
        for (String fuel : fuelNames) {
            double sum = dayIntervals.stream()
                    .flatMap(interval -> interval.generationmix().stream())
                    .filter(f -> f.fuel().equals(fuel))
                    .mapToDouble(FuelSource::perc)
                    .sum();

            avgSources.add(new FuelSource(fuel, sum / dayIntervals.size()));
        }
        return avgSources;
    }

}