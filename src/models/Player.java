package models;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends User {
    static int uniqueID = 0;
    private ArrayList<Game> savedGame = new ArrayList<>();
    private Game playing = null;

    public Player() {
    }

    public Player(String username, String password, String userName, String userEmail) {
        super(username, password, userName, userEmail);
    }

    @Override
    String idGenerator() {
        return String.format("player%06d",++uniqueID);
    }

    //TODO Don't throw Exception
    public Game loadGame(String gameID){
        boolean foundGame = false;
        for(String id: getGames().keySet())
            if(id.equals(gameID)){
                foundGame = true;
                if(Arrays.asList(getGames().get(id).getPlayers()).contains(getUserID())){
                    return getGames().get(id);
                }
            }
        if(foundGame)
            throw new IllegalArgumentException("Failed to load game. You didn't played this game");
        else
            throw new IllegalArgumentException("Failed to load Game. Game not exit.");
    }

    //Generate a new Game with unique GameID and default round = 0;
    //Player could choose role to play.
    //And admin should set a board before start the game.
    public Game startNewGame(){
        Game newGame = new Game();
        saveGame(newGame);
        return newGame;
    }

    //Save game in this player's savedGame
    //And update the game status to games
    public void saveGame(Game game){
        playing = game;
        savedGame.add(game);
        getGames().put(game.getGameID(),game);
    }

    public void saveGame(){
        saveGame(playing);
    }

    //TODO implement pauseGame
    public boolean pauseGame(){
        return false;
    }

    //TODO implement resumeGame
    public boolean resumeGame(){
        return false;
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("Player[ " + "ID: " + getUserID() + "   Username: " + getUserName());
        ret.append("   Saved Game: ");
        for(int i = 0; i < savedGame.size(); i++){
            ret.append(savedGame.get(i).getGameID());
            if (i != savedGame.size() -1){
                ret.append(",");
            }
        }
        ret.append(" ]");
        return ret.toString();
    }

    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();
        Game game = new Game(player1,player2,new Board());
        player1.saveGame(game);
        for (Game g: player2.getGames().values()) {
            System.out.println(g);
        }

    }
}
