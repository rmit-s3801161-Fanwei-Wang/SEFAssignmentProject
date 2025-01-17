package model.junitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import model.exception.CannotMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.exception.OutOfBoardException;
import model.entity.*;

public class PieceTest {
	Piece[] pieces = new Piece[6];
	Dice dice = new Dice();
	ArrayList<Entity> collections = new ArrayList<>();
	
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
	public void testMove() throws CannotMoveException {
		Dice dice = new Dice();
		pieces[0].move(collections, dice.rollDice());
		pieces[1].move(collections, dice.rollDice());
		try {
			assertFalse(pieces[0].move(collections, "TR"));
			pieces[0].addLevel();
			assertFalse(pieces[0].move(collections, "TR"));
			pieces[0].addLevel();
			assertTrue(pieces[0].move(collections, "TR"));
			assertEquals(1,pieces[0].getPosition().getY());
		} catch (OutOfBoardException e) {
			e.printStackTrace();
		}
		pieces[1].setBuff();
		assertFalse(pieces[1].move(collections, dice.rollDice()));
	}
}