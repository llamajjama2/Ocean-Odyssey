import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LevelsTest {

    /**
     * Test of the getLevel method in the Levels class
     */
    @Test
    void getLevel() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        int expResult = 2;
        int result = myLevel.getLevel();
        assertEquals(expResult,result);
    }

    /**
     * Test of the getObstacles method in the Levels class
     */
    @Test
    void getObstacles() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        int expResult = 75;
        long result = Arrays.stream(myLevel.getObstacles()).count();
        assertEquals(expResult,result);
    }

    /**
     * Test of the getInitialSpeed method in the Levels class
     */
    @Test
    void getInitialSpeed() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        int expResult = 10;
        int result = myLevel.getInitialSpeed();
        assertEquals(expResult,result);
    }

    /**
     * Test of the getSpeedMultiplier method in the Levels class
     */
    @Test
    void getSpeedMultiplier() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        int expResult = 3;
        int result = myLevel.getSpeedMultiplier();
        assertEquals(expResult,result);
    }

    /**
     * Test of the getDensity method in the Levels class
     */
    @Test
    void getDensity() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        int expResult = 5;
        int result = myLevel.getDensity();
        assertEquals(expResult,result);
    }

    /**
     * Test of the getLength method in the Levels class
     */
    @Test
    void getLength() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        int expResult = 150;
        int result = myLevel.getLength();
        assertEquals(expResult,result);
    }

    /**
     * Test of the setLevel method in the Levels class
     */
    @Test
    void setLevel() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        myLevel.setLevel(5);
        int expResult = 5;
        int result = myLevel.getLevel();
        assertEquals(expResult,result);
    }

    /**
     * Test of the setObstacles method in the Levels class
     */
    @Test
    void setObstacles_Basic() {
        Levels myLevel = new Levels(1, 10, 2, 5, 100); // Original state

        // Create a new test array of Obstacles
        Obstacle[] testObstacles = new Obstacle[3];  // Populate this with test data if needed
        testObstacles[0] = new Obstacle(2);

        myLevel.setObstacles(testObstacles);
        assertArrayEquals(testObstacles, myLevel.getObstacles());
    }

    /**
     * Test of the setInitialSpeed method in the Levels class
     */
    @Test
    void setInitialSpeed() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        myLevel.setInitialSpeed(15); // Set to a new value
        int expResult = 15;
        int result = myLevel.getInitialSpeed();
        assertEquals(expResult, result);
    }

    /**
     * Test of the setSpeedMultiplier method in the Levels class
     */
    @Test
    void setSpeedMultiplier() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        myLevel.setSpeedMultiplier(5); // Set to a new value
        int expResult = 5;
        int result = myLevel.getSpeedMultiplier();
        assertEquals(expResult, result);
    }

    /**
     * Test of the setDensity method in the Levels class
     */
    @Test
    void setDensity() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        myLevel.setDensity(8); // Set to a new value
        int expResult = 8;
        int result = myLevel.getDensity();
        assertEquals(expResult, result);
    }

    /**
     * Test of the setLength method in the Levels class
     */
    @Test
    void setLength() {
        Levels myLevel = new Levels(2, 10, 3, 5, 150);
        myLevel.setLength(200); // Set to a new value
        int expResult = 200;
        int result = myLevel.getLength();
        assertEquals(expResult, result);
    }
}
