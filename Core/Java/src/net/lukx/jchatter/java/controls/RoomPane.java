package net.lukx.jchatter.java.controls;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.supporting.RemovingList;
import net.lukx.jchatter.lib.models.Room;

import java.util.List;

public class RoomPane extends Pane {

    private double innerElementHeight = 60;
    private double innerElementPadding = 6;
    private double innerElementTopMargin = 6;

    private List<InnerRoomPane> roomPanes = new RemovingList<>(this);

    public List<InnerRoomPane> getRoomPanes(){
        return roomPanes;
    }

    private void recalculateHeight(){
        this.setPrefHeight(
                Math.max(
                        roomPanes.size()*(innerElementHeight+innerElementPadding),
                        this.getPrefHeight()
                )
        );
    }

    public  class InnerRoomPane extends Pane{

        private Circle pictureCircle;
        private Circle statusCircle;
        private Label nameLabel;
        private double height;
        private double width;
        private Room room;
        private int heightIndex;
        private static final double sqrtTwo = 1.4142135623746;

        public InnerRoomPane(Room room) {
            this.room = room;
        }

        public Room getRoom() {
            return room;
        }

        public boolean isStatusCircleHidden(){
            return !statusCircle.isVisible();
        }

        public void showStatusCircle(){
            statusCircle.setVisible(true);
        }

        public void hideStatusCircle(){
            statusCircle.setVisible(false);
        }

        public int getHeightIndex() {
            return heightIndex;
        }

        public void setHeightIndex(int heightIndex) {
            if(this.heightIndex == heightIndex) {
                return;
            }
            this.heightIndex = heightIndex;
            this.setLayoutY(getYOffset());
        }

        public void setStatusColor(Color color){
            this.statusCircle.setFill(color);
        }

        public void setImage(Image image){
            this.pictureCircle.setFill(new ImagePattern(image));
        }

        public String getName(){
            return this.nameLabel.getText();
        }

        public void setName(String name){
            this.nameLabel.setText(name);
        }

        private double getYOffset(){
            return heightIndex*(innerElementHeight+innerElementTopMargin);
        }

        private void initPictureCircle(){
            pictureCircle = new Circle();
            pictureCircle.setRadius((innerElementHeight-2*innerElementPadding)/2.0);
            pictureCircle.setLayoutX(innerElementPadding+pictureCircle.getRadius());
            pictureCircle.setLayoutY(innerElementPadding+pictureCircle.getRadius());
            pictureCircle.setFill(null);
            pictureCircle.setStroke(Color.BLACK);
            pictureCircle.setStrokeWidth(1);
        }

        private void initStatusCircle(){
            statusCircle = new Circle();
            statusCircle.setRadius(pictureCircle.getRadius()/3.0);
            statusCircle.setLayoutY(pictureCircle.getLayoutY()+pictureCircle.getRadius()/sqrtTwo);
            statusCircle.setLayoutX(pictureCircle.getLayoutX()+pictureCircle.getRadius()/sqrtTwo);
            statusCircle.setFill(Color.GRAY);
            statusCircle.setStroke(null);
        }

        private void initNameLabel(){
            nameLabel = new Label();
            nameLabel.getStyleClass().add("roomName");
            nameLabel.setLayoutY(innerElementPadding);
            nameLabel.setLayoutX(2*(innerElementPadding*pictureCircle.getRadius()));
        }

        public void initializeInside(int heightIndex){
            this.heightIndex = heightIndex;
            this.setLayoutY(getYOffset());
            recalculateHeight();
            initPictureCircle();
            initStatusCircle();
            initNameLabel();
            this.getChildren().addAll(pictureCircle,statusCircle,nameLabel);
        }


    }

    public double getInnerElementHeight() {
        return innerElementHeight;
    }

    public void setInnerElementHeight(double innerElementHeight) {
        this.innerElementHeight = innerElementHeight;
    }

    public double getInnerElementPadding() {
        return innerElementPadding;
    }

    public void setInnerElementPadding(double innerElementPadding) {
        this.innerElementPadding = innerElementPadding;
    }

    public double getInnerElementTopMargin() {
        return innerElementTopMargin;
    }

    public void setInnerElementTopMargin(double innerElementTopMargin) {
        this.innerElementTopMargin = innerElementTopMargin;
    }

    private InnerRoomPane getPaneWithWindow(Room room){
        InnerRoomPane pane = null;
        for (InnerRoomPane roomPane : roomPanes) {
            if(roomPane.room == room){
                pane = roomPane;
                break;
            }
        }
        return pane;
    }


    public RoomPane() {

    }


}
