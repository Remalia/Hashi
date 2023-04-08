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

    /**
     * Retourne la direction du pont par rapport à l'île passée en paramètre
     * @param ile l'île dont on veut connaître la direction du pont par rapport à elle
     * @return la direction du pont par rapport à l'île passée en paramètre
     */
    @Override
    public Direction getDirectionFrom(Ile ile){
        if(this.getIle1() == ile){
            return (this.getIle1().getOrd() > this.getIle2().getOrd()) ? Direction.HAUT : Direction.BAS;
        }
        else if(this.getIle2() == ile){
            return (this.getIle2().getOrd() > this.getIle1().getOrd()) ? Direction.HAUT : Direction.BAS;
        }
        else{
            //Cas où l'île n'est pas liée au pont
            return null;
        }
    }
}
