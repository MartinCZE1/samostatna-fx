package com.example.maturita;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoxEndPosition {

    private double x;
    private double y;
    private final int SIZE = 30;

    public BoxEndPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, SIZE, SIZE);
    }

    public boolean isInEndPosition(Box box) {
        double tolerance = box.getSize() / 2;
        return Math.abs(box.getX() + box.getSize() / 2 - this.x - SIZE / 2) < tolerance &&
                Math.abs(box.getY() + box.getSize() / 2 - this.y - SIZE / 2) < tolerance;
    }
}
