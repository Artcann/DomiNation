package org.example.core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Domino extends Tile {

    private final StringProperty type = new SimpleStringProperty();
    private int number;
    private int nbCrown;

    public Domino(String type, int nbCrown, int number) {
        setType(type);
        this.nbCrown = nbCrown;
        this.number = number;
    }

    public String getType() { return type.get(); }

    public void setType(String type) { this.type.set(type); }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public int getNbCrown() { return nbCrown; }

    public void setNbCrown(int nbCrown) { this.nbCrown = nbCrown; }

    public String toString() {
        return "Domino " + this.number + " : " + getType() + " " + this.nbCrown;
    }

}
