package net.lukx.jchatter.java.controls;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.fetching.ContentRepository;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.PopupMarshall;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.Message;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlowingMessagePane extends FlowPane {

    private Repos repos;
    private ContentRepository contentRepository;
    private CurrentValues currentValues;
    private InitArgs args = new ConcreteInitArgs(6, 100, 60, 0);
    private List<User> usersInRoom = new ArrayList<>();
    private PopupMarshall popupMarshall;
    private Room currentRoom;
    private boolean bound = false;


    public FlowingMessagePane() {
        super();
        //this.prefWidthProperty().bind(this.getPar)

        this.widthProperty().addListener((l,oldVal,newVal) -> {
            for (Node child : this.getChildren()) {
                if(child instanceof LinedAnchorPane){
                    ((LinedAnchorPane) child).setPrefWidth(this.getWidth());
                }
            }
        });
    }


    public void clearInner() {
        usersInRoom.clear();
        getChildren().clear();
    }

    private void loadUsersInRoom(Room room) throws IOException, URISyntaxException {
        for (User user : repos.getRoomRepo().getUsersInRoom(room.id)) {
            if (user.id == currentValues.getCurrentUser().id) {
                continue;
            }

            usersInRoom.add(user);
        }

    }

    private User getUserWithId(int id) {
        if (id == currentValues.getCurrentUser().id) {
            return currentValues.getCurrentUser();
        }
        for (User user : usersInRoom) {
            if (user.id == id) {
                return user;
            }
        }
        throw new NullPointerException("User not found in room");
    }

    public void showAllMessagesInRoomSince(Room room, Date date) throws IOException, URISyntaxException {
        this.clearInner();
        this.currentRoom = room;
        args = new ConcreteInitArgs(args.getPadding(), this.getWidth(), args.getHeight(), args.getTopMargin());
        Message[] msgs = repos.getMessageRepo().getMessagesInRoomSince(room.id, date);
        loadUsersInRoom(room);
        if (msgs.length < 1) {
            popupMarshall.makeInfo("This room has no messages");
            return;
        }
        for (Message message : msgs) {
            FlowingMessagePane.InnerMessagePane imp = new FlowingMessagePane.InnerMessagePane(args, message);
            imp.initElements();
            getChildren().add(imp);

        }
    }

    public void showAllMessagesInRoom(Room room) throws IOException, URISyntaxException {
        this.clearInner();
        this.currentRoom = room;
        args = new ConcreteInitArgs(args.getPadding(), this.getWidth(), args.getHeight(), args.getTopMargin());
        Message[] msgs = repos.getMessageRepo().getMessagesInRoom(room.id);
        loadUsersInRoom(room);
        if (msgs.length < 1) {
            //popupMarshall.makeInfo("This room has no messages");
            return;
        }
        for (Message message : msgs) {
            FlowingMessagePane.InnerMessagePane imp = new FlowingMessagePane.InnerMessagePane(args, message);
            imp.initElements();
            getChildren().add(imp);
        }
    }

    public void init(Repos repos, ContentRepository contentRepository, CurrentValues currentValues, PopupMarshall popupMarshall) {
        this.repos = repos;
        this.contentRepository = contentRepository;
        this.currentValues = currentValues;
        this.popupMarshall = popupMarshall;
    }


    public Room getCurrentRoom() {
        return currentRoom;
    }

    public class InnerMessagePane extends LinedAnchorPane {
        private Circle picture;
        private Label header;
        private Label text;
        private Message message;
        private User sentBy;

        public InnerMessagePane(InitArgs initArgs, Message message) {
            this.initArgs = initArgs;
            this.setMaxWidth(initArgs.getWidth());
            this.message = message;
            this.sentBy = getUserWithId(message.idsender);
            handleMessage();
        }

        private void updateMessage(){
            message.dateRead = new Date();
            if(message.dateReceived == null){
                message.dateReceived = new Date();
            }
            try {
                repos.getMessageRepo().setMessage(message);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }


        private void handleMessage(){
            if(message.idsender == currentValues.getCurrentUser().id)
                return;
            new Thread(this::updateMessage).start();
        }

        private String fomatDateHuman(Date date){
            long diffInMillies = new Date().getTime() - date.getTime();
            long hoursElapsed = TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
            if(hoursElapsed < 24){
                return "Today";
            }
            else if( hoursElapsed < 48){
                return "Yesterday";
            }
            else {
                return new SimpleDateFormat("MM/dd").format(date);
            }
        }

        private void makeHeadeText(){
            StringBuilder builder = new StringBuilder();
            builder.append(sentBy.login);
            builder.append("  ");
            if (sentBy.id != currentValues.getCurrentUser().id) { // Sent by other user
                builder.append(fomatDateHuman(message.dateSent));
                if(message.edited){
                    builder.append(" *");
                }
            } else { // Sent by me
                if(message.dateRead == null && message.dateReceived == null){ // Message not read or received
                    builder.append(fomatDateHuman(message.dateSent));
                    builder.append(" \u2753");//Question mark emoji
                }
                else if(message.dateReceived != null && message.dateRead == null){ // Message received
                    builder.append(fomatDateHuman(message.dateReceived));
                    builder.append(" \ud83d\udce9");//Envolope with arrow
                }
                else { // Message read
                    builder.append(fomatDateHuman(message.dateRead));
                    builder.append(" \u2714\ufe0f");//Envolope with arrow
                }
            }
            this.header.setText(builder.toString());
        }

        @Override
        protected void initElements() throws IOException, URISyntaxException {
            picture = new Circle();
            header = new Label();
            text = new Label();

            this.setMaxHeight(Double.NEGATIVE_INFINITY);
            this.setMinHeight(initArgs.getHeight());
            this.setPrefHeight(initArgs.getHeight());
            this.setMinWidth(-1);
            this.setMaxWidth(9999);

            if(!bound) {
                FlowingMessagePane.this.prefWidthProperty().bind(((ScrollPane)FlowingMessagePane.this.getParent().getParent().getParent()).widthProperty());
                bound = true;
            }


            createCenterLeftCircle(picture, 16);
            createHeaderLabelNextToPicture(header);

            text.setLayoutY(picture.getRadius() + initArgs.getPadding() * 4);
            text.setWrapText(true);
            text.setLayoutX(initArgs.getPadding());
            text.wrapTextProperty().setValue(true);
            text.setAlignment(Pos.TOP_LEFT);
            text.setMaxHeight(Double.POSITIVE_INFINITY);

            picture.setFill(new ImagePattern(contentRepository.fetchImageWithFallback(sentBy.picture)));
            text.setText(message.content);

            /*text.heightProperty().addListener((o,oldV,newV) -> {
                //this.setPrefHeight(this.getPrefHeight()+(newV.doubleValue()-oldV.doubleValue()));\
                thisLinedPane.setPrefHeight(100);
                thisLinedPane.setMinHeight(100);
                doSetHeight(100);
            });*/

            getChildren().add(text);

            makeHeadeText();

            if (sentBy.id == currentValues.getCurrentUser().id) {
                getStyleClass().add("DarkerBg");
            }

        }
    }

    public class InnerFilePane extends LinedPane {
        private Circle picture;
        //private Box

        public InnerFilePane(InitArgs initArgs) {
            super(initArgs);
        }

        @Override
        protected void initElements() throws IOException, URISyntaxException {

        }
    }

}
