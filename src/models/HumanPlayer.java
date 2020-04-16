package models;

public class HumanPlayer extends Player {
    private Piece[] pieces = new Piece[4];
    private Guard[] guards = new Guard[3];
    
    //TODO implement
    public int rollDice(Dice dice){
        return dice.rollDice();
    }

    //TODO implement
    public void placeGuard(){

    }

    //TODO implement
    public Piece choosePiece(String choice){
        return null;
    }

    //TODO
    public boolean isEnd(){
        return false;
    }



}
