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
            /** 
                Si une île peut accueilir plus de 2 ponts cela signifie qu'elle a au moins 2 voisins, on ne la prend pas en compte dans notre recherche 
                Si une île est complète on ne la prend pas en compte
            */
            if(obj.getClass() == Ile.class)
            {
                if(((Ile)obj).getNum() <= 2 && !((Ile)obj).estComplete() )
                {
                    if(nbVoisins((Ile) obj, uneGrille) == 1){
                        return(true);
                    }
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
        /**
            On récupère l'île d'origine
        */
        Ile ileOrigine = (Ile)matriceGrille[xIle][yIle];
        
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
                        Si au contraire il y a un pont on regarde si le pont est accueili par l'île d'origine
                    */

                    if(matriceGrille[xIle][y].getClass() == Pont.class)
                    {
                        /** On doit regarder si une des deux îles du pont est la même que celle d'origine */

                        if( ((Pont)matriceGrille[xIle][y]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[xIle][y]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
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
                        if( ((Pont)matriceGrille[x][yIle]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[x][yIle]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
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
                        if( ((Pont)matriceGrille[xIle][y]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[xIle][y]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
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
                        if( ((Pont)matriceGrille[x][yIle]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[x][yIle]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);
                    }
                }
                break;
        }

        return(false);
    }


    /**
        Méthode qui retourne une île qui n'a qu'un voisin
    */
    Ile ileUnVoisin(Grille uneGrille)
    {
        /**
            On parcourt la liste des objets de la matrice de la grille
            Quand on trouve une île qui n'a qu'un voisin on change sa couleur
        */
        /** 
            Si une île peut accueilir plus de 2 ponts cela signifie qu'elle a au moins 2 voisins, on ne la prend pas en compte dans notre recherche 
            Si une île est complète on ne la prend pas en compte
        */
        if(obj.getClass() == Ile.class)
        {
            if(((Ile)obj).getNum() <= 2 && !((Ile)obj).estComplete() )
            {
                if(nbVoisins((Ile) obj, uneGrille) == 1){
                    return((Ile)obj);
                }
            }
        }
    }


    /**
        Méthode qui cherche si une île a deux îles voisines dont une île qui n'acceuille qu'un seul pont au maximum
        Retourne un booléen
            --> Vrai si une île existe dans cette configuration
            --> Faux sinon
    */
    boolean ileDeuxVoisinsDontUnUn(Grille uneGrille)
    {
        /**
            On parcourt la grille  
            Si l'object parcouru est une île alors on regarde son nombre de voisins
        */
        for(Object obj: uneGrille.matriceGrille)
        {
            /** 
                Si l'île est complète on ne la considère pas
            */
            if(obj.getClass() == Ile.class)
            {
                if( !((Ile)obj).estComplete() )
                {
                    /** Si l'île a 2 voisins on regarde si une des deux îles accueille au maximum un pont */
                    if(nbVoisins((Ile) obj, uneGrille) == 2){
                        //return(true);
                        /**
                            On doit regarder si un des deux voisins accueille au maximum un pont
                        */

                    }
                }
            }
        }

        return(false);
    }

    /**
        Méthode qui récupèrent la liste des îles voisines d'une île
    */
    ArrayList<Ile> listeIlesVoisines(Ile ileOrigine, Grille uneGrille)
    {
        ArrayList<Ile> listeIlesVois = new ArrayList<Ile>();

        /** 
            On parcourt les 4 directions pour récupérer les îles valables
        */
        for(int direction: listeDirections)
        {
            if(parcoursGrille(ileOrigine.getAbs(), ileOrigine.getOrd(), direction, uneGrille.getTaille(), uneGrille.matriceGrille))
            {
                /**
                    Si dans une direction il y a une île on récupère l'île de la direction parcourue
                */
                listeIlesVois.add(  );
            }
        }

        return(listeIlesVois);
    }

    /**
        Méthode qui retourne une île
        On parcourt une grille dans une direction et on retourne une île si on en trouve une valable
    */
    /** 
        Méthode qui parcourt la grille en fonction d'une direction
        On passe la grille en paramètres
        On passe les coordonnées de l'île d'origine en paramètres
        On passe la direction en paramètres
        On passe la taille de la grille en paramètres
        On retourne une île si elle est trouvée
    */
    Ile recupIleGrille(int xIle, int yIle, int direction, int taille, Object [][] matriceGrille){
        /**
            On récupère l'île d'origine
        */
        Ile ileOrigine = (Ile)matriceGrille[xIle][yIle];
        
        /** On fait une disjonction de cas selon la direction */
        switch(direction)
        {
            case HAUT:
                for(int y  = yIle - 1; y >= 0; y--)
                {
                    /** 
                        On ne parcourt une direction que si on est sûr d'obtenir une île valable à un moment donné
                        Par conséquent il n'y a pas de vérification à réaliser 
                        On retourne simplement l'île que l'on trouve en premier
                    */
                    if(matriceGrille[xIle][y].getClass() == Ile.class)
                    {
                        return((Ile)matriceGrille[xIle][y]);
                    }
                }
                break;
            
            case GAUCHE:
                for(int x = xIle - 1; x >= 0; x--)
                {
                    if(matriceGrille[x][yIle].getClass() == Ile.class)
                    {
                        return((Ile)matriceGrille[x][yIle]);
                    }
                }
                break;
            
            case BAS:
                for(int y = yIle + 1; y <= taille; y++)
                {
                    if(matriceGrille[xIle][y].getClass() == Ile.class)
                    {
                        return((Ile)matriceGrille[xIle][y]);
                    }
                }
                break;
            
            case DROITE:
                for(int x = xIle + 1; x <= taille; x++)
                {
                    if(matriceGrille[x][yIle].getClass() == Ile.class)
                    {
                        return((Ile)matriceGrille[x][yIle]);
                    }
                }
                break;
        }

        return(null);
    }
}