package org.headroyce.kenisi;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

/**
 * All information regarding a sketch
 */
public class Plan {
    private final StringProperty title;
    public final UUID id;

    /**
     * Construct a plan with a starting title
     * @param title title of the plan; Set to "Untitled" if null or blank
     */
    public Plan(String title, UUID id) {
        if (title == null || title.isBlank()) {
            title = "Untitled";
        }
        this.id = id;
        this.title = new SimpleStringProperty();
        setTitle(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getTitle() {
        return title.getValue();
    }

    public void setTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title.setValue(title);
        }
    }
}

