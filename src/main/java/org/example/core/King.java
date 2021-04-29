package org.example.core;

public class King {

    private Domino[] domino;
    private Color color;

    public King(Color color) {
        this.color = color;
    }

    public Domino[] getDomino() { return this.domino; }

    public void setPosition(Domino[] domino) { this.domino = domino; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }
}
