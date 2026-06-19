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
import com.energymix.energy_mix_backend.service.ChargingWindowService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChargingWindowTest {

    @Mock
    private CarbonIntensityService carbonIntensityService;

    @InjectMocks
    private ChargingWindowService chargingWindowService;

    @Test
    public void GetEnergyMixTest() {
        // Test if the service can find the best charging window
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

        // Data for testing purposes
        var data1 = new CarbonApiResponse.CarbonApiData(today + "T00:00:00Z", today + "T00:30:00Z", mix1);
        var data2 = new CarbonApiResponse.CarbonApiData(today + "T00:30:00Z", today + "T01:00:00Z", mix2);
        var data3 = new CarbonApiResponse.CarbonApiData(today + "T01:00:00Z", today + "T01:30:00Z", mix3);

        // Response with data
        CarbonApiResponse response = new CarbonApiResponse(List.of(data1, data2, data3));

        // Mock the response
        when(carbonIntensityService.fetch()).thenReturn(response);

        // Get the energy mix from the service
        var result = chargingWindowService.findBestWindow(1.0);

        assertEquals(today + "T00:00:00Z", result.start(), "Window should start from data1");
        assertEquals(today + "T01:00:00Z", result.end(), "Window should end at data2");
        assertEquals(45.0, result.cleanEnergyPerc(), 0.01, "Average of first two intervals is 45%");
    }

}
