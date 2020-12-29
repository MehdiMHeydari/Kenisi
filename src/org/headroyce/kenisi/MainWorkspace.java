package org.headroyce.kenisi;

/**
 * To connect DrawingWorkspace to PlanetIndex (picture changes when button clicked)
 */

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class MainWorkspace extends BorderPane {
    private DrawingWorkspace dw;
    private PlanetIndex plansIndex;

    public MainWorkspace(){
        dw = new DrawingWorkspace();
        plansIndex = new PlanetIndex();
        plansIndex.prefWidthProperty().bind(this.widthProperty().divide(3));

        dw.setOnOpenPlanetIndex(actionEvent -> {
            if( plansIndex.getParent() != null ){
                setLeft(null);
            }
            else {
                setLeft(plansIndex);
            }
        });

        plansIndex.setOnPlanetSelected(event -> dw.setActivePlanet((Plan)event.getSource()));

        this.setCenter(dw);
    }
}

