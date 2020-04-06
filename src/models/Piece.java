package models;

public class Piece {

	private String name;
	private int level = 1;
	private Position position = null;
	private boolean debuff = false;

	public Piece(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void move(int dice) {
		if (position == null) {
			System.out.println(name + " move to " + dice);
			position = new Position(dice - 1, 0, this);
			return;
		}
		if (!debuff) {
			System.out.print(name + " move from " + position.positionToInt());
			this.position.move(dice);
			System.out.println(" to " + position.positionToInt());
		}
		else
			System.out.println(name + " cannot move when having buff");
	}
	
	public void move(String choice) throws Exception {
		position.move(choice);
	}

	public Position getPosition() {
		return position;
	}

	public void setBuff() {
		debuff = true;
	}

}
