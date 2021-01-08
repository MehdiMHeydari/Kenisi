package org.headroyce.kenisi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class DrawingWorkspace extends Pane {

    private final Button openPlanet;
    private EventHandler<ActionEvent> openPlanetHandler;
    private final DrawingArea drawingArea;

    public DrawingWorkspace(DrawingArea da) {
        drawingArea = da;

        Button pause = new Button();
        pause.setTooltip(new Tooltip("Pause"));
        Image img = new Image(getClass().getResourceAsStream("/images/pause.png"));
        ImageView imageView = new javafx.scene.image.ImageView(img);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        pause.setGraphic(imageView);
        pause.setOnAction(actionEvent -> {
            //Changes color of button on click
            if (pause.getStyleClass().contains("active")) {
                pause.getStyleClass().remove("active");
                pause.setEffect(null);
            } else {
                pause.getStyleClass().add("active");
                ColorAdjust ca = new ColorAdjust();
                ca.setBrightness(-0.5);
                pause.setEffect(ca);
            }
            //Pauses game
            drawingArea.pauseGame();
        });
        pause.layoutXProperty().bind(this.widthProperty().subtract(pause.widthProperty()));

        openPlanet = new Button();
        openPlanet.setTooltip(new Tooltip("Planet"));
        img = new Image(getClass().getResourceAsStream("/images/plans.png"));
        imageView = new ImageView(img);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        openPlanet.setGraphic(imageView);
        openPlanet.setLayoutY(0);
        openPlanet.setOnAction(actionEvent -> {
            if (openPlanet.getStyleClass().contains("active")) {
                openPlanet.getStyleClass().remove("active");
                openPlanet.setEffect(null);
            } else {
                openPlanet.getStyleClass().add("active");
                ColorAdjust ca = new ColorAdjust();
                ca.setBrightness(-0.5);
                openPlanet.setEffect(ca);
            }
            //Opens planet handler
            if (openPlanetHandler != null) {
                openPlanetHandler.handle(actionEvent);
            }
        });

        drawingArea.prefHeightProperty().bind(this.heightProperty());
        drawingArea.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().add(drawingArea);
        this.getChildren().add(pause);
        this.getChildren().add(openPlanet);
    }

    /**
     * Opens planet index
     *
     * @param handler Worst-case time complexity: O(1)
     */
    public void setOnOpenPlanetIndex(EventHandler<ActionEvent> handler) {
        openPlanetHandler = handler;
    }

    /**
     * Sets active planet
     *
     * @param p Active plan to set to
     *          Worst-case time complexity: O(1)
     */
    public void setActivePlanet(Plan p) {
        drawingArea.setActivePlanet(p);
    }
}
