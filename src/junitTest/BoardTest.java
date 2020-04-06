package junitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.InitializeException;
import exception.OutOfBoardException;
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


		ladders[0] = new Ladder(new Position(1,0),new Position(2,2));
		ladders[1] = new Ladder(new Position(7,0),new Position(1,2));
		ladders[2] = new Ladder(new Position(3,1),new Position(3,3));
		ladders[3] = new Ladder(new Position(4,3),new Position(0,5));
		ladders[4] = new Ladder(new Position(9,4),new Position(0,6));
		
		for(int i=0;i<ladders.length;i++)
			board.addCollection(ladders[i]);
		
		snakes[0] = new Snake(new Position(1,9),new Position(1,7));
		snakes[1] = new Snake(new Position(3,7),new Position(7,5));
		snakes[2] = new Snake(new Position(5,5),new Position(6,3));
		snakes[3] = new Snake(new Position(8,8),new Position(1,6));
		snakes[4] = new Snake(new Position(6,3),new Position(9,1));
		
		for(int i=0;i<ladders.length;i++) {
//			System.out.println(snakes[i].getHead().positionToInt());
			board.addCollection(snakes[i]);
		}
		
		for (int i = 0; i < 4; i++) {
			pieces.add(new Piece(null, null, Character.toString((char)(65 + i))));
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
							((Snake) v).adajustPosition(p);
						else
							((Ladder) v).adajustPosition(p);;
					}
				});
			}			
		}
	}
	
	@Test
	public void snakeMoveTest2() {
		try {
			snakes[1].move(events, false, false);
			snakes[2].move(events, false, true);				
			snakes[3].move(events, true, true);	
		} catch (OutOfBoardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
}
	
	
	@Test (expected = OutOfBoardException.class)
	public void snakeMoveTest() throws OutOfBoardException {
			snakes[0].move(events, true, true);	
	}
	
	@Test 
	public void initialTest()  {
			try {
				Snake s = new Snake(new Position(0 , 1),new Position(0,8));
			} catch (InitializeException e) {
				System.out.println(e.toString());
			}
	}
	
	@Test 
	public void initialTest2()  {
			try {
				Ladder l = new Ladder(new Position(0 , 4),new Position(0,1));
				Ladder l1 = new Ladder(new Position(0 , 1),new Position(0,8));
			} catch (InitializeException e) {
				System.out.println(e.toString());
			}
	}
}
