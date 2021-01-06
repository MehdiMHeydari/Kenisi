package org.headroyce.kenisi;


import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

    public void start() {
        gamerunner.start();
    }

    public void stop() {
        gamerunner.stop();
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

                                //System.out.println(localplanet.getX());
                                //System.out.println(primaryplanet.getX());

                                // System.out.println("radi= " + (primaryplanet.radius + localplanet.radius));

                                if (primaryplanet.cordradius >= localplanet.cordradius * 1.5) {
                                    Body combined = new Body((primaryplanet.cordradius + localplanet.cordradius / 4), (primaryplanet.radius + localplanet.radius / 4), primX, primY, primaryplanet.getVelX(), primaryplanet.getVelY(), primaryplanet.getColor());
                                    planets.set(i, combined);
                                    planets.remove(j);
                                } else if (localplanet.cordradius >= primaryplanet.cordradius * 1.5) {

                                    Body combined = new Body((localplanet.cordradius + primaryplanet.cordradius / 4), (localplanet.radius + primaryplanet.radius / 4), localX, localY, localplanet.getVelX(), localplanet.getVelY(), localplanet.getColor());
                                    planets.set(j, combined);
                                    planets.remove(i);

                                } else {

                                    if (planets.size() != 0) {
                                        //insert random scatter spawn here
                                        int numdebris = ThreadLocalRandom.current().nextInt(2, 4);
                                        double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                        double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();
                                        double radiusremaning = primaryplanet.cordradius + localplanet.cordradius;
                                        double UIradiusremaining = primaryplanet.radius + localplanet.radius;

                                        Integer[] Xgenarray = new Integer[(int) primaryplanet.cordradius * 2];
                                        for (int x = 0; x < Xgenarray.length; x++) {
                                            Xgenarray[x] = (int) primX - (int) primaryplanet.cordradius + x;
                                        }

                                        Integer[] Ygenarray = new Integer[(int) primaryplanet.cordradius * 2];
                                        for (int y = 0; y < Ygenarray.length; y++) {
                                            Ygenarray[y] = (int) primY - (int) primaryplanet.cordradius + y;
                                        }

                                        Collections.shuffle(Arrays.asList(Xgenarray));
                                        Collections.shuffle(Arrays.asList(Ygenarray));

                                        //double Vel = Math.sqrt(Math.pow(primaryplanet.getVelX(),2) + Math.pow(primaryplanet.getVelY(),2)) + Math.sqrt(Math.pow(localplanet.getVelX(),2) + Math.pow(localplanet.getVelY(),2));
                                        //double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                        //double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();

                                        if ((planets.indexOf(primaryplanet) != -1 && planets.indexOf(localplanet) != -1) &&
                                                (planets.get(planets.indexOf(primaryplanet)) != null
                                                        && planets.get(planets.indexOf(localplanet)) != null)) {
                                            planets.remove(planets.indexOf(localplanet));
                                            planets.remove(planets.indexOf(primaryplanet));
                                        }

                                        for (int p = 0; p < numdebris; p++) {
                                            // double Xgen = ThreadLocalRandom.current().nextInt((int) (primX - primaryplanet.cordradius), (int) (primX + primaryplanet.cordradius));
                                            //double Ygen = ThreadLocalRandom.current().nextInt((int) (primY - primaryplanet.cordradius), (int) (primY + primaryplanet.cordradius));
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

                                            Color finalMarioCart64 = Color.color(Math.random(), Math.random(), Math.random());

                                            if (radgen > 25) {
                                                Body debris = new Body(radgen, UIradgen, Xgen, Ygen, velXgen, velYgen, finalMarioCart64);
                                                planets.addLast(debris);
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


