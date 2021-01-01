package org.headroyce.kenisi;


import javafx.animation.AnimationTimer;

import java.util.LinkedList;


// the whole screen aka game area
public class Logic {
    public static final int tick = 17; //updates every 17 milliseconds for 60 fps
    private final gameRun gamerunner;
    private final LinkedList<Body> planets;

    public Logic() {
        this.gamerunner = new gameRun();
        this.planets = Body_Tool.bodies;
    }

    public void start() { gamerunner.start(); }

    public void stop() { gamerunner.stop(); }

    //gamerunner
    private class gameRun extends AnimationTimer {
        private long time;

        public gameRun() {
            time = 0;
        }

        public void handle(long timein) {
            timein = timein / 1000; //convert nanoseconds to milliseconds
            if (timein - time >= tick) {
                Body primaryplanet;
                Body localplanet;
                for (int i = 0; i < planets.size(); i++) {
                    primaryplanet = planets.get(i);

                    double primX = primaryplanet.getX();
                    double primY = primaryplanet.getY();

                    for (int j = 0; j < planets.size(); j++) {
                        if (j != i) {
                            localplanet = planets.get(j);
                            primaryplanet.findForce(localplanet);
                            localplanet.move();

                            //collisions
                            //if (primaryplanet.collision(localplanet)) {
                            //    Body combined = new Body((primaryplanet.radius + localplanet.radius / 2), primX, primY, primaryplanet.getVelX(), primaryplanet.getVelY());
                            //    planets.set(i, combined);
                            //    planets.remove(j);
                            //}
                        }
                        primaryplanet.move();
                    }
                }
                time = timein;
            }
        }
    }
}


