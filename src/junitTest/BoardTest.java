package junitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.*;

public class BoardTest {

	Board board;
	Ladder[] ladders;
	Snake[] snakes;
	Guard[] guards;
	ArrayList<Piece> pieces;
	HashMap<Position, Object> events;
	@Before
	public void setUp() throws Exception {
		board = new Board();
		ladders = board.getLadders();
		snakes = board.getSnakes();
		guards = board.getGuards();
		pieces = board.getPieces();
		events = board.getCollections();


		ladders[0] = new Ladder(new Position(1,0),new Position(2,7));
		ladders[1] = new Ladder(new Position(7,0),new Position(1,4));
		ladders[2] = new Ladder(new Position(3,1),new Position(3,9));
		ladders[3] = new Ladder(new Position(4,3),new Position(0,6));
		ladders[4] = new Ladder(new Position(9,4),new Position(0,7));
		
		for(int i=0;i<ladders.length;i++)
			board.addCollection(ladders[i]);
		
		snakes[0] = new Snake(new Position(1,9),new Position(1,1));
		snakes[1] = new Snake(new Position(3,7),new Position(7,3));
		snakes[2] = new Snake(new Position(5,5),new Position(6,2));
		snakes[3] = new Snake(new Position(8,8),new Position(1,2));
		snakes[4] = new Snake(new Position(6,3),new Position(9,0));
		
		for(int i=0;i<ladders.length;i++) {
			System.out.println("Snake head: " + snakes[i].getHead().positionToInt());
			board.addCollection(snakes[i]);
		}
		
		for (int i = 0; i < 4; i++) {
			pieces.add(new Piece(Character.toString((char) 65 + i)));
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Dice dice = new Dice();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < pieces.size(); j++) {
				pieces.get(j).move(dice.rollDice());
				Piece p = pieces.get(j);
				if(i==0)
					board.addCollection(p);
				if (p.getPosition() != null)
					events.put(p.getPosition(), p);
				events.forEach((k, v) -> {
					if (!(v instanceof Piece) && k.compareTo(p.getPosition())) {
						if (v instanceof Snake)
							((Snake) v).eat(p);
						else
							((Ladder) v).climb(p);
					}
				});
			}			
		}
	}
	
	public void snakeMoveTest() {
		
	}

}
