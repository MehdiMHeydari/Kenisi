package org.headroyce.kenisi;

import java.util.LinkedList;

public class Body_Tool {

    public static final LinkedList<Body> bodies = new LinkedList<>();
    private boolean mouseDown;
    private double startX, startY; //the starting point of the mouse

    /**
     * called by UI when mouse is clicked
     * @return boolean: true if mouse is not currently down, false otherwise
     * worst case time complexity O(1)
     */
    public boolean mouseClick (double x, double y) {
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
     * @param x the x coordinate of the mouse
     * @param y the y coordinate of the mouse
     * @param duration how long the mouse was held down in milliseconds
     */
    public Boolean mouseRelease (double x, double y, long duration) {
        if (mouseDown = true) {
            long radius = (duration * 2 + 100);//duration / 1000 = time in seconds, radius = 2000t + 100 where t is time in seconds
            long cordradius = radius/100;
            /*
            System.out.println("real radius:" + radius);
            System.out.println("real X:" + x);
            System.out.println("real Y:" + y);
*/
            double velY = 1000 * (y - this.startY) / duration; //velocity = l1 norm of space with velX and velY vectors
            double velX = 1000 * (x - this.startX) / duration; //velX = x2 - x1 / time in seconds
            bodies.add(new Body(cordradius, radius, x, y, velX, velY));
            mouseDown = false;
            return true;
        }
        return false;
    }
}
