package org.headroyce.kenisi;

import javafx.scene.layout.BorderPane;

/**
 * Connects DrawingWorkspace and Planet Index
 */
public class MainWorkspace extends BorderPane {
    private final PlanetIndex plansIndex;

    public MainWorkspace(){
        plansIndex = new PlanetIndex();
        plansIndex.prefWidthProperty().bind(this.widthProperty().divide(3));
        DrawingWorkspace dw = new DrawingWorkspace(plansIndex);

        dw.setOnOpenPlanetIndex(actionEvent -> {
            if (plansIndex.getParent() != null) {
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

