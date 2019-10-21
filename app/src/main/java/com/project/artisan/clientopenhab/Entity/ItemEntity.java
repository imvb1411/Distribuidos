package com.project.artisan.clientopenhab.Entity;

import androidx.annotation.NonNull;

public class ItemEntity {
    String state;
    String label;
    String name;
    String type;

    public ItemEntity(String state, String label, String name,String type) {
        this.state = state;
        this.label = label;
        this.name = name;
        this.type=type;
    }

    public ItemEntity() {
        state=label=name="";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
