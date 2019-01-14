package net.lukx.jchatter.java.supporting;

import net.lukx.jchatter.lib.models.User;

import java.util.Date;

public class CurrentValues {

    private static CurrentValues instance;

    private Date lastLogin = new Date();

    private CurrentValues(){}

    public static CurrentValues createInstance(){
        if(instance == null){
            return instance=new CurrentValues();
        }
        return instance;
    }

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
