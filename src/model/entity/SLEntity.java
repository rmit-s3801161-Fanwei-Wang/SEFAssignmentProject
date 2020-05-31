package model.entity;


import javafx.scene.image.ImageView;

abstract public class SLEntity extends Entity{
    private Position entry;
    private Position exit;

    public SLEntity(Position entry, Position exit, String name) {
        super(name);
        this.entry = entry;
        this.exit = exit;
    }

    public Position getEntry() {
        return entry;
    }

    public Position getExit() {
        return exit;
    }

    public boolean adjustPosition(Piece piece) {
        if (exit == null || entry == null)
            return false;
        if (piece.getPosition() != null)
            if (piece.getPosition().compareTo(entry)) {
                piece.getPosition().setXY(exit);
                return true;
            }
        return false;
    }

    abstract public void draw(ImageView imageView,Position position);

}
