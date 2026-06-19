package com.energymix.energy_mix_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.energymix.energy_mix_backend.dto.CarbonApiResponse;

import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CarbonIntensityService {
    // Base URL for the Carbon Intensity API
    @Value("${carbon-intensity.api.base-url}")
    private String baseUrl;

    // RestTemplate for making HTTP requests
    private final RestTemplate restTemplate;

    public CarbonIntensityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CarbonApiResponse fetch() {
        // Method to fetch data from the Carbon Intensity API

        LocalDateTime from = LocalDate.now().atStartOfDay();
        LocalDateTime to = from.plusDays(3);

        String url = baseUrl + "/generation/" + from.format(DateTimeFormatter.ISO_DATE_TIME)
                + "/" + to.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(url);

        CarbonApiResponse result = restTemplate.getForObject(url, CarbonApiResponse.class);
        return result;
    }
}
