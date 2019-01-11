package net.lukx.jchatter.java.controls;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.supporting.RemovingList;
import net.lukx.jchatter.lib.models.Message;

import java.util.List;

public class MessagePane extends Pane{

    private double innerElementPadding;
    private double innerElementTopMargin;
    private double innerElementHeight;

    private List<InnerMessagePane> messagePanes = new RemovingList<>(this);
    

    public class InnerMessagePane extends Pane implements Initable{

        private Message message;

        private int heightIndex = 0;

        private Circle circle;
        private Label name;
        private Label text;
        private Pane filePane;

        public double getYOffset(){
            return heightIndex*(innerElementHeight+innerElementTopMargin);
        }

        public InnerMessagePane(){

        }

        public String getText(){
            return this.text.getText();
        }

        public void setText(String text){

        }

        public String getName(){
            return this.name.getText();
        }

        public void setName(String name){
            this.name.setText(name);
        }

        public void setImage(Image image){
            this.circle.setFill(new ImagePattern(image));
        }

        private void initCircle(){
            circle = new Circle();
            circle.setRadius(8);
            circle.setLayoutY(circle.getRadius()+innerElementPadding);
            circle.setLayoutX(circle.getRadius()+innerElementPadding);
            circle.setFill(null);
            circle.setStrokeWidth(1);
            circle.setStroke(Color.BLACK);
        }

        private void initName(){
            name = new Label();
            name.setLayoutY(circle.getRadius()+innerElementPadding);
            name.setLayoutX(2*(circle.getRadius()+innerElementPadding));
            name.setText("Name not loaded");
        }

        private void initText(){
            text = new Label();
            text.setLayoutY(2*(circle.getRadius()+innerElementPadding));
            text.setLayoutX(innerElementPadding);
            text.setMaxWidth(this.getMaxWidth()-innerElementPadding*2);
            text.setWrapText(true);
        }

        public void initializeInside(int index){
            this.heightIndex = index;
            this.setLayoutY(getYOffset());
            initCircle();
            initName();
            initText();
            this.getChildren().addAll(circle,name,text);
        }

    }

}
