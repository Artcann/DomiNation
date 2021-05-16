package org.example.core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;


public class Domino extends Tile implements Comparable<Domino> {

    private final StringProperty type = new SimpleStringProperty();
    private int number;
    private int index;
    private int nbCrown;

    public Domino(String type, int nbCrown, int number, int index) {
        setType(type);
        this.nbCrown = nbCrown;
        this.number = number;
        this.index = index;
    }

    @Override
    public String getType() { return type.get(); }

    @Override
    public int getIndex() {
        return this.index;
    }

    public void setType(String type) { this.type.set(type); }

    @Override
    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public int getNbCrown() { return nbCrown; }

    public void setNbCrown(int nbCrown) { this.nbCrown = nbCrown; }

    public String toString() {
        return "Domino " + this.number + " : " + getType() + " " + this.nbCrown;
    }

    @Override
    public int compareTo(@NotNull Domino o) {
        return Integer.compare(this.number, o.number);
    }
}
