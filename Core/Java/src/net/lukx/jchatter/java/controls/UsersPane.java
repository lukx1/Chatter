package net.lukx.jchatter.java.controls;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.fetching.ContentRepository;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.Relationship;
import net.lukx.jchatter.lib.models.RelationshipStatus;
import net.lukx.jchatter.lib.models.User;
import net.lukx.jchatter.lib.models.UserStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class UsersPane extends LinedPaneManagerPane<LinedPane> {

    private Repos repos;
    private ContentRepository contentRepository;
    private CurrentValues currentValues;

    private Relationship[] myRelationships;

    private InitArgs initArgs = new ConcreteInitArgs(6,100,60,0);

    private List<UsersPane.UserInnerPane> innerPanesClicked = new ArrayList<>();

    public UsersPane(){
        super();
    }

    public void clearInner(){
        clearInnerElements();
    }

    public void init(Repos repos, ContentRepository contentRepository, CurrentValues currentValues) {
        this.repos = repos;
        this.contentRepository = contentRepository;
        this.currentValues = currentValues;
    }

    private List<User> getUsersAsList() throws IOException, URISyntaxException {
        return getUsersAsList(true);
    }

    private List<User> getUsersAsList(boolean filterCurrentUser) throws IOException, URISyntaxException {
        List<User> users = new ArrayList<>(Arrays.asList(repos.getUserRepo().getUsers()));
        users.sort(Comparator.comparingInt(a -> a.status));

        if(filterCurrentUser) {
            User toRemove = null;
            for (User user : users) {
                if (user.id == currentValues.getCurrentUser().id) {
                    toRemove = user;
                    break;
                }
            }
            users.remove(toRemove);
        }

        return users;
    }

    public void showAllFriends() throws IOException, URISyntaxException {
        clearInnerElements();
        myRelationships = repos.getRelationshipRepo().getRelForUser(currentValues.getCurrentUser().id);
        initArgs = new ConcreteInitArgs(initArgs.getPadding(),this.getWidth(),initArgs.getHeight(),initArgs.getTopMargin());
        List<User> users = getUsersAsList();
        List<User> showUsers = new ArrayList<>();
        for (User user : users) {
            for (Relationship myRelationship : myRelationships) {
                if(myRelationship.idtargetUser == user.id && RelationshipStatus.fromKey(myRelationship.relationType).contains(RelationshipStatus.FRIEND)){
                    showUsers.add(user);
                }
            }
        }
        addIesForUsers(showUsers);
    }
    
    public void showAllUsers() throws IOException, URISyntaxException {
        showAllUsers(100);
    }

    public void showUsersMatchingRegex(String regex) throws IOException, URISyntaxException {
        List<User> goodUsers = new ArrayList<>();

        regex = regex.toLowerCase();

        for (User user : getUsersAsList()) {
            if(user.login.toLowerCase().contains(regex)){
                goodUsers.add(user);
            }
            else if((user.firstName.toLowerCase()+" "+user.secondName).toLowerCase().contains(regex)){
                goodUsers.add(user);
            }
        }

        addIesForUsers(goodUsers);
    }

    public List<User> getUsersSelected(){
        List<User> selected = new ArrayList<>();
        for (UserInnerPane userInnerPane : innerPanesClicked) {
            selected.add(userInnerPane.user);
        }
        return selected;
    }

    private void deselectPane(UserInnerPane uip){
        uip.getStyleClass().remove("ObjSelected");
    }

    private void selectPane(UserInnerPane uip){
        uip.getStyleClass().add("ObjSelected");
    }

    private void innerPaneClicked(MouseEvent e){
        UserInnerPane uip = ((UserInnerPane)e.getSource());
        if(innerPanesClicked.contains(uip)){
            deselectPane(uip);
            innerPanesClicked.remove(uip);
        }
        else {
            selectPane(uip);
            innerPanesClicked.add(uip);
        }
    }

    public void addIesForUsers(List<User> users) throws IOException, URISyntaxException {
        for (User user : users) {
            LinedPane p = new UserInnerPane(user,initArgs);
            p.initElements();
            p.addEventHandler(MouseEvent.MOUSE_CLICKED, this::innerPaneClicked);
            addInnerElement(p);
        }
        int x = 0;
    }

    public void showAllUsers(int limit) throws IOException, URISyntaxException {
        clearInnerElements();
        myRelationships = repos.getRelationshipRepo().getRelForUser(currentValues.getCurrentUser().id);
        initArgs = new ConcreteInitArgs(initArgs.getPadding(),this.getWidth(),initArgs.getHeight(),initArgs.getTopMargin());
        List<User> users = getUsersAsList();
        addIesForUsers(users.subList(0,Math.min(users.size(),limit)));
    }

    public class UserInnerPane extends LinedPane {

        private Circle pictureCircle;
        private Circle statusCircle;
        private Label headerLabel;
        private Label fullNameLabel;
        private User user;

        public User getUser(){return user;}

        public UserInnerPane(User user,InitArgs initArgs){
            super(initArgs);
            this.user = user;
        }

        private String shouldCreateRelStatusLabel(User targetUser){
            for (Relationship myRelationship : myRelationships) {
                if(myRelationship.idtargetUser == targetUser.id && myRelationship.relationType != 0){
                    EnumSet<RelationshipStatus> es = RelationshipStatus.fromKey(myRelationship.relationType);
                    if(es.contains(RelationshipStatus.BLOCKED)){
                        return " (BLOCKED)";
                    }
                    else if(es.contains(RelationshipStatus.FRIEND)){
                        return " (FRIEND)";
                    }
                    else if(es.contains(RelationshipStatus.FRIENDSHIP_PENDING)){
                        return " (PENDING)";
                    }

                }
            }
            return "";
        }

        @Override
        protected void initElements() {
            pictureCircle = new Circle();
            statusCircle = new Circle();
            headerLabel = new Label();
            fullNameLabel = new Label();


            this.createCenterLeftCircle(pictureCircle);
            this.createHeaderLabelNextToPicture(headerLabel);
            this.createStatusCircleOn(statusCircle,pictureCircle);
            this.createLabelBellowLabel(fullNameLabel,headerLabel);

            setPicture(new ImagePattern(contentRepository.fetchImageWithFallback(user.picture)));
            setStatusColor(UserStatus.fromKey(user.status));
            setHeaderText(user.login+shouldCreateRelStatusLabel(user));

            setTextOnLabelLine(user.firstName+" "+user.secondName,0);

            this.getStyleClass().add("CursorHand");
        }
    }

    /*private double innerElementHeight = 32;
    private double innerElementPadding = 6;
    private double innerElementTopMargin = 6;


    private List<UsersPane.InnerUserPane> userPanes = new RemovingList<>(this);

    public List<UsersPane.InnerUserPane> getRoomPanes() {
        return userPanes;
    }

    public class InnerUserPane2 extends LinedPane {

        @Override
        protected void initElements() {

        }
    }

    public class InnerUserPane extends Pane implements Initable {

        private Circle pictureCircle;
        private Circle statusCircle;
        private Label nickName;
        private Label fullName;
        private int heightIndex;

        private void initCircle() {
            pictureCircle = new Circle();
            pictureCircle.setRadius(8);
            pictureCircle.setLayoutY(pictureCircle.getRadius() + innerElementPadding);
            pictureCircle.setLayoutX(pictureCircle.getRadius() + innerElementPadding);
            pictureCircle.setFill(null);
            pictureCircle.setStrokeWidth(1);
            pictureCircle.setStroke(Color.BLACK);

        }

        @Override
        public void initializeInside(int heightIndex) {

        }

        @Override
        public double getYOffset() {
            return heightIndex * (innerElementHeight + innerElementTopMargin);
        }
    }*/

}
