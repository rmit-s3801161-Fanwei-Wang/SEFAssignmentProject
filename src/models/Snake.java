package models;

import java.util.ArrayList;

public class Snake {
	private Position head;
	private Position tail;
	private static int relativeX;
	private static int relativeY;

	public Snake(Position head, Position tail) {
		this.head = head;
		this.tail = tail;
		relativeX = head.getX() - tail.getX();
		relativeY = head.getY() - tail.getY();
		// relativeY should be greater than 0
	}

	public Position getHead() {
		return head;
	}

	public boolean move(ArrayList<Position> positions, boolean top, boolean left) {
		if (top) {
			if (head.getY() + 1 > 9)
				// outOfBoardException
				return false;
		} else {
			if (tail.getY() - 1 < 0)
				// outOfBoardException
				return false;
		}
		if (left) {
			if (head.getX() - 1 < 0 || tail.getX() - 1 < 0)
				// outOfBoardException
				return false;
		} else {
			if (head.getX() + 1 > 9 || tail.getX() + 1 > 9)
				// outOfBoardException
				return false;
		}
		Position headDestination = new Position(head.getX(), head.getY(), null);
		Position tailDestination;
		if (top && left)
			headDestination.move("TL");
		else if (top && !left)
			headDestination.move("TR");
		else if (!top && !left)
			headDestination.move("BR");
		else
			headDestination.move("BL");
		tailDestination = new Position(headDestination.getX() - relativeX, headDestination.getY() - relativeY, null);
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for (Position p : positions) {
			if (p.equals(this.getHead()) || p.equals(this.getTail()))
				continue;
			// Snake or Ladder or Guard
			if (!(p.getOccupy() instanceof Piece)) {
				if (p.compareTo(headDestination) || p.compareTo(tailDestination))
					return false;
			}
			// Piece
			else {
				if (p.compareTo(headDestination))
					pieces.add((Piece) p.getOccupy());
			}
		}
		head.setXY(headDestination);
		tail.setXY(tailDestination);
		eat(pieces);
		return true;
	}

	public Position getTail() {
		return tail;
	}

	public void eat(ArrayList<Piece> pieces) {
		for (Piece piece : pieces) {
			if (piece.getPosition().compareTo(head)) {
				piece.setPosition(tail);
				piece.setBuff();
			}
		}
	}
}
