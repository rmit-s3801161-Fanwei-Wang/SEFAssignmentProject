package models;

import java.util.HashMap;

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

	public Position getTail() {
		return tail;
	}

	public boolean move(HashMap<Position, Object> collections, boolean top, boolean left) throws Exception {
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
		Position headDestination = new Position(head.getX(), head.getY());
		Position tailDestination;
		if (top && left)
			headDestination.move("TL");
		else if (top && !left)
			headDestination.move("TR");
		else if (!top && !left)
			headDestination.move("BR");
		else
			headDestination.move("BL");
		tailDestination = new Position(headDestination.getX() - relativeX, headDestination.getY() - relativeY);

		for (Position p : collections.keySet()) {
			if (p.compareTo(headDestination) || p.compareTo(tailDestination)) {
				if (collections.get(p).equals(this) || collections.get(p) instanceof Piece)
					continue;
				else
					// Exception
					return false;
			}
		}
		head.setXY(headDestination);
		tail.setXY(tailDestination);
		for (Position p : collections.keySet()) {
			if (p.compareTo(head)) {
				if (collections.get(p) instanceof Piece)
					eat((Piece)collections.get(p));
			}
		}
		return true;
	}

	public void eat(Piece piece) {
		if (piece.getPosition() != null)
			if (piece.getPosition().compareTo(head)) {
				piece.getPosition().setXY(tail);
				System.out.println(piece.getName() + " is eaten by a snake to " + tail.positionToInt());
				piece.setBuff();
			}
	}
}
