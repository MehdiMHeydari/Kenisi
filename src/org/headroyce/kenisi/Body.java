package org.headroyce.kenisi;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Body {
    public final double radius; //radius is more useful than area, use radius to draw body
    public final double cordradius;
    public final double mass; //use mass to determine gravitational well
    private double x, y; //the x and y coordinates of the center of the body
    private double velX, velY; //the x and y velocity of the body
    public Body (double cordradius, double radius, double posX, double posY, double velX, double velY) { //initialize org.headroyce.kenisi.Body object with attributes from UI
        this.cordradius = cordradius;
        this.radius = radius;
        this.mass = radius * 500; //r = 2000t + 100, density = 500kg/km^3, mass = 500 * r
        this.x = posX;
        this.y = posY;
        this.velX = velX / 17;
        this.velY = velY / 17;
    }

    /**
     * getter for x position of body
     * @return the x position of the body
     * worst case time complexity O(1)
     */
    public double getX () { return this.x; }


    /**
     * getter for y position of body
     * @return the y position of the body
     * worst case time complexity O(1)
     */
    public double getY () { return this.y; }

    public double getVelX () { return this.velX; }

    public double getVelY () { return this.velY; }

    /**
     * moves body based on x and y velocity
     * worst case time complexity O(1)
     */
    public void move () {
        this.x += this.velX / 17;
        this.y += this.velY / 17;
    }

    public boolean collision (Body localplanet) {
        double primX = this.x;
        double primY = this.y;
        double localX = localplanet.getX();
        double localY = localplanet.getY();

       // if (((localX - localplanet.radius <= primX + this.radius) && (localX + localplanet.radius >= primX + this.radius))  || ((localX + localplanet.radius >= primX - this.radius) && (localX - localplanet.radius <= primX - this.radius))){
     //       System.out.println("here");
      //      return ((localY - localplanet.radius <= primY + this.radius) && (localY + localplanet.radius >= primY + this.radius) ) || ((localY + localplanet.radius >= primY - this.radius) && (localY - localplanet.radius <= primY - this.radius));
     //   }
            //System.out.println("d = " + distance(primX,primY, localX, localY));


        //System.out.println("lr= " + localplanet.radius);
        //System.out.println("pr= " + this.radius);

        //i.radius / (Math.sqrt(mainCanvas.computeAreaInScreen()) / 10);

        //double primrad = this.radius * ;



        if ( (distance(primX,primY, localX, localY) - this.cordradius/4) <= (this.cordradius + localplanet.cordradius) ){
            return true;
        }
        return false;
    }

    public double distance (double x1, double y1, double x2, double y2){
        double x = Math.pow((x2-x1), 2);
        double y = Math.pow((y2-y1), 2);
       // System.out.println("distance: " + Math.sqrt(x + y));
        return Math.sqrt(x + y);
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
        double force = (0.667408 * body.mass / this.mass) / (Math.pow(distance, 2) + 1);
        this.velX -= force * xDiff; //scale the j vector by force
        this.velY -= force * yDiff; //scale the i vector by force
    }
}
