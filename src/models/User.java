package models;

import javax.print.attribute.standard.PresentationDirection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String role;
    private String userName;
    private String userEmail;
    private ArrayList<Game> gameHistories = new ArrayList<Game>();
    private static HashMap<String,User> users = new HashMap<>();



    public User() {
    }

    public User(String username, String password, String role, String userName, String userEmail) {
        this.username = userName;
        this.password = password;
        this.role = role;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean forgetPassword(String email){
        for(User user: users.values()){
            if(email.equals(user.getUserEmail()))
                //TODO send email
                return true;
        }
        return false;
    }

    //
    public boolean resetPassword(String newPassword, String confirmPassword){
     
        /*
        TODO
        1. The length of password should in [6,20]
        2. Password includes upper case letter
        3. Password includes lower case letter
        */
        if(newPassword.equals(confirmPassword)){
            setPassword(newPassword);
            return true;
        }
        return false;
    }
}
