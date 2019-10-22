package com.project.artisan.clientopenhab.Entity;

public class ControlItemsEntity {
    private int background;
    private String value;

    public ControlItemsEntity(int background, String value) {
        this.background = background;
        this.value = value;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
