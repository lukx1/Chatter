package net.lukx.jchatter.java.controls;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import net.lukx.jchatter.java.fetching.ContentRepository;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class NotificationPane extends LinedPaneManagerPane<LinedPane> {

    private List<NotificationButtonHandler> innerButtonsClicked = new ArrayList<>();
    private CurrentValues currentValues;
    private Repos repos;
    private ContentRepository contentRepository;
    private InitArgs args = new ConcreteInitArgs(6,100,60,0);

    private List<InnerNotificationNewMessagesPane> getNewMessagePanes(){
        List<InnerNotificationNewMessagesPane> nofs = new ArrayList<>();
        for (LinedPane innerElement : innerElements) {
            if(innerElement instanceof InnerNotificationNewMessagesPane){
                nofs.add((InnerNotificationNewMessagesPane) innerElement);
            }
        }
        return nofs;
    }

    private List<InnerNotificationRelationPane> getNotificationRelationPanes(){
        List<InnerNotificationRelationPane> nofs = new ArrayList<>();
        for (LinedPane innerElement : innerElements) {
            if(innerElement instanceof InnerNotificationRelationPane){
                nofs.add((InnerNotificationRelationPane) innerElement);
            }
        }
        return nofs;
    }



    public void addNotificationButtonClickedHandler(NotificationButtonHandler h){
        innerButtonsClicked.add(h);
    }



    public void removeNotificationButtonHandler(NotificationButtonHandler h){
        innerButtonsClicked.remove(h);
    }

    private void fireNotificationButtonHandlers(NotificationButtonEvent e){
        for (NotificationButtonHandler notificationButtonHandler : innerButtonsClicked) {
            notificationButtonHandler.event(e);
        }
    }

    public void init(Repos repos, ContentRepository contentRepository, CurrentValues currentValues){
        this.repos = repos;
        this.currentValues = currentValues;
        this.contentRepository = contentRepository;
    }

    private void showNewFriendsNotifications() throws IOException, URISyntaxException {
        Relationship[] rels = repos.getRelationshipRepo().getRelAboutUser(currentValues.getCurrentUser().id);
        for (Relationship rel : rels) {
            if(RelationshipStatus.fromKey(rel.relationType).contains(RelationshipStatus.FRIENDSHIP_PENDING)){
                InnerNotificationRelationPane ip = new InnerNotificationRelationPane(args,rel);
                ip.initElements();
                addInnerElement(ip);
            }
        }
    }

    private void showNewMessagesNotifications() throws IOException, URISyntaxException {
        Room[] roomsUserIsIn = repos.getRoomRepo().getRoomsWithUser(currentValues.getCurrentUser().id);
        for (Room room : roomsUserIsIn) {
            Message[] msgs = repos.getMessageRepo().getMessagesInRoomSince(room.id,currentValues.getCurrentUser().dateLastLogin);
            if(msgs.length > 0){
                Message lastMsg = msgs[msgs.length-1];
                InnerNotificationNewMessagesPane ip = new InnerNotificationNewMessagesPane(args,room,msgs.length,lastMsg.content);
                ip.initElements();
                ip.initRoom();
            }
        }

    }

    public void showNotifications() throws IOException, URISyntaxException {
        args = new ConcreteInitArgs(args.getPadding(),getWidth(),args.getHeight(),args.getTopMargin());
        showNewFriendsNotifications();
        //showNewMessagesNotifications();
    }

    public void clearInner() {
        super.clearInnerElements();
    }

    public interface NotificationButtonHandler {
        void event(NotificationButtonEvent e);
    }

    public enum NotificationButtonClicked {
        ACCEPT,REFUSE,BLOCK
    }

    public class NotificationButtonEvent{
        private NotificationButtonClicked notificationButtonClicked;
        private Object source;

        public NotificationButtonClicked getNotificationButtonClicked() {
            return notificationButtonClicked;
        }

        public Relationship getRelationship() {
            return relationship;
        }

        private Relationship relationship;

        public NotificationButtonEvent(NotificationButtonClicked notificationButtonClicked, Relationship relationship, Object source) {
            this.notificationButtonClicked = notificationButtonClicked;
            this.relationship = relationship;
            this.source = source;
        }

        public Object getSource() {
            return source;
        }
    }

    public class InnerNotificationNewMessagesPane extends LinedPane {

        private Circle picture;
        private Circle notificationCircle;
        private Label header;
        private Label showcase;
        private Text notificationCountText;
        private StackPane stackPane;
        private Room room;
        private int notifications;
        private String lastMessage;

        public InnerNotificationNewMessagesPane(InitArgs initArgs, Room room, int notificationCount, String lastMessage) {
            super(initArgs);
            if(room == null){
                throw new NullPointerException("Room is null");
            }
            this.room = room;
            this.lastMessage =lastMessage;
            this.notifications = notificationCount;
        }

        private String getLastMessageCut(){
            if(lastMessage.length() > 60){
                return lastMessage.substring(0,57)+"...";
            }
            else {
                return lastMessage;
            }
        }

        private void initOneOnOne() throws IOException, URISyntaxException {
            User otherUser = RoomUtils.getOtherUserInRoom(repos,room,currentValues.getCurrentUser());
            picture.setFill(new ImagePattern(contentRepository.fetchImageWithFallback(otherUser.picture)));

            header.setText("New messages from "+otherUser.firstName);
            showcase.setText(getLastMessageCut());
        }

        private void initRoom(){
            picture.setFill(new ImagePattern(contentRepository.fetchImageWithFallback(room.picture)));

            header.setText("New messages in group "+room.name);
            showcase.setText(getLastMessageCut());
        }

        private void initInternal() throws IOException, URISyntaxException {
            if(room.oneOnOne){
                initOneOnOne();
            }
            else {
                initRoom();
            }
        }

        @Override
        protected void initElements() throws IOException, URISyntaxException {
            picture = new Circle();
            notificationCircle = new Circle();
            header = new Label();
            showcase = new Label();
            notificationCountText = new Text(Integer.toString(notifications));
            stackPane = new StackPane();

            createCenterLeftCircle(picture);
            createStatusCircleTextOn(notificationCircle, notificationCountText,stackPane,picture);
            createHeaderLabelNextToPicture(header);
            createLabelBellowLabel(showcase,header);

            notificationCircle.setFill(Color.RED);

            initInternal();
        }
    }

    public class InnerNotificationRelationPane extends LinedPane {

        private Circle picture;
        private Label name;
        private Label status;
        private Button accept;
        private Button refuse;
        private Button block;

        private Relationship relationship;


        public Relationship getRelationship() {
            return relationship;
        }

        public InnerNotificationRelationPane(InitArgs initArgs, Relationship rel) {
            super(initArgs);
            this.relationship = rel;
        }

        private NotificationButtonClicked fromStr(String s){
            if(s.equals("BLOCK")){
                return NotificationButtonClicked.BLOCK;
            }
            else if(s.equals("REFUSE")){
                return NotificationButtonClicked.REFUSE;
            }
            else {
                return NotificationButtonClicked.ACCEPT;
            }
        }

        private void fireNotificationEvent(MouseEvent e){
            Button b = (Button)e.getSource();
            NotificationButtonEvent event = new NotificationButtonEvent(
                    fromStr(b.getText()),
                    relationship,
                    this
            );
            fireNotificationButtonHandlers(event);
        }

        @Override
        protected void initElements() {
            picture = new Circle();
            name = new Label();
            status = new Label();

            accept = new Button();
            refuse = new Button();
            block = new Button();

            createCenterLeftCircle(picture);
            createHeaderLabelNextToPicture(name,true);
            createLabelBellowLabel(status,name);

            accept.setLayoutY(status.getLayoutY()+32);
            accept.setLayoutX(6);
            accept.setText("ACCEPT");
            accept.getStyleClass().add("Friend");

            refuse.setLayoutY(status.getLayoutY()+32);
            refuse.setLayoutX(72);
            refuse.setText("REFUSE");
            refuse.getStyleClass().add("FriendPending");

            block.setLayoutY(status.getLayoutY()+32);
            block.setLayoutX(138);
            block.setText("BLOCK");
            block.getStyleClass().add("Blocked");

            status.setWrapText(true);
            status.setText("Wants to be your friend");

            try {
                User other = repos.getUserRepo().getUser(relationship.idsourceUser);
                picture.setFill(new ImagePattern(contentRepository.fetchImageWithFallback(other.picture))); //TODO:Finish thios
                name.setText(other.login);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

            this.getChildren().add(accept);
            this.getChildren().add(refuse);
            this.getChildren().add(block);

            accept.addEventHandler(MouseEvent.MOUSE_CLICKED,this::fireNotificationEvent);
            refuse.addEventHandler(MouseEvent.MOUSE_CLICKED,this::fireNotificationEvent);
            block.addEventHandler(MouseEvent.MOUSE_CLICKED,this::fireNotificationEvent);
        }

    }

}
