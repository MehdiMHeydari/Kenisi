package org.headroyce.kenisi;

/**
 *
 */
public class Body {
    public final double radius; //radius is more useful than area, use radius to draw body
    public final double mass; //use mass to determine gravitational well
    private double x, y; //the x and y coordinates of the center of the body
    private double velX, velY; //the x and y velocity of the body
    public Body (double radius, double posX, double posY, double velX, double velY) { //initialize org.headroyce.kenisi.Body object with attributes from UI
        this.radius = radius;
        this.mass = radius * 500; //r = 2000t + 100, density = 500kg/km^3, mass = 500 * r
        this.x = posX;
        this.y = posY;
        this.velX = velX;
        this.velY = velY;
    }

    /**
     * getter for 2D coordinates of body
     * @return pos, the vector containing the x and y coordinates of body
     * worst case time complexity O(1)
     */
    public double[] getPos () { return new double[]{this.getX(), this.getY()}; }

    /**
     * getter for x position of body
     * @return pos[0], the x position of the body
     * worst case time complexity O(1)
     */
    public double getX () { return this.x; }

    /**
     * getter for y position of body
     * @return pos[1], the y position of the body
     * worst case time complexity O(1)
     */
    public double getY () { return this.y; }

    /**
     * moves body based on x and y velocity
     * worst case time complexity O(1)
     */
    public void move () {
        this.x += this.velX;
        this.y += this.velY;
    }

    /**
     * determines the x and y velocity change of this body by another body
     * @param body, the body pulling this
     * worst case time complexity
     */
    public void findForce (Body body) {
        double xDiff = this.x - body.getX(); //scalar on j vector
        double yDiff = this.y - body.getY(); //scalar on i vector
        double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)); //sum of i and j vectors
        double force = 0.000000000667 * this.mass * body.mass / Math.pow(distance, 2); //multiply mass of both bodies by gravitational constant and divide by distance squared
        this.velX += force * xDiff; //scale the j vector by force
        this.velY += force * yDiff; //scale the i vector by force
    }
}
