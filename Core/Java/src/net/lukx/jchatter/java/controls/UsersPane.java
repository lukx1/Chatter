package net.lukx.jchatter.java.controls;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.fetching.ContentRepository;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.RemovingList;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.User;
import net.lukx.jchatter.lib.models.UserStatus;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersPane extends LinedPaneManagerPane<LinedPane> {

    private Repos repos;
    private ContentRepository contentRepository;
    private CurrentValues currentValues;

    private InitArgs initArgs = new ConcreteInitArgs(6,100,60);

    public UsersPane(Repos repos, ContentRepository contentRepository, CurrentValues currentValues) {
        this.repos = repos;
        this.contentRepository = contentRepository;
        this.currentValues = currentValues;
    }

    private List<User> getUsersAsList() throws IOException, URISyntaxException {
        return Arrays.asList(repos.getUserRepo().getUsers());
    }

    public void showAllUsers() throws IOException, URISyntaxException {
        showAllUsers(100);
    }

    public void showUsersMatchingRegex(String regex) throws IOException, URISyntaxException {
        List<User> goodUsers = new ArrayList<>();

        for (User user : getUsersAsList()) {

        }
    }

    public void addIEsFromUsers(List<User> users){
        for (User user : users) {
            addInnerElement(new UserInnerPane(user,initArgs));
        }
    }

    public void showAllUsers(int limit) throws IOException, URISyntaxException {
        clearInnerElements();
        initArgs = new ConcreteInitArgs(initArgs.getPadding(),this.getWidth(),initArgs.getPadding());
        List<User> users = getUsersAsList();
        addIEsFromUsers(users.subList(0,Math.min(users.size(),limit)));
    }

    public class UserInnerPane extends LinedPane {

        private Circle pictureCircle = new Circle();
        private Circle statusCircle = new Circle();
        private Label headerLabel = new Label();
        private Label fullNameLabel = new Label();
        private User user;

        public User getUser(){return user;}

        public UserInnerPane(User user,InitArgs initArgs){
            super(initArgs);
            this.user = user;
        }


        @Override
        protected void initElements() {
            this.createCenterLeftCircle(pictureCircle);
            this.createHeaderLabelNextToPicture(headerLabel);
            this.createStatusCircleOn(statusCircle,pictureCircle);
            this.createLabelBellowLabel(fullNameLabel,headerLabel);

            setPicture(new ImagePattern(contentRepository.fetchImageWithFallback(user.picture)));
            setStatusColor(UserStatus.fromKey(user.status));
            setHeaderText(user.login);
            setTextOnLabelLine(user.firstName+" "+user.secondName,0);
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
