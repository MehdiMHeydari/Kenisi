package org.headroyce.kenisi;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import java.util.*;
import java.util.ArrayList;
import java.util.Random;


// the whole screen aka game area
    public class Logic extends AnimationTimer {

    private long time;

    LinkedList<Body> planets = new LinkedList<Body>();

    public void Logic() {

    }

    public void gravatationpull(Body A, Body B){

    }

    //gamerunner
    public void handle (long timein) {

        Body primaryplanet;
        Body localplanet;
        for (int i = 0; i < planets.size(); i++){
            primaryplanet = planets.get(i);
            for (int j = i+1; j < planets.size(); j++){
                localplanet = planets.get(j);
                gravatationpull(primaryplanet, localplanet);

            }

        }


    }

}

