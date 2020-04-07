package junitTest;

import java.util.ArrayList;
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

	Board board;
	Ladder[] ladders;
	Snake[] snakes;
	Guard[] guards;
	ArrayList<Piece> pieces;
	HashMap<Position, Entity> events;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		ladders = board.getLadders();
		snakes = board.getSnakes();
		guards = board.getGuards();
		pieces = board.getPieces();
		events = board.getCollections();

		ladders[0] = new Ladder(new Position(1, 0), new Position(2, 2), "Ladder1");
		ladders[1] = new Ladder(new Position(7, 0), new Position(1, 2), "Ladder2");
		ladders[2] = new Ladder(new Position(3, 1), new Position(3, 3), "Ladder3");
		ladders[3] = new Ladder(new Position(4, 3), new Position(0, 5), "Ladder4");
		try {
			ladders[4] = new Ladder(new Position(0, 9), new Position(0, 8), "Ladder5");
		} catch (InitializeException ex) {
			System.out.println(ex.getMessage());
		}
		ladders[4] = new Ladder(new Position(9, 4), new Position(0, 6), "Ladder5");

		for (int i = 0; i < ladders.length; i++)
			board.addCollection(ladders[i]);

		snakes[0] = new Snake(new Position(1, 9), new Position(1, 7), "Snake1");
		snakes[1] = new Snake(new Position(3, 7), new Position(7, 5), "Snake2");
		snakes[2] = new Snake(new Position(5, 5), new Position(6, 3), "Snake3");
		snakes[3] = new Snake(new Position(8, 8), new Position(1, 6), "Snake4");
		try {
			snakes[4] = new Snake(new Position(0, 1), new Position(0, 8), "Snake5");
		} catch (InitializeException ex) {
			System.out.println(ex.getMessage());
		}
		snakes[4] = new Snake(new Position(6, 3), new Position(9, 1), "Snake5");

		for (int i = 0; i < ladders.length; i++) {
			board.addCollection(ladders[i]);
		}

		for (int i = 0; i < snakes.length; i++) {
			board.addCollection(snakes[i]);
		}

		for (int i = 0; i < 4; i++) {
			pieces.add(new Piece(null, Character.toString((char) (65 + i))));
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
		int snake;
		try {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < pieces.size(); j++) {
					pieces.get(j).move(events, dice.rollDice());
				}

				do {
					okay = false;
					System.out.print("Select snake:");
					snake = scan.nextInt();
					scan.nextLine();
					System.out.println("TL,TR,BR,BL:");
					choice = scan.nextLine();
					try {
						snakes[snake].move(events, choice);
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
		scan.close();
	}
}
