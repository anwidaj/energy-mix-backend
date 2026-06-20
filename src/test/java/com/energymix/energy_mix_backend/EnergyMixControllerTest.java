package com.energymix.energy_mix_backend;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

import com.energymix.energy_mix_backend.controller.EnergyMixController;
import com.energymix.energy_mix_backend.dto.ChargingWindowResponse;
import com.energymix.energy_mix_backend.dto.DayMix;
import com.energymix.energy_mix_backend.dto.EnergyMixResponse;
import com.energymix.energy_mix_backend.dto.FuelSource;
import com.energymix.energy_mix_backend.service.ChargingWindowService;
import com.energymix.energy_mix_backend.service.EnergyMixService;

@WebMvcTest(EnergyMixController.class)
public class EnergyMixControllerTest {

    // Inject the MockMvc object for testing the endpoint
    @Autowired
    private MockMvc mockMvc;

    // Mock the energy mix service
    @MockitoBean
    private EnergyMixService energyMixService;

    // Mock the charging window service
    @MockitoBean
    private ChargingWindowService chargingWindowService;

    @Test
    public void GetEnergyMixEndpointTest() throws Exception {
        // Data for testing purposes
        var day = new DayMix("2026-06-20", List.of(new FuelSource("solar", 50.0), new FuelSource("wind", 50.0)), 100.0);
        var response = new EnergyMixResponse(List.of(day));

        // Mock the response
        when(energyMixService.getEnergyMix()).thenReturn(response);

        // Test the endpoint
        mockMvc.perform(get("/api/energy-mix"))
                .andExpect(status().isOk()) // Check if the request was successful
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check if the response type is JSON
                .andExpect(jsonPath("$.days[0].date").value("2026-06-20")) // Check if the date is correct
                .andExpect(jsonPath("$.days[0].perc").value(100.0)); // Check if the percentage is correct
    }

    @Test
    public void GetChargingWindowEndpointTest() throws Exception {
        // Data for testing purposes
        var chargingWindow = new ChargingWindowResponse("2026-06-20T00:00:00Z", "2026-06-20T00:30:00Z", 100.0);

        // Mock the response
        when(chargingWindowService.findBestWindow(2)).thenReturn(chargingWindow);

        // Test the endpoint
        mockMvc.perform(get("/api/charging-window"))
                .andExpect(status().isOk()) // Check if the request was successful
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check if the response type is JSON
                .andExpect(jsonPath("$.start").value("2026-06-20T00:00:00Z")) // Check if the start time is correct
                .andExpect(jsonPath("$.end").value("2026-06-20T00:30:00Z")) // Check if the end time is correct
                .andExpect(jsonPath("$.cleanEnergyPerc").value(100.0)); // Check if the clean energy percentage is
    }
}
