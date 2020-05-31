package model.entity;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.exception.InitializeException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Ladder extends SLEntity {
	private String ladderName = "./src/model/icon/ladder1.png";

	public Ladder(Position bottom, Position top, String name) throws InitializeException {
		super(bottom, top, name);
		InitializeException ex = new InitializeException(
				("Bottom:" + bottom.positionToInt() + " ,Top:" + top.positionToInt() + " is not possible"));
		if (super.getExit().positionToInt() - super.getEntry().positionToInt() > 30
				|| super.getEntry().positionToInt() > super.getExit().positionToInt()
				|| super.getExit().positionToInt() == 100) {
//							|| super.getEntry().getY() == super.getExit().getY()
			throw ex;
		}
	}

	@Override
	public boolean adjustPosition(Piece piece) {
		if (super.adjustPosition(piece)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION,piece.getName() + " climb " + super.getName() + " to " + super.getExit().positionToInt());
			alert.showAndWait();
			return true;
		}
		return false;
	}

	@Override
	public void draw(ImageView imageView,Position position) {
		Image image = null;
		try {
			if(this.getName().compareToIgnoreCase("L1")!=0){
				ladderName = ladderName.replace('1',this.getName().charAt(1));
			}
			image = new Image(new FileInputStream(ladderName.toString()));
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toDbString() {
		return String.format("{\"Type\":\"Ladder\",\"Name\":\"%s\",\"TopX\":\"%d\",\"TopY\":\"%d\",\"BotX\":\"%d\",\"BotY\":\"%d\"}", super.getName(),
				super.getExit().getX(),super.getExit().getY(),super.getEntry().getX(),super.getEntry().getY());
	}

}
