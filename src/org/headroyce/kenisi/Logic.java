package org.headroyce.kenisi;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;

import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Random;


// the whole screen aka game area
    public class Logic {

        public static final int tick = 100;

    private gameRun gamerunner;

    //LinkedList<Body> planets = new LinkedList<Body>();


    LinkedList<Body> planets = Body_Tool.bodies;


    public void Logic() {
        gamerunner = new gameRun();
    }

    public void renderer (Canvas canvas){
        for (Body planet : planets){
            //ASK OTTO HOW TO RENDER
            //planet.render
        }

    }

    public void pause (boolean isPause){
        if (isPause == true){
            gamerunner.stop();
        }else {
            gamerunner.start();
        }
    }

    public void start(){
        gamerunner.start();
    }

    public void stop(){
        gamerunner.stop();
    }


    //gamerunner
    private class gameRun extends AnimationTimer {
        private long time;

        public gameRun() {
            time = 0;
        }

        public void handle(long timein) {

            Body primaryplanet;
            Body localplanet;
            for (int i = 0; i < planets.size(); i++) {
                primaryplanet = planets.get(i);

                double primX = primaryplanet.getX();
                double primY = primaryplanet.getY();


                for (int j = i + 1; j < planets.size(); j++) {
                    localplanet = planets.get(j);
                    primaryplanet.findForce(localplanet);
                    localplanet.move();

                    double localX = localplanet.getX();
                    double localY = localplanet.getY();

                    //collisions
                        if (primaryplanet.collision(localplanet)){
                            Body combined = new Body((primaryplanet.radius + localplanet.radius / 2), primX, primY, primaryplanet.getVelX(), primaryplanet.getVelY());
                            planets.set(i, combined);
                            planets.remove(j);
                        }

                }


                primaryplanet.move();
            }

            time = timein;

        }

    }
}


