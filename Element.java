import java.awt.Color;

/**
 * Classe abstraite représentant un élément du jeu
 */
public class Element {
    private Color couleur;

    /**
     * Constructeur de la classe Element
     * @param color la couleur de l'élément
     */
    public Element (Color color) {
        this.couleur = color;
    }

    /**
     * Constructeur de la classe Element
     * @param r la composante rouge de la couleur
     */
    public Element(){
        this.couleur = new Color(0, 0, 255);
    }

    /**
    * Retourne la couleur de l'élément'
    *
    * @return la couleur de l'élément
    */
    public Color getCouleur(){
        return this.couleur;
    }

    /**
    * Affecte une couleur à un élément
    *
    * @param  c  la couleur à affecter
    */
    public void setCouleur(Color c){
        this.couleur = c;
    }
}
