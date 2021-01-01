package org.headroyce.kenisi;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class DrawingArea extends StackPane {

    // The main drawing canvas
    private final Canvas mainCanvas;

    // The plan to draw
    private Plan activePlan;

    // All the selected shapes in the world
    private final DrawingWorkspace mainWorkspace;

    private Instant time;
    private final Body_Tool tool;
    private double radius;
    private boolean mouseHeld;
    private boolean pause;
    private Logic logic;

    public DrawingArea(DrawingWorkspace mw) {
        mouseHeld = false;
        pause = false;
        tool = new Body_Tool();
        mainWorkspace = mw;
        mainCanvas = new Canvas();
        AnimTimer timer = new AnimTimer();
        logic = new Logic();
        timer.start();
        logic.start();

        // Force the canvas to resize to the screen's size
        mainCanvas.widthProperty().bind(this.widthProperty());
        mainCanvas.heightProperty().bind(this.heightProperty());

        // Attach mouse handlers to the canvas
        EventHandler<MouseEvent> handler = new MouseHandler();
        mainCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
        mainCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
        mainCanvas.addEventHandler(MouseEvent.MOUSE_MOVED, handler);
        mainCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);

        this.getChildren().add(mainCanvas);
    }

    /**
     * Render the viewable canvas
     */

    private class AnimTimer extends AnimationTimer {
        @Override
        public void handle(long now) {
            GraphicsContext gc = mainCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            Body_Tool.bodies.forEach(i -> {
                radius = i.radius / (Math.sqrt(mainCanvas.computeAreaInScreen()) / 7);
                //gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                gc.setFill(Color.BLACK);
                gc.fillOval(i.getX(), i.getY(), radius, radius);
            });
            if (mouseHeld) {
                radius = (Duration.between(time, Instant.now()).toMillis() + 100) / (Math.sqrt(mainCanvas.computeAreaInScreen()) / 7);
                Point p = MouseInfo.getPointerInfo().getLocation();
                gc.fillOval(p.x, p.y, radius, radius);
            }
        }
    }

    /**
     * Helps to handle all of the mouse events on the canvas
     */
    private class MouseHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (!pause) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    time = Instant.now();
                    if (tool.mouseClick(event.getX(), event.getY())) {
                        mouseHeld = true;
                        return;
                    }
                }
                if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                    if (tool.mouseRelease(event.getX(), event.getY(), Duration.between(time, Instant.now()).toMillis())) {
                        mouseHeld = false;
                    }
                }
            }
        }
    }

    public void delete() {
    }

    public void setActivePlanet(Plan p) {
        activePlan = p;
    }

    public void escape() {
    }

    public void pause() {
        if (!pause) {
            pause = true;
            logic.stop();
        }
        else {
            pause = false;
            logic.start();
        }
    }
}


