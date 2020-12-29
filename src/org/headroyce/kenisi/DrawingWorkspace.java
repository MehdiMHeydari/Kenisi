package org.headroyce.kenisi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class DrawingWorkspace extends Pane {

    private Button openPlanet;
    private EventHandler<ActionEvent> openPlanetHandler;
    private DrawingArea drawingArea;

    public DrawingWorkspace() {
        drawingArea = new DrawingArea(this);

        Button delete = new Button();
        delete.setTooltip(new Tooltip("Delete"));
        Image img = new Image(getClass().getResourceAsStream("/images/trash.png"));
        ImageView imageView = new javafx.scene.image.ImageView(img);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        delete.setGraphic(imageView);
        delete.setAlignment(Pos.BOTTOM_LEFT);
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        delete.layoutYProperty().bind(this.heightProperty().subtract(delete.heightProperty()));

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
            }
            else {
                openPlanet.getStyleClass().add("active");
                ColorAdjust ca = new ColorAdjust();
                ca.setBrightness(-0.5);
                openPlanet.setEffect(ca);
            }
            if (openPlanetHandler != null) {
                openPlanetHandler.handle(actionEvent);
            }
        });

        drawingArea.prefHeightProperty().bind(this.heightProperty());
        drawingArea.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().add(drawingArea);

        this.getChildren().add(delete);

        this.getChildren().add(openPlanet);
    }
    public void setOnOpenPlanetIndex(EventHandler<ActionEvent> handler){
        openPlanetHandler = handler;
    }

    public void setActivePlanet( Plan p ){
        drawingArea.setActivePlanet(p);
    }
}
