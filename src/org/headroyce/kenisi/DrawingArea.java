package org.headroyce.kenisi;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.time.Duration;
import java.time.Instant;

public class DrawingArea extends StackPane {

    // The main drawing canvas
    private Canvas mainCanvas;

    // The plan to draw
    private Plan activePlan;

    // All the selected shapes in the world
    private DrawingWorkspace mainWorkspace;

    private Instant time;

    private Body_Tool tool;

    public DrawingArea(DrawingWorkspace mw){
        tool = new Body_Tool(mainCanvas);

        mainWorkspace = mw;

        mainCanvas = new Canvas();

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
    public void renderWorld(){
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    private class AnimTimer extends AnimationTimer {
        @Override
        public void handle(long now) {
            renderWorld();

        }
    }

    /**
     * Helps to handle all of the mouse events on the canvas
     */
    private class MouseHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                //System.out.println(time + " " + event.getSceneX() + " " + event.getSceneY());
                time = Instant.now();
                if (tool.mouseClick(event.getSceneX(), event.getSceneY())) {
                    return;
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                tool.mouseRelease(event.getSceneX(), event.getSceneY(), Duration.between(time, Instant.now()).toMillis());
            }
            else {

            }
            renderWorld();
        }
    }
    public void delete() {

        renderWorld();
    }

    public void setActivePlanet(Plan p) {
        activePlan = p;

        renderWorld();
    }

    public void escape() {

        renderWorld();
    }
}
