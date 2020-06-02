package model.junitTest;

import model.entity.*;
import model.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Piece[] pieces = new Piece[4];
    Board board;
    ArrayList<Entity> collections;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        collections = board.getCollections();

        board.addCollection(new Ladder(new Position(1, 0), new Position(2, 2), "L1",collections));
        board.addCollection(new Ladder(new Position(7, 0), new Position(1, 2), "L2",collections));
        board.addCollection(new Ladder(new Position(3, 1), new Position(3, 3), "L3",collections));
        board.addCollection(new Ladder(new Position(4, 3), new Position(0, 5), "L4",collections));
        board.addCollection(new Ladder(new Position(9, 4), new Position(0, 6), "L5",collections));

        board.addCollection(new Snake(new Position(1, 5), new Position(1, 3), "S1", collections));
        board.addCollection(new Snake(new Position(3, 7), new Position(7, 5), "S2", collections));
        board.addCollection(new Snake(new Position(5, 5), new Position(6, 3), "S3", collections));
        board.addCollection(new Snake(new Position(8, 8), new Position(1, 6), "S4", collections));
        board.addCollection(new Snake(new Position(5, 3), new Position(9, 1), "S5", collections));

        for (int i = 0; i < 4; i++) {
            pieces[i] = new Piece(null, Character.toString((char) (65 + i)));
        }
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test(expected = InitializeException.class)
    public void testLadder() throws InitializeException, GridsBeingTakenException {
        new Ladder(new Position(0, 8), new Position(0, 4), "Ladder",new ArrayList<>());
    }

    @Test(expected = InitializeException.class)
    public void testLadder1() throws InitializeException, GridsBeingTakenException {
        new Ladder(new Position(0, 7), new Position(0, 9), "Ladder",new ArrayList<>());
    }

    @Test(expected = InitializeException.class)
    public void testSnake() throws InitializeException, OnlyOneSnakeGreaterEightyException, GridsBeingTakenException {
        new Snake(new Position(0, 1), new Position(0, 8), "Snake",new ArrayList<>());
    }

    @Test(expected = InitializeException.class)
    public void testSnake1() throws InitializeException, OnlyOneSnakeGreaterEightyException, GridsBeingTakenException {
        new Snake(new Position(0, 9), new Position(0, 7), "Snake",new ArrayList<>());
    }

}
