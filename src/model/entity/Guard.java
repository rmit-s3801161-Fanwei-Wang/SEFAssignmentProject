package model.entity;

import javafx.scene.image.ImageView;

public class Guard extends PGEntity{

	public Guard(Position position,String name) {
		super(position,name);
	}

	@Override
	public void draw(ImageView imageView) {
		return;
	}

	@Override
	public String toDbString() {
		return String.format("{\"Type\":\"Guard\",\"Name\":\"%s\",\"PositionX\":%d,\"PositionY\":%d}",
				super.getName(),
				super.getPosition().getX(),super.getPosition().getY());
	}


}
