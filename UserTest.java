import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "testPass");
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void getUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("testPass", user.getPassword());
    }

    @Test
    void getEmail() {
        assertEquals("Null", user.getEmail());
    }

    @Test
    void getHighScore() {
        assertEquals(0, user.getHighScore());
    }

    @Test
    void getSkins() {
        List<Skin> skins = user.getSkins();
        assertNotNull(skins);
        assertTrue(skins.size() > 0);
    }

    @Test
    void getMaxLevel() {
        assertEquals(1, user.getMaxLevel());
    }

    @Test
    void setHighScore() {
        user.setHighScore(100);
        assertEquals(100, user.getHighScore());
    }

    @Test
    void addSkin() {
        int initialSize = user.getSkins().size();
        user.addSkin(new Skin("NewSkin.png", 100, "Rare"));
        assertEquals(initialSize + 1, user.getSkins().size());
    }

    @Test
    void setMaxLevel() {
        user.setMaxLevel(5);
        assertEquals(5, user.getMaxLevel());
    }

    @Test
    void setCurrentSkin() {
        Skin newSkin = new Skin("NewCurrentSkin.png", 100, "Rare");
        user.setCurrentSkin(newSkin);
        assertEquals(newSkin, user.getCurrentSkin());
    }

    @Test
    void getCurrentSkin() {
        Skin currentSkin = user.getCurrentSkin();
        assertNotNull(currentSkin);
        assertEquals("Pirate.png", currentSkin.getName());
    }
}
