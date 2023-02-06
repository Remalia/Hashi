public class Technique{
    
    /**
        Constantes static qui indiquent le sens de parcours de la grille
    */
    private static int HAUT = 0;
    private static int GAUCHE = 1;
    private static int BAS = 2;
    private static int DROITE = 3;

    /** 
        Liste des directions
    */
    private static int listeDirections = [this.HAUT, this.GAUCHE, this.BAS, this.DROITE];

    /**
        Méthode qui regarde si une île a seulement un voisin
        Retourne un boolean
        Vrai si une île a seulement un voisin non lié déjà
    */
    boolean unVoisin(Grille uneGrille)
    {
        
        /**
            On parcourt la grille  
            Si l'object parcouru est une île alors on regarde son nombre de voisins
        */
        for(Object obj: uneGrille.matriceGrille)
        {
            if(obj.getClass() == "Ile"){
                if(nbVoisins(obj, uneGrille) == 1){
                    return(true);
                }
            }
        }

        return(false);
    }

    /**
        Méthode qui retourne le nombre d'îles auxquelles une île peut se connecter
    */
    int nbVoisins(Ile uneIle, Grille uneGrille)
    {
        /** Compteur du nombre d'îles voisines */
        int nbIlesVois = 0;

        /** 
            On parcourt la matrice de la grille
            On parcourt chaque direction à partir de l'île passée en paramètres
            On s'arrête de parcourir lorsqu'on rencontre une île
            Si on ne rencontre pas une île on s'arrête à la limite de la grille
        */

        // à modifier avec un for each ==> liste avec les 4 directions
        for(int direction: listeDirections)
        {
            if(parcoursGrille(uneIle.getAbs(), uneIle.getOrd(), direction, uneGrille.getTaille(), uneGrille.matriceGrille)) 
                nbIlesVois++;
        }

        return(nbIlesVois);
    }


    /** 
        Méthode qui parcourt la grille en fonction d'une direction
        On passe la grille en paramètres
        On passe les coordonnées de l'île d'origine en paramètres
        On passe la direction en paramètres
        On passe la taille de la grille en paramètres
        On retourne vrai si lors du parcours une île est trouvée
    */
    boolean parcoursGrille(int xIle, int yIle, int direction, int taille, ArrayList<Object> matriceGrille){
        switch(direction)
        {
            case this.HAUT:
                for(int y  = yIle; y >= 0; y--)
                {
                    if(matriceGrille(xIle))
                }
                break;
            
            case this.GAUCHE:
                break;
            
            case this.BAS:
                break;
            
            case this.DROITE:
                break;
        }

        return(false);
    }
}
