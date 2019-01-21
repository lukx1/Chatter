package net.lukx.jchatter.java.controls;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.lukx.jchatter.java.supporting.RemovingList;

import java.util.ArrayList;
import java.util.List;

public class LinedPaneManagerBorderPane<T extends LinedPane> extends BorderPane {



    public LinedPaneManagerBorderPane(){
        super();
    }

    protected void addInnerElement(T element){
        getChildren().add(element);
    }



}
