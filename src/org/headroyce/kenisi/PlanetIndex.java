package org.headroyce.kenisi;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlanetIndex extends VBox {
    private int sortMode;
    private final String[] sortImages = {"sort.png", "sortdown.png", "sortup.png"};
    private String name;
    private final DrawingArea da;
    private final Stage primaryStage;
    private final VBox plansArea;
    private final DrawingWorkspace dw;
    private static BST<PlansIndexItem> sortByTitle;
    private EventHandler<ActionEvent> selectedPlanetEventHandler;

    public PlanetIndex() {
        sortByTitle = new BST<>();
        da = new DrawingArea(this);
        dw = new DrawingWorkspace(da);
        name = "Untitled";
        primaryStage = new Stage();
        plansArea = new VBox();
        plansArea.getStyleClass().add("planetIndexList");
        Label title = new Label("Planets");
        title.prefWidthProperty().bind(this.widthProperty());
        HBox tools = new HBox();
        this.getStyleClass().add("planetsIndex");
        title.getStyleClass().add("planetsIndexHeader");
        tools.getStyleClass().add("planetsIndexTools");

        Button sortingButton = new Button();
        sortingButton.setTooltip(new Tooltip("Sort"));
        Image img = new Image(getClass().getResourceAsStream("/images/"+sortImages[sortMode]));
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        sortingButton.setGraphic(imageView);
        sortingButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // When the button is selected then we change the icon
                // and reorder the plans based on the new ordering
                sortMode = (sortMode + 1) % 3;

                Image img = new Image(getClass().getResourceAsStream("/images/" + sortImages[sortMode]));
                ((ImageView) sortingButton.getGraphic()).setImage(img);
                if (sortMode != 0) {
                    List<PlansIndexItem> order = sortByTitle.inOrder();

                    // Suggestions by Otto Reed
                    // Descending Order
                    if (sortMode == 2) {
                        Collections.reverse(order);
                    }
                    plansArea.getChildren().clear();
                    plansArea.getChildren().addAll(order);
                }
            }
        });

        Button delete = new Button();
        delete.setTooltip(new Tooltip("Delete"));
        img = new Image(getClass().getResourceAsStream("/images/trash.png"));
        imageView = new ImageView(img);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        delete.setGraphic(imageView);
        delete.setOnAction(actionEvent -> {
            plansArea.getChildren().clear();
            da.delete();
        });

        this.getChildren().addAll(title, tools, plansArea);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        tools.getChildren().addAll(sortingButton, spacer, delete);
    }

    /**
     * Adds new plan to planet index
     * @param p Plan needed to be added
     * Worst-case time complexity: O(1)
     */
    public void addPlan(Plan p) {
        if( p == null ) return;

        // Wrap the plan in a view and bind the width to the index's width
        PlansIndexItem guiItem = new PlansIndexItem(p);
        guiItem.prefWidthProperty().bind(this.widthProperty());
        guiItem.setPrefHeight(50);

        // If the item is clicked on then fire the PlanSelection Event
        guiItem.setOnMouseClicked(event -> {
            if( selectedPlanetEventHandler != null ) {
                PlansIndexItem pi = (PlansIndexItem) event.getSource();
                ActionEvent e = new ActionEvent(pi.plan, Event.NULL_SOURCE_TARGET);
                selectedPlanetEventHandler.handle(e);
            }
        });

        plansArea.getChildren().add(0, guiItem);

        // Add the new plan to our BST
        sortByTitle.add(guiItem, p.id);

        // fire the selection event to display the new plan
        if( selectedPlanetEventHandler != null ){
            ActionEvent evt = new ActionEvent(p, Event.NULL_SOURCE_TARGET);
            selectedPlanetEventHandler.handle(evt);
        }
    }

    public void render () {
        List<PlansIndexItem> order = sortByTitle.inOrder();
        if (sortMode == 2) {
            Collections.reverse(order);
        }
        plansArea.getChildren().clear();
        plansArea.getChildren().addAll(order);
    }

    /**
     * Sets on specific planet selected
     * @param handler
     * Worst-case time complexity: O(1)
     */
    public void setOnPlanetSelected( EventHandler<ActionEvent> handler ){
        this.selectedPlanetEventHandler = handler;
    }

    private class PlansIndexItem extends HBox implements Comparable<PlansIndexItem> {

        private final Plan plan;

        @Override
        //Compare list items to each other
        public int compareTo(PlansIndexItem other) {

            // If the string version are the same, then we consider the plan
            // equal to each other (this allows two plans of the same title)
            int hashCompare = this.toString().compareTo(other.toString());
            if( hashCompare == 0 ){
                return 0;
            }

            // The String versions are the same, so compare titles
            return this.plan.getTitle().compareTo(other.plan.getTitle());
        }

        public PlansIndexItem(Plan plan){
            if( plan == null ) throw new IllegalArgumentException("Plan cannot be null");
            this.plan = plan;
            CheckBox selected = new CheckBox();
            TextField title = new TextField(this.plan.getTitle());
            Button info = new Button();
            Image img = new Image(getClass().getResourceAsStream("/images/info.png"));
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            info.setGraphic(imageView);
            info.setOnAction(actionEvent -> {
                da.setActivePlanet(plan);
                info(primaryStage, name);
            });

            this.setAlignment(Pos.CENTER);

            title.setMaxWidth(Double.MAX_VALUE);
            HBox.setMargin(title, new Insets(5,5,5,5));
            HBox.setHgrow(title, Priority.ALWAYS);
            title.textProperty().addListener((observableValue, oldVal, newVal) -> {
                // The text-field has changed (title)
                // So remove the element from the BST, change the title, and then add it back
                PlansIndexItem.this.plan.setTitle(newVal);
                name = newVal;

            });
            selected.prefHeightProperty().bind(this.heightProperty());
            HBox.setMargin(selected, new Insets(5,5,5,5));

            info.setMinWidth(45);
            info.setMaxWidth(Double.MAX_VALUE);
            info.setMaxHeight(Double.MAX_VALUE);
            info.prefHeightProperty().bind(this.heightProperty());
            HBox.setMargin(info, new Insets(5,5,5,5));

            this.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.getChildren().addAll(title, info);
        }
    }

    public static BST<?> BSTFactory () {
        if (sortByTitle == null) {
            sortByTitle = new BST<>();
        }
        return sortByTitle;
    }

    /**
     * Displays pop up with planet information
     * @param primaryStage The stage to set
     * @param name Planet name
     * Worst-case time complexity: O(n)
     */
    public void info (final Stage primaryStage, String name) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        UUID id = da.getActivePlan();
        Body_Tool.bodies.stream().filter(i -> i.id == id).findFirst().ifPresent(active -> dialogVbox.getChildren().add(new Text(
                "Name: " + name + "\n"
                        + "Radius: " + active.radius + "\n"
                        + "Velocity: " + (int)Math.sqrt((active.getVelX()*active.getVelX()) + (active.getVelY()*active.getVelX()))
        )));

        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public DrawingWorkspace getDrawingWorkspace () {
        return this.dw;
    }
}
