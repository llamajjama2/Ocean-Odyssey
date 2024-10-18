import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getX() {
        BufferedImage myImage = new BufferedImage(100,100,1);

        Player myPlayer = new Player(100, 100,myImage);
        assertEquals(100, myPlayer.getX());
    }

    @Test
    void getY() {
        BufferedImage myImage = new BufferedImage(100,100,1);

        Player myPlayer = new Player(100, 100,myImage);
        assertEquals(100,myPlayer.getY());
    }

    @Test
    void setX() {
        BufferedImage myImage = new BufferedImage(100,100,1);

        Player myPlayer = new Player(100, 100,myImage);
        myPlayer.setX(120);
        assertEquals(120,myPlayer.getX());
    }

    @Test
    void setY() {
        BufferedImage myImage = new BufferedImage(100,100,1);

        Player myPlayer = new Player(100, 100,myImage);
        myPlayer.setY(120);
        assertEquals(120,myPlayer.getY());
    }

    @Test
    void getBounds() {
        BufferedImage myImage = new BufferedImage(100,100,1);

        Player myPlayer = new Player(100, 100,myImage);
        Rectangle example = new Rectangle(100,100,100,100);

        assertEquals(example, myPlayer.getBounds());
    }

    @Test
    void resetPosition() {
        BufferedImage myImage = new BufferedImage(100,100,1);

        Player myPlayer = new Player(100, 100,myImage);

        myPlayer.resetPosition();

        assertEquals(1000,myPlayer.getX());
        assertEquals(250,myPlayer.getY());
    }
}