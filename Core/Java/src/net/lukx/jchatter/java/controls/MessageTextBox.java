package net.lukx.jchatter.java.controls;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.PopupMarshall;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.Message;
import net.lukx.jchatter.lib.models.Room;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

public class MessageTextBox extends TextField {

    private Repos repos;
    private CurrentValues currentValues;
    private PopupMarshall popupMarshall;
    private Room room;

    public MessageTextBox(){
        super();
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public void sendMessage(){

        if(this.room == null){
            popupMarshall.makeWarning("No room selected");
            return;
        }

        Message message = new Message();
        message.edited = false;
        message.dateSent = new Date();
        message.content = this.getText();
        message.dateEdited = null;
        message.idsender = currentValues.getCurrentUser().id;
        message.idroomReceiver = room.id;
        if(this.getText().equals(""))
            return;

        try {
            repos.getMessageRepo().addMessage(message);
        } catch (IOException | URISyntaxException e1) {
            popupMarshall.makeError(e1.toString());
        }
    }

    public void init(Repos repos, CurrentValues currentValues, PopupMarshall popupMarshall){
        this.repos = repos;
        this.currentValues =currentValues;
        this.popupMarshall = popupMarshall;
    }

}
