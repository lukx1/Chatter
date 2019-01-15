package net.lukx.jchatter.java.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.controls.*;
import net.lukx.jchatter.java.fetching.ContentRepository;
import net.lukx.jchatter.java.fetching.SelectedInfoPane;
import net.lukx.jchatter.java.fetching.SelectedInformationMarshall;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.PopupMarshall;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;

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
    public Button EmojiButton;
    public Button FileButton;
    public AnchorPane InfoPane;
    public AnchorPane NotificationPane;
    public Circle NotificationCountCircle;
    public TextField LoginField;
    public javafx.scene.control.PasswordField PasswordField;
    public Label SelectedUserOrGroupLabel;
    public Label SelectedUserOrGroupInfo;
    public Button UnfriendOrManageUsersButton;
    public Button BlockOrLeaveButton;
    public javafx.scene.control.PasswordField RegisterPasswordField;
    public TextField RegisterNameField;
    public TextField RegisterSurnameField;
    public TextField RegisterNicknameFIeld;
    public TextField RegisterEmailField;
    public Button FinishRegisterButton;
    public Button BackRegisterButton;
    public HBox RegisterHolder;
    public AnchorPane RegisterInnerPane;
    public Circle CurrentUserPicture;
    public Button FriendsButton;
    public Button GroupsButton;
    public AnchorPane PopupPane;
    public Label PopupHeaderLabel;
    public Label PopupBodyLabel;
    public Circle PopupCircle;
    public TextField addFriendsField;
    public Button addFriendsSearchButton;
    public UsersPane addFriendsUsersPane;
    public Button addFriendsBackButton;
    public Button addFriendsAddButton;
    public HBox FriendsHolder;
    public net.lukx.jchatter.java.controls.NotificationPane NotifPane;
    public FlowingMessagePane MsgPane;//HERE
    public MessageTextBox SendMessageTextField;

    private GaussianBlur blurEffect;
    private Repos repos;

    private ContentRepository contentRepository;

    private PopupMarshall popupMarshall;

    //private RoomMarshall roomMarshall;
    private SelectedInformationMarshall informationMarshall;
    private boolean popupVisible = false;

    private CurrentValues currentValues = CurrentValues.createInstance();

    private File contentRepoFile = new File(System.getenv("LOCALAPPDATA")+"/Chatter/Content");

    private Timer timer = new Timer();

    public MainSceneController(){
        try {
            repos = new Repos(new URI("http://78.102.218.164:8080/api"));
            repos.init("aaa","aaa");
            contentRepository = new ContentRepository(repos.getcFileRepo(),contentRepoFile);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private Relationship getRelationshipWithOther(User other) throws IOException, URISyntaxException {
        Relationship[] rels = repos.getRelationshipRepo().getRelForUser(other.id);
        Relationship withOter = null;
        for (Relationship rel : rels) {
            if(rel.idtargetUser == other.id){
                withOter = rel;
            }
        }
        return withOter;
    }

    private User getOtherUserInRoom(Room room) throws IOException, URISyntaxException {
        User[] users = repos.getRoomRepo().getUsersInRoom(room.id);
        User other;
        if(users[0] == currentValues.getCurrentUser()){
            other = users[1];
        }
        else {
            other = users[0];
        }
        return other;
    }

    private void changeCurrentUser(User user){
        UsernameLabel.setText(user.firstName+" "+user.secondName);
        Image image;
        if(user.picture != null){
            image = contentRepository.fetchImageWithFallback(user.picture);

        }
        else {
            image = contentRepository.getFallbackImage();
        }
        CurrentUserPicture.setFill(new ImagePattern(image));
        currentValues.setCurrentUser(user);
    }

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
        LoginRegisterHolder.setVisible(true);
        toggleScreenBlur();
    }

    private void closeLoginScene(){
        LoginRegisterHolder.setVisible(false);
        LoginField.setText("");
        PasswordField.setText("");
        toggleScreenBlur();
        loggedIn();
        setStatusOnline();
        timer.scheduleAtFixedRate(new TimerTask() { public void run() { timerTick(); }},0,1000);
    }

    private int TimerIndex = 0;
    private void timerTick(){
        if(TimerIndex > 1000000){
            TimerIndex = 0;
        }

        if(TimerIndex % 10 == 0){
            setStatusOnline();
        }

        if(MsgPane.getCurrentRoom() != null) {
            Platform.runLater(() -> {
                try {
                    MsgPane.clearInner();
                    MsgPane.showAllMessagesInRoom(MsgPane.getCurrentRoom());
                } catch (IOException | URISyntaxException e) {
                    popupMarshall.makeError(e.toString());
                }
            });
        }
    }

    private void setStatusOnline(){
        /*User cur = currentValues.getCurrentUser();
        cur.dateLastLogin = new Date();
        cur.status = UserStatus.ONLINE.getKey();
        currentValues.setCurrentUser(cur);

        try {
            repos.getUserRepo().setUser(cur);
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }*/
    }

    private void loggedIn(){
        TopContainer.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED,this::globalClickHandler);


        //roomMarshall = new RoomMarshall(RoomsPane,repos.getRoomRepo(),repos.getRelationshipRepo(),contentRepository,currentValues);
        informationMarshall = new SelectedInformationMarshall(
                new SelectedInfoPane(
                        InfoPane,
                        PictueCircle,
                        SelectedUserOrGroupLabel,
                        SelectedUserOrGroupInfo,
                        UnfriendOrManageUsersButton,
                        BlockOrLeaveButton
                ),
                repos,
                contentRepository,
                currentValues
        );
        try {
            //roomMarshall.loadForUser();
            informationMarshall.init();
            NotifPane.showNotifications();
            NotifPane.clearInner();
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }

        RoomsPane.init(repos,contentRepository,currentValues);
        RoomsPane.addInnerRoomClickedHandler(this::roomClickedHandler);
        try {
            RoomsPane.showAllRooms();
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }
        SendMessageTextField.init(repos,currentValues,popupMarshall);
        MsgPane.init(repos,contentRepository,currentValues,popupMarshall);
        List<Room> rooms = RoomsPane.getAllRoomsShown();
        if(rooms.size() > 0){
            SendMessageTextField.setRoom(rooms.get(0));//TODO:Since
            try {
                MsgPane.showAllMessagesInRoom(rooms.get(0));
            } catch (IOException | URISyntaxException e) {
                popupMarshall.makeError(e.toString());
            }
        }

    }

    private void roomClickedHandler(RoomPane.InnerRoomClickedEvent e){
        try {
            MsgPane.clearInner();
            MsgPane.showAllMessagesInRoom(e.getRoom());
            SendMessageTextField.setRoom(e.getRoom());
        } catch (IOException | URISyntaxException e1) {
            popupMarshall.makeError(e.toString());
        }
    }

    private void backgroundInit(){
        PictueCircle.setFill(new ImagePattern(new Image("/pictures/nopicture.png")));
    }

    @FXML
    public void initialize(){
        popupMarshall = new PopupMarshall(PopupPane,PopupHeaderLabel,PopupBodyLabel,PopupCircle);
        popupMarshall.init();
        addFriendsUsersPane.init(repos,contentRepository,currentValues);
        NotifPane.init(repos,contentRepository,currentValues);
        backgroundInit();
        openLoginScene();
    }

    private void sometingDebug(){

    }

    private boolean isLoginValid(String username, String password){
        try {
            return repos.tryChangeLogin(username,password);
        }
        catch (IOException | URISyntaxException e ){
            popupMarshall.makeWarning(e.getMessage());
            return false;
        }
    }

    private boolean isRegisterValid(){
        if(!Pattern.matches("[a-zA-Z]{2,24}",RegisterNameField.getText())){
            popupMarshall.makeWarning("Invalid name");
            return false;
        }
        if(!Pattern.matches("[a-zA-Z]{2,24}",RegisterSurnameField.getText())){
            popupMarshall.makeWarning("Invalid surname");
            return false;
        }
        if(!Pattern.matches("[a-zA-Z0-9_]{2,24}",RegisterNicknameFIeld.getText())){
            popupMarshall.makeWarning("Invalid nickname");
            return false;
        }
        if(!Pattern.matches("[a-zA-Z0-9_*!@#$%^&]{4,32}",RegisterPasswordField.getText())){
            if(RegisterPasswordField.getText().length() < 4){
                popupMarshall.makeWarning("Password is not long enough");
            }
            else {
                popupMarshall.makeWarning("Invalid password");
            }
            return false;
        }
        if(!Pattern.matches(".+@.+\\..+",RegisterEmailField.getText())){
            popupMarshall.makeWarning("Invalid email");
            return false;
        }

        try {
            if(repos.getUserRepo().getUserWithLogin(RegisterNicknameFIeld.getText()) != null){
                popupMarshall.makeWarning("User with same nickname already exists!");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.getMessage());
            return false;
        }
        return true;
    }

    private void openRegister(){
        LoginRegisterHolder.setVisible(false);
    }

    public void LoginClicked(ActionEvent actionEvent)
    {
        if(isLoginValid(LoginField.getText(),PasswordField.getText())) {
            try {
                changeCurrentUser(repos.getUserRepo().getUserWithLogin(LoginField.getText()));
            } catch (IOException | URISyntaxException e) {
                popupMarshall.makeWarning(e.getMessage());
                return;
            }
            closeLoginScene();
        }
        else {
            popupMarshall.makeWarning("Invalid login information");
        }
    }

    public void RegisterClicked(ActionEvent actionEvent) {
        LoginRegisterHolder.setVisible(false);
        RegisterHolder.setVisible(true);
    }

    public void LogoutButtonClicked(ActionEvent actionEvent) {
        openLoginScene();

    }

    public void BackRegisterButtonClicked(ActionEvent actionEvent) {
        RegisterHolder.setVisible(false);
        LoginRegisterHolder.setVisible(true);
    }

    private boolean createUserFromOnScreen(){
        User user = new User();
        user.firstName = RegisterNameField.getText();
        user.secondName = RegisterSurnameField.getText();
        user.email = RegisterEmailField.getText();
        user.password = RegisterPasswordField.getText();
        user.login = RegisterNicknameFIeld.getText();
        try {
            repos.getUserRepo().registerUser(user);
            changeCurrentUser(repos.getUserRepo().getUserWithLogin(user.login));
            popupMarshall.makeSuccess("Registered","You have been successfully registered");
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
            return false;
        }
        return true;
    }

    public void FinishRegisterButtonClicked(ActionEvent actionEvent) {
        if(isRegisterValid()){
            if(!createUserFromOnScreen()){
                return;
            }
            RegisterHolder.setVisible(false);
            RegisterNicknameFIeld.setText("");
            RegisterSurnameField.setText("");
            RegisterEmailField.setText("");
            RegisterPasswordField.setText("");
            RegisterNicknameFIeld.setText("");
            closeLoginScene();
        }
    }

    private void toggleFriendsWindow(){
        if(!FriendsHolder.isVisible()){ // Show
            toggleScreenBlur();
            FriendsHolder.setVisible(true);
            addFriendsField.setText("");
            addFriendsUsersPane.clearInner();
            try {
                addFriendsUsersPane.showAllUsers();
            } catch (IOException | URISyntaxException e) {
                popupMarshall.makeError(e.toString());
            }
        }
        else { // Hide
            FriendsHolder.setVisible(false);
            toggleScreenBlur();
        }
    }

    public void FriendsButtonClicked(ActionEvent actionEvent) {
        toggleFriendsWindow();
    }

    public void GroupsButtonClicked(ActionEvent actionEvent) {
    }

    public void PopupClicked(MouseEvent e){}

    private void globalClickHandler(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        if((x > 60 && x < TopPane.getScene().getWidth()-60) && (y > 0 && y < 100)){
            popupMarshall.forcePopupHide();
        }
    }

    private Relationship getRelWithUserOrNewRel(int user, Relationship[] relationships){
        for (Relationship relationship : relationships) {
            if(relationship.idtargetUser == user)
                return relationship;
        }
        return new Relationship();
    }

    private void sendFriendsRequests(List<User> users){
        if(users.size() < 1){
            return;
        }
        try {
            Relationship[] allMyRels = repos.getRelationshipRepo().getRelForUser(currentValues.getCurrentUser().id);
            for (User user : users) {
                Relationship r = getRelWithUserOrNewRel(user.id,allMyRels);
                if((r.relationType & RelationshipStatus.FRIENDSHIP_PENDING.getKey()) > 0 || //Already a friends
                        (r.relationType & RelationshipStatus.FRIEND.getKey()) > 0){         //Or pending
                    continue;
                }
                r.idtargetUser = user.id;
                r.idsourceUser = currentValues.getCurrentUser().id;
                r.relationType |= RelationshipStatus.FRIENDSHIP_PENDING.getKey();

                repos.getRelationshipRepo().addRel(r);
            }
        }
        catch(IOException | URISyntaxException e){
            popupMarshall.makeError(e.toString());
        }
        popupMarshall.makeSuccess("Friendship","Friendship requests sent!");

    }

    public void addFriendsSearchButtonClicked(ActionEvent actionEvent) {
        addFriendsUsersPane.clearInner();

        try {
            addFriendsUsersPane.showUsersMatchingRegex(addFriendsField.getText());
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }
    }

    public void addFriendsAddButtonClicked(ActionEvent actionEvent) {
        sendFriendsRequests(addFriendsUsersPane.getUsersSelected());
        toggleFriendsWindow();
    }

    public void addFriendsBackButtonClicked(ActionEvent actionEvent) {
        toggleFriendsWindow();
    }

    public void sendClicked(ActionEvent actionEvent) {
        Date beforeSent = new Date();
        SendMessageTextField.sendMessage();
        SendMessageTextField.setText("");
        try {
            MsgPane.showAllMessagesInRoomSince(MsgPane.getCurrentRoom(),beforeSent);
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }
    }
}
