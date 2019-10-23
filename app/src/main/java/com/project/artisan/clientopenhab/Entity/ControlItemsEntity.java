package com.project.artisan.clientopenhab.Entity;

public class ControlItemsEntity {
    private String name;
    private int background;
    private String value;

    public ControlItemsEntity(int background, String value,String name) {
        this.background = background;
        this.value = value;
        this.name=name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
