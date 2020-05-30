import model.entity.Position;
import model.exception.OutOfBoardException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    Position p;

    @Before
    public void setUp() {
        p = new Position(0, 0);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPosition() {
        p.move(1);
        assertEquals(1, p.getX());
        p.move(6);
        assertEquals(7, p.getX());
        p.move(6);
        assertEquals(1, p.getY());
    }

    @Test
    public void testMovePositionInt() {
        Position p1 = new Position(5, 7);
        p1.move(1);
        assertEquals(4, p1.getX());
        assertEquals(76, p1.positionToInt());
    }

    @Test(expected = OutOfBoardException.class)
    public void testMove() throws OutOfBoardException {
        Position p2 = new Position(4, 0);
        p2.move("TL");
        assertEquals(17, p2.positionToInt());
        p2.move("B1L2");
        assertEquals(2, p2.positionToInt());
        p2.move("BR");
    }

}
