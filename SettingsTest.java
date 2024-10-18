import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {
    private Settings settings;

    @BeforeEach
    void setUp() {
        // Initialize Settings object with some default test values
        settings = new Settings(50, 50, 1, 50, 50);
    }

    @AfterEach
    void tearDown() {
        // Cleanup resources, if any
        settings = null;
    }

    @Test
    void getSoundLevel() {
        assertEquals(50, settings.getSoundLevel(), "The sound level should initially be 50.");
    }

    @Test
    void getMusicLevel() {
        assertEquals(50, settings.getMusicLevel(), "The music level should initially be 50.");
    }

    @Test
    void getResolutionLevel() {
        assertEquals(1, settings.getResolutionLevel(), "The resolution level should initially be 1.");
    }

    @Test
    void getBrightnessLevel() {
        assertEquals(50, settings.getBrightnessLevel(), "The brightness level should initially be 50.");
    }

    @Test
    void getSensitivityLevel() {
        assertEquals(50, settings.getSensitivityLevel(), "The sensitivity level should initially be 50.");
    }

    @Test
    void setSoundLevel() {
        settings.setSoundLevel(70);
        assertEquals(70, settings.getSoundLevel(), "The sound level should be updated to 70.");
    }

    @Test
    void setMusicLevel() {
        settings.setMusicLevel(80);
        assertEquals(80, settings.getMusicLevel(), "The music level should be updated to 80.");
    }

    @Test
    void setResolutionLevel() {
        settings.setResolutionLevel(2);
        assertEquals(2, settings.getResolutionLevel(), "The resolution level should be updated to 2.");
    }

    @Test
    void setBrightnessLevel() {
        settings.setBrightnessLevel(80);
        assertEquals(80, settings.getBrightnessLevel(), "The brightness level should be updated to 80.");
    }

    @Test
    void setSensitivityLevel() {
        settings.setSensitivityLevel(75);
        assertEquals(75, settings.getSensitivityLevel(), "The sensitivity level should be updated to 75.");
    }
}
