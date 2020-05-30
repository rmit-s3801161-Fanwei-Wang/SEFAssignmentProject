package model.player;

import model.entity.Board;

public class Admin extends User {
//    static int uniqueID = 0;

    public Admin() {
    }

    public Admin(String username, String password, String userEmail) {
        super(username, password, userEmail);
    }
    
    // ladder ,snake larger than 30    
    //TODO implement
    public Board setNewBoard(){
        return null;
    }
}
