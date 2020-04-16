package junitTest;

import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.*;
import models.*;

public class BoardTest {

	private Piece[] pieces = new Piece[4];
	Board board;
	HashMap<Position, Entity> collections;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		collections = board.getCollections();

		board.addCollection(new Ladder(new Position(1, 0), new Position(2, 2), "Q"));
		board.addCollection(new Ladder(new Position(7, 0), new Position(1, 2), "W"));
		board.addCollection(new Ladder(new Position(3, 1), new Position(3, 3), "R"));
		board.addCollection(new Ladder(new Position(4, 3), new Position(0, 5), "T"));
		
		board.addCollection(new Ladder(new Position(9, 4), new Position(0, 6), "Y"));

		board.addCollection(new Snake(new Position(1, 9), new Position(1, 7), "1"));
		board.addCollection(new Snake(new Position(3, 7), new Position(7, 5), "2"));
		board.addCollection(new Snake(new Position(5, 5), new Position(6, 3), "3"));
		board.addCollection(new Snake(new Position(8, 8), new Position(1, 6), "4"));
		
		board.addCollection(new Snake(new Position(5, 3), new Position(9, 1), "5"));
		
		for (int i = 0; i < 4; i++) {
			pieces[i] = new Piece(null, Character.toString((char) (65 + i)));
		}
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test(expected = InitializeException.class)
	public void testLadder() throws InitializeException {
		new Ladder(new Position(0, 8), new Position(0, 4), "Ladder5");
	}
		
	
	
	@Test(expected = InitializeException.class)
	public void testSnake() throws InitializeException {
		new Snake(new Position(0, 1), new Position(0, 8), "Snake5");
	}
	
	@Test
	public void test() {
		
		Dice dice = new Dice();
		Scanner scan = new Scanner(System.in);
		String choice;
		boolean okay = true;
		String snake;
		try {
			for (int i = 0; i < 50; i++) {
				for (int j=0;j<4;j++) {
					pieces[j].move(collections, dice.rollDice());
				}
				board.viewBoard();
				do {
					System.out.print("Select snake:");
					snake = scan.nextLine();
					if(snake.compareToIgnoreCase("Q")==0)
						throw new Exception("Quit!");
					try {
						for(Entity e: collections.values()) {
							if(e instanceof Snake) {
								if(e.getName().compareToIgnoreCase(snake)==0) {
									System.out.println("TL,TR,BR,BL:");
									choice = scan.nextLine();
									((Snake)e).move(collections, choice);
									okay = false;
									break;
								}
							}
						}
					} catch (OutOfBoardException e) {
						System.out.println(e.toString());
						okay = true;
					}
					catch(GridsBeingTakenException e2) {
						System.out.println(e2.toString());
						okay = true;
					}
				} while (okay);
				board.viewBoard();
			}
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		finally {
			scan.close();
		}
	}
}
