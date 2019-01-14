package net.lukx.jchatter.java.controls;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlowingMessagePane extends FlowPane {

    private Repos repos;
    private ContentRepository contentRepository;
    private CurrentValues currentValues ;
    private InitArgs args = new ConcreteInitArgs(6,100,60,0);
    private List<User> usersInRoom = new ArrayList<>();
    private PopupMarshall popupMarshall;
    private Room currentRoom;

    public FlowingMessagePane(){
        super();
    }

    public void clearInner(){
        usersInRoom.clear();
        getChildren().clear();
    }

    private void loadUsersInRoom(Room room) throws IOException, URISyntaxException {
        for (User user : repos.getRoomRepo().getUsersInRoom(room.id)) {
            if(user.id == currentValues.getCurrentUser().id){
                continue;
            }

            usersInRoom.add(user);
        }
        if(usersInRoom.size() < 1){
            throw new IllegalArgumentException("No users are in this room");
        }
    }

    private User getUserWithId(int id){
        if(id == currentValues.getCurrentUser().id){
            return currentValues.getCurrentUser();
        }
        for (User user : usersInRoom) {
            if(user.id == id){
                return user;
            }
        }
        throw new NullPointerException("User not found in room");
    }

    public void showAllMessagesInRoomSince(Room room, Date date) throws IOException, URISyntaxException {
        this.clearInner();
        this.currentRoom = room;
        args = new ConcreteInitArgs(args.getPadding(),this.getWidth(),args.getHeight(),args.getTopMargin());
        Message[] msgs = repos.getMessageRepo().getMessagesInRoomSince(room.id,date);
        loadUsersInRoom(room);
        if(msgs.length < 1){
            popupMarshall.makeInfo("This room has no messages");
            return;
        }
        for (Message message : msgs) {
            FlowingMessagePane.InnerMessagePane imp = new FlowingMessagePane.InnerMessagePane(args,message);
            imp.initElements();
            getChildren().add(imp);
        }
    }

    public void showAllMessagesInRoom(Room room) throws IOException, URISyntaxException {
        this.clearInner();
        this.currentRoom = room;
        args = new ConcreteInitArgs(args.getPadding(),this.getWidth(),args.getHeight(),args.getTopMargin());
        Message[] msgs = repos.getMessageRepo().getMessagesInRoom(room.id);
        loadUsersInRoom(room);
        if(msgs.length < 1){
            popupMarshall.makeInfo("This room has no messages");
            return;
        }
        for (Message message : msgs) {
            FlowingMessagePane.InnerMessagePane imp = new FlowingMessagePane.InnerMessagePane(args,message);
            imp.initElements();
            getChildren().add(imp);
        }
    }

    public void init(Repos repos, ContentRepository contentRepository, CurrentValues currentValues, PopupMarshall popupMarshall){
        this.repos = repos;
        this.contentRepository = contentRepository;
        this.currentValues = currentValues;
        this.popupMarshall = popupMarshall;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public class InnerMessagePane extends LinedPane {
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
        }

        private void doSetHeight(double d){
            this.setHeight(d);
        }

        @Override
        protected void initElements() throws IOException, URISyntaxException {
            picture = new Circle();
            header = new Label();
            text = new Label();
            LinedPane thisLinedPane = this;

            this.setMaxHeight(Double.NEGATIVE_INFINITY);
            this.setMinHeight(initArgs.getHeight());
            this.setPrefHeight(initArgs.getHeight());

            createCenterLeftCircle(picture,16);
            createHeaderLabelNextToPicture(header);

            text.setLayoutY(picture.getRadius()+ initArgs.getPadding()*4);
            text.setWrapText(true);
            text.setLayoutX(initArgs.getPadding());
            text.wrapTextProperty().setValue(true);
            text.setAlignment(Pos.TOP_LEFT);
            text.setMaxWidth(380);
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

            if(sentBy.id != currentValues.getCurrentUser().id){
                header.setText(sentBy.login+"   "+message.dateSent+(message.edited ? " *":""));
            }
            else {
                header.setText(sentBy.login+"   "+message.dateSent+(message.edited ? " *":""));
            }

            if(sentBy.id == currentValues.getCurrentUser().id){
                getStyleClass().add("DarkerBg");
            }

        }    }

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
