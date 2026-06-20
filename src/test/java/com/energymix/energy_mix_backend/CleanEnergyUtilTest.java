package com.energymix.energy_mix_backend;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.energymix.energy_mix_backend.utils.CleanEnergyUtil;

public class CleanEnergyUtilTest {

    @Test
    public void isCleanEnergyTest() {
        assertTrue(CleanEnergyUtil.isCleanEnergy("wind"), "Wind is clean energy");
        assertTrue(CleanEnergyUtil.isCleanEnergy("solar"), "Solar is clean energy");
        assertTrue(CleanEnergyUtil.isCleanEnergy("nuclear"), "Nuclear is clean energy");
        assertFalse(CleanEnergyUtil.isCleanEnergy("coal"), "Coal is not clean energy");
        assertFalse(CleanEnergyUtil.isCleanEnergy("gas"), "Gas is not clean energy");
        assertFalse(CleanEnergyUtil.isCleanEnergy("other"), "Other is not clean energy");
    }
}
