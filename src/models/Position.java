package models;

public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXY(int x, int y) {
		setX(x);
		setY(y);
	}

	public void move(int dice) {
		intToPosition(this.positionToInt() + dice);
	}

	public Position move(String choice) {
		return null;// 先不急，人走马步用，蛇斜着走
	}

	public int positionToInt() {
		return this.getY() % 2 == 0 ? (this.getY() * 10 + this.getX() + 1) : (this.getY() * 10 + 10 - this.getX());
	}

	private void intToPosition(int z) {
		if (z / 10 % 2 == 0)
			this.setXY(z % 10-1 , z / 10);
		else
			this.setXY((100-z) % 10, z / 10);
	}
}
