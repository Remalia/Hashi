package Application.BackEnd.Grille;

import javafx.scene.paint.Color;



/**
 * Classe représentant un pont du jeu
 * @see Element
 * @see Ile
 */
public abstract class Pont extends Element implements InterfacePont{
    private final Ile ile1;
    private final Ile ile2;
    private int nbPont;
    private final Orientation orient;

    /**
     * Constructeur de la classe Application.BackEnd.Grille.Pont
     * @param i1 première île
     * @param i2 deuxième île
     * @param orientation L'orientation du pont
     */
    public Pont(Ile i1, Ile i2,Orientation orientation){
        super(Color.rgb(0, 0, 255));
        this.ile1 = i1;
        this.ile2 = i2;
        this.ile1.ajouterPont(this);
        this.ile2.ajouterPont(this);
        this.nbPont = 0;
        this.orient = orientation;
    }

    /**
     * Retourne la première île liée au pont
     * @return la première île liée au pont
     */
    @Override
    public Ile getIle1() {
        return ile1;
    }

    /**
     * Retourne la deuxième île liée au pont
     * @return la deuxième île liée au pont
     */
    @Override
    public Ile getIle2() {
        return ile2;
    }

    /**
     * Renvoie l'orientation du pont
     * @return L'orientation
     */
    public Orientation getOrient() {
        return orient;
    }

    /**
     * Retourne le nombre de ponts
     * @return si 1 ce pont est simple et 2 si il est double
     */
    public int getNbPont(){
        return this.nbPont;
    }


    /**
     * Incrémente le nombre de pont et le remet à 0 si il y a déjà 2 ponts
     */
    public void incrementerPont(){
        // si le pont est simple on le passe en double
        if(++this.nbPont == 2){
            // si le pont est double on le supprime
            this.nbPont = 0;
        }
    }

    /**
     * Affecte un nombre de ponts
     * @param nbPont nombre de ponts
     */
    public void setNbPont(int nbPont) {
        this.nbPont = nbPont;
    }

    /**
     * Méthode toString de la classe Application.BackEnd.Grille.Pont
     * @return la représentation textuelle du pont en foncitrion de sa valeur
     */
    public String toString(){
        if(this.nbPont == 0){
            return ".";
        }
        else if (this.nbPont == 1){
            return switch (this.getOrientation()) {
                case HORIZONTAL -> "-";
                case VERTICAL -> "|";
            };
        }else{
            return switch (this.getOrientation()){
                case VERTICAL -> "∥";
                case HORIZONTAL -> "=";
            };
        }
    }

    /**
     * Donne le pont
     * @param ile1 premiere ile du pont
     * @param ile2 deuxieme ile du pont
     * @return pont Element est un pont
     */
    @Override
    public Pont donnePont(Ile ile1, Ile ile2){
        return this;
    }

    @Override
    public Element donneIle(){
        this.setNbPont(0);
        return this;
    }

    @Override
    public boolean estDisponible(){
        return nbPont == 0;
    }


}
