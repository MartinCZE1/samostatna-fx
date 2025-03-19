package com.example.maturita;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box {

    private double x;
    private double y;
    private double size;
    private boolean inPosition = false;
    private double canvasWidth = 750;
    private double canvasHeight = 750;

    public Box(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size);
    }

    public void drawInPosition(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, size, size);
    }

    public void move(int dx, int dy) {
        this.x = this.x + dx;
        this.y = this.y + dy;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getSize() {
        return this.size;
    }

    public void setInPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }

    public boolean isInPosition() {
        return this.inPosition;
    }
}
