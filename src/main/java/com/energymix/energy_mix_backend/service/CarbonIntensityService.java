package com.energymix.energy_mix_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service    
public class CarbonIntensityService {
    @Value("${carbon-intensity.api.base-url}")
    private String baseUrl;

    public CarbonIntensityService() {
        LocalDateTime from = LocalDate.now().atStartOfDay();
        LocalDateTime to = from.plusDays(3);

        String url = baseUrl + "/generation/" + from.format(DateTimeFormatter.ISO_DATE_TIME) 
        + "/" + to.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(url);
    }
}
