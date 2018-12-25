package net.lukx.jchatter.java.imagedModels;

import javafx.scene.image.Image;
import net.lukx.jchatter.lib.models.User;

public class ImagedUser implements Imaged{

    private User user;
    private Image image;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(user == this.user) {
            return;
        }
        this.user = user;
    }
}
