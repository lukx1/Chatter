package net.lukx.jchatter.java.supporting;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class PopupMarshall {

    private AnchorPane pane;
    private Label header;
    private Label body;
    private Circle circle;
    private volatile boolean popupVisible;
    private double duration = 200;
    private double delay = 5000;
    private double hiddenPos = -100;
    private double shownPos = 0;

    private Animation currentAnimation = null;

    public PopupMarshall(AnchorPane pane, Label header, Label body, Circle circle) {
        this.pane = pane;
        this.header = header;
        this.body = body;
        this.circle = circle;
    }

    private void stopCurrentAnimation(){
        if(currentAnimation != null){
            currentAnimation.stop();
        }
    }

    private void makePopupAutoHide(Animation animation){
        final Timer t = new Timer();
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        currentAnimation = null;
                        if(popupVisible) {
                            Animation reverse = createHideAnimation(0,duration);
                            currentAnimation = reverse;
                            reverse.play();
                        }
                        popupVisible = false;
                    }
                },
                (int)delay
        );
    }

    /*\
    final Animation animation = new Transition() {
            {
                setRate(1);
                setCycleDuration(new Duration(duration));
            }
            @Override
            protected void interpolate(double frac) {
                pane.setLayoutY(100);
            }
        };
     */

    public void init(){
        pane.toFront();
    }

    private Animation createShowAnimation(){
        final TranslateTransition animation = new TranslateTransition();
        animation.setDuration(new Duration(500));
        animation.setToY(100);
        animation.setCycleCount(1);
        animation.setAutoReverse(false);
        animation.setNode(pane);
        return animation;
    }

    private Animation createHideAnimation(double delay, double duration){
        final TranslateTransition reverseAnimation = new TranslateTransition();
        reverseAnimation.setDelay(new Duration(delay));
        reverseAnimation.setDuration(new Duration(duration));
        reverseAnimation.setToY(hiddenPos);
        reverseAnimation.setCycleCount(1);
        reverseAnimation.setAutoReverse(false);
        reverseAnimation.setNode(pane);
        reverseAnimation.play();
        return reverseAnimation;
    }

    public void makeSuccess(String text){
        makePopup("Success",text,Color.ORANGE);
    }

    public void makeSuccess(String header, String text){
        makePopup(header,text,Color.GREEN);
    }

    public void makeInfo(String text){
        makePopup("Information",text,Color.BLUE);
    }

    public void makeInfo(String header, String text){
        makePopup(header,text,Color.BLUE);
    }

    public void makeError(String text){
        makePopup("Error",text,Color.ORANGE);
    }

    public void makeError(String header, String text){
        makePopup(header,text,Color.RED);
    }

    public void makeWarning(String text){
        makePopup("Warning",text,Color.ORANGE);
    }

    public void makeWarning(String header, String text){
        makePopup(header,text,Color.ORANGE);
    }

    public void makePopup(String header,String body, Paint paint){
        if(isPopupVisible()){
            stopCurrentAnimation();
        }
        popupVisible = true;
        this.header.setText(header);
        this.body.setText(body);
        this.circle.setFill(paint);
        doPlay();
    }

    private void doPlay(){
        popupVisible = true;
        currentAnimation = createShowAnimation();
        makePopupAutoHide(currentAnimation);
        currentAnimation.play();
    }

    public void forcePopupHide(){
        stopCurrentAnimation();
        popupVisible = false;
        createHideAnimation(0, duration).play();
    }

    public boolean isPopupVisible() {
        return popupVisible;
    }

}
