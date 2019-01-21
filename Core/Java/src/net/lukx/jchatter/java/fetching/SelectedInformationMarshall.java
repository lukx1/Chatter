package net.lukx.jchatter.java.fetching;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import net.lukx.jchatter.java.controls.RoomUtils;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.RelationshipStatus;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;
import sun.util.resources.cldr.et.CurrencyNames_et;

import java.io.IOException;
import java.net.URISyntaxException;

public class SelectedInformationMarshall {

    private Repos repos;
    private CurrentValues currentValues;
    private SelectedInfoPane selectedInfoPane;
    private ContentRepository contentRepository;
    private User userSelected;
    private boolean isUserBlocked = false;

    public User getUserSelected() {
        return userSelected;
    }

    public Room getRoomSelected() {
        return roomSelected;
    }

    private Room roomSelected;

    public boolean isUserSelected(){
        return userSelected != null;
    }

    public boolean isRoomSelected(){
        return !isUserSelected();
    }

    public SelectedInformationMarshall(SelectedInfoPane selectedInfoPane,Repos repos,ContentRepository contentRepository, CurrentValues currentValues){
        this.repos = repos;
        this.contentRepository = contentRepository;
        this.currentValues = currentValues;
        this.selectedInfoPane = selectedInfoPane;
    }

    public void init(){
        clickedOnUser(currentValues.getCurrentUser());
    }

    private void clickedOnUser(User user){

        selectedInfoPane.getCircle().setFill(new ImagePattern(contentRepository.fetchImageWithFallback(user.picture)));
        selectedInfoPane.getTopLabel().setText(user.firstName+" "+user.secondName);
        selectedInfoPane.getBottomLAbel().setText(user.email);
        selectedInfoPane.getTopButton().setText("REMOVE FRIEND");
        selectedInfoPane.getBottomButton().setText("BLOCK");
        boolean disableButtons = user == currentValues.getCurrentUser();
        selectedInfoPane.getBottomButton().setDisable(disableButtons);
        selectedInfoPane.getTopButton().setDisable(disableButtons);
        userSelected = user;
        isUserBlocked = false;
        roomSelected = null;
        try {
            if(RelUtils.isUser(RelationshipStatus.BLOCKED,repos,userSelected,currentValues.getCurrentUser())){
                selectedInfoPane.getBottomButton().setText("UNBLOCK");
                isUserBlocked = true;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void clickedOn(Room room){
        if(room.oneOnOne){
            try {
                this.clickedOnUser(RoomUtils.getOtherUserInRoom(repos,room,currentValues.getCurrentUser()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else {
            this.clickedOnRoom(room);
        }
    }

    private void clickedOnRoom(Room room){
        selectedInfoPane.getCircle().setFill(new ImagePattern(new Image("/pictures/nopicture_group.png")));
        selectedInfoPane.getTopLabel().setText(room.name);
        selectedInfoPane.getBottomLAbel().setText("");
        selectedInfoPane.getTopButton().setText("ADD/REMOVE USERS");
        selectedInfoPane.getBottomButton().setText("LEAVE");
        selectedInfoPane.getTopButton().setDisable(false);
        selectedInfoPane.getBottomButton().setDisable(false);
        roomSelected = room;
        userSelected = null;
        isUserBlocked = false;
    }

    public boolean isUserBlocked() {
        return isUserBlocked;
    }
}
