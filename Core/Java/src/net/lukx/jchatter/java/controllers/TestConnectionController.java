package net.lukx.jchatter.java.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.User;
import net.lukx.jchatter.lib.repos.UserRepo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TestConnectionController {

    public Button TestBtn;
    public Label Output;
    public TextField Login;
    public PasswordField Password;
    public TextField Uri;

    public TestConnectionController(){

    }

    private String UsersToString(User[] users){
        StringBuilder b = new StringBuilder();
        for (User user : users) {
            b.append(user.id).append("\n");
            b.append(user.status).append("\n");
            b.append(user.email).append("\n");
            b.append(user.firstName).append("\n");
            b.append(user.secondName).append("\n");
            b.append(user.login).append("\n");
            b.append(user.password).append("\n");
            b.append(user.picture).append("\n");
            b.append(user.dateRegistered).append("\n");
            b.append(user.dateLastLogin).append("\n");
            b.append("---");
        }
        return b.toString();
    }

    public void btnClicked(ActionEvent actionEvent) {
        net.lukx.jchatter.lib.comms.Communicator cc;
        Communicator c = new Communicator();
        try {
            c.setServerURI(new URI(Uri.getText()));
            UserRepo userRepo = new UserRepo(c);
            userRepo.getLoginHeader().setPassword(Password.getText());
            userRepo.getLoginHeader().setLogin(Login.getText());
            User[] users = userRepo.getUsers();
            Output.setText(UsersToString(users));
        } catch (URISyntaxException | IOException e) {
            Output.setText(e.getLocalizedMessage());
        }
    }
    
    /*public void TestBtnClicked(MouseEvent mouseEvent) {

    }*/
}
