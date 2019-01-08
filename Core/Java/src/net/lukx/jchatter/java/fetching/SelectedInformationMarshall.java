package net.lukx.jchatter.java.fetching;

import javafx.scene.paint.ImagePattern;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;
import sun.util.resources.cldr.et.CurrencyNames_et;

public class SelectedInformationMarshall {

    private Repos repos;
    private CurrentValues currentValues;
    private SelectedInfoPane selectedInfoPane;
    private ContentRepository contentRepository;

    public boolean isUserSelected(){
        return selectedInfoPane.getTopLabel().getText().equals("ADD/REMOVE FRIEND");
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
        clickedOn(currentValues.getCurrentUser());
    }

    private void clickedOn(User user){
        selectedInfoPane.getCircle().setFill(new ImagePattern(contentRepository.fetchImageWithFallback(user.picture)));
        selectedInfoPane.getTopLabel().setText(user.firstName+" "+user.secondName);
        selectedInfoPane.getBottomLAbel().setText(user.email);
        selectedInfoPane.getTopButton().setText("ADD/REMOVE FRIEND");
        selectedInfoPane.getBottomButton().setText("BLOCK");
        boolean disableButtons = user == currentValues.getCurrentUser();
        selectedInfoPane.getBottomButton().setDisable(disableButtons);
        selectedInfoPane.getTopButton().setDisable(disableButtons);
    }

    private void clickedOn(Room room){
        selectedInfoPane.getCircle().setFill(new ImagePattern(contentRepository.fetchImageWithFallback(room.picture)));
        selectedInfoPane.getTopLabel().setText(room.name);
        selectedInfoPane.getBottomLAbel().setText("");
        selectedInfoPane.getTopButton().setText("ADD/REMOVE USERS");
        selectedInfoPane.getBottomButton().setText("LEAVE");
        selectedInfoPane.getTopButton().setDisable(false);
        selectedInfoPane.getBottomButton().setDisable(false);
    }

}
