import java.awt.Color;


public class Pont {
    private Ile ile1;
    private Ile ile2;
    private Color couleur;
    private int nombrePont;

    



    /**
     * @param i1 première île
     * @param i2 deuxième île
     * @param c couleur du pont
     */
    public Pont(Ile i1,Ile i2,Color c){
        this.ile1 = i1;
        this.ile2 = i2;
        this.ile1.ajouterPont();
        this.ile2.ajouterPont();
        this.couleur = c;
        this.nombrePont = 0;
    }

    /**
     * @return la première île liée au pont
     */
    public Ile getIle1() {
        return ile1;
    }

    /**
     * @return la deuxième île liée au pont
     */
    public Ile getIle2() {
        return ile2;
    }

    /**
     * @return retourne la couleur actuelle du pont
     */
    public Color getCouleur() {
        return couleur;
    }


    /**
     * @param couleur nouvelle couleur d'un pont
     */
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    /**
     * @return si 1 ce pont est simple et 2 si il est double 
     */
    public int getNombrePont(){
        return this.nombrePont;
    }
}
