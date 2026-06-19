package com.energymix.energy_mix_backend;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.energymix.energy_mix_backend.dto.CarbonApiResponse;
import com.energymix.energy_mix_backend.dto.FuelSource;
import com.energymix.energy_mix_backend.service.CarbonIntensityService;
import com.energymix.energy_mix_backend.service.EnergyMixService;
import com.energymix.energy_mix_backend.utils.CleanEnergyUtil;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnergyMixServiceTest {

    @Mock
    private CarbonIntensityService carbonIntensityService;

    @InjectMocks
    private EnergyMixService EnergyMixService;

    @Test
    public void GetEnergyMixTest() {
        // Test if the service can calculate the average energy mix for a day
        String today = LocalDate.now().toString();

        List<FuelSource> mix1 = List.of(
                new FuelSource("coal", 5.0),
                new FuelSource("gas", 45.0),
                new FuelSource("nuclear", 20.0),
                new FuelSource("wind", 15.0),
                new FuelSource("solar", 10.0),
                new FuelSource("other", 5.0));
        List<FuelSource> mix2 = List.of(
                new FuelSource("coal", 0.0),
                new FuelSource("gas", 50.0),
                new FuelSource("nuclear", 10.0),
                new FuelSource("wind", 25.0),
                new FuelSource("solar", 10.0),
                new FuelSource("other", 5.0));
        List<FuelSource> mix3 = List.of(
                new FuelSource("coal", 0.0),
                new FuelSource("gas", 60.0),
                new FuelSource("nuclear", 10.0),
                new FuelSource("wind", 10.0),
                new FuelSource("solar", 15.0),
                new FuelSource("other", 5.0));

        var data1 = new CarbonApiResponse.CarbonApiData(today + "T00:00:00Z", today + "T00:30:00Z", mix1);
        var data2 = new CarbonApiResponse.CarbonApiData(today + "T00:30:00Z", today + "T01:00:00Z", mix2);
        var data3 = new CarbonApiResponse.CarbonApiData(today + "T01:00:00Z", today + "T01:30:00Z", mix3);

        CarbonApiResponse response = new CarbonApiResponse(List.of(data1, data2, data3));

        when(carbonIntensityService.fetch()).thenReturn(response);

        // Get the energy mix from the service
        var result = EnergyMixService.getEnergyMix();

        // Get the calculated average sources for today
        var todayMix = result.days().stream()
                .filter(d -> d.date().equals(today))
                .findFirst()
                .get();

        assertEquals(41.66, todayMix.perc(), 0.01,
                "Clean energy average should be 41.66%");
        // Test
        var windAvg = todayMix.sources().stream()
                .filter(s -> s.fuel().equals("wind"))
                .findFirst()
                .get();

        // Test if wind average is correct
        assertEquals(16.66, windAvg.perc(), 0.01,
                "Wind average should be 16.66%");

        // Test if the service can calculate clean energy average correctly
        var cleanAvg = todayMix.sources().stream()
                .filter(s -> CleanEnergyUtil.isCleanEnergy(s.fuel()))
                .mapToDouble(FuelSource::perc)
                .sum();

        assertEquals(41.66, cleanAvg, 0.01,
                "Clean energy average should be 41.66%");

    }

}
