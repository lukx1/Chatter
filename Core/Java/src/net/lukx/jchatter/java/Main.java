package net.lukx.jchatter.java;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        byte[] b = new byte[]{1,57,32,110,25,36,88,55,1,57,32,110,25,36,88,55};
        String s = Base64.encode(b);
        Parent root = FXMLLoader.load(getClass().getResource("scenes/mainScene.fxml"));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600, Color.BLACK));
        primaryStage.show();
    }

    private void TestA(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
