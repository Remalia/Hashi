import java.util.ArrayList;

public class Technique{
    
    /**
        Constantes static qui indiquent le sens de parcours de la grille
    */
    private static final int HAUT = 0;
    private static final int GAUCHE = 1;
    private static final int BAS = 2;
    private static final int DROITE = 3;

    /** 
        Liste des directions
    */
    private static int [] listeDirections = {HAUT, GAUCHE, BAS, DROITE};

    /**
        Méthode qui regarde si une île a seulement un voisin
        Retourne un boolean
        Vrai si une île a seulement un voisin non lié déjà
        Lorsqu'on vérifie si une île a un seul voisin, on vérifie qu'il n'y a pas de ponts déjà construit sur la direction qu'on teste qui ne part pas du pont
    */
    boolean unVoisin(Grille uneGrille)
    {
        
        /**
            On parcourt la grille  
            Si l'object parcouru est une île alors on regarde son nombre de voisins
        */
        for(Object obj: uneGrille.matriceGrille)
        {
            if(obj.getClass() == Ile.class){
                if(nbVoisins((Ile) obj, uneGrille) == 1){
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
    boolean parcoursGrille(int xIle, int yIle, int direction, int taille, Object [][] matriceGrille){
        /** On fait une disjonction de cas selon la direction */
        switch(direction)
        {
            case HAUT:
                for(int y  = yIle - 1; y >= 0; y--)
                {
                    /** Si on trouve une île on retourne true */
                    if(matriceGrille[xIle][y].getClass() == Ile.class)
                    {
                        /** 
                            Il faut vérifier si l'île trouvée est pleine
                            Si c'est le cas on retourne faux
                            On considère que "le côté est bloqué"
                        */
                        if(((Ile) matriceGrille[xIle][y]).estComplete())
                        {
                            return(false);
                        }
                        /** Si l'île peut encore accepter un pont on retourne vrai*/
                        return(true);
                    }
                    /** 
                        Si au contraire on trouve un pont
                        On considère que "le côté est bloqué" puisqu'il est déjà occupé
                    */

                    if(matriceGrille[xIle][y].getClass() == Pont.class)
                    {
                        return(false);
                    }
                }
                break;
            
            case GAUCHE:
                for(int x = xIle - 1; x >= 0; x--)
                {
                    if(matriceGrille[x][yIle].getClass() == Ile.class)
                    {
                        if(((Ile) matriceGrille[x][yIle]).estComplete())
                        {
                            return(false);
                        }
                        return(true);
                    }
                    if(matriceGrille[x][yIle].getClass() == Pont.class)
                    {
                        return(false);
                    }
                }
                break;
            
            case BAS:
                for(int y = yIle + 1; y <= taille; y++)
                {
                    if(matriceGrille[xIle][y].getClass() == Ile.class)
                    {
                        if(((Ile) matriceGrille[xIle][y]).estComplete())
                        {
                            return(false);
                        }
                        return(true);
                    }
                    if(matriceGrille[xIle][y].getClass() == Pont.class)
                    {
                        return(false);
                    }
                }
                break;
            
            case DROITE:
                for(int x = xIle + 1; x <= taille; x++)
                {
                    if(matriceGrille[x][yIle].getClass() == Ile.class)
                    {
                        if(((Ile) matriceGrille[x][yIle]).estComplete()){
                            return(false);
                        }
                        return(true);
                    }
                    if(matriceGrille[x][yIle].getClass() == Pont.class)
                    {
                        return(false);
                    }
                }
                break;
        }

        return(false);
    }
}