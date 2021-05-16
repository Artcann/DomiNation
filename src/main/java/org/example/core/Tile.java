package org.example.core;

// Tile Class is created for polymorphism

public class Tile {

    protected Integer[] position;

    public Integer[] getPosition() {
        return position;
    }

    public void setPosition(Integer[] position) {
        this.position = position;
    }

    public String getType() {
        return "Tile";
    }

    public int getIndex() {
        return 0;
    }

    public int getNumber() {
        return 0;
    }

}
