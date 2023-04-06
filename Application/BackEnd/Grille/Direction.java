package Application.BackEnd.Grille;

import javafx.geometry.Orientation;

public enum Direction {
    HAUT,BAS,GAUCHE,DROITE;

    public Direction getInverse() {
        return switch (this) {
            case HAUT -> BAS;
            case DROITE -> GAUCHE;
            case BAS -> HAUT;
            case GAUCHE -> DROITE;
            default -> null;
        };
    }

    public Orientation getOrientation(Direction d) {
        return switch (d) {
            case HAUT, BAS -> Orientation.VERTICAL;
            case GAUCHE, DROITE -> Orientation.HORIZONTAL;
            default -> null;
        };
    }

}