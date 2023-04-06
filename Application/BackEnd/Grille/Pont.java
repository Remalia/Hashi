package Application.BackEnd.Grille;

import java.awt.Color;


/**
 * Classe représentant un pont du jeu
 * @see Element
 * @see Ile
 */
public abstract class Pont extends Element implements InterfacePont{
    private Ile ile1;
    private Ile ile2;
    private int nombrePont;


    /**
     * Constructeur de la classe Application.BackEnd.Grille.Pont
     * @param i1 première île
     * @param i2 deuxième île
     * @param nbPonts nombre de ponts
     */
    public Pont(Ile i1, Ile i2, int nbPonts){
        super(new Color(0, 0, 255));
        this.ile1 = i1;
        this.ile2 = i2;
        this.ile1.ajouterPont(this);
        this.ile2.ajouterPont(this);
        this.nombrePont = nbPonts;
    }

    /**
     * Retourne la première île liée au pont
     * @return la première île liée au pont
     */
    public Ile getIle1() {
        return ile1;
    }

    /**
     * Retourne la deuxième île liée au pont
     * @return la deuxième île liée au pont
     */
    public Ile getIle2() {
        return ile2;
    }

    /**
     * Retourne le nombre de ponts
     * @return si 1 ce pont est simple et 2 si il est double 
     */
    public int getNombrePont(){
        return this.nombrePont;
    }


    /**
     * Incrémente le nombre de pont et le remet à 0 si il y a déjà 2 ponts
     */
    public void incrementerPont(){
        // si le pont est simple on le passe en double
        if(++this.nombrePont == 2){
            // si le pont est double on le supprime
            this.nombrePont = 0;
        }
    }

    /**
     * Affecte un nombre de ponts
     * @param nombrePont nombre de ponts
     */
    public void setNombrePont(int nombrePont) {
        this.nombrePont = nombrePont;
    }

    
    /**
     * Retire le pont des lists de ponts des îles
     */
    @Override
    public void nettoyerCase(){
        this.ile1.getListePont().remove(this);
        this.ile2.getListePont().remove(this);                                                                                  
    }

    /**
     * Méthode toString de la classe Application.BackEnd.Grille.Pont
     * @return la représentation textuelle du pont en foncitrion de sa valeur
     */
    public String toString(){
        if(this.nombrePont == 0){
            return ".";
        }
        else if (this.nombrePont == 1){
            return "─";
        }else{
            return "═";
        }
    }

    /**
     * 
     * @param ile1 premiere ile du pont
     * @param ile2 deuxieme ile du pont
     * @return pont si Application.BackEnd.Grille.Element est un pont
     */
    @Override
    public Pont donnePont(Ile ile1, Ile ile2){
        return this;
    }

    @Override
    public Element donneIle(){
        this.setNombrePont(0);
        return this;
    }

    @Override
    public boolean estIncrementable(Ile ile1, Ile ile2){
        return true;
    }


}
