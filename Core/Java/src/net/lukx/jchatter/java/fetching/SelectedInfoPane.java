package net.lukx.jchatter.java.fetching;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class SelectedInfoPane {

    private Pane pane;
    private Circle circle;
    private Label topLabel;
    private Label bottomLAbel;
    private Button topButton;
    private Button bottomButton;

    public Circle getCircle() {
        return circle;
    }

    public Label getTopLabel() {
        return topLabel;
    }

    public Label getBottomLAbel() {
        return bottomLAbel;
    }

    public Button getTopButton() {
        return topButton;
    }

    public Button getBottomButton() {
        return bottomButton;
    }

    public SelectedInfoPane(Pane pane,Circle circle, Label topLabel, Label bottomLAbel, Button topButton, Button bottomButton){
        this.pane = pane;
        this.circle = circle;
        this.topLabel = topLabel;
        this.bottomLAbel = bottomLAbel;
        this.topButton = topButton;
        this.bottomButton = bottomButton;
    }


    public Pane getPane() {
        return pane;
    }
}
