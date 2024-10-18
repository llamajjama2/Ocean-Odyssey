import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveTest {
    private final String TEST_FILE_PATH = "testSaveData.json";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testGetSettings() {
        Settings expected = new Settings(50, 50, 1080, 100, 5);
        Save.setSettings(expected);
        assertEquals(expected, Save.getSettings());
    }

    @Test
    void testGetUser() {
        User expected = new User("TestUser", "TestPass");
        Save.setUser(expected);
        assertEquals(expected, Save.getUser());
    }

    @Test
    void testGetLeaderboard() {
        Leaderboard_B expected = new Leaderboard_B();
        Save.setLeaderboard(expected);
        assertEquals(expected, Save.getLeaderboard());
    }

    @Test
    void testSetSettings() {
        Settings newSettings = new Settings(20, 20, 720, 50, 10);
        Save.setSettings(newSettings);
        assertEquals(newSettings, Save.getSettings());
    }

    @Test
    void testSetUsers() {
        List<User> newUsers = new ArrayList<>();
        newUsers.add(new User("NewUser", "NewPass"));
        Save.setUsers(newUsers);
        assertEquals(newUsers, Save.getUsers());
    }

    @Test
    void testSetUser() {
        User newUser = new User("NewUser", "NewPass");
        Save.setUser(newUser);
        assertEquals(newUser, Save.getUser());
    }

    @Test
    void testSetLeaderboard() {
        Leaderboard_B newLeaderboard = new Leaderboard_B();
        Save.setLeaderboard(newLeaderboard);
        assertEquals(newLeaderboard, Save.getLeaderboard());
    }

    @Test
    void testAddUser() {
        User newUser = new User("AddUser", "AddPass");
        Save.addUser(newUser);
        assertTrue(Save.getUsers().contains(newUser));
    }

    @Test
    void testGetUsers() {
        User testUser = new User("TestUserList", "TestPassList");
        List<User> expected = new ArrayList<>();
        expected.add(testUser);
        Save.setUsers(expected);
        assertEquals(expected, Save.getUsers());
    }
}
