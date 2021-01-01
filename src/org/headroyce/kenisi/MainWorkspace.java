package org.headroyce.kenisi;

import javafx.scene.layout.BorderPane;

public class MainWorkspace extends BorderPane {
    private final PlanetIndex plansIndex;

    public MainWorkspace(){
        DrawingWorkspace dw = new DrawingWorkspace();
        plansIndex = new PlanetIndex();
        plansIndex.prefWidthProperty().bind(this.widthProperty().divide(3));

        dw.setOnOpenPlanetIndex(actionEvent -> {
            if (plansIndex.getParent() != null) {
                setLeft(null);
            } else {
                setLeft(plansIndex);
            }
        });
        this.setCenter(dw);
    }

}

