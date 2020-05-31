package model.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Guard extends PGEntity{

	private static final String guardPath = "./src/model/icon/guard.png";

	public Guard(Position position,String name) {
		super(position,name);
	}

	@Override
	public void draw(ImageView imageView) {
		Image image = null;
		try {
			image = new Image(new FileInputStream(guardPath));
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toDbString() {
		return String.format("{\"Type\":\"Guard\",\"Name\":\"%s\",\"PositionX\":%d,\"PositionY\":%d}",
				super.getName(),
				super.getPosition().getX(),super.getPosition().getY());
	}


}
