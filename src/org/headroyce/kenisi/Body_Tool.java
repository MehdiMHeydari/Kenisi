package org.headroyce.kenisi;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.UUID;

public class Body_Tool {

    public static final LinkedList<Body> bodies = new LinkedList<>();
    private boolean mouseDown;
    private double startX, startY; //the starting point of the mouse

    /**
     * called by UI when mouse is clicked
     *
     * @return boolean: true if mouse is not currently down, false otherwise
     * worst case time complexity O(1)
     */
    public boolean mouseClick(double x, double y) {
        if (!mouseDown) {
            this.startX = x;
            this.startY = y;
            this.mouseDown = true;
            return true;
        }
        return false;
    }

    /**
     * called by UI when mouse is released
     *
     * @param x        the x coordinate of the mouse
     * @param y        the y coordinate of the mouse
     * @param duration how long the mouse was held down in milliseconds
     */
    public UUID mouseRelease(double x, double y, long duration) {
        long radius = (duration * 2 + 100);//duration / 1000 = time in seconds, radius = 2000t + 100 where t is time in seconds
        long cordradius = radius / 100;
        double velY = 1000 * (y - this.startY) / duration; //velocity = l1 norm of space with velX and velY vectors
        double velX = 1000 * (x - this.startX) / duration; //velX = x2 - x1 / time in seconds

        Color color = Color.color(Math.random(), Math.random(), Math.random());
        UUID id = UUID.randomUUID();
        bodies.add(new Body(id, cordradius, radius, x, y, velX, velY, color));
        mouseDown = false;
        return id;
    }
}
