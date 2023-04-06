package Application.BackEnd.Grille;

public class PontHorizontal extends Pont{

    /**
     * Constructeur de la classe Pont
     * @param i1      première île
     * @param i2      deuxième île
     */
    public PontHorizontal(Ile i1, Ile i2) {
        super(i1, i2,Orientation.HORIZONTAL);
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
