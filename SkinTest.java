import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkinTest {
    private Skin skin;

    @BeforeEach
    void setUp() {
        skin = new Skin("Pirate", 500, "Rare");
    }

    @AfterEach
    void tearDown() {
        skin = null;
    }

    @Test
    void getName() {
        assertEquals("Pirate", skin.getName(), "getName should return the correct name.");
    }

    @Test
    void getCost() {
        assertEquals(500, skin.getCost(), "getCost should return the correct cost.");
    }

    @Test
    void getRarity() {
        assertEquals("Rare", skin.getRarity(), "getRarity should return the correct rarity.");
    }
}
