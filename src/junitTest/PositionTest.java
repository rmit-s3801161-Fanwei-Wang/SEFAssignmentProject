package junitTest;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Piece;
import models.Position;

public class PositionTest {
	Position p;

	@Before
	public void setUp() {
		p = new Position(0, 0, new Piece("A"));
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
		assertEquals("A", ((Piece)p.getOccupy()).getName());
	}

	@Test
	public void testMovePositionInt() {
		Position p1 = new Position(5, 7, new Piece("B"));
		p1.move(1);
		assertEquals(4, p1.getX());
		assertEquals(76, p1.positionToInt());
		assertEquals("B", ((Piece)p1.getOccupy()).getName());

	}

}
