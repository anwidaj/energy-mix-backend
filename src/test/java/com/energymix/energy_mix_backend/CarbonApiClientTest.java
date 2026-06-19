package com.energymix.energy_mix_backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.energymix.energy_mix_backend.dto.CarbonApiResponse;
import com.energymix.energy_mix_backend.service.CarbonIntensityService;

@SpringBootTest

public class CarbonApiClientTest {

    // Automatically inject
    @Autowired
    private CarbonIntensityService carbonIntensityService;

    // Test the API call
    @Test
    public void testGetCarbonIntensity() {
        CarbonApiResponse result = carbonIntensityService.fetch();
        System.out.println(result);

        // Test if the API response is not null
        assertNotNull(result, "The response from the API cannot be null");
        // Test if the API response contains the key 'data'
        assertNotNull(result.data(), "The response must contain the key 'data'");
        // Test if the API response contains the key 'from'
        assertNotNull(result.data().get(0).from(), "The response must contain the key 'from'");
        // Test if the API response contains the key 'to'
        assertNotNull(result.data().get(0).to(), "The response must contain the key 'to'");
        // Test if the API response contains the key 'generationmix'
        assertNotNull(result.data().get(0).generationmix(), "The response must contain the key 'generationmix'");
        // Check if the generationmix list is not empty
        assertNotEquals(0, result.data().get(0).generationmix().size(),
                "The generationmix list cannot be empty");
    }

}