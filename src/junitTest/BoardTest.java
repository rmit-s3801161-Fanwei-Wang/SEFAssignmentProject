

import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.GridsBeingTakenException;
import exception.InitializeException;
import exception.OutOfBoardException;
import models.*;

public class BoardTest {

	private Piece[] pieces = new Piece[4];
	Board board;
	HashMap<Position, Entity> collections;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		collections = board.getCollections();

		board.addCollection(new Ladder(new Position(1, 0), new Position(2, 2), "Ladder1"));
		board.addCollection(new Ladder(new Position(7, 0), new Position(1, 2), "Ladder2"));
		board.addCollection(new Ladder(new Position(3, 1), new Position(3, 3), "Ladder3"));
		board.addCollection(new Ladder(new Position(4, 3), new Position(0, 5), "Ladder4"));
		try {
			new Ladder(new Position(0, 9), new Position(0, 8), "Ladder5");
		} catch (InitializeException ex) {
			System.out.println(ex.getMessage());
		}
		board.addCollection(new Ladder(new Position(9, 4), new Position(0, 6), "Ladder5"));

		board.addCollection(new Snake(new Position(1, 9), new Position(1, 7), "Snake1"));
		board.addCollection(new Snake(new Position(3, 7), new Position(7, 5), "Snake2"));
		board.addCollection(new Snake(new Position(5, 5), new Position(6, 3), "Snake3"));
		board.addCollection(new Snake(new Position(8, 8), new Position(1, 6), "Snake4"));
		try {
			new Snake(new Position(0, 1), new Position(0, 8), "Snake5");
		} catch (InitializeException ex) {
			System.out.println(ex.getMessage());
		}
		board.addCollection(new Snake(new Position(6, 3), new Position(9, 1), "Snake5"));
		
		for (int i = 0; i < 4; i++) {
			pieces[i] = new Piece(null, Character.toString((char) (65 + i)));
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Dice dice = new Dice();
		Scanner scan = new Scanner(System.in);
		String choice;
		boolean okay;
		String snake;
		try {
			for (int i = 0; i < 10; i++) {
				for (int j=0;j<4;j++) {
					pieces[j].move(collections, dice.rollDice());
				}

				do {
					okay = false;
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
			}
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		finally {
			scan.close();
		}
	}
}
