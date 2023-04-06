package Application.BackEnd.Grille;

import javafx.geometry.Orientation;

public class PontHorizontal extends Pont{

    /**
     * Constructeur de la classe Pont
     * @param i1      première île
     * @param i2      deuxième île
     * @param nbPonts nombre de ponts
     */
    public PontHorizontal(Ile i1, Ile i2, int nbPonts) {
        super(i1, i2, nbPonts,Orientation.HORIZONTAL);
    }

    @Override
    public int getNbPont() {
        return super.getNbPont();
    }

    @Override
    public Orientation getOrientation() {
        return this.getOrient();
    }
}
