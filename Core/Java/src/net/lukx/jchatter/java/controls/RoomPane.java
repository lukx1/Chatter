package net.lukx.jchatter.java.controls;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.lib.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomPane extends Pane {

    private double innerElementHeight = 60;
    private double innerElementPadding = 6;
    private double innerElementTopMargin = 6;

    private List<InnerRoomPane> roomPanes = new ArrayList<>();

    private void recalculateHeight(){
        this.setPrefHeight(
                Math.max(
                        roomPanes.size()*(innerElementHeight+innerElementPadding),
                        this.getPrefHeight()
                )
        );
    }

    private class InnerRoomPane extends Pane{

        private Circle pictureCircle;
        private Circle statusCircle;
        private Label nameLabel;
        private Room room;
        private double height;
        private double width;
        private int heightIndex;
        private final double sqrtTwo = 1.4142135623746;

        private double getYOffset(){
            return heightIndex*(innerElementHeight+innerElementTopMargin);
        }

        private void initPictureCircle(){
            pictureCircle = new Circle();
            pictureCircle.setRadius((innerElementHeight-2*innerElementPadding)/2.0);
            pictureCircle.setLayoutX(innerElementPadding+pictureCircle.getRadius());
            pictureCircle.setLayoutY(getYOffset()+innerElementPadding+pictureCircle.getRadius());
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

        public void initializeInside(int heightIndex){
            this.heightIndex = heightIndex;
            recalculateHeight();
            initPictureCircle();
            initStatusCircle();
            this.getChildren().add(pictureCircle);
            this.getChildren().add(statusCircle);
        }

    }

    public void addRoom(Room room){
        InnerRoomPane irp = new InnerRoomPane();
        roomPanes.add(irp);
        this.getChildren().add(irp);
        irp.initializeInside(roomPanes.size()-1);
    }

    public boolean removeRoom(Room room){
        return roomPanes.remove(room);
    }

    public RoomPane() {

    }


}
