package Application.BackEnd.Grille;

public class PontVertical extends Pont{

    /**
     * Constructeur de la classe Pont
     * @param i1      première île
     * @param i2      deuxième île
     */
    public PontVertical(Ile i1, Ile i2) {
        super(i1, i2,Orientation.VERTICAL);
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
