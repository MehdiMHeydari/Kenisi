package org.headroyce.kenisi;

import javafx.scene.paint.Color;

import java.util.UUID;

/**
 * Body object implementation
 * Contains methods to calculate forces for 2 body interactions
 */
public class Body {
    public final double radius; //radius is more useful than area, use radius to draw body
    public final double cordRadius; //"actual" radius used in logic
    public final double mass; //use mass to determine gravitational well
    private double x, y; //the x and y coordinates of the center of the body
    private double velX, velY; //the x and y velocity of the body
    private Color color;
    public final UUID id;

    public Body(UUID id, double cordRadius, double radius, double posX, double posY, double velX, double velY, Color color) { //initialize body object with attributes from UI
        this.id = id;
        this.cordRadius = cordRadius;
        this.radius = radius;
        this.mass = radius * 500; //r = 2000t + 100, density = 500kg/km^3, mass = 500 * r
        this.x = posX;
        this.y = posY;
        this.velX = velX / 17; //velocity is in km/s, logic updates every 17ms so divide by 17
        this.velY = velY / 17;
        this.color = color;
    }

    /**
     * getters and setters for mutable body attributes
     * worst case time complexity O(1)
     */
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getVelX() {
        return this.velX;
    }

    public double getVelY() {
        return this.velY;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor (Color color) {
        this.color = color;
    }

    /**
     * moves body based on x and y velocity
     * worst case time complexity O(1)
     */
    public void move() {
        this.x += this.velX / 17;
        this.y += this.velY / 17;
    }

    /**
     * determine if 2 bodies collided
     * @param localPlanet the other body in the collision
     * @return true if bodies collided, false otherwise
     */
    public boolean collision(Body localPlanet) {
        double primX = this.x;
        double primY = this.y;
        double localX = localPlanet.getX();
        double localY = localPlanet.getY();

        return (distance(primX, primY, localX, localY)) <= (this.cordRadius / 1.5 + localPlanet.cordRadius / 1.5); //the distance between the centers is less than the sum of their scaled radii if they collided
    }

    /**
     * calculates euclidean distance between two bodies based on their centers
     * @param x1 the x coordinate of the first body
     * @param y1 the y coordinate of the first body
     * @param x2 the x coordinate of the second body
     * @param y2 the y coordinate of the second body
     * @return the euclidean distance between the center of the two bodies
     * worst case time complexity O(log n)
     */
    public double distance(double x1, double y1, double x2, double y2) {
        double x = Math.pow((x2 - x1), 2);
        double y = Math.pow((y2 - y1), 2);
        return Math.sqrt(x + y); //math.sqrt is O(log n) assuming java uses smart power algorithm
    }

    /**
     * determines the x and y velocity change of this body due to force of another body
     * @param body, the body pulling this body
     * worst case time complexity O(1)
     */
    public void findForce(Body body) {
        double xDiff = this.x - body.getX(); //scalar on j vector
        double yDiff = this.y - body.getY(); //scalar on i vector
        double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)); //sum of i and j vectors
        double force = (0.667408 * body.mass / this.mass) / (Math.pow(distance, 2) + 1); //multiply this mass by scaled gravitational constant, divide by other mass scale with inverse square law (add 1 to prevent infinite force in edge cases)
        this.velX -= force * xDiff; //scale force by vectors, subtract from velocity to move in correct direction
        this.velY -= force * yDiff;
    }
}
