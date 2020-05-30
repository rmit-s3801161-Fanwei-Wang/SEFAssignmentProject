package model.entity;

import java.awt.*;
//import javafx.beans.property.*;

abstract public class Entity {
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public void draw(Graphics g);

    abstract public String toDbString();
}
