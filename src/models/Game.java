package models;

public class Game {
    private String gameID = idGenerator();
    private static int uniqueID = 0;
    private Player snakePlayer;
    private Player humanPlayer;
    private Player[] players = new Player[2];// players[0] is snake control ID, players[1] is Human control ID
    private Board board;
    private int round;

    public Game(Player snakePlayer,Player humanPlayer, Board board, int round) {
        this.snakePlayer = snakePlayer;
        this.humanPlayer = humanPlayer;
        players = new Player[]{snakePlayer, humanPlayer};
        this.board = board;
        this.round = round;
    }

    public Game(Player snakePlayer,Player humanPlayer, Board board) {
        this(snakePlayer,humanPlayer,board,0);
    }

    public Game() {
        round = 0;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
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
        String snake = "N/A";
        String human = "N/A";
        if(snakePlayer != null)
            snake = snakePlayer.getUserID();
        if(humanPlayer != null)
            human = humanPlayer.getUserID();
        return String.format("Game[ ID: %s   Snake PlayerID: %s   Human PlayerID: %s ]", gameID, snake,human);
    }

    public static void main(String[] args) {
        Player snake = new Player("s1","123","123","@");
        Player human = new Player("ss2","123","2123","@");
        Game game = new Game(snake,human,new Board());
        System.out.println(game);
    }
}
