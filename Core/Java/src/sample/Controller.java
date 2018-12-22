package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URI;

public class Controller {
    public Label helloWorld;
    public TextField URI;
    public TextField Login;
    public PasswordField Password;

    public void sayHelloWorld(ActionEvent actionEvent) {
       // Communicator com = new Communicator();
        
        helloWorld.setText("Hello world");
    }
}
