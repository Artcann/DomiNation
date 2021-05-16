package org.example.util;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.example.core.Color;

import java.util.Objects;

public class Ressource {

    private static Image blueCastle;
    private static Image pinkCastle;
    private static Image yellowCastle;
    private static Image greenCastle;

    private static Image recto;

    public static void loadRessources() {
        blueCastle = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("blueCastle.png")));
        pinkCastle = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("pinkCastle.png")));

        recto = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("recto.png")));
    }

    public static Image getCastleImage(Color color) {
        switch (color) {
            case BLUE:
                return blueCastle;
            case PINK:
                return pinkCastle;
        }
        return null;
    }

    public static Image getVerso(Integer number) {
        String name = "verso_" + number + ".png";

        return new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream(name)));
    }

    public static Image getRecto(Integer number) {
        int row = (number - 1) % 8;
        int column = number / 8;

        return new WritableImage(recto.getPixelReader(), 26 + (column * 215), 26 + (row * 117), 207, 109);

    }

    public static Image getRecto(Integer number, Integer index) {
        int row = (number - 1) % 8;
        int column = number / 8;

        if(index == 0) {
            return new WritableImage(recto.getPixelReader(), 26 + (column * 215), 26 + (row * 117), 103, 103);
        } else {
            return new WritableImage(recto.getPixelReader(), 129 + (column * 215), 26 + (row * 117), 104, 109);
        }
    }

}