package org.example.core;

import javafx.beans.InvalidationListener;

public class Castle extends Tile {

    private String color;

    public Castle(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
