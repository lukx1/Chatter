package net.lukx.jchatter.java.controls;

import com.sun.istack.internal.Nullable;
import com.sun.org.apache.bcel.internal.generic.LADD;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import net.lukx.jchatter.lib.models.UserStatus;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public abstract class LinedPane extends Pane{

    protected static final double sqrtTwo = 1.4142135623746;
    protected InitArgs initArgs;

    @Nullable
    private Circle pictureCircleRef;
    @Nullable
    private Circle statusCircleRef;
    @Nullable
    private Label headerLabelRef;
    private List<Label> labelLines = new ArrayList<>();

    private List<EventHandler<? super MouseEvent>> pictureCircleClickedListener = new ArrayList<>();

    public String getTextOnLabelLine(int line){
        return labelLines.get(line).getText();
    }

    public void setTextOnLabelLine(String text, int line){
        labelLines.get(line).setText(text);
    }

    public double getHeightWithTopMargin(){
        return this.getHeight()+initArgs.getTopMargin();
    }

    protected abstract void initElements() throws IOException, URISyntaxException;

    public void setStatusColor(UserStatus statusColor){
        switch (statusColor){
            default:
            case NONE:
                setStatusColor(Color.GRAY);
                break;
            case ONLINE:
                setStatusColor(Color.LIGHTGREEN);
                break;
            case AWAY:
                setStatusColor(Color.YELLOW);
                break;
            case OFFLINE:
                setStatusColor(Color.DARKGRAY);
                break;
            case BUSY:
                setStatusColor(Color.ORANGE);
                break;
        }
    }

    public LinedPane(InitArgs initArgs){
        super();
        this.initArgs = initArgs;
        this.setWidth(initArgs.getWidth());
        this.setHeight(initArgs.getHeight());
    }

    public <T extends Event> void removePictureCircleEventHandler(EventType<T> eventType, EventHandler<? super T> listener){
        pictureCircleRef.removeEventHandler(eventType,listener);
    }

    public <T extends Event> void addPictureCircleEventHandler(EventType<T> eventType, EventHandler<? super T> listener){
        if(pictureCircleRef == null){
            throw new IllegalStateException("No picture circle exists(null)");
        }
        pictureCircleRef.addEventHandler(eventType,listener);
    }

    private void addToLabelLines(Label label) {
        if (labelLines.contains(label)) {
            throw new IllegalArgumentException("List already contains this label");
        }
        labelLines.add(label);
    }

    protected Label getLabelAtLineIndex(int index) {
        if (index > labelLines.size() - 1)
            throw new NullPointerException("Label at selected index does not exist");
        return labelLines.get(index);
    }

    protected void setPicture(Paint paint) {
        pictureCircleRef.setFill(paint);
    }

    protected void setStatusColor(Color color) {
        statusCircleRef.setFill(color);
    }

    protected void setHeaderText(String text) {
        headerLabelRef.setText(text);
    }

    protected String getHeaderText() {
        return headerLabelRef.getText();
    }

    protected void createHeaderLabelNextToPicture(Label label) {
        createHeaderLabelNextToPicture(label, true);
    }

    protected void createLabelBellowLabel(Label labelToCreate, Label referenceLabel){
        createLabelBellowLabel(labelToCreate,referenceLabel,referenceLabel.getPrefWidth(),initArgs.getPadding());
    }

    protected void createLabelBellowLabel(Label labelToCreate, Label referenceLabel, double width){
        createLabelBellowLabel(labelToCreate,referenceLabel,width,initArgs.getPadding());
    }

    protected void createLabelBellowLabel(Label labelToCreate, Label referenceLabel, double width, double topPadding) {
        labelToCreate.setLayoutX(referenceLabel.getLayoutX());
        labelToCreate.setMaxWidth(width);
        labelToCreate.setLayoutY(referenceLabel.getLayoutY() + referenceLabel.getPrefHeight() + labelToCreate.getPrefHeight());

        getChildren().add(labelToCreate);
        addToLabelLines(labelToCreate);
    }

    protected void createHeaderLabelNextToPicture(Label label, boolean largeText) {
        if (pictureCircleRef == null)
            throw new NullPointerException("Picture circle not initialized");
        if (largeText) {
            label.getStyleClass().add("LargeText");
            label.setPrefHeight(20*1.25);
        }
        else {
            label.setPrefHeight(20);
        }
        label.setLayoutY(initArgs.getPadding());
        label.setLayoutX(2 * (initArgs.getPadding()+ pictureCircleRef.getRadius()));
        label.setPrefWidth(initArgs.getWidth() - label.getLayoutX() - initArgs.getPadding());


        getChildren().add(label);
        headerLabelRef = label;
    }

    @SuppressWarnings("Duplicates")
    protected void createStatusCircleOn(Circle statusCircle, Circle pictureCircle) {
        statusCircle.setRadius(pictureCircle.getRadius() / 3.0);
        statusCircle.setLayoutY(pictureCircle.getLayoutY() + pictureCircle.getRadius() / sqrtTwo);
        statusCircle.setLayoutX(pictureCircle.getLayoutX() + pictureCircle.getRadius() / sqrtTwo);
        statusCircle.setFill(Color.GRAY);
        statusCircle.setStroke(null);

        getChildren().add(statusCircle);
        this.statusCircleRef = statusCircle;
    }

    @SuppressWarnings("Duplicates")
    protected void createStatusCircleTextOn(Circle statusCircle, Text text, StackPane stackPane, Circle pictureCircle) {
        statusCircle.setRadius(pictureCircle.getRadius() / 3.0);
        stackPane.setLayoutY(pictureCircle.getLayoutY() + pictureCircle.getRadius() / sqrtTwo);
        stackPane.setLayoutX(pictureCircle.getLayoutX() + pictureCircle.getRadius() / sqrtTwo);
        statusCircle.setFill(Color.GRAY);
        statusCircle.setStroke(null);

        stackPane.getChildren().add(statusCircle);
        stackPane.getChildren().add(text);

        getChildren().add(stackPane);
        this.statusCircleRef = statusCircle;
    }

    protected void createCenterLeftCircle(Circle circle) {
        createCenterLeftCircle(circle,(initArgs.getHeight() - 2.0 * initArgs.getPadding())/2.0);
    }


    protected void createCenterLeftCircle(Circle circle, double radius) {
        circle.setRadius(radius);
        circle.setLayoutY(circle.getRadius() + initArgs.getPadding());
        circle.setLayoutX(circle.getRadius() + initArgs.getPadding());
        circle.setFill(null);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        getChildren().add(circle);
        this.pictureCircleRef = circle;
    }

}
