package org.headroyce.kenisi;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.UUID;

public class Body_Tool {

    public static final LinkedList<Body> bodies = new LinkedList<>();
    private double startX, startY; //the starting point of the mouse
    private UUID id;
    private Color color;

    /**
     * called by UI when mouse is clicked
     * worst case time complexity O(1)
     */
    public void mouseClick(double x, double y) {
        this.startX = x;
        this.startY = y;
    }

    /**
     * called by UI when mouse is released
     * @param x the x coordinate of the mouse
     * @param y the y coordinate of the mouse
     * @param duration how long the mouse was held down in milliseconds
     */
    public UUID mouseRelease(double x, double y, long duration) {
        long radius = (duration * 2 + 100);//duration / 1000 = time in seconds, radius = 2000t + 100 where t is time in seconds
        double velY = 1000 * (y - this.startY) / duration; //velocity = l1 norm of space with velX and velY vectors
        double velX = 1000 * (x - this.startX) / duration; //velX = x2 - x1 / time in seconds

        Color color = Color.color(Math.random(), Math.random(), Math.random());
        while (color == Color.RED) {
            color = Color.color(Math.random(), Math.random(), Math.random());
        }
        UUID id = UUID.randomUUID();
        bodies.add(new Body(id, radius / 100.0, radius, x, y, velX, velY, color));
        return id;
    }

    public void removePlanet (UUID id) {
        bodies.removeIf(i -> i.id == id);
        PlanetIndex.BSTFactory().removeById(id);
        DrawingArea.PlanetIndexFactory().render();
    }

    public void addPlanet (double radius, double x, double y, double velX, double velY) {
        Color color = Color.color(Math.random(), Math.random(), Math.random());
        while (color == Color.RED) {
            color = Color.color(Math.random(), Math.random(), Math.random());
        }
        UUID id = UUID.randomUUID();
        bodies.addLast(new Body(id, radius / 100.0, radius, x, y, velX, velY, color));
        Plan newPlan = new Plan(null, id);
        DrawingArea.PlanetIndexFactory().addPlan(newPlan);
    }

    public void setActive(UUID id) {
        for (Body i : bodies) {
            if ( i.id == this.id) {
                i.setColor(this.color);
            }
            else if ( i.id == id) {
                this.id = id;
                this.color = i.getColor();
                i.setColor(Color.RED);
                break;
            }
        }
    }
}
