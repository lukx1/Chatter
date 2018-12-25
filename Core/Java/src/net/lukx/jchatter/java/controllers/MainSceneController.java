package net.lukx.jchatter.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.controls.RoomPane;
import net.lukx.jchatter.lib.models.Room;

public class MainSceneController {

    public Pane LeftPane;
    public AnchorPane TopPane;
    public ScrollPane ChatPane;
    public AnchorPane SendMessagePane;
    public Circle PictueCircle;
    public BorderPane WholePane;
    public AnchorPane TopContainer;
    public AnchorPane LoginPanel;
    public HBox LoginRegisterHolder;
    public Button LoginButton;
    public Button RegisterButton;
    public ScrollPane RoomsScrollPane;
    public RoomPane RoomsPane;
    public TextField SearchTextField;
    public Label UsernameLabel;
    public AnchorPane CenterAnchorPane;
    public Button LogoutButton;
    public ScrollPane ChatScrollPane;
    public Button SendButton;
    public TextArea SendMessageTextField;
    public Button EmojiButton;
    public Button FileButton;
    public AnchorPane InfoPane;
    public AnchorPane NotificationPane;
    public Circle NotificationCountCircle;

    private GaussianBlur blurEffect;

    private void toggleScreenBlur(){
        if(blurEffect == null){
            blurEffect = new GaussianBlur();
        }
        if(WholePane.getEffect() == blurEffect){
            WholePane.setEffect(null);
            WholePane.setDisable(false);
        }
        else {
            WholePane.setEffect(blurEffect);
            WholePane.setDisable(true);
        }
    }


    private void openLoginScene(){
        toggleScreenBlur();
    }

    private void closeLoginScene(){
        LoginRegisterHolder.setVisible(false);
        toggleScreenBlur();
    }

    private void backgroundInit(){
        PictueCircle.setFill(new ImagePattern(new Image("/pictures/nopicture.png")));
    }

    @FXML
    public void initialize(){
        for (int i = 0; i < 20; i++) {
            RoomsPane.addRoom(new Room());
        }

        RoomsPane.addRoom(new Room());
        backgroundInit();
        //openLoginScene();
    }

    private void sometingDebug(){

    }

    public void LoginClicked(ActionEvent actionEvent) {
        closeLoginScene();
    }

    public void RegisterClicked(ActionEvent actionEvent) {
    }
}
