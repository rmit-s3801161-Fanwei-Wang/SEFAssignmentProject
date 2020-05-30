package model.exception;

public class OutOfBoardException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OutOfBoardException() {
        super("outofbound");
        // TODO Auto-generated constructor stub
    }


    public OutOfBoardException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }


}
