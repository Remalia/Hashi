package Application.BackEnd.Grille;

import java.awt.Color;

/**
 * Classe abstraite représentant un élément du jeu
 */
public abstract class Element {
    private Color couleur;
    /**
     * Constructeur de la classe Application.BackEnd.Grille.Element
     * @param color la couleur de l'élément
     */
    public Element (Color color) {
        this.couleur = color;
    }

    /**
     * Constructeur de la classe Application.BackEnd.Grille.Element
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
    * @param  c  la couleur à affecter
    */
    public void setCouleur(Color c){
        this.couleur = c;
    }

    /**
     * Méthode toString de la classe Application.BackEnd.Grille.Element
     * @return la représentation textuelle de l'élément
     */
    public String toString(){
        return "░";
    }

    /**
     * Retourne null si Element quelconque
     * @param ile1 premiere ile du pont
     * @param ile2 deuxieme ile du pont
     * @return null si Element quelconque
     */
    public Pont donnePont(Ile ile1, Ile ile2){
        return null;
    }
    public Element donneIle(){return this;}

    /**
     * Donne une ile en fonction d'une direction
     * @param x x du parcours dans la grille
     * @param y y du parcours dans la grille
     * @param d direction dans laquelle on cherche
     * @param Grille Application.BackEnd.Grille.Grille à parcourir
     * @return L'ile si trouvée sinon null.
     */
    public Ile getIleFromDirection(int x, int y, Direction d, Element[][] Grille){
        switch(d){
            case HAUT:
                if(y > 0 && Grille[x][y - 1] != null){
                    return Grille[x][y - 1].getIleFromDirection(x, y - 1, d, Grille);
                }
                break;
            case BAS:
                if(y < 9 && Grille[x][y+1] != null){
                    return Grille[x][y + 1].getIleFromDirection(x, y + 1, d, Grille);
                }
                break;
            case GAUCHE:
                if(x > 0 && Grille[x-1][y] != null){
                    return Grille[x-1][y].getIleFromDirection( x - 1, y, d, Grille);
                }
                break;
            case DROITE:
                if(x < 9 && Grille[x+1][y] != null){
                    return Grille[x+1][y].getIleFromDirection( x + 1, y, d, Grille);
                }
                break;
        }
        //Si aucune île n'as été trouvée
        return null;
    }

    public boolean estIncrementable(Ile ile1, Ile ile2){
        return false;
    }


    /**
     * Méthode abstract qui permet de nettoyer une case
     */
    public abstract void nettoyerCase();

}
