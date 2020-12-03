/**
 *
 */
public class Body {
    public final double radius; //radius is more useful than area, use radius to draw body
    public final double mass; //use mass to determine gravitational well
    private final double[] pos; //array implementation of 2D vector, pos[0] = x, pos[1] = 1
    public Body (double radius, double mass, double posX, double posY) { //initialize Body object with attributes from UI
        this.radius = radius;
        this.mass = mass;
        this.pos = new double[]{posX, posY};
    }

    /**
     * getter for 2D coordinates of body
     * @return pos, the vector containing the x and y coordinates of body
     * worst case time complexity O(1)
     */
    public double[] getPos () { return this.pos; }

    /**
     * getter for x position of body
     * @return pos[0], the x position of the body
     * worst case time complexity O(1)
     */
    public double getX () {
        return this.pos[0];
    }

    /**
     * getter for y position of body
     * @return pos[1], the y position of the body
     * worst case time complexity O(1)
     */
    public double getY () {
        return this.pos[1];
    }

    /**
     * setter for x position of body
     * @param x, the change to the x position
     * worst case time complexity O(1)
     */
    public void moveX (double x) {
        this.pos[0] += x;
    }

    /**
     * setter for y position of body
     * @param y, the change to the x position
     * worst case time complexity O(1)
     */
    public void moveY (double y) { this.pos[1] += y; }
}
