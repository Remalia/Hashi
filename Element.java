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

    public String toString(){
        return "░";
    }

    /**
     * 
     * @param ile1 premiere ile du pont
     * @param ile2 deuxieme ile du pont
     * @return null si Element quelconque
     */
    public Pont donePont(Ile ile1, Ile ile2){
        return null;
    }


    /**
     * @param x x du parcours dans la grille
     * @param y y du parcours dans la grille
     * @param d direction dans laquelle on cherche
     * @param Grille Grille à parcourir
     * @return Une ile si trouvée, null sinon.
     */
    public Ile parcoursMatrice(int x, int y, Direction d, Element Grille[][]){
        switch(d){
            case HAUT :
                if(y > 0 && Grille[x][y - 1] != null){
                    return Grille[x][y - 1].parcoursMatrice(x, y - 1, d, Grille);
                }
                break;
            case BAS :
                if(y < 9 && Grille[x][y+1] != null){
                    return Grille[x][y + 1].parcoursMatrice(x, y + 1, d, Grille);
                }
                break;
            case GAUCHE :
                if(x > 0 && Grille[x-1][y] != null){
                    return Grille[x-1][y].parcoursMatrice( x - 1, y, d, Grille);
                }
                break;
            case DROITE :
                if(x < 9 && Grille[x+1][y] != null){
                    return Grille[x+1][y].parcoursMatrice( x + 1, y, d, Grille);
                }
                break;
        }
        //Si aucune île n'as été trouvée
        return null;
    }

    public void nettoyerCase(){
    }

}

/*
    null -> ile
    ile -> elle meme
    pont -> ile (recréer les ponts)
    inter -> ile (recréer les ponts)
*/