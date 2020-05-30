package model.player;

import model.entity.Board;

public class Admin extends User {
//    static int uniqueID = 0;

    public Admin() {
    }

    public Admin(String username, String password, String userEmail) {
        super(username, password, userEmail);
    }

    //TODO implement
    public Board setNewBoard(){
        return null;
    }
//    @Override
//    String idGenerator() {
//        return String.format("admin%06d",++uniqueID);
//    }
}
