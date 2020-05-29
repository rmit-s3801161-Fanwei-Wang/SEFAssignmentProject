package model.entity;

import java.awt.Graphics;

public class Guard extends PGEntity{

	public Guard(Position position,String name) {
		super(position,name);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toDbString() {
		return String.format("{\"Type\":\"Guard\",\"Name\":\"%s\",\"PositionX\":%d,\"PositionY\":%d}",
				super.getName(),
				super.getPosition().getX(),super.getPosition().getY());
	}


}
