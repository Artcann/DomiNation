package org.example.core;

import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Domino extends Tile {

    private StringProperty type;
    private int number;
    private int nbCrown;

    public String getType() { return type.get(); }

    public void setType(String type) { this.type.set(type); }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public int getNbCrown() { return nbCrown; }

    public void setNbCrown(int nbCrown) { this.nbCrown = nbCrown; }


}
