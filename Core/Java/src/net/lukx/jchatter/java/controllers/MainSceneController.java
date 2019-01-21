package net.lukx.jchatter.java.controllers;

import com.sun.glass.ui.Robot;
import com.sun.javafx.robot.FXRobot;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.nashorn.internal.runtime.ECMAException;
import net.lukx.jchatter.java.controls.*;
import net.lukx.jchatter.java.fetching.ContentRepository;
import net.lukx.jchatter.java.fetching.RelUtils;
import net.lukx.jchatter.java.fetching.SelectedInfoPane;
import net.lukx.jchatter.java.fetching.SelectedInformationMarshall;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.PopupMarshall;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.*;
import sun.util.resources.ro.CurrencyNames_ro_RO;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
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
    public Rectangle logoRect;
    public Circle registerCircle;
    public Label RegisterHeader;
    public TextField addGroupTextField;
    public Button addGroupButton;
    public HBox addGroupHolder;
    public HBox addFriendsToGroupHolder;
    public TextField addFriendsToGroupField;
    public Button addFriendstoGroupSearchButton;
    public UsersPane addFriendstoGroupUsersPane;
    public Button addFriendstoGroupBackButton;
    public Button addFriendstoGroupAddButton;

    private GaussianBlur blurEffect;
    private Repos repos;

    private boolean isRegistering = false;

    private ContentRepository contentRepository;

    private PopupMarshall popupMarshall;

    //private RoomMarshall roomMarshall;
    private SelectedInformationMarshall informationMarshall;
    private boolean popupVisible = false;

    private CurrentValues currentValues = CurrentValues.createInstance();

    private File contentRepoFile = new File(System.getenv("LOCALAPPDATA")+"/Chatter/Content");

    private Timer timer = new Timer();


    private void pressWindowsDot(){
        Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
        robot.keyPress(java.awt.event.KeyEvent.VK_WINDOWS); // Left windows key
        robot.keyPress(java.awt.event.KeyEvent.VK_PERIOD); /// The '.' key
        robot.keyRelease(java.awt.event.KeyEvent.VK_PERIOD);
        robot.keyRelease(java.awt.event.KeyEvent.VK_WINDOWS);
    }

    public MainSceneController(){
        try {
            //repos = new Repos(new URI("http://78.102.218.164:8080/api"));
            repos = new Repos(new URI("http://127.0.0.1:8080/api"));
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
        timer.scheduleAtFixedRate(new TimerTask() { public void run() { timerTick(); }},0,1000);
    }

    private int TimerIndex = 0;
    private void timerTick(){
        if(TimerIndex > 1000000){
            TimerIndex = 0;
        }

        if(TimerIndex % 10 == 0){
            Platform.runLater(() -> {
                try {
                    RoomsPane.clearInner();
                    RoomsPane.showAllRooms();
                } catch (IOException | URISyntaxException e) {
                    popupMarshall.makeError(e.toString());
                }
            });
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


    private void updateReceivedMessages(Date sinceDate){
        try {
            for (Room room : repos.getRoomRepo().getRoomsWithUser(currentValues.getCurrentUser().id)) {
                for (Message message : repos.getMessageRepo().getMessagesInRoomSince(room.id,sinceDate)) {
                    if(message.dateReceived == null && message.idsender != currentValues.getCurrentUser().id){
                        message.dateReceived = new Date();
                        repos.getMessageRepo().setMessage(message);
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void doUpdateLastLogin(){
        User cur = currentValues.getCurrentUser();
        cur.dateLastLogin = new Date();
        cur.status = UserStatus.ONLINE.getKey();
        currentValues.setCurrentUser(cur);

        try {
            repos.getUserRepo().setUser(cur);
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }
    }

    private void updateReceivedMessagesInNewThread(Date sinceDate){
        new Thread(() -> updateReceivedMessages(sinceDate)).start();
    }

    private void updateLastLogin(){
        Date lastLoginBeforeThisLogin = currentValues.getCurrentUser().dateLastLogin;
        this.currentValues.getCurrentUser().dateLastLogin = new Date();
        updateReceivedMessagesInNewThread(lastLoginBeforeThisLogin);
        new Thread(this::doUpdateLastLogin).start();
    }


    private void loggedIn(){
        updateLastLogin();
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
            SendMessageTextField.setRoom(rooms.get(0));
            informationMarshall.clickedOn(rooms.get(0));
            try {
                MsgPane.showAllMessagesInRoom(rooms.get(0));
            } catch (IOException | URISyntaxException e) {
                popupMarshall.makeError(e.toString());
            }
        }
        Stage stage = (Stage) TopContainer.getScene().getWindow();
        stage.setOnCloseRequest(this::closeRequestHandler);
    }

    private void roomClickedHandler(RoomPane.InnerRoomClickedEvent e){
        try {
            MsgPane.clearInner();
            MsgPane.showAllMessagesInRoom(e.getRoom());
            SendMessageTextField.setRoom(e.getRoom());
            informationMarshall.clickedOn(e.getRoom());
        } catch (IOException | URISyntaxException e1) {
            popupMarshall.makeError(e.toString());
        }
    }

    private void backgroundInit(){
        PictueCircle.setFill(new ImagePattern(new Image("/pictures/nopicture.png")));
    }

    @FXML
    public void initialize(){
        registerCircle.setFill(new ImagePattern(new Image("/pictures/nopicture.png")));
        logoRect.setFill(new ImagePattern(new Image("/pictures/logochatter-wide.png")));
        popupMarshall = new PopupMarshall(PopupPane,PopupHeaderLabel,PopupBodyLabel,PopupCircle);
        popupMarshall.init();
        addFriendsUsersPane.init(repos,contentRepository,currentValues);
        addFriendstoGroupUsersPane.init(repos,contentRepository,currentValues);
        NotifPane.init(repos,contentRepository,currentValues);
        NotifPane.addNotificationButtonClickedHandler(this::notificationButtonClickedHandler);
        backgroundInit();
        openLoginScene();

    }

    private void closeRequestHandler(WindowEvent e){
        if(currentValues.getCurrentUser() != null){
            currentValues.getCurrentUser().status = 0;
            try {
                repos.getUserRepo().setUser(currentValues.getCurrentUser());
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void notificationButtonClickedHandler(net.lukx.jchatter.java.controls.NotificationPane.NotificationButtonEvent e){
        switch (e.getNotificationButtonClicked()){
            case ACCEPT:
                Relationship relOther = e.getRelationship();
                relOther.relationType = RelationshipStatus.FRIEND.getKey();

                Relationship relNew = new Relationship();
                relNew.idsourceUser = currentValues.getCurrentUser().id;
                relNew.idtargetUser = relOther.idsourceUser;
                relNew.dateCreated = new Date();
                relNew.relationType = RelationshipStatus.FRIEND.getKey();

                Room room = new Room();
                room.oneOnOne = true;
                room.name = "1o1";
                room.idcreator = currentValues.getCurrentUser().id;

                try {
                    //int id = repos.getRoomRepo().addRoom(room);
                    //room.id = id;
                    //repos.getRoomRepo().addUserToRoom(currentValues.getCurrentUser().id,id);
                    //repos.getRoomRepo().addUserToRoom(relNew.idtargetUser,id);
                    repos.getRelationshipRepo().setRel(relOther);
                    repos.getRelationshipRepo().addRel(relNew);
                } catch (IOException | URISyntaxException e1) {
                    popupMarshall.makeError(e.toString());
                }
                break;
            case REFUSE:
                try {
                    repos.getRelationshipRepo().deleteRel(e.getRelationship().id);
                } catch (IOException | URISyntaxException e1) {
                    popupMarshall.makeError(e1.toString());
                }
                break;
            case BLOCK:
                try {
                    repos.getRelationshipRepo().deleteRel(e.getRelationship().id);

                    Relationship rel2 = new Relationship();
                    rel2.idsourceUser = currentValues.getCurrentUser().id;
                    rel2.idtargetUser = e.getRelationship().idsourceUser;
                    rel2.dateCreated = new Date();
                    rel2.relationType = RelationshipStatus.BLOCKED.getKey();

                    repos.getRelationshipRepo().addRel(rel2);
                } catch (IOException | URISyntaxException e1) {
                    popupMarshall.makeError(e1.toString());
                }
                break;
        }
        try {
            NotifPane.clearInner();
            NotifPane.showNotifications();
            RoomsPane.clearInner();
            RoomsPane.showAllRooms();
        } catch (IOException | URISyntaxException e1) {
            popupMarshall.makeError(e1.toString());
        }
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
        if(!Pattern.matches("[a-zA-Z0-9_*!@#$%^&]{4,32}",RegisterPasswordField.getText()) && isRegistering){
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
            if(isRegistering && repos.getUserRepo().getUserWithLogin(RegisterNicknameFIeld.getText()) != null){
                popupMarshall.makeWarning("User with same nickname already exists!");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.getMessage());
            return false;
        }
        return true;
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
        isRegistering = true;
        RegisterHeader.setText("Register");
        RegisterButton.setText("Register");
        RegisterNicknameFIeld.setDisable(false);
        registerCircle.setFill(new ImagePattern(new Image("/pictures/nopicture.png")));
        LoginRegisterHolder.setVisible(false);
        RegisterHolder.setVisible(true);
    }

    public void openRegisterAsChange(){
        isRegistering = false;

        RegisterButton.setText("Update");

        toggleScreenBlur();

        RegisterNicknameFIeld.setDisable(true);
        RegisterHeader.setText("Profile");
        LoginRegisterHolder.setVisible(false);
        RegisterHolder.setVisible(true);

        registerCircle.setFill(new ImagePattern(contentRepository.fetchImageWithFallback(currentValues.getCurrentUser().picture)));
        RegisterNameField.setText(currentValues.getCurrentUser().firstName);
        RegisterSurnameField.setText(currentValues.getCurrentUser().secondName);
        RegisterEmailField.setText(currentValues.getCurrentUser().email);
        RegisterNicknameFIeld.setText(currentValues.getCurrentUser().login);
    }

    public void LogoutButtonClicked(ActionEvent actionEvent) {
        openLoginScene();

    }

    public void BackRegisterButtonClicked(ActionEvent actionEvent) {
        RegisterHolder.setVisible(false);
        if(currentValues.getCurrentUser() != null){
            toggleScreenBlur();
        }
        if(isRegistering) {
            LoginRegisterHolder.setVisible(true);
        }

    }

    private User getUserFromOnScreen(){
        User user = new User();
        user.firstName = RegisterNameField.getText();
        user.secondName = RegisterSurnameField.getText();
        user.email = RegisterEmailField.getText();
        if(isRegistering)
            user.password = RegisterPasswordField.getText();
        user.login = RegisterNicknameFIeld.getText();
        return user;
    }

    private void setOrAddProfilePic(){
        return;
        /*try {
            if (currentValues.getSelectedImageFile() != null) {
                byte[] bytes = Files.readAllBytes(currentValues.getSelectedImageFile().toPath());
                if(bytes.length > 500000){
                    popupMarshall.makeError("Image too large max 500KB");
                    return;
                }
                CFile cFile = new CFile();
                cFile.iduploader = currentValues.getCurrentUser().id;
                cFile.idroom = -1;
                cFile.fileName = currentValues.getSelectedImageFile().getName();
                cFile.expired = false;
                cFile.dateUploaded = new Date();
                byte[] uuid = repos.getcFileRepo().addFile(Base64.getEncoder().encodeToString(bytes), cFile);
                //currentValues.getCurrentUser().picture = uuid;
                repos.getUserRepo().setUser(currentValues.getCurrentUser());
            }
        }
        catch (IOException | URISyntaxException e){
            popupMarshall.makeError(e.toString());
        }
        finally {
            currentValues.setSelectedImageFile(null);
        }*/
    }

    private boolean createUserFromOnScreen(){
        User user = getUserFromOnScreen();
        try {
            repos.getUserRepo().registerUser(user);
            changeCurrentUser(repos.getUserRepo().getUserWithLogin(user.login));
            setOrAddProfilePic();
            popupMarshall.makeSuccess("Registered","You have been successfully registered");
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
            return false;
        }
        return true;
    }

    public void FinishRegisterButtonClicked(ActionEvent actionEvent) {
        if(isRegisterValid()){
            if(isRegistering) { // Normal register
                if (!createUserFromOnScreen()) {
                    return;
                }


            }
            else { // Change
                User u = getUserFromOnScreen();
                if(u.password != null)
                    currentValues.getCurrentUser().password = u.password;
                else
                    currentValues.getCurrentUser().password = null;
                currentValues.getCurrentUser().firstName = u.firstName;
                currentValues.getCurrentUser().secondName = u.secondName;
                currentValues.getCurrentUser().email = u.email;
                try {
                    repos.getUserRepo().setUser(currentValues.getCurrentUser());
                    currentValues.setCurrentUser(repos.getUserRepo().getUser(currentValues.getCurrentUser().id));
                } catch (IOException | URISyntaxException e) {
                    popupMarshall.makeError(e.toString());
                    return;
                }
                setOrAddProfilePic();
                RegisterHolder.setVisible(false);
                RegisterNicknameFIeld.setText("");
                RegisterSurnameField.setText("");
                RegisterEmailField.setText("");
                RegisterPasswordField.setText("");
                RegisterNicknameFIeld.setText("");
                RegisterHolder.setVisible(false);
                toggleScreenBlur();
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

    private void toggleAddGroupWindow(){
        if(addGroupHolder.isVisible())
            addGroupHolder.setVisible(false);
        else{
            addGroupHolder.setVisible(true);
            addGroupTextField.setText("");
        }

    }
    
    public void GroupsButtonClicked(ActionEvent actionEvent) {
        toggleScreenBlur();
        toggleAddGroupWindow();
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

    public void registerCircleClicked(MouseEvent mouseEvent) {
        return;
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(TopContainer.getScene().getWindow());
        if (selectedFile != null) {
            Image img = new Image("file:///"+selectedFile.getAbsolutePath());
            registerCircle.setFill(new ImagePattern(img));
            currentValues.setSelectedImageFile(selectedFile);
        }*/
    }

    public void CurrentUserPictureClicked(MouseEvent mouseEvent) {
        openRegisterAsChange();
    }

    private void unfriendUser(){
        try {
            informationMarshall.isUserSelected();
            User otherUser = informationMarshall.getUserSelected();

            Relationship[] relsAboutTarget = repos.getRelationshipRepo().getRelAboutUser(otherUser.id);
            for (Relationship relationship : relsAboutTarget) {
                if (relationship.idsourceUser == currentValues.getCurrentUser().id) {
                    repos.getRelationshipRepo().deleteRel(relationship.id);
                    break;
                }
            }


            RoomsPane.clearInner();
            RoomsPane.showAllRooms();
        } catch (Exception e) {
            popupMarshall.makeError(e.toString());
        }
    }

    private void toggleAddFriendsToGroup(){

        if(addFriendsToGroupHolder.isVisible()){ // HIDE
            addFriendsToGroupHolder.setVisible(false);
        }
        else { // SHOW
            addFriendsToGroupHolder.setVisible(true);
            try {
                addFriendstoGroupUsersPane.showAllFriends(MsgPane.getCurrentRoom().id,addFriendstoGroupAddButton);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }

    }

    public void UnfriendOrManageUsersButtonClicked(ActionEvent actionEvent) {
        if(informationMarshall.isUserSelected()) {
            unfriendUser();
        }
        else { // Room selected, manage
            toggleScreenBlur();
            toggleAddFriendsToGroup();
        }
    }

    public void BlockOrLeaveButtonClicked(ActionEvent actionEvent) {
        if(informationMarshall.isUserSelected()){
            User other = informationMarshall.getUserSelected();
            if(!informationMarshall.isUserBlocked()) {
                try {
                    RelUtils.changeRelWithUser(RelationshipStatus.BLOCKED, repos, other, currentValues.getCurrentUser());
                    RoomsPane.clearInner();
                    RoomsPane.showAllRooms();

                } catch (IOException | URISyntaxException e) {
                    popupMarshall.makeError(e.toString());
                }
            }
            else {
                try {
                    RelUtils.changeRelWithUser(RelationshipStatus.FRIEND, repos, other, currentValues.getCurrentUser());
                    RoomsPane.clearInner();
                    RoomsPane.showAllRooms();
                    BlockOrLeaveButton.setText("BLOCK");
                } catch (IOException | URISyntaxException e) {
                    popupMarshall.makeError(e.toString());
                }
            }
        } //Block user
        else { // Leave room
            try {
                Room room = MsgPane.getCurrentRoom();
                User[] usersInThisRoom = repos.getRoomRepo().getUsersInRoom(room.id);
                if (room.idcreator == currentValues.getCurrentUser().id && usersInThisRoom.length > 1) { // Give the creator status to someone else
                    for (User user : usersInThisRoom) {
                        if(user.id == currentValues.getCurrentUser().id){
                            continue;
                        }
                        else {
                            room.idcreator = user.id;
                            repos.getRoomRepo().setRoom(room);
                            repos.getRoomRepo().removeUserFromRoom(currentValues.getCurrentUser().id,room.id);
                            popupMarshall.makeInfo("You left the group chat and the ownership was given to "+user.login);
                            break;
                        }
                    }
                }
                else if(room.idcreator != currentValues.getCurrentUser().id && usersInThisRoom.length > 1){ // Isnt creator nor only person
                    repos.getRoomRepo().removeUserFromRoom(currentValues.getCurrentUser().id,room.id);
                    popupMarshall.makeInfo("You left the group chat");
                }
                else if(usersInThisRoom.length <= 1){ // Last person in room
                    repos.getRoomRepo().removeRoom(room.id);
                    popupMarshall.makeInfo("Group chat "+room.name+" has been disbanded");
                }
                else {
                    popupMarshall.makeError("Unexpected branch");
                }
                Room[] allRoomsThisUserIsIn = repos.getRoomRepo().getRoomsWithUser(currentValues.getCurrentUser().id);
                if(allRoomsThisUserIsIn.length > 1)
                    MsgPane.showAllMessagesInRoom(allRoomsThisUserIsIn[0]);
                else {
                    MsgPane.clearInner();
                    MsgPane.setCurrentRoomDoNotUse(null);
                }
            }
            catch (Exception e){
                popupMarshall.makeError(e.toString());
            }
        }
    }

    public void SendMessageTextFieldKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            sendClicked(null);
        }
    }

    public void addGroupHolderClicked(MouseEvent mouseEvent) {
        toggleAddGroupWindow();
        toggleScreenBlur();
    }

    public void addGroupButtonClicked(ActionEvent actionEvent) {
        toggleAddGroupWindow();
        toggleScreenBlur();
        String groupName = addGroupTextField.getText();
        if(groupName.length() < 4){
            popupMarshall.makeWarning("Group name must be atleast 4 symbols long!");
            return;
        }
        Room room = new Room();
        room.oneOnOne = false;
        room.idcreator = currentValues.getCurrentUser().id;
        room.name = groupName;
        try {
            room.id = repos.getRoomRepo().addRoom(room);
            repos.getRoomRepo().addUserToRoom(currentValues.getCurrentUser().id,room.id);
            RoomsPane.clearInner();
            RoomsPane.showAllRooms();
            popupMarshall.makeSuccess("New room "+room.name+" created!");
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }
    }

    public void addFriendstoGroupAddButtonClicked(ActionEvent actionEvent) {
        List<User> selected = addFriendstoGroupUsersPane.getUsersSelected();
        if(selected.size() < 1){
            popupMarshall.makeWarning("You didn't select anyone!");
            return;
        }
        Room room = MsgPane.getCurrentRoom();
        if(room.oneOnOne){
            popupMarshall.makeError("Room mismatch");
            return;
        }
        StringBuilder msg = new StringBuilder();
        for (User user : selected) {
            try {
                if(addFriendstoGroupUsersPane.isUserAlreadyInRoom(user)){
                    msg.append("Removed ").append(user.login).append(" from the room.");
                    repos.getRoomRepo().removeUserFromRoom(user.id,room.id);
                }
                else {
                    msg.append("Added ").append(user.login).append(" to the room.");
                    repos.getRoomRepo().addUserToRoom(user.id,room.id);
                }
            } catch (IOException | URISyntaxException e) {
                popupMarshall.makeError(e.toString());
            }
        }
        addFriendstoGroupUsersPane.clearInner();
        try {
            addFriendstoGroupUsersPane.showAllFriends(MsgPane.getCurrentRoom().id,addFriendstoGroupAddButton);
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }
        popupMarshall.makeSuccess("Room management",msg.toString());
    }

    public void addFriendstoGroupBackButtonClicked(ActionEvent actionEvent) {
        toggleAddFriendsToGroup();
        toggleScreenBlur();
    }

    public void addFriendstoGroupSearchButtonClicked(ActionEvent actionEvent) {
        try {
            addFriendstoGroupUsersPane.showUsersMatchingRegex(addFriendsToGroupField.getText());
        } catch (IOException | URISyntaxException e) {
            popupMarshall.makeError(e.toString());
        }
    }

    public void EmojiButtonClicked(ActionEvent actionEvent) {
        SendMessageTextField.requestFocus();
        pressWindowsDot();
    }

    public void FileButtonClicked(ActionEvent actionEvent) {
        if(MsgPane.getCurrentRoom() == null)
            return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Any file", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(TopContainer.getScene().getWindow());
        if (selectedFile != null) {
            try {
                byte[] content = Files.readAllBytes(selectedFile.toPath());
                if(content.length > 3000000){
                    popupMarshall.makeError("File is too large (3MB max)");
                    return;
                }
                CFile cFile = new CFile();
                cFile.dateUploaded = new Date();
                cFile.expired = false;
                cFile.fileName = selectedFile.getName();
                cFile.idroom = MsgPane.getCurrentRoom().id;
                cFile.iduploader = currentValues.getCurrentUser().id;
                byte[] uuid = repos.getcFileRepo().addFile(Base64.getEncoder().encodeToString(content),cFile);
                Message message = new Message();
                message.idsender = currentValues.getCurrentUser().id;
                message.idroomReceiver = MsgPane.getCurrentRoom().id;
                message.dateSent = new Date();
                message.content = "!<"+Base64.getEncoder().encodeToString(uuid)+">!";
                repos.getMessageRepo().addMessage(message);
            } catch (IOException | URISyntaxException e) {
                popupMarshall.makeError(e.toString());
                return;
            }
        }
    }
}
