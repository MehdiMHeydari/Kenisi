package org.headroyce.kenisi;


import javafx.animation.AnimationTimer;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;



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
                            double localX = localplanet.getX();
                            double localY = localplanet.getY();

                            //collisions
                            if (primaryplanet.collision(localplanet)) {
                            /*
                                System.out.println(localplanet.getX());
                                System.out.println(primaryplanet.getX());

                                System.out.println("radi= " + (primaryplanet.radius + localplanet.radius));

                                if ( primaryplanet.radius >= localplanet.radius * 2){
                                    Body combined = new Body((primaryplanet.radius + localplanet.radius / 2), primX, primY, primaryplanet.getVelX(), primaryplanet.getVelY());
                                    planets.set(i, combined);
                                    planets.remove(j);
                                } else if ( localplanet.radius >= primaryplanet.radius * 2){
                                    Body combined = new Body((localplanet.radius + primaryplanet.radius / 2), localX , localY, localplanet.getVelX(), localplanet.getVelY());
                                    planets.set(j, combined);
                                    planets.remove(i);
                                } else {

                             */
                                    //insert random scatter spawn here
                                    int numdebris = ThreadLocalRandom.current().nextInt(2, 4);
                                    double radgen = primaryplanet.radius / numdebris;
                                    //double Vel = Math.sqrt(Math.pow(primaryplanet.getVelX(),2) + Math.pow(primaryplanet.getVelY(),2)) + Math.sqrt(Math.pow(localplanet.getVelX(),2) + Math.pow(localplanet.getVelY(),2));
                                    //double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                    //double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();


                                    for( int p = 0; p < numdebris; p++){
                                        double Xgen = ThreadLocalRandom.current().nextInt((int) (primX - primaryplanet.radius), (int) (primX+ primaryplanet.radius));
                                        double Ygen = ThreadLocalRandom.current().nextInt((int) (primY - primaryplanet.radius), (int) (primY+ primaryplanet.radius));
                                        double velXgen;
                                        double velYgen;

                                      //  if ( Xgen < primX ){
                                            //set Vel west
                                          //  velXgen = -1;
                                        //} else {
                                            //set Vel east
                                        //    velYgen = 1;
                                      //  }

                                      //  if ( Ygen < primY ){
                                            //set Vel south

                                       // } else {
                                            //set Vel north
                                        //}

                                        Body debris = new Body(radgen, Xgen , Ygen, 0, 0);
                                        planets.addLast(debris);


                                    planets.remove(i);
                                   // planets.remove(j-1);
                                }

                            }
                        }

                        primaryplanet.move();
                    }
                }
                time = timein;
            }
        }
    }
}


