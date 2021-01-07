package org.headroyce.kenisi;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;
import java.util.UUID;

/**
 * All information regarding a sketch
 */
public class Plan {
    private StringProperty title;
    private Date timeCreated;
    private Date timeModified;
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
        timeCreated = new Date();
        timeModified = new Date();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getTitle() {
        return title.getValue();
    }

    public boolean setTitle(String title) {
        boolean rtn = false;
        if (title != null && !title.isBlank()) {
            this.title.setValue(title);
            rtn = true;
        }
        return rtn;
    }
}

