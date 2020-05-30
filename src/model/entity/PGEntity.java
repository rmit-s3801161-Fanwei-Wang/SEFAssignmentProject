package model.entity;

import java.awt.*;

abstract public class PGEntity extends Entity{
    private Position position;
    public PGEntity(Position position, String name) {
        super(name);
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }
}
