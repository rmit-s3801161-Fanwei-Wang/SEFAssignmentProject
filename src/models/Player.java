package models;

import java.util.ArrayList;

public class Player extends User {
    static int uniqueID = 0;
    private ArrayList<Game> gameHistories = new ArrayList<>();

    public Player() {
    }

    public Player(String username, String password, String userName, String userEmail) {
        super(username, password, userName, userEmail);
    }

    @Override
    String idGenerator() {
        return String.format("player%06d",++uniqueID);
    }

    //TODO implement load Game
    public boolean loadGame(String gameID){
        return false;
    }

    //TODO implement startNewGame
    public Game startNewGame(){
        return null;
    }

    //TODO implement saveGame
    public Game saveGame(){
        return null;
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
        ret.append("Player[ " + "ID: " + getId() + "   Username: " + getUserName());
        ret.append("   Game Histories: ");
        for(int i = 0; i < gameHistories.size(); i++){
            ret.append(gameHistories.get(i).getGameID());
            if (i != gameHistories.size() -1){
                ret.append(",");
            }
        }
        ret.append(" ]");
        return ret.toString();
    }
}
