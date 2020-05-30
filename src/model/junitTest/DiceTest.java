<<<<<<< HEAD
import model.entity.Dice;
=======
package model.junitTest;

import static org.junit.Assert.*;
>>>>>>> e3eb5d4da3fe9362d2cf5fb77402919b846586f3
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {
    Dice d1, d2;

    @Before
    public void setUp() {
        d1 = new Dice();
        d2 = new Dice(2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDice() {
        int temp;
        for (int i = 0; i < 50; i++) {
            temp = d1.rollDice();
            assertTrue(temp < 7);
            System.out.println(temp);
        }


    }

    @Test
    public void testDice2() {
        assertTrue(d2.rollDice() > 1);

    }

}
