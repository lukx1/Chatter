package net.lukx.jchatter.java.supporting;

import net.lukx.jchatter.lib.models.User;

import java.io.File;
import java.util.Date;

public class CurrentValues {

    private static CurrentValues instance;

    private Date lastLogin = new Date();

    private CurrentValues(){}

    private File selectedImageFile;

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

    public File getSelectedImageFile() {
        return selectedImageFile;
    }

    public void setSelectedImageFile(File selectedImageFile) {
        this.selectedImageFile = selectedImageFile;
    }
}
