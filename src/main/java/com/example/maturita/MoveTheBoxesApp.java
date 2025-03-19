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
    Box[] boxes;
    BoxEndPosition[] endPositions;
    Box pushedBox = null;
    int windowWidth = 750;
    int windowHeight = 750;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        player = new Player(100, 100, 30);
        boxes = new Box[]{new Box(500, 200, 30), new Box(400, 200, 30)};
        endPositions = new BoxEndPosition[]{new BoxEndPosition(500, 400), new BoxEndPosition(650, 400)};

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
        } else if (event.getCode() == KeyCode.A) {
            player.moveLeft();
        } else if (event.getCode() == KeyCode.S) {
            player.moveDown();
        } else if (event.getCode() == KeyCode.D) {
            player.moveRight();
        }
    }

    void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.W) {
            player.stopMoveUp();
        } else if (event.getCode() == KeyCode.A) {
            player.stopMoveLeft();
        } else if (event.getCode() == KeyCode.S) {
            player.stopMoveDown();
        } else if (event.getCode() == KeyCode.D) {
            player.stopMoveRight();
        }
    }

    void tick(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, windowWidth, windowHeight);

        player.updatePosition();
        player.draw(gc);

        for (int i = 0; i < boxes.length; i++) {
            boxes[i].draw(gc);
        }

        for (int i = 0; i < endPositions.length; i++) {
            endPositions[i].draw(gc);
        }

        if (pushedBox == null) {
            for (int i = 0; i < boxes.length; i++) {
                if (player.isInCollision(boxes[i])) {
                    pushedBox = boxes[i];
                    break;
                }
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

                if (player.isInCollision(pushedBox) == false) {
                    //pushedBox.move(dx, dy);
                    pushedBox = null;
                }
            } else {

               pushedBox = null;
            }
        }

        checkWinCondition(gc);
    }

    void checkWinCondition(GraphicsContext gc) {
        boolean allInPosition = true;
        for (int i = 0; i < boxes.length; i++) {
            boolean inPosition = false;
            for (int j = 0; j < endPositions.length; j++) {
                if (endPositions[j].isInEndPosition(boxes[i])) {
                    inPosition = true;
                    boxes[i].setInPosition(true);
                    break;
                }
            }

            if (inPosition == false) {
                allInPosition = false;
                boxes[i].setInPosition(false);
            }
        }

        if (allInPosition) {
            System.out.println("Nice job!");
        }
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].draw(gc);
            if (boxes[i].isInPosition()) {
                boxes[i].drawInPosition(gc);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
