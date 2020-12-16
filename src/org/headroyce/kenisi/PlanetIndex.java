package org.headroyce.kenisi;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PlanetIndex extends VBox {
    private VBox plansArea;
    private Button addButton, minusButton, sortingButton;
    private ArrayList<Plan> plans;

    private EventHandler<ActionEvent> selectedPlanetEventHandler;

    public PlanetIndex() {
        Label title = new Label("Planets");
        title.prefWidthProperty().bind(this.widthProperty());

        HBox tools = new HBox();
        this.getStyleClass().add("planetsIndex");
        title.getStyleClass().add("planetsIndexHeader");
        tools.getStyleClass().add("planetsIndexTools");

        addButton = new Button();
        addButton.setTooltip(new Tooltip("Add"));
        Image img = new Image(getClass().getResourceAsStream("/images/plus-square.png"));
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        addButton.setGraphic(imageView);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Plan newPlan = new Plan(null);
                addPlan(newPlan);
            }
        });

        sortingButton = new Button();
        sortingButton.setTooltip(new Tooltip("Sort"));
        img = new Image(getClass().getResourceAsStream("/images/sort.png"));
        imageView = new ImageView(img);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        sortingButton.setGraphic(imageView);
        sortingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        tools.getChildren().addAll(sortingButton, spacer, addButton);

        plansArea = new VBox();
        plansArea.getStyleClass().add("planetIndexList");

        this.getChildren().addAll(title, tools, plansArea);
    }

    public void addPlan(Plan p) {
        if( p == null ) return;

        // Wrap the plan in a view and bind the width to the index's width
        PlansIndexItem guiItem = new PlansIndexItem(p);
        guiItem.prefWidthProperty().bind(this.widthProperty());
        guiItem.setPrefHeight(50);

        // If the item is clicked on then fire the PlanSelection Event
        guiItem.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( selectedPlanetEventHandler != null ) {
                    PlansIndexItem pi = (PlansIndexItem) event.getSource();
                    ActionEvent e = new ActionEvent(pi.plan, Event.NULL_SOURCE_TARGET);
                    selectedPlanetEventHandler.handle(e);
                }
            }
        });

        plansArea.getChildren().add(0, guiItem);

        // fire the selection event to display the new plan
        if( selectedPlanetEventHandler != null ){
            ActionEvent evt = new ActionEvent(p, Event.NULL_SOURCE_TARGET);
            selectedPlanetEventHandler.handle(evt);
        }
    }

    public void removePlan (PlansIndexItem p) {
        for (int i=0; i<this.getChildren().size(); i++) {
            if (this.getChildren().get(i) == p) {
                this.getChildren().remove(i);
                break;
            }
        }
    }

    public void setOnPlanetSelected( EventHandler<ActionEvent> handler ){
        this.selectedPlanetEventHandler = handler;
    }

    private class PlansIndexItem extends HBox {

        private CheckBox selected;
        private TextField title;
        private Button info;

        private Plan plan;

        public PlansIndexItem(Plan plan){
            if( plan == null ) throw new IllegalArgumentException("Plan cannot be null");
            this.plan = plan;

            selected = new CheckBox();
            title = new TextField(this.plan.getTitle());
//            info = new Button();
//            Image img = new Image(getClass().getResourceAsStream("/images/info.png"));
//            ImageView imageView = new ImageView(img);
//            imageView.setFitWidth(30);
//            imageView.setFitHeight(30);
//            info.setGraphic(imageView);

            minusButton = new Button();
            minusButton.setTooltip(new Tooltip("Minus"));
            Image img = new Image(getClass().getResourceAsStream("/images/minus-square.png"));
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            minusButton.setGraphic(imageView);
            minusButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //Remove planet if clicked
                    removePlan(PlansIndexItem.this);
                }
            });

            this.setAlignment(Pos.CENTER);

            title.setMaxWidth(Double.MAX_VALUE);
            HBox.setMargin(title, new Insets(5,5,5,5));
            HBox.setHgrow(title, Priority.ALWAYS);
            title.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                    // The text-field has changed (title)
                    // So remove the element from the BST, change the title, and then add it back
                    PlansIndexItem.this.plan.setTitle(newVal);
                }
            });
            selected.prefHeightProperty().bind(this.heightProperty());
            HBox.setMargin(selected, new Insets(5,5,5,5));

//            info.setMinWidth(45);
//            info.setMaxWidth(Double.MAX_VALUE);
//            info.setMaxHeight(Double.MAX_VALUE);
//            info.prefHeightProperty().bind(this.heightProperty());
//            HBox.setMargin(info, new Insets(5,5,5,5));

            minusButton.setMinWidth(45);
            minusButton.setMaxWidth(Double.MAX_VALUE);
            minusButton.setMaxHeight(Double.MAX_VALUE);
            minusButton.prefHeightProperty().bind(this.heightProperty());
            HBox.setMargin(minusButton, new Insets(5,5,5,5));

            this.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.getChildren().addAll(title, minusButton);

        }
    }
}
