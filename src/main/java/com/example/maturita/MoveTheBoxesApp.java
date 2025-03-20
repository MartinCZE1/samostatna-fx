package com.example.maturita;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class MoveTheBoxesApp extends Application {

    Player player;
    Box box1;
    Box box2;
    BoxEndPosition end1;
    BoxEndPosition end2;
    Box pushedBox = null;
    int windowWidth = 750;
    int windowHeight = 750;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        player = new Player(100, 100, 30);
        box1 = new Box(500, 200, 30);
        box2 = new Box(400, 200, 30);
        end1 = new BoxEndPosition(500, 400);
        end2 = new BoxEndPosition(650, 400);

        canvas.setOnKeyPressed(this::handleKeyPress);
        canvas.setOnKeyReleased(this::handleKeyRelease);
        canvas.setFocusTraversable(true);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                tick(gc);
            }
        }.start();

        Group root = new Group(canvas);
        Scene scene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setTitle("Move the Boxes Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.W) {
            player.moveUp();
        }
        if (event.getCode() == KeyCode.A) {
            player.moveLeft();
        }
        if (event.getCode() == KeyCode.S) {
            player.moveDown();
        }
        if (event.getCode() == KeyCode.D) {
            player.moveRight();
        }
    }

    void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.W) {
            player.stopMoveUp();
        }
        if (event.getCode() == KeyCode.A) {
            player.stopMoveLeft();
        }
        if (event.getCode() == KeyCode.S) {
            player.stopMoveDown();
        }
        if (event.getCode() == KeyCode.D) {
            player.stopMoveRight();
        }
    }

    void tick(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, windowWidth, windowHeight);

        player.updatePosition();
        player.draw(gc);

        box1.draw(gc);
        box2.draw(gc);
        end1.draw(gc);
        end2.draw(gc);

        if (pushedBox == null) {
            if (player.isInCollision(box1)) {
                pushedBox = box1;
            } else if (player.isInCollision(box2)) {
                pushedBox = box2;
            }
        }

        if (pushedBox != null) {
            int dx = player.getDx();
            int dy = player.getDy();

            double newBoxX = pushedBox.getX() + dx;
            double newBoxY = pushedBox.getY() + dy;

            if (newBoxX >= 0 && newBoxX <= windowWidth - pushedBox.getSize() &&
                    newBoxY >= 0 && newBoxY <= windowHeight - pushedBox.getSize()) {
                pushedBox.move(dx, dy);
                if (!player.isInCollision(pushedBox)) {
                    pushedBox = null;
                }
            } else {
                pushedBox = null;
            }
        }

        checkWinCondition(gc);
    }

    void checkWinCondition(GraphicsContext gc) {
        boolean box1InPlace = end1.isInEndPosition(box1) || end2.isInEndPosition(box1);
        boolean box2InPlace = end1.isInEndPosition(box2) || end2.isInEndPosition(box2);

        if (box1InPlace) {
            box1.setInPosition(true);
        } else {
            box1.setInPosition(false);
        }

        if (box2InPlace) {
            box2.setInPosition(true);
        } else {
            box2.setInPosition(false);
        }

        if (box1InPlace && box2InPlace) {
            System.out.println("Nice job!");
        }

        box1.draw(gc);
        box2.draw(gc);

        if (box1.isInPosition()) {
            box1.drawInPosition(gc);
        }
        if (box2.isInPosition()) {
            box2.drawInPosition(gc);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
