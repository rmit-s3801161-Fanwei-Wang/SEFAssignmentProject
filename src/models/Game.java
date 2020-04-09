package models;

public class Game {
    private String gameID = idGenerator();
    private static int uniqueID = 0;
    private Player[] players = new Player[2];// players[0] is snake control ID, players[1] is Human control ID
    private Board board;
    private int round;

    public Game(Player snakePlayer,Player humanPlayer, Board board, int round) {
        players[0] = snakePlayer;
        players[1] = humanPlayer;
        this.board = board;
        this.round = round;
    }

    public Game(Player snakePlayer,Player humanPlayer, Board board) {
        this(snakePlayer,humanPlayer,board,0);
    }

    public String getGameID() {
        return gameID;
    }

    public int addRound(){
        return round++;
    }

    private String idGenerator() {
        return String.format("game%06d", ++uniqueID);
    }


    @Override
    public String toString() {
        return String.format("Game[ ID: %s   BoardID: %s   Snake PlayerID: %s   Human PlayerID: %s ]",gameID,"board.getBoardID()",players[0].getId(),players[1].getId() );
        //TODO generate boardID
    }
}
