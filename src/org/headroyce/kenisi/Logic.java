package org.headroyce.kenisi;


import javafx.animation.AnimationTimer;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;


// the whole screen aka game area
public class Logic {
    public static final int tick = 17; //updates every 17 milliseconds for 60 fps
    private final gameRun gameRunner;
    private final LinkedList<Body> planets;
    private final Body_Tool tool;

    public Logic() {
        this.gameRunner = new gameRun();
        this.tool = new Body_Tool();
        this.planets = Body_Tool.bodies;
    }

    public void start() {
        gameRunner.start();
    }

    public void stop() {
        gameRunner.stop();
    }

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

                            // if ((primX - primaryplanet.radius <=0)
                            localplanet = planets.get(j);
                            primaryplanet.findForce(localplanet);
                            localplanet.move();
                            double localX = localplanet.getX();
                            double localY = localplanet.getY();

                            //collisions

                            if (primaryplanet.collision(localplanet)) {
                                if (primaryplanet.cordRadius >= localplanet.cordRadius * 1.5) {
                                    tool.removePlanet(primaryplanet.id);
                                    tool.removePlanet(localplanet.id);
                                    tool.addPlanet((primaryplanet.radius + localplanet.radius / 4), primX, primY, primaryplanet.getVelX(), primaryplanet.getVelY());
                                } else if (localplanet.cordRadius >= primaryplanet.cordRadius * 1.5) {
                                    tool.removePlanet(primaryplanet.id);
                                    tool.removePlanet(localplanet.id);
                                    tool.addPlanet((localplanet.radius + primaryplanet.radius / 4), localX, localY, localplanet.getVelX(), localplanet.getVelY());

                                } else {
                                    if (planets.size() != 0) {
                                        //insert random scatter spawn here
                                        int numdebris = ThreadLocalRandom.current().nextInt(2, 4);
                                        double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                        double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();
                                        double radiusremaning = primaryplanet.cordRadius + localplanet.cordRadius;
                                        double UIradiusremaining = primaryplanet.radius + localplanet.radius;

                                        Integer[] Xgenarray = new Integer[(int) primaryplanet.cordRadius * 2];
                                        for (int x = 0; x < Xgenarray.length; x++) {
                                            Xgenarray[x] = (int) primX - (int) primaryplanet.cordRadius + x;
                                        }

                                        Integer[] Ygenarray = new Integer[(int) primaryplanet.cordRadius * 2];
                                        for (int y = 0; y < Ygenarray.length; y++) {
                                            Ygenarray[y] = (int) primY - (int) primaryplanet.cordRadius + y;
                                        }

                                        Collections.shuffle(Arrays.asList(Xgenarray));
                                        Collections.shuffle(Arrays.asList(Ygenarray));

                                        //double Vel = Math.sqrt(Math.pow(primaryplanet.getVelX(),2) + Math.pow(primaryplanet.getVelY(),2)) + Math.sqrt(Math.pow(localplanet.getVelX(),2) + Math.pow(localplanet.getVelY(),2));
                                        //double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                        //double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();

                                        if (planets.contains(primaryplanet) && planets.contains(localplanet) &&
                                                planets.get(planets.indexOf(primaryplanet)) != null
                                                && planets.get(planets.indexOf(localplanet)) != null) {
                                            tool.removePlanet(localplanet.id);
                                            tool.removePlanet(primaryplanet.id);
                                        }

                                        for (int p = 0; p < numdebris; p++) {
                                            // double Xgen = ThreadLocalRandom.current().nextInt((int) (primX - primaryplanet.cordRadius), (int) (primX + primaryplanet.cordRadius));
                                            //double Ygen = ThreadLocalRandom.current().nextInt((int) (primY - primaryplanet.cordRadius), (int) (primY + primaryplanet.cordRadius));
                                            double Xgen = Xgenarray[p];
                                            double Ygen = Ygenarray[p];
                                            double velXgen;
                                            double velYgen;
                                            double divide = ThreadLocalRandom.current().nextInt(2, 4);
                                            double radgen = radiusremaning / divide;
                                            double UIradgen = UIradiusremaining / divide;

                                            if (Xgen < primX) {
                                                //set Vel west
                                                velXgen = -4 * Math.abs(totalvelX / numdebris);
                                            } else {
                                                //set Vel east
                                                velXgen = 4 * Math.abs(totalvelX / numdebris);
                                            }

                                            if (Ygen < primY) {
                                                //set Vel south
                                                velYgen = -4 * Math.abs(totalvelY / numdebris);
                                            } else {
                                                //set Vel north
                                                velYgen = 4 * Math.abs(totalvelY / numdebris);
                                            }
                                            if (radgen > 25) {
                                                tool.addPlanet(UIradgen, Xgen, Ygen, velXgen, velYgen);
                                            }
                                        }
                                    }
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


