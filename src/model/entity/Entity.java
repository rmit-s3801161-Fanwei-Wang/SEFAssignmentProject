package model.entity;

import com.google.gson.JsonObject;

import javafx.scene.image.ImageView;

abstract public class Entity {
	private String name;

	public Entity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	abstract public JsonObject toDbString();
}
