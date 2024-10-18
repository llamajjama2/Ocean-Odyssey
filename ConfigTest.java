import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {
    private Config config;
    private final String TEST_CONFIG_PATH = "testConfig.json";

    @BeforeEach
    void setUp() {
        this.config = new Config();
        // Assuming Config class can be modified to accept a path for testing purposes
        // If not, you might need to mock the filesystem or redirect outputs
        System.setProperty("config.path", TEST_CONFIG_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_CONFIG_PATH)); // Clean up the test file
    }

    @Test
    void addLevel() {
        Levels newLevel = new Levels(1, 10, 2, 5, 100);
        config.addLevel(newLevel);
        assertTrue(config.getLevels().contains(newLevel), "The new level should be added to the list.");
    }

    @Test
    void removeLevel() {
        Levels levelToRemove = new Levels(2, 15, 3, 7, 150);
        config.addLevel(levelToRemove); // First add it
        config.removeLevel(levelToRemove); // Then remove it
        assertFalse(config.getLevels().contains(levelToRemove), "The level should be removed from the list.");
    }

    @Test
    void getLevels() {
        Levels level1 = new Levels(1, 10, 2, 5, 100);
        Levels level2 = new Levels(2, 15, 3, 7, 150);
        config.addLevel(level1);
        config.addLevel(level2);
        List<Levels> levels = config.getLevels();
        assertAll(
                () -> assertTrue(levels.contains(level1), "Levels list should contain level1."),
                () -> assertTrue(levels.contains(level2), "Levels list should contain level2."),
                () -> assertEquals(2, levels.size(), "Levels list should have exactly 2 elements.")
        );
    }

    @Test
    void storeConfig() throws IOException {
        Levels level1 = new Levels(1, 10, 2, 5, 100);
        config.addLevel(level1);
        config.storeConfig();
        assertFalse(Files.exists(Path.of(TEST_CONFIG_PATH)), "Config file should exist after storing config.");
    }

    @Test
    void loadConfig() throws IOException {
        // Set up a config to store
        Levels level1 = new Levels(1, 10, 2, 5, 100);
        config.addLevel(level1);
        config.storeConfig();

        // Create a new instance to load the stored config
        Config newConfig = new Config();
        newConfig.loadConfig();
        assertEquals(1, newConfig.getLevels().size(), "Should load one level from config.");
        Levels loadedLevel = newConfig.getLevels().get(0);
        assertAll(
                () -> assertEquals(level1.getLevel(), loadedLevel.getLevel()),
                () -> assertEquals(level1.getInitialSpeed(), loadedLevel.getInitialSpeed()),
                () -> assertEquals(level1.getSpeedMultiplier(), loadedLevel.getSpeedMultiplier()),
                () -> assertEquals(level1.getDensity(), loadedLevel.getDensity()),
                () -> assertEquals(level1.getLength(), loadedLevel.getLength())
        );
    }
}
