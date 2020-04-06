package models;

import java.awt.Graphics;
import java.util.HashMap;

import exception.InitializeException;
import exception.OutOfBoardException;

public class Snake extends Entity{
	private Position head;
	private Position tail;

	public Snake(Position head, Position tail) throws InitializeException {
		super(head,tail);
		this.head = head;
		this.tail = tail;
		InitializeException ex = new InitializeException("Head:" + head.positionToInt() + " ,Tail:"+ tail.positionToInt() + " is not possible");
		if(this.head.positionToInt() - this.tail.positionToInt() > 30 || this.tail.positionToInt() > this.head.positionToInt()) {
			throw ex;
		}
	}

	public Position getHead() {
		return head;
	}

	public Position getTail() {
		return tail;
	}

	public boolean move(HashMap<Position, Object> collections, boolean top, boolean left) throws OutOfBoardException {
		
		Position headDestination = new Position(head.getX(), head.getY());
		Position tailDestination = new Position(tail.getX(), tail.getY());
		if (top && left) {
			headDestination.move("TL");
			tailDestination.move("TL");
		}
		else if (top && !left) {
			headDestination.move("TR");
			tailDestination.move("TR");
		}
		else if (!top && !left) {
			headDestination.move("BR");
			tailDestination.move("BR");
		}
		else {
			headDestination.move("BL");
			tailDestination.move("BL");
		}

		for (Position p : collections.keySet()) {
			if (p.compareTo(headDestination) || p.compareTo(tailDestination)) {
				if (collections.get(p).equals(this) || collections.get(p) instanceof Piece)
					continue;
				else
					// Exception
					return false;
			}
		}
		
		System.out.print("Snake head moves from " + head.positionToInt());
		head.setXY(headDestination);
		System.out.println(" to " + head.positionToInt());
		
		System.out.print("Snake tail moves from " + tail.positionToInt());
		tail.setXY(tailDestination);
		System.out.println(" to " + tail.positionToInt());
		
		for (Position p : collections.keySet()) {
			if (p.compareTo(head)) {
				if (collections.get(p) instanceof Piece)
					adajustPosition((Piece) collections.get(p));
			}
		}
		return true;
	}

//	public void eat(Piece piece) {
//		if (piece.getPosition() != null)
//			if (piece.getPosition().compareTo(head)) {
//				piece.getPosition().setXY(tail);
//				System.out.println(piece.getName() + " is eaten by a snake to " + tail.positionToInt());
//				piece.setBuff();
//			}
//	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
