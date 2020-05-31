package model.exception;

public class CannotMoveException extends Exception {
    private static final long serialVersionUID = 4L;
    public CannotMoveException(String s) {
        super(s);
    }
}
