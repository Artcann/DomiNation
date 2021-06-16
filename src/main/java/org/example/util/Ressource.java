package org.example.util;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.example.core.Color;

import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class Ressource {

    private static Image blueCastle;
    private static Image pinkCastle;
    private static Image yellowCastle;
    private static Image greenCastle;

    private static Image recto;

    private static Image white;

    public static void loadRessources() {
        blueCastle = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("blueCastle.png")));
        pinkCastle = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("pinkCastle.png")));
        yellowCastle = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("yellowCastle.png")));
        greenCastle = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("greenCastle.png")));

        recto = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("recto.png")));
        white = new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream("white.png")));
    }

    public static Image getCastleImage(Color color) {
        switch (color) {
            case BLUE:
                return blueCastle;
            case PINK:
                return pinkCastle;
            case YELLOW:
                return yellowCastle;
            case GREEN:
                return greenCastle;
        }
        return null;
    }

    public static Image getWhite() {
        return white;
    }

    public static Image getVerso(Integer number) {
        String name = "verso_" + number + ".png";

        return new Image(Objects.requireNonNull(Ressource.class.getClassLoader().getResourceAsStream(name)));
    }

    public static Image getRecto(Integer number) {
        int row = (number - 1) % 8;
        int column = number / 8;

        if(number%8 == 0) {
            column--;
        }

        return new WritableImage(recto.getPixelReader(), 26 + (column * 215), 26 + (row * 117), 207, 109);

    }

    public static Image getRecto(Integer number, Integer index) {
        int row = (number - 1) % 8;
        int column = number / 8;

        if(number%8 == 0) {
            column--;
        }

        if(index == 0) {
            return new WritableImage(recto.getPixelReader(), 26 + (column * 215), 26 + (row * 117), 103, 103);
        } else {
            return new WritableImage(recto.getPixelReader(), 129 + (column * 215), 26 + (row * 117), 104, 104);
        }
    }

}
