package org.headroyce.kenisi;


import javafx.animation.AnimationTimer;

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

                                if (primaryplanet.cordradius >= localplanet.cordradius * 2) {
                                    Body combined = new Body((primaryplanet.cordradius + localplanet.cordradius / 4), (primaryplanet.radius + localplanet.radius / 4), primX, primY, primaryplanet.getVelX(), primaryplanet.getVelY());

                                    /*
                                    System.out.println("Local planet rad:" + localplanet.cordradius);
                                    System.out.println("prim planet shownrad:" + primaryplanet.radius);
                                    System.out.println("Prim planet rad:" + primaryplanet.cordradius);
                                    System.out.println("Combined planet rad:" + combined.cordradius);
                                    System.out.println("Combined planet shownrad:" + combined.radius);

                                     */
                                    planets.set(i, combined);
                                    planets.remove(j);
                                } else if (localplanet.cordradius >= primaryplanet.cordradius * 2) {

                                    Body combined = new Body((localplanet.cordradius + primaryplanet.cordradius / 4), (localplanet.radius + primaryplanet.radius / 4),  localX, localY, localplanet.getVelX(), localplanet.getVelY());

                                    /*
                                    System.out.println("Local planet rad:" + localplanet.cordradius);
                                    System.out.println("Local planet shownrad:" + localplanet.radius);
                                    System.out.println("Prim planet rad:" + primaryplanet.cordradius);
                                    System.out.println("Combined planet rad:" + combined.cordradius);
                                    System.out.println("Combined planet shownrad:" + combined.radius);
                                    */

                                    planets.set(j, combined);
                                    planets.remove(i);


                                } else {


                                    //insert random scatter spawn here
                                    int numdebris = ThreadLocalRandom.current().nextInt(2, 4);
                                    System.out.println(numdebris);
                                    double radgen = primaryplanet.cordradius / numdebris;
                                    double UIradgen = primaryplanet.radius /numdebris;

                                   Integer[] Xgenarray = new Integer[(int) primaryplanet.cordradius * 2 ];
                                    for( int x = 0; x < Xgenarray.length; x++){
                                        Xgenarray[x] = (int) primX - (int) primaryplanet.cordradius + x;
                                    }

                                    Integer[] Ygenarray = new Integer[(int) primaryplanet.cordradius * 2];
                                    for( int x = 0; x < Xgenarray.length; x++){
                                        Ygenarray[x] = (int) primY - (int) primaryplanet.cordradius + x;
                                    }

                                    Collections.shuffle(Arrays.asList(Xgenarray));

                                    Collections.shuffle(Arrays.asList(Ygenarray));


                                    //double Vel = Math.sqrt(Math.pow(primaryplanet.getVelX(),2) + Math.pow(primaryplanet.getVelY(),2)) + Math.sqrt(Math.pow(localplanet.getVelX(),2) + Math.pow(localplanet.getVelY(),2));
                                    //double totalvelX = primaryplanet.getVelX() + localplanet.getVelX();
                                    //double totalvelY = primaryplanet.getVelY() + localplanet.getVelY();

                                    if (planets.get(planets.indexOf(primaryplanet)) != null
                                            && planets.get(planets.indexOf(localplanet)) != null ){
                                        planets.remove(planets.indexOf(localplanet));
                                        planets.remove(planets.indexOf(primaryplanet));
                                    }

                                    for (int p = 0; p < numdebris; p++) {
                                        System.out.println("hi");

                                       // double Xgen = ThreadLocalRandom.current().nextInt((int) (primX - primaryplanet.cordradius), (int) (primX + primaryplanet.cordradius));
                                        //double Ygen = ThreadLocalRandom.current().nextInt((int) (primY - primaryplanet.cordradius), (int) (primY + primaryplanet.cordradius));

                                        double Xgen = Xgenarray[p];
                                        double Ygen = Ygenarray[p];

                                        double velXgen;
                                        double velYgen;

                                          if ( Xgen < primX ){
                                        //set Vel west
                                              velXgen = -10;
                                        } else {
                                        //set Vel east
                                              velXgen = 10;
                                         }

                                      if ( Ygen < primY ){
                                        //set Vel south
                                          velYgen = -10;

                                        } else {
                                        //set Vel north
                                            velYgen = 10;
                                        }

                                        //if(planets.indexOf(primaryplanet) == i && planets.indexOf(localplanet) == j) {
                                        //    planets.remove(i);

                                       // }

                                        //System.out.println(planets.size());
                                        //System.out.println(j);


                                        Body debris = new Body(radgen, UIradgen, Xgen, Ygen, velXgen, velYgen);
                                        planets.addLast(debris);

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


