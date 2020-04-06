package junitTest;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Dice;
import models.Piece;

public class PieceTest {
	Piece[] pieces = new Piece[6];
	Dice dice = new Dice();

	@Before
	public void setUp() throws Exception {

		pieces[0] = new Piece(null, null, "A");
		pieces[1] = new Piece(null, null, "B");
		pieces[2] = new Piece(null, null, "C");
		pieces[3] = new Piece(null, null, "D");
		pieces[4] = new Piece(null, null, "E");
		pieces[5] = new Piece(null, null, "F");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMove() {
		pieces[0].move(1);
		assertEquals(0, pieces[0].getPosition().getX());
		assertEquals(0, pieces[0].getPosition().getY());
		pieces[1].move(dice.rollDice());
		assertFalse(pieces[1].getPosition() == null);
		assertTrue(pieces[2].getPosition() == null);
		pieces[2].move(dice.rollDice());
		assertFalse(pieces[2].getPosition() == null);
	}

	@Test
	public void testSetBuff() {
		pieces[3].move(1);
		pieces[3].setBuff();
		pieces[3].move(6);
		assertFalse(pieces[3].getPosition().getX() > 0);
		for(int i=0;i<20;i++)
			pieces[4].move(5);
	}
	
	@Test
	public void testMoveALot() {
		int pre = 0;
		int value;
		for(int i=0;i<16;i++) {
			if(i!=0)
				pre = pieces[5].getPosition().positionToInt();
			value = dice.rollDice();
			System.out.println("Dice:"+value);
			pieces[5].move(value);
			assertTrue(pieces[5].getPosition().positionToInt()-pre<7);
		}
	}

}
