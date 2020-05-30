import model.entity.*;
import model.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    Board board;
    HashMap<Position, Entity> collections;
    private Piece[] pieces = new Piece[4];

    @Before
    public void setUp() throws Exception {
        board = new Board();
        collections = board.getCollections();

        board.addCollection(new Ladder(new Position(1, 0), new Position(2, 2), "L1"));
        board.addCollection(new Ladder(new Position(7, 0), new Position(1, 2), "L2"));
        board.addCollection(new Ladder(new Position(3, 1), new Position(3, 3), "L3"));
        board.addCollection(new Ladder(new Position(4, 3), new Position(0, 5), "L4"));
        board.addCollection(new Ladder(new Position(9, 4), new Position(0, 6), "L5"));

        board.addCollection(new Snake(new Position(1, 9), new Position(1, 7), "S1"));
        board.addCollection(new Snake(new Position(3, 7), new Position(7, 5), "S2"));
        board.addCollection(new Snake(new Position(5, 5), new Position(6, 3), "S3"));
        board.addCollection(new Snake(new Position(8, 8), new Position(1, 6), "S4"));
        board.addCollection(new Snake(new Position(5, 3), new Position(9, 1), "S5"));

        for (int i = 0; i < 4; i++) {
            pieces[i] = new Piece(null, Character.toString((char) (65 + i)));
        }
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test(expected = InitializeException.class)
    public void testLadder() throws InitializeException {
        new Ladder(new Position(0, 8), new Position(0, 4), "Ladder");
    }

    @Test(expected = InitializeException.class)
    public void testLadder1() throws InitializeException {
        new Ladder(new Position(0, 7), new Position(0, 9), "Ladder");
    }

    @Test(expected = InitializeException.class)
    public void testLadder2() throws InitializeException {
        new Ladder(new Position(0, 0), new Position(3, 0), "Ladder");
    }

    @Test(expected = InitializeException.class)
    public void testSnake() throws InitializeException {
        new Snake(new Position(0, 1), new Position(0, 8), "Snake");
    }

    @Test(expected = InitializeException.class)
    public void testSnake1() throws InitializeException {
        new Snake(new Position(0, 9), new Position(0, 7), "Snake");
    }

    @Test(expected = InitializeException.class)
    public void testSnake2() throws InitializeException {
        new Snake(new Position(7, 0), new Position(2, 0), "Snake");
    }

    @Test(expected = GridsBeingTakenException.class)
    public void testMoveSnake() throws GridsBeingTakenException, OutOfBoardException {
        for (Entity e : collections.values()) {
            if (e.getName().compareToIgnoreCase("S1") == 0) {
                ((Snake) e).move(collections, "BL");
                break;
            }
        }
    }

    @Test(expected = OutOfBoardException.class)
    public void testMoveSnake1() throws GridsBeingTakenException, OutOfBoardException {
        for (Entity e : collections.values()) {
            if (e.getName().compareToIgnoreCase("S1") == 0) {
                ((Snake) e).move(collections, "TR");
                break;
            }
        }
    }

    @Test
    public void testLevel1() throws GridsBeingTakenException, OutOfBoardException {
        board.viewBoard();
        Dice dice = new Dice();
        for (int j = 0; j < 4; j++) {
            int m = dice.rollDice();
            pieces[j].move(collections, m);
            if (m == 6)
                pieces[j].move(collections, dice.rollDice());
        }
        for (Entity e : collections.values()) {
            if (e.getName().compareToIgnoreCase("S3") == 0) {
                ((Snake) e).move(collections, "BR");
                assertEquals(((Snake) e).getEntry().positionToInt(), 47);
                break;
            }
        }
        for (Entity e : collections.values()) {
            System.out.println(e.toDbString());
        }
        board.viewBoard();
    }

    @Test
    public void testLevel3() throws InitializeException {
        for (int j = 0; j < 4; j++) {
            pieces[j].move(collections, 1);
        }

        pieces[0].move(collections, 17);
        pieces[1].move(collections, 35);
        pieces[2].move(collections, 53);
        pieces[3].move(collections, 71);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= 1; j++) {
                pieces[i].addLevel();
            }
        }

        board.viewBoard();

        try {
            pieces[1].move(collections, "T2R1");
            pieces[3].move(collections, "B1L2");
            board.viewBoard();
            for (Entity e : collections.values()) {
                if (e.getName().compareTo("S3") == 0) {
                    ((Snake) e).move(collections, "TR");
                    assertEquals(((Snake) e).getExit().positionToInt(), 48);
                    break;
                }
            }
        } catch (OutOfBoardException e) {
            e.printStackTrace();
        } catch (GridsBeingTakenException e1) {
            e1.printStackTrace();
        } finally {
            board.viewBoard();
        }
    }

}
