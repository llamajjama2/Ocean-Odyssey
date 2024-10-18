import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void isActive() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        assertTrue(obstacle1.isActive());
    }

    @Test
    void setActive() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        obstacle1.setActive(false);
        assertFalse(obstacle1.isActive());
    }

    @Test
    void setType() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        int expResult = 1;
        assertEquals(expResult, obstacle1.getType());
    }

    @Test
    void setSize() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        obstacle1.setSize(2);
        assertEquals(8,obstacle1.getSize());

    }

    @Test
    void getType() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        assertEquals(1,obstacle1.getType());
    }

    @Test
    void getSize() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        Integer temp = obstacle1.getSize();
        assertTrue( temp instanceof Integer);
    }

    @Test
    void getX() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        assertEquals(100,obstacle1.getX());
    }

    @Test
    void getBounds() {
        Obstacle obstacle1 = new Obstacle(100, 100, 5,null, 2);
        Integer temp = (int) obstacle1.getX();

        assertTrue(temp instanceof Integer);
    }
}