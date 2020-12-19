package org.headroyce.kenisi;

import javafx.beans.property.IntegerProperty;
import javafx.scene.canvas.Canvas;

import java.time.Duration;
import java.time.Instant;

public class Body_Tool {

    private Linkedlist<Body> bodies;
    private boolean mouseDown, mouseMove;
    // Selected elements of the line
    private int[] selectedPoint, oldSelectedPoint;
    private IntegerProperty radius;

    // View
    private Canvas view;

    public void mouseClick () { }

    public void mouseRelease (double[] center, long duration) {
        bodies.add(new Body(duration/ 1000.0, center[0], center[1])); //determine radius based on duration
    }
}
