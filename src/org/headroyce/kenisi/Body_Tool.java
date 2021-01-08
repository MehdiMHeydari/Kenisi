package org.headroyce.kenisi;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Controller implementation
 * Adds and removes bodies from UI and logic
 */
public class Body_Tool {

    public static final LinkedList<Body> bodies = new LinkedList<>(); //the linkedlist used in game and animation loops
    private double startX, startY; //the starting point of the mouse, used to calculate velocity for new body object
    private UUID id; //the UUID of the active object
    private Color color; //the color of the active object before it became active

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
     * @return id the UUID of the created body object
     * Possible (but astronomically unlikely) that while loop runs indefinitely; discounting this scenario, worst case time complexity O(1)
     */
    public UUID mouseRelease(double x, double y, long duration) {
        long radius = (duration * 2 + 100);//duration / 1000 = time in seconds, radius = 2000t + 100 where t is time in seconds
        double velY = 1000 * (y - this.startY) / duration; //velocity = l1 norm of space with velX and velY vectors
        double velX = 1000 * (x - this.startX) / duration; //velX = x2 - x1 / time in seconds

        Color color = Color.color(0, Math.random(), Math.random()); //set random color, R value is 0 to make sure it's not red
        UUID id = UUID.randomUUID(); //set random UUID for new body object, this will be returned to UI
        bodies.add(new Body(id, radius / 100.0, radius, x, y, velX, velY, color)); //add object to linkedlist, radius/100 = cordRadius for logic
        return id;
    }

    /**
     * removes body from UI and logic
     * @param id the UUID of the body to remove
     * worst case time complexity O(n)
     */
    public void removePlanet (UUID id) {
        bodies.removeIf(i -> i.id == id); //first remove body from linkedlist
        PlanetIndex.BSTFactory().removeById(id); //now remove body from BST of plans
        DrawingArea.PlanetIndexFactory().render(); //update the PlanetIndex
    }

    /**
     * add body object to UI and logic, but only called by logic when collisions change the bodies in the system
     * @param id the UUID of the body to add
     * @param radius the radius of the body object
     * @param x the x coordinate of the center of the body
     * @param y the y coordinate of the center of the body
     * @param velX the scalar on the x velocity vector (j) of the body
     * @param velY the scalar on the y velocity vector (i) of the body
     * worst case time complexity O(n)
     */
    public void addPlanet (UUID id, double cordRadius, double radius, double x, double y, double velX, double velY) { //void because it's not called by UI, method parameters are calculated in logic
        Color color = Color.color(0, Math.random(), Math.random()); //same color generation as above
        bodies.addLast(new Body(id, cordRadius, radius, x, y, velX, velY, color)); //add body to the end of the linkedlist
        Plan newPlan = new Plan(null, id); //create new plan with matching UUID
        DrawingArea.PlanetIndexFactory().addPlan(newPlan); //add plan to the PlanetIndex
        DrawingArea.PlanetIndexFactory().render(); //update the PlanetIndex
    }

    /**
     * changes the color of the active body to red, stores color so that it can be restored when new body becomes active
      * @param id the id of the active body
     * worst case time complexity O(n)
     */
    public void setActive(UUID id) {
        for (Body i : bodies) {
            if (this.id != null) {
                if (i.id.compareTo(this.id) == 0) {
                    i.setColor(this.color);
                }
            }
            else if ( i.id.compareTo(id) == 0) {
                this.id = id;
                this.color = i.getColor();
                i.setColor(Color.RED);
                break;
            }
        }
    }
}
