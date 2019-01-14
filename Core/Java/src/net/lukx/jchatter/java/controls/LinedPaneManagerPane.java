package net.lukx.jchatter.java.controls;

import javafx.scene.layout.Pane;
import net.lukx.jchatter.java.supporting.RemovingList;

import java.util.ArrayList;
import java.util.List;

public class LinedPaneManagerPane<T extends LinedPane> extends Pane {

    protected List<T> innerElements = new RemovingList<>(this);

    protected void clearInnerElements(){
        innerElements.clear();
    }

    public LinedPaneManagerPane(){
        super();
    }

    protected double getTotalHeightAtIndex(int index){
        double height = 0;
        for (int i = 0; i < index; i++) {
            height += innerElements.get(i).getHeightWithTopMargin();
        }
        return height;
    }

    protected double getTotalHeightAtInnerEnd(){
        return getTotalHeightAtIndex(innerElements.size());
    }

    protected double getTotalHeightAtInnerElement(T element){
        if(!innerElements.contains(element)){
            throw new IllegalArgumentException("Element is not contained in this pane");
        }

        return getTotalHeightAtIndex(innerElements.indexOf(element));
    }

    protected void addInnerElement(T element){
        element.setLayoutY(getTotalHeightAtInnerEnd());//TODO:maybe add topMargin
        innerElements.add(element);
        this.setPrefHeight(getTotalHeightAtInnerEnd());
    }

    protected void insertInnerElementAtLine(T element, int line){
        for (int i = line; i < innerElements.size(); i++) {//TODO:check
            T ie = innerElements.get(i);
            ie.setLayoutY(ie.getLayoutY()+element.getHeightWithTopMargin());
        }
        innerElements.add(line,element);
    }

    public int getLineOfElement(T element){
        return innerElements.indexOf(element);
    }

    public void removeInnerElement(T element){
        removeInnerElementAtLine(innerElements.indexOf(element));
    }

    public void removeInnerElementAtLine(int index){
        T elemToRemove = innerElements.get(index);
        if(index != innerElements.size()-1){ // Element is not last
            for (int i = index; i < innerElements.size(); i++) {
                T ie = innerElements.get(i);
                ie.setLayoutY(ie.getLayoutY()-elemToRemove.getHeightWithTopMargin());
            }
            innerElements.remove(elemToRemove);
        }
        else {
            innerElements.remove(index);
        }
        this.setPrefHeight(getTotalHeightAtInnerEnd());
    }

    public boolean containsInnerElement(T inner){
        return innerElements.contains(inner);
    }

    public T getInnerElement(int index){
        return innerElements.get(index);
    }

}
