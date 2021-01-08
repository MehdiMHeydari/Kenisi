package org.headroyce.kenisi;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class DrawingArea extends StackPane {

    // The main drawing canvas
    private final Canvas mainCanvas;

    // All the selected shapes in the world
    private Instant time;
    private final Body_Tool tool;
    private Double radius;
    private boolean mouseHeld;
    private boolean pause;
    private final Logic logic;
    private static PlanetIndex plan;
    private final MouseHandler handler;
    private UUID activePlan;

    public DrawingArea(PlanetIndex p) {
        mouseHeld = false;
        pause = false;
        tool = new Body_Tool();
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
            //Sets radius of planet
            Body_Tool.bodies.forEach(i -> {
                radius = i.radius / 100;
                gc.setFill(i.getColor());
                gc.fillOval(i.getX() - radius / 2, i.getY() - radius / 2, radius, radius);
            });
            if (mouseHeld) {
                //Slowly increases size of planet while mouse is held
                gc.setFill(Color.WHITE);
                radius = (2 * Duration.between(time, Instant.now()).toMillis() + 100) / 100.0;
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
                this.x = event.getX();
                this.y = event.getY();
                //Creates planet on mouse click
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
     * Get the id of the active plan
     * @return the active plan
     * Worst-case time complexity: O(1)
     */
    public UUID getActivePlan() {
        return activePlan;
    }

    /**
     * Deletes all Planets
     * Worst-case time complexity: O(1)
     */
    public void delete() {
        Body_Tool.bodies.clear();
    }

    /**
     * Sets color of individual planet clicked on to red
     * @param p Plan to set to
     */
    public void setActivePlanet(Plan p) {
        activePlan = p.id;
        tool.setActive(activePlan);
    }

    /**
     * Pauses game
     * Worst-case time complexity: O(1)
     */
    public void pauseGame() {
        if (!this.pause) {
            this.pause = true;
            this.logic.stop();
        }
        else {
            this.pause = false;
            this.logic.start();
        }
    }

    /**
     * returns plan
     * @return plan
     * Worst-case time complexity: O(1)
     */
    public static PlanetIndex PlanetIndexFactory () {
        if (plan == null) {
            plan = new PlanetIndex();
        }
        return plan;
    }
}


