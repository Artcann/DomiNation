package org.example.core;

public class Castle extends Tile {

    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String toString() {
        return "\uD83C\uDFF0";
    }

    @Override
    public String getType() {
        return "Castle";
    }
}
