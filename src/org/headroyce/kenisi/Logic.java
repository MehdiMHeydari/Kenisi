package org.headroyce.kenisi;


import javafx.animation.AnimationTimer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


// the whole screen aka game area
public class Logic {
    public static final int tick = 17; //updates every 17 milliseconds for 60 fps
    private final gameRun gameRunner;
    private final Body_Tool tool;
    private final LinkedList<Body> planets;

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
                                    tool.addPlanet(primaryplanet.id, (primaryplanet.cordRadius + localplanet.cordRadius / 4), (primaryplanet.radius + localplanet.radius / 4), primX, primY, primaryplanet.getVelX(), primaryplanet.getVelY());
                                } else if (localplanet.cordRadius >= primaryplanet.cordRadius * 1.5) {
                                    tool.removePlanet(primaryplanet.id);
                                    tool.removePlanet(localplanet.id);
                                    tool.addPlanet(localplanet.id, (localplanet.cordRadius + primaryplanet.cordRadius / 4), (localplanet.radius + primaryplanet.radius / 4), localX, localY, localplanet.getVelX(), localplanet.getVelY());

                                } else {
                                    if (planets.size() != 0) {
                                        //insert random scatter spawn here
                                        Random rand = new Random();
                                        int numdebris = rand.ints(4, 10).findFirst().getAsInt();
                                        double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                        double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();
                                        double radiusremaning = primaryplanet.cordRadius + localplanet.cordRadius;
                                        double UIradiusremaining = primaryplanet.radius + localplanet.radius;

                                        double midpointX = (primX + localX)/2;
                                        double midpointY = (primY + localY)/2;
                                        Integer[] Xgenarray = new Integer[((int) (primaryplanet.cordRadius + localplanet.cordRadius))];
                                        for (int x = 0; x < Xgenarray.length; x++) {
                                            Xgenarray[x] = (int) midpointX + (((int) ((primaryplanet.cordRadius + localplanet.cordRadius)/2))) + x;
                                        }

                                        Integer[] Ygenarray = new Integer[((int) (primaryplanet.cordRadius + localplanet.cordRadius))];
                                        for (int y = 0; y < Ygenarray.length; y++) {
                                            Ygenarray[y] = (int) midpointY - (((int) ((primaryplanet.cordRadius + localplanet.cordRadius)/2))) + y;
                                        }

                                        Collections.shuffle(Arrays.asList(Xgenarray));
                                        Collections.shuffle(Arrays.asList(Ygenarray));

                                        //double Vel = Math.sqrt(Math.pow(primaryplanet.getVelX(),2) + Math.pow(primaryplanet.getVelY(),2)) + Math.sqrt(Math.pow(localplanet.getVelX(),2) + Math.pow(localplanet.getVelY(),2));
                                        //double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                        //double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();

                                        if ((planets.contains(primaryplanet) && planets.contains(localplanet)) &&
                                                (planets.get(planets.indexOf(primaryplanet)) != null &&
                                                        planets.get(planets.indexOf(localplanet)) != null)) {
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
                                            double divide = ThreadLocalRandom.current().nextInt(5, 10);
                                            double radgen = radiusremaning / divide;
                                            double UIradgen = UIradiusremaining / divide;

                                            if (Xgen < primX) {
                                                //set Vel west
                                                velXgen = -50 * totalvelX;
                                            } else {
                                                //set Vel east
                                                velXgen =  50 * totalvelX;
                                            }
                                            if (Ygen < midpointY) {
                                                //set Vel north
                                                velYgen = 4 * Math.abs(totalvelY / numdebris);
                                            } else {
                                                //set Vel south
                                                velYgen = 50 * totalvelY;
                                            }
                                            if (radgen > 7) {
                                                tool.addPlanet(UUID.randomUUID(), radgen, UIradgen, Xgen, Ygen, velXgen, velYgen);
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


