package com.energymix.energy_mix_backend.utils;

import java.util.List;

public class CleanEnergyUtil {
    public static boolean isCleanEnergy(String fuel) {
        // Check if the fuel is clean
        List<String> cleanFuels = List.of("biomass", "nuclear", "hydro", "wind", "solar");
        return cleanFuels.contains(fuel);
    }
}
