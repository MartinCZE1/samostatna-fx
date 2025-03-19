package com.example.maturita;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {

    private double x;
    private double y;
    private double size;
    private int dx;
    private int dy;
    private double canvasWidth = 750;
    private double canvasHeight = 750;

    public Player(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.dx = 0;
        this.dy = 0;
    }

    public void moveUp() {
        dy = -5;
    }

    public void moveDown() {
        dy = 5;
    }

    public void moveLeft() {
        dx = -5;
    }

    public void moveRight() {
        dx = 5;
    }

    public void stopMoveUp() {
        if (dy < 0) {
            dy = 0;
        }
    }

    public void stopMoveDown() {
        if (dy > 0) {
            dy = 0;
        }
    }

    public void stopMoveLeft() {
        if (dx < 0) {
            dx = 0;
        }
    }

    public void stopMoveRight() {
        if (dx > 0) {
            dx = 0;
        }
    }

    public void updatePosition() {
        if (x + dx >= 0 && x + dx <= canvasWidth - size) {
            x = x + dx;
        } else if (x + dx < 0) {
            x = 0;
        } else if (x + dx > canvasWidth - size) {
            x = canvasWidth - size;
        }

        if (y + dy >= 0 && y + dy <= canvasHeight - size) {
            y = y + dy;
        } else if (y + dy < 0) {
            y = 0;
        } else if (y + dy > canvasHeight - size) {
            y = canvasHeight - size;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(x, y, size, size);
    }

    public boolean isInCollision(Box box) {
        if (x < box.getX() + box.getSize() &&
                x + size > box.getX() &&
                y < box.getY() + box.getSize() &&
                y + size > box.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
