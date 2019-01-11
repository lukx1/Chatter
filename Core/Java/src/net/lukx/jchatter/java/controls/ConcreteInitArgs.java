package net.lukx.jchatter.java.controls;

public class ConcreteInitArgs implements InitArgs {

    private double padding;
    private double width;
    private double height;
    private double topMargin;

    public ConcreteInitArgs(){}

    public ConcreteInitArgs(double padding, double width, double height) {
        this.padding = padding;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getPadding() {
        return padding;
    }

    public void setPadding(double padding) {
        this.padding = padding;
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }


    @Override
    public double getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(double topMargin) {
        this.topMargin = topMargin;
    }
}
