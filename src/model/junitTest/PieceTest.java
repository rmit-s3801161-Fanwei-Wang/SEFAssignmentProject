<<<<<<< HEAD
import model.entity.*;
import model.exception.OutOfBoardException;
=======
package model.junitTest;

import static org.junit.Assert.*;

import java.util.HashMap;

>>>>>>> e3eb5d4da3fe9362d2cf5fb77402919b846586f3
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PieceTest {
    Piece[] pieces = new Piece[6];
    Dice dice = new Dice();
    HashMap<Position, Entity> collections = new HashMap<Position, Entity>();

    @Before
    public void setUp() throws Exception {

        pieces[0] = new Piece(null, "A");
        pieces[1] = new Piece(null, "B");
        pieces[2] = new Piece(null, "C");
        pieces[3] = new Piece(null, "D");
        pieces[4] = new Piece(null, "E");
        pieces[5] = new Piece(null, "F");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMove() {
        Dice dice = new Dice();
        pieces[0].move(collections, dice.rollDice());
        pieces[1].move(collections, dice.rollDice());
        try {
            assertFalse(pieces[0].move(collections, "TR"));
            pieces[0].addLevel();
            assertFalse(pieces[0].move(collections, "TR"));
            pieces[0].addLevel();
            assertTrue(pieces[0].move(collections, "TR"));
            assertEquals(1, pieces[0].getPosition().getY());
        } catch (OutOfBoardException e) {
            e.printStackTrace();
        }
        pieces[1].setBuff();
        assertFalse(pieces[1].move(collections, dice.rollDice()));
    }
}