package org.headroyce.kenisi;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class DrawingArea extends StackPane {

    // The main drawing canvas
    private final Canvas mainCanvas;

    // All the selected shapes in the world
    private final DrawingWorkspace mainWorkspace;
    private Instant time;
    private final Body_Tool tool;
    private Double radius;
    private boolean mouseHeld;
    private boolean pause;
    private Logic logic;
    private PlanetIndex plan;
    private final MouseHandler handler;

    public DrawingArea(DrawingWorkspace mw, PlanetIndex p) {
        mouseHeld = false;
        pause = false;
        tool = new Body_Tool();
        mainWorkspace = mw;
        plan = p;
        mainCanvas = new Canvas();
        AnimTimer timer = new AnimTimer();
        logic = new Logic();
        timer.start();
        logic.start();
        radius = 1.0;

        // Force the canvas to resize to the screen's size
        mainCanvas.widthProperty().bind(this.widthProperty());
        mainCanvas.heightProperty().bind(this.heightProperty());

        // Attach mouse handlers to the canvas
        handler = new MouseHandler();
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
                radius = i.radius / (Math.sqrt(mainCanvas.computeAreaInScreen()) / 10);
                gc.setFill(i.getColor());
                gc.fillOval(i.getX() - radius / 2, i.getY() - radius / 2, radius, radius);
            });
            if (mouseHeld) {
                gc.setFill(Color.BLACK);
                radius = (2 * Duration.between(time, Instant.now()).toMillis() + 100) / (Math.sqrt(mainCanvas.computeAreaInScreen()) / 10);
                gc.fillOval(handler.getX() - radius / 2, handler.getY() - radius / 2, radius, radius);
            }
        }
    }


    /**
     * Helps to handle all of the mouse events on the canvas
     */
    private class MouseHandler implements EventHandler<MouseEvent> {
        private double x, y;

        /**
         * Mouse events
         * @param event The mouse event
         * Worst-case time complexity: O(1)
         */
        @Override
        public void handle(MouseEvent event) {
            if (!pause) {
                this.x = event.getSceneX();
                this.y = event.getSceneY();
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    time = Instant.now();
                    tool.mouseClick(event.getX(), event.getY());
                    mouseHeld = true;
                    return;
                }
                if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                    UUID id = tool.mouseRelease(event.getX(), event.getY(), Duration.between(time, Instant.now()).toMillis());
                    //Creates new Plan after creating planet
                    Plan newPlan = new Plan( null, id);
                    plan.addPlan(newPlan);
                    mouseHeld = false;
                }
            }
        }
        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }
    }

    /**
     * Deletes Planets
     * Worst-case time complexity:
     */
    public void delete() {

    }

    /**
     * Sets color of individual planet clicked on to red
     * @param p Plan to set to
     */
    public void setActivePlanet(Plan p) {

    }

    /**
     * Pauses game
     * Worst-case time complexity: O(1)
     */
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


