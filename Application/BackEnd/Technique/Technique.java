package Application.BackEnd.Technique;

import Application.BackEnd.Grille.*;

import java.util.ArrayList;
import java.awt.Color;
import java.util.Arrays;

public class Technique{
    
    private String description;
    private Ile ileCour;

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


    Technique() // Peut-être créer un autre constructeur qui permettrait d'ajouter une description et une île cour
    {
        this.setDescription("");
        this.setIleCour(null);
    }

    void setDescription(String description)
    {
        this.description = description;
    }

    void setIleCour(Ile ile)
    {
        this.ileCour = ile;
    }

    String getDescription()
    {
        return(this.description);
    }

    Ile getIleCour()
    {
        return(this.ileCour);
    }

    /** 
        Méthode qui parcourt une direction passée en paramètres et qui regarde s'il existe un pont
    */
    static boolean absencePont(int direction, Ile ileOrigine, Ile ileDestination, Element[][] matrice, int taille){
        int coord;

        int xOrigine = ileOrigine.getAbs();
        int yOrigine = ileOrigine.getOrd();

        int xDestination = ileDestination.getAbs();
        int yDestination = ileDestination.getOrd();

        

        switch(direction)
        {
            case HAUT:
                for(coord = yOrigine; coord > yDestination && coord >= 0; coord--)
                {
                    if(matrice[xOrigine][coord] instanceof Intersection)
                    {
                        // On doit vérifier si le second pont de l'intersection existe ou non

                        Pont pontUn = ((Intersection) matrice[xOrigine][coord]).getPont1();
                        Pont pontDeux = ((Intersection) matrice[xOrigine][coord]).getPont2();

                        if(pontUn.getIle1() == ileOrigine || pontUn.getIle2() == ileOrigine)
                        {
                            // Si le premier pont est celui qui rejoint les 2 îles passées en paramètres alors on regarde la valeur de l'autre pont   
                            if(pontDeux.getNombrePont() != 0)
                            {
                                // Si le pont existe alors on retourne faux car l'île n'est pas accessible
                                return false;
                            }
                        }
                        else
                        {
                            if(pontUn.getNombrePont() != 0)
                            {
                                return false;
                            }
                        }
                    }
                }
                break;
            case BAS:
                for(coord = yOrigine; coord < yDestination && coord <= taille; coord++)
                {
                    if(matrice[xOrigine][coord] instanceof Intersection)
                    {
                        // On doit vérifier si le second pont de l'intersection existe ou non

                        Pont pontUn = ((Intersection) matrice[xOrigine][coord]).getPont1();
                        Pont pontDeux = ((Intersection) matrice[xOrigine][coord]).getPont2();

                        if(pontUn.getIle1() == ileOrigine || pontUn.getIle2() == ileOrigine)
                        {
                            // Si le premier pont est celui qui rejoint les 2 îles passées en paramètres alors on regarde la valeur de l'autre pont   
                            if(pontDeux.getNombrePont() != 0) return false;
                        }
                        else
                        {
                            if(pontUn.getNombrePont() != 0) return false;
                        }
                    }
                }
                break;
            case GAUCHE:
                for(coord = xOrigine; coord > xDestination && coord >= 0; coord--)
                {
                    if(matrice[coord][yOrigine] instanceof Intersection)
                    {
                        // On doit vérifier si le second pont de l'intersection existe ou non

                        Pont pontUn = ((Intersection) matrice[coord][yOrigine]).getPont1();
                        Pont pontDeux = ((Intersection) matrice[coord][yOrigine]).getPont2();

                        if(pontUn.getIle1() == ileOrigine || pontUn.getIle2() == ileOrigine)
                        {
                            // Si le premier pont est celui qui rejoint les 2 îles passées en paramètres alors on regarde la valeur de l'autre pont   
                            if(pontDeux.getNombrePont() != 0) return false;
                        }
                        else
                        {
                            if(pontUn.getNombrePont() != 0) return false;
                        }
                    }
                }
                break;
            case DROITE:
                for(coord = xOrigine; coord < xDestination && coord <= taille; coord++)
                {
                    if(matrice[coord][yOrigine] instanceof Intersection)
                    {
                        // On doit vérifier si le second pont de l'intersection existe ou non

                        Pont pontUn = ((Intersection) matrice[coord][yOrigine]).getPont1();
                        Pont pontDeux = ((Intersection) matrice[coord][yOrigine]).getPont2();

                        if(pontUn.getIle1() == ileOrigine || pontUn.getIle2() == ileOrigine)
                        {
                            // Si le premier pont est celui qui rejoint les 2 îles passées en paramètres alors on regarde la valeur de l'autre pont   
                            if(pontDeux.getNombrePont() != 0) return false;
                        }
                        else
                        {
                            if(pontUn.getNombrePont() != 0) return false;
                        }
                    }
                }
                break;
        }

        return true;
    }


    /** 
        Méthode qui vérifie que l'on peut incrémenter le pont entre deux îles
    */
    static boolean verifCreationPont(Ile ileOrigine, Ile ileDestination, Grille uneGrille)
    {
        Element[][] matrice = uneGrille.getMatriceGrille();

        int xOrigine = ileOrigine.getAbs();
        int yOrigine = ileOrigine.getOrd();

        int xDestination = ileDestination.getAbs();
        int yDestination = ileDestination.getOrd();

        int taille = uneGrille.getTaille();

        // Il faut connaître si les 2 îles sont sur la même ligne ou la même colonne
        if(xOrigine == xDestination)
        {
            // Même ligne
            // 2 cas possibles => l'île d'origine est à gauche de l'île de destination ou l'inverse
            if(yOrigine < yDestination)
            {
                // On parcourt vers la droite car l'île d'origine est à gauche de l'île de destination
                
                return absencePont(DROITE, ileOrigine, ileDestination, matrice, taille);
            }
            else
            {
                // On parcourt vers la gauche car l'île d'origine est à droite de l'île de destination
                return absencePont(GAUCHE, ileOrigine, ileDestination, matrice, taille);
            }
        }
        else
        {
            // Même colonne (on considère qu'on ne peut pas arriver ici sans avoir déjà vérifié qu'ils étaient sur une même ligne ou une même colonne)
            if(xOrigine < xDestination)
            {
                // On parcourt vers le bas car l'île d'origine est au dessus de l'île de destination
            
                return absencePont(BAS, ileOrigine, ileDestination, matrice, taille);
            }
            else
            {
                // On parcourt vers le haut car l'île d'origine est en dessous de l'île de destination
                return absencePont(HAUT, ileOrigine, ileDestination, matrice, taille);
            }
        }

        //return false;
    }

    /**
        Méthode qui indique si une île peut accepter un pont simple
        Vrai si c'est le cas, faux sino,
    */

    static boolean ajoutPontSimple(Ile ile)
    {
        return(ile.getNum() > ile.getNbPonts());
    }

    /** 
        Méthode qui indique si une île peut accepter un pont double
        Vrai si c'est le cas, faux sinon
    */
    static boolean ajoutPontDouble(Ile ile)
    {
        return( (ile.getNum() - ile.getNbPonts()) >= 2);
    }

    /** 
        Méthode qui retourne si toutes les îles de la liste sont accessibles à partir de l'île d'origine
        On ne vérifie pas si elles sont complètes, on vérifie seulement s'il existe un pont entre les 2
    */
    static boolean ilesAccessibles(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {
        for(Ile i: voisins)
        {
            if(!Technique.verifCreationPont(ileOrigine, i, uneGrille))
            {
                return(false);
            }
        }
        return(true);
    }

    /**
        Méthode qui vérifie si entre une île et son unique voisin on peut créer un pont 
    */
    static boolean unVoisinRejoignable(Ile ileOrigine, Ile ileDestination, Grille uneGrille)
    {
        /**
            On vérifie juste que l'île d'arrivée est libre et qu'il n'y a pas de ponts entre les 2 îles
        */

        return( !ileDestination.estComplete() && Technique.verifCreationPont(ileOrigine, ileDestination, uneGrille) == true);
    }

    /**
        Méthode qui retourne une Application.BackEnd.Technique.Technique
        Prend en paramètres une liste d'îles composée d'une seule île car il n'y a qu'un seul voisin 
        Prend en paramètres l'île d'origine
        Effectue des vérifications sur cette liste d'îles
        Retourne une technique appliquable sur cette liste d'îles
    */
    static Technique unVoisinBis(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {
        Technique t = new Technique();

        /**
            On récupère l'unique île voisine de la liste
        */
        Ile premVois = voisins.get(0);

        /**
            On vérifie juste que l'île d'arrivée est libre et qu'il n'y a pas de ponts entre les 2 îles
        */
        if(Technique.unVoisinRejoignable(ileOrigine, premVois, uneGrille))
        {
            t.setDescription("Il y a une île qui n'a qu'un seul voisin, vous devriez les rejoindre !");
            t.setIleCour(ileOrigine);
            return(t);
        }

        return(null); 
    }

    /**
        Méthode qui retourne une Application.BackEnd.Technique.Technique
        Prend en paramètres une liste d'îles composée de deux îles car il y a 2 îles voisines
        Prend en paramètres l'île d'origine
        Effectue des vérifications sur cette liste d'îles pour voir quelle technique est appliquable
        Retourne une technique appliquable sur cette liste d'îles
    */
    static Technique deuxVoisinsBis(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {
        Technique t = new Technique();

        /**
            On récupère les îles voisines
        */

        Ile premVois = voisins.get(0);
        Ile scdVois = voisins.get(1);

        /**
            On vérifie en premier lieu que toutes les îles de la liste sont accessibles à partir de l'île d'origine
        */
        if(!ilesAccessibles(ileOrigine, voisins, uneGrille))
        {
            return(null);
        }

        /**
            On vérifie pour chaque cas si la technique est appliquable
            Dès que l'on trouve une technique appliquable on arrête
        */

        /**
            On va procéder par disjonction de cas en fonction du nombre de ponts que peut créer l'îles
            Il y a plusieurs cas possibles:
                - 1 pont peut être créé
                    -> S'il y a une île qui ne peut créer au maximum qu'un pont, on crée un pont avec l'autre île

                - 2 ponts peuvent être créés:
                    -> S'il y a une île qui admet 1 pont au maximum on crée un pont avec chaque île si l'île qui admet un pont au maximum n'a qu'un voisin
                    -> S'il y a une île qui admet 2 ponts au maximum on crée un pont avec l'autre île
                - 3 ponts peuvent être créés
                    -> Un pont au moins peut être créé avec chaque voisin
                - 4 ponts peuvent être créés
                    -> Deux ponts peuvent être créés avec chaque voisin
        */

        switch(ileOrigine.getNum())
        {
            case 1:
                if( (premVois.getNum() == 1 && !premVois.estComplete() && Technique.ajoutPontSimple(scdVois)) || (scdVois.getNum() == 1 && !scdVois.estComplete() && Technique.ajoutPontSimple(premVois)) )
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui ne peut créer qu'un pont au maximum. Cependant un de ses voisins ne peut accepter qu'un pont, il faut donc la relier à l'autre île par un pont simple.");
                    return t;
                }
                break;
            case 2:
                if( (premVois.getNum() == 1 && Technique.nbVoisins(premVois, uneGrille) == 1) || (scdVois.getNum() == 1 && Technique.nbVoisins(scdVois, uneGrille) == 1) )
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer deux ponts au maximum. Cependant un de ses voisins (qui a pour unique voisin l'île en question) ne peut accepter qu'un pont au maximum, il faut donc relier l'île aux 2 autres îles par un pont simple");
                    return t;
                }
                else if( (premVois.getNum() == 2 && Technique.ajoutPontSimple(premVois) && Technique.ajoutPontSimple(scdVois) ) || (scdVois.getNum() == 2 && Technique.ajoutPontSimple(scdVois) && Technique.ajoutPontSimple(premVois)))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer deux ponts au maximum. Cependant un de ses voisins ne peut accepter que deux ponts au maximum, il faut donc relier l'île à l'autre île par un pont simple");
                    return t;
                }
                else if( (premVois.getNum() == 1 && Technique.ajoutPontSimple(scdVois)) || (scdVois.getNum() == 1 && Technique.ajoutPontSimple(premVois)) )
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer deux ponts au maximum. Cependant un de ses voisins ne peut accepter qu'un pont au maximum, il faut donc relier l'île à l'autre île par un pont simple");
                    return t;
                }
                break;
            case 3:
                if(Technique.ajoutPontSimple(premVois) && Technique.ajoutPontSimple(scdVois))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer trois ponts au maximum. Il faut donc relier cette île à ses 2 voisines par des ponts simples.");
                    return t;
                }
                break;
            case 4:
                if(Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(scdVois))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer quatre ponts au maximum. Il faut donc relier cette île à ses 2 voisines par des ponts double.");
                    return t;
                }
                break;
        }

        return(null);
    }

    /**
        Méthode qui retourne une Application.BackEnd.Technique.Technique
        Prend en paramètres une liste d'îles composée de trois îles car il y a 3 îles voisines
        Prend en paramètres l'île d'origine
        Effectue des vérifications sur cette liste d'îles pour voir quelle technique est appliquable
        Retourne une technique appliquable sur cette liste d'îles
    */

    static Technique troisVoisinsBis(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {

        Technique t = new Technique();

        /**
            Il y a 3 cas possibles:
                - L'île d'origine accepte 5 ponts => 1 pont avec chaque île
                - L'île d'origine accepte 5 ponts et possède un voisin qui n'accepte qu'un pont => 1 pont avec l'île qui n'en accepte qu'un + des ponts doubles avec les autres îles
                - L'île d'origine accepte 6 ponts => double ponts avec chaque île
        */

        /**
            On récupère les îles voisines
        */

        Ile premVois = voisins.get(0);
        Ile scdVois = voisins.get(1);
        Ile trsmVois = voisins.get(2);

        /**
            On vérifie en premier lieu que toutes les îles de la liste sont accessibles à partir de l'île d'origine
        */
        if(!ilesAccessibles(ileOrigine, voisins, uneGrille))
        {
            return null;
        }

        switch(ileOrigine.getNum())
        {
            case 5:
                /* On regarde si une des îles voisines n'accepte qu'un voisin */
                if((premVois.getNum() == 1 && Technique.ajoutPontSimple(premVois) && Technique.ajoutPontDouble(scdVois) && Technique.ajoutPontDouble(trsmVois)) || (scdVois.getNum() == 1 && Technique.ajoutPontSimple(scdVois) && Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(trsmVois))|| (trsmVois.getNum() == 1 && Technique.ajoutPontSimple(trsmVois) && Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(scdVois)))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement trois voisins qui peut créer cinq ponts. Parmi ses trois voisins, une île n'accepte qu'un pont au maximum. L'île doit donc être reliée à cette dernière par un pont simple. L'île doit être reliée aux deux autres par des ponts double.");
                    return t;
                }
                else
                {
                    if(Technique.ajoutPontSimple(premVois) && Technique.ajoutPontSimple(scdVois) && Technique.ajoutPontSimple(trsmVois))
                    {
                        t.setIleCour(ileOrigine);
                        t.setDescription("Il y a une île qui a exactement trois voisins qui peut créer cinq ponts. L'île doit donc se relier à chaque île par un pont simple minimum.");
                        return t;
                    }
                }
                break;
            case 6:
                if(Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(scdVois) && Technique.ajoutPontDouble(trsmVois))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une ile qui a exactement trois voisins qui peut doit créer six ponts. L'île doit donc se relier à chaque île par un double pont.");
                    return t;
                }
                break;
        }

        return null;
    }


    /** 
        Méthode qui retourne une Application.BackEnd.Technique.Technique
        Prend en paramètres une liste d'îles composée de quatre îles car il y a 4 îles voisines
        Prend en paramètres l'île d'origine
        Effectue des vérifications sur cette liste d'îles pour voir quelle technique est appliquable
        Retourne une technique appliquable sur cette liste d'îles
    */


    static Technique quatreVoisinsBis(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {

        Technique t = new Technique();

        /**
            Il y a cas possibles:
                - 7 ponts => un pont simple avec chaque
                - 7 ponts dont un voisin avec un => un pont simple avec le un + pont double avec les autres
                - 8 ponts => pont double avec chaque voisin
        */

        /**
            On récupère les îles voisines
        */

        Ile premVois = voisins.get(0);
        Ile scdVois = voisins.get(1);
        Ile trsmVois = voisins.get(2);
        Ile qtrmVois = voisins.get(3);

        /**
            On vérifie en premier lieu que toutes les îles de la liste sont accessibles à partir de l'île d'origine
        */
        if(!ilesAccessibles(ileOrigine, voisins, uneGrille))
        {
            return null;
        }

        switch(ileOrigine.getNum())
        {
            case 7:
                if((premVois.getNum() == 1 && Technique.ajoutPontSimple(premVois) && Technique.ajoutPontDouble(scdVois) && Technique.ajoutPontDouble(trsmVois) && Technique.ajoutPontDouble(qtrmVois)) || (scdVois.getNum() == 1 && Technique.ajoutPontSimple(scdVois) && Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(trsmVois) && Technique.ajoutPontDouble(qtrmVois)) || (trsmVois.getNum() == 1 && Technique.ajoutPontSimple(trsmVois) && Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(scdVois) && Technique.ajoutPontDouble(qtrmVois)) || (qtrmVois.getNum() == 1 && Technique.ajoutPontSimple(qtrmVois) && Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(scdVois) && Technique.ajoutPontDouble(qtrmVois)))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement quatre voisins qui doit créer 7 ponts. Un de ses voisins ne doit créer qu'un pont au maximum. L'île doit donc rejoindre la dernière via un pont simple et les autres via des ponts double.");
                    return t;
                }
                else
                {
                    if(Technique.ajoutPontSimple(premVois) && Technique.ajoutPontSimple(scdVois) && Technique.ajoutPontSimple(trsmVois) && Technique.ajoutPontSimple(qtrmVois))
                    {
                        t.setIleCour(ileOrigine);
                        t.setDescription("Il y a une île qui a exactement quatre voisins qui doit créer 7 ponts. Elle doit donc rejoindre les autres îles via au moins un pont simple.");
                        return t;
                    }
                }
                break;
            case 8:
                if(Technique.ajoutPontDouble(premVois) && Technique.ajoutPontDouble(scdVois) && Technique.ajoutPontDouble(trsmVois) && Technique.ajoutPontDouble(qtrmVois))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement quatre voisins qui doit créer 8 ponts. Elle doit donc rejoindre les autres îles via des double ponts.");
                    return t;
                }
                break;
        }

        return null;
    }



    /**
        Méthode qui regarde si une île a seulement un voisin
        Retourne un boolean
        Vrai si une île a seulement un voisin non lié déjà
        Lorsqu'on vérifie si une île a un seul voisin, on vérifie qu'il n'y a pas de ponts déjà construit sur la direction qu'on teste qui ne part pas du pont
    */
    static boolean unVoisin(Grille uneGrille)
    {
        Object[][] matrice = uneGrille.getMatriceGrille();
        Object obj;
        /**
            On parcourt la grille  
            Si l'object parcouru est une île alors on regarde son nombre de voisins
        */
        /*for(Object obj: matrice)
        {
            for(Object obj2: obj)
            {
                System.out.prinltn("test");
            }
        }*/

        for(int i = 0; i < uneGrille.getTaille(); i++)
        {
            for(int j = 0; j < uneGrille.getTaille(); j++)
            {
                obj = matrice[i][j];
                if(obj.getClass() == Ile.class)
                {
                    System.out.println("Il y a une île, i/j : "+i+"/"+j);
                    if(((Ile)obj).getNum() <= 2 && !((Ile)obj).estComplete() )
                    {
                        if(nbVoisins((Ile) obj, uneGrille) == 1){
                            return(true);
                        }
                    }
                }
                else{
                    System.out.println("Pas une île i/j : "+i+"/"+j);
                }
            }
            System.out.println("\n");
        }
        //{
            /** 
                Si une île peut accueilir plus de 2 ponts cela signifie qu'elle a au moins 2 voisins, on ne la prend pas en compte dans notre recherche 
                Si une île est complète on ne la prend pas en compte
            */
          /*  if(obj.getClass() == Application.BackEnd.Grille.Ile.class)
            {
                System.out.println("\n\n\nIl y a une île");
                if(((Application.BackEnd.Grille.Ile)obj).getNum() <= 2 && !((Application.BackEnd.Grille.Ile)obj).estComplete() )
                {
                    if(nbVoisins((Application.BackEnd.Grille.Ile) obj, uneGrille) == 1){
                        return(true);
                    }
                }
            }
            else{
                System.out.println("Pas une île");
            }
        }*/

        return(false);
    }

    /**
        Méthode qui retourne le nombre d'îles auxquelles une île peut se connecter
    */
    static int nbVoisins(Ile uneIle, Grille uneGrille)
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
            if(parcoursGrille(uneIle.getAbs(), uneIle.getOrd(), direction, uneGrille.getTaille(), uneGrille.getMatriceGrille())) 
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
    static boolean parcoursGrille(int xIle, int yIle, int direction, int taille, Element [][] matriceGrille){
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
                    if(matriceGrille[xIle][y] instanceof Ile)
                    {
                        /** 
                            Il ne faut pas vérifier maintenant si l'île trouvée est pleine
                            On le vérifiera plus tard 
                        */
                        /*if(((Application.BackEnd.Grille.Ile) matriceGrille[xIle][y]).estComplete())
                        {
                            return(false);
                        }*/
                        /** Si l'île peut encore accepter un pont on retourne vrai*/
                        return(true);
                    }
                    /** 
                        Si au contraire il y a un pont on regarde si le pont est accueili par l'île d'origine
                    */

                    /*if(matriceGrille[xIle][y].getClass() == Application.BackEnd.Grille.Pont.class)
                    {
                        //On doit regarder si une des deux îles du pont est la même que celle d'origine  

                        if( ((Application.BackEnd.Grille.Pont)matriceGrille[xIle][y]).getIle1().equals(ileOrigine) ||  ((Application.BackEnd.Grille.Pont)matriceGrille[xIle][y]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);
                    }*/
                }
                break;
            
            case GAUCHE:
                for(int x = xIle - 1; x >= 0; x--)
                {
                    if(matriceGrille[x][yIle] instanceof Ile)
                    {
                        /*if(((Application.BackEnd.Grille.Ile) matriceGrille[x][yIle]).estComplete())
                        {
                            return(false);
                        }*/
                        return(true);
                    }/*
                    if(matriceGrille[x][yIle].getClass() == Application.BackEnd.Grille.Pont.class)
                    {
                        if( ((Application.BackEnd.Grille.Pont)matriceGrille[x][yIle]).getIle1().equals(ileOrigine) ||  ((Application.BackEnd.Grille.Pont)matriceGrille[x][yIle]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);                    
                    }*/
                }
                break;
            
            case BAS:
                for(int y = yIle + 1; y < taille; y++)
                {
                    if(matriceGrille[xIle][y] instanceof Ile)
                    {
                        /*
                        if(((Application.BackEnd.Grille.Ile) matriceGrille[xIle][y]).estComplete())
                        {
                            return(false);
                        }*/
                        return(true);
                    }/*
                    if(matriceGrille[xIle][y].getClass() == Application.BackEnd.Grille.Pont.class)
                    {
                        if( ((Application.BackEnd.Grille.Pont)matriceGrille[xIle][y]).getIle1().equals(ileOrigine) ||  ((Application.BackEnd.Grille.Pont)matriceGrille[xIle][y]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);
                    }*/
                }
                break;
            
            case DROITE:
                for(int x = xIle + 1; x < taille; x++)
                {
                    if(matriceGrille[x][yIle] instanceof Ile)
                    {/*
                        if(((Application.BackEnd.Grille.Ile) matriceGrille[x][yIle]).estComplete()){
                            return(false);
                        }*/
                        return(true);
                    }/*
                    if(matriceGrille[x][yIle].getClass() == Application.BackEnd.Grille.Pont.class)
                    {
                        if( ((Application.BackEnd.Grille.Pont)matriceGrille[x][yIle]).getIle1().equals(ileOrigine) ||  ((Application.BackEnd.Grille.Pont)matriceGrille[x][yIle]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);
                    }*/
                }
                break;
        }

        return(false);
    }


    /**
        Méthode qui retourne une île qui n'a qu'un voisin
    */
    static Ile ileUnVoisin(Grille uneGrille)
    {
        /**
            On parcourt la liste des objets de la matrice de la grille
            Quand on trouve une île qui n'a qu'un voisin on change sa couleur
        */
        for(Object obj: uneGrille.getMatriceGrille())
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
                        return((Ile)obj);
                    }
                }
            }
        }
        /**
            N'arrive jamais car on vérifie qu'il y a au moins une île à un seul voisin libre sur la grille avant d'appeler cette méthode 
        */
        return(null);
    }


    /**
        Méthode qui cherche si une île a deux îles voisines dont une île qui n'acceuille qu'un seul pont au maximum
        Retourne un booléen
            --> Vrai si une île existe dans cette configuration
            --> Faux sinon
    */
    static boolean ileDeuxVoisinsDontUnUn(Grille uneGrille)
    {
        /**
            On parcourt la grille  
            Si l'object parcouru est une île alors on regarde son nombre de voisins
        */
        for(Object obj: uneGrille.getMatriceGrille())
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
    static ArrayList<Ile> listeIlesVoisines(Ile ileOrigine, Grille uneGrille){
        ArrayList<Ile> listeIlesVois = new ArrayList<Ile>();

        int xIle = ileOrigine.getAbs();
        int yIle = ileOrigine.getOrd();
        int tailleGrille = uneGrille.getTaille();

        /** 
            On parcourt les 4 directions pour récupérer les îles valables
        */
        for(int direction: listeDirections)
        {
            if(parcoursGrille(xIle, yIle, direction, tailleGrille, uneGrille.getMatriceGrille()))
            {
                /**
                    Si dans une direction il y a une île on récupère l'île de la direction parcourue
                */
                listeIlesVois.add( recupIleGrille(xIle, yIle, direction, tailleGrille, uneGrille.getMatriceGrille()) );
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
    static Ile recupIleGrille(int xIle, int yIle, int direction, int taille, Element [][] matriceGrille){
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
                    if(matriceGrille[xIle][y] instanceof Ile)
                    {
                        return((Ile)matriceGrille[xIle][y]);
                    }
                }
                break;
            
            case GAUCHE:
                for(int x = xIle - 1; x >= 0; x--)
                {
                    if(matriceGrille[x][yIle] instanceof Ile)
                    {
                        return((Ile)matriceGrille[x][yIle]);
                    }
                }
                break;
            
            case BAS:
                for(int y = yIle + 1; y <= taille; y++)
                {
                    if(matriceGrille[xIle][y] instanceof Ile)
                    {
                        return((Ile)matriceGrille[xIle][y]);
                    }
                }
                break;
            
            case DROITE:
                for(int x = xIle + 1; x <= taille; x++)
                {
                    if(matriceGrille[x][yIle] instanceof Ile)
                    {
                        return((Ile)matriceGrille[x][yIle]);
                    }
                }
                break;
        }

        return(null);
    }

    /**
        Méthode qui cherche les îles voisines auxquelles une île peut se connecter
    */
    static ArrayList<Ile> trouverVoisins(int abs, int ord, Grille uneGrille)
    {
        ArrayList<Ile> voisins = new ArrayList<Ile>();
        int tailleGrille = uneGrille.getTaille();
        Element [][] matrice = uneGrille.getMatriceGrille();

        /** 
            On parcourt les 4 directions pour récupérer les îles valables
        */
        for(int direction: listeDirections)
        {
            if(parcoursGrille(abs, ord, direction, tailleGrille, matrice))
            {
                /**
                    Si dans une direction il y a une île on récupère l'île de la direction parcourue
                */
                voisins.add( recupIleGrille(abs, ord, direction, tailleGrille, matrice) );
            }
        }

        return(voisins);
    }

    /**
        Méthode qui retourne une technique applicable sur la grille
        Si aucune technique n'est applicable alors on retourne une technique qui indique que la grille actuelle ne permet pas d'appliquer de techniques simples
    */
    
    static Technique trouverTechniqueGrille(Grille uneGrille)
    {
        ArrayList<Ile> voisins = new ArrayList<Ile>();
        Element[][] matrice = uneGrille.getMatriceGrille();
        Element elem;

        Technique t = new Technique();
        
        /**
            On parcourt toutes les cases de la grille une à une
        */
        for(int i = 0; i < uneGrille.getTaille(); i++)
        {
            for(int j = 0; j < uneGrille.getTaille(); j++)
            {
                
                elem = matrice[i][j];
                /** 
                    On vérifie que la case courrante du parcours est une île avant d'effectuer une recherche à partir des coordonnées de celle-ci
                */
                if(elem instanceof Ile)
                {

                    System.out.println("Durant la recherche d'une technique nous avons affaire à une îles aux coordonnées :"+i+","+j);
                    /**
                        On vérifie que l'île peut encore accepter au moins un pont sinon on considère l'île comme complète
                        On ne réalise pas de recherche à partir de celle-ci
                    */
                    if(!((Ile)elem).estComplete())
                    {

                        System.out.println("L'île aux coordonnées ("+i+","+j+") n'est pas complète.");

                        voisins = Technique.trouverVoisins(i, j, uneGrille);

                        switch(voisins.size())
                        {
                            case 1:
                                // on retourne la technique seulement si elle existe, on ne retourne pas une technique null

                                System.out.println("L'île aux coordonnées ("+i+","+j+") a un voisin");
                                t = Technique.unVoisinBis((Ile) elem, voisins, uneGrille);
                                
                                if(t != null) return(t);
                                
                                break;




                                /**
                                
                                
                                
                                    On parcourt la grille jusqu'à trouver une île où une bordure
                                    On ne prend plus en compte la présence de ponts 
                                    Les vérifications entre l'île et ses voisines seront faites après
                                    Le but est de na pas fausser le "début" de la technique
                                    Par exemple: 
                                    si une île a au début de la partie 3 voisines atteignables mais qu'à cause des ponts créés par le joueur elle n'en ai plus qu'une
                                    on ne va pas détecter qu'elle n'a qu'une seule voisine quand on cherchera les techniques appliquables à cette île car ça serait très probablement inadaptée
                                
                                
                                 */




                                
                            case 2:
                                System.out.println("L'île aux coordonnées ("+i+","+j+") a deux voisins");
                                t = Technique.deuxVoisinsBis((Ile) elem, voisins, uneGrille);

                                if(t != null) return t;

                                break;
                                //return(Application.BackEnd.Technique.Technique.deuxVoisins(voisins));
                                
                            case 3:
                                System.out.println("L'île aux coordonnées ("+i+","+j+") a trois voisins");
                                t = Technique.troisVoisinsBis((Ile) elem, voisins, uneGrille);
                                
                                if(t != null) return t;

                                break;
                                //return(Application.BackEnd.Technique.Technique.troisVoisins(voisins));
                                
                            case 4:
                                System.out.println("L'île aux coordonnées ("+i+","+j+") a quatre voisins");
                                t = Technique.quatreVoisinsBis((Ile) elem, voisins, uneGrille);

                                if(t != null) return t;

                                break;
                                //return(Application.BackEnd.Technique.Technique.quatreVoisins(voisins));
                            default:
                                break;
                        }
                    }
                    else
                    {
                        System.out.println("L'île aux coordonnées ("+i+","+j+") est complète");
                        System.out.println("L'île accepte "+((Ile)elem).getNum()+" ponts");
                        System.out.println("L'île a déjà "+((Ile)elem).getNbPonts()+" ponts");
                    }
                    System.out.println("\n\n");
                }
            }

        }
        /**
            Si aucune technique n'a été détectée sur l'ensemble de la grille
            On retourne une technique indiquant que la grille actuelle ne permet pas d'appliquer une quelconque technique
        */

        //return(Application.BackEnd.Technique.Technique.aucuneTechnique());

        //temporaire pour l'instant
        return(null);
    }
    /** 
        Méthode qui copie une matrice d'élément
    */

    static Element[][] copierGrille(Grille uneGrille)
    {
        int taille = uneGrille.getTaille();

        Element[][] m = new Element[taille][taille];

        Element[][] mOrigine = uneGrille.getMatriceGrille();

        for(int i = 0; i < taille; i++)
        {
            m[i] =  Arrays.copyOf(mOrigine[i], taille);
        }

        return m;
    }


    /** 
        Méthode qui retourne une tecnique appliquable sur la grille
        On tente de bloquer une direction d'un pont pour développer un réseau correcte
    */
    static Technique bloquagePont(Grille uneGrille)
    {
        ArrayList<Ile> voisins = new ArrayList<Ile>();
        Element[][] matrice = uneGrille.getMatriceGrille();
        Element[][] matriceBis;


        Element elem;

        int taille = uneGrille.getTaille();

        Technique t = new Technique();

        for(int i = 0; i < uneGrille.getTaille(); i++)
        {
            for(int j = 0; j < uneGrille.getTaille(); j++)
            {
                /** on récupère l'élément i,j */
                elem = matrice[i][j];

                if(elem instanceof Ile)
                {
                    /** 
                        si l'élément est une île et qu'elle n'est pas complète
                        on va copier la matrice
                    
                    */
                    if(!((Ile)elem).estComplete())
                    {
                        /** 
                            On récupère les voisins
                        */
                        voisins = Technique.trouverVoisins(i, j, uneGrille);

                        /** 
                            On doit vérifier qu'il y a au moins un voisin atteignable
                            La méthode retourne false s'il n'y a pas de voisins atteignables ou pas de voisins du tout
                        */

                        if(!uneIleAccessible((Ile)elem, voisins, uneGrille)){}
                        else
                        {
                            /** 
                                Matrice que l'on va modifier
                            */
                            matriceBis = Technique.copierGrille(uneGrille);

                            /** 
                                On va désormais modifier le comportement en fonction du nombre de voisins
                            */

                            switch(voisins.size())
                            {   
                                /** 
                                    Il faut qu'on parcourt les 4 directions
                                        ==> S'il n'y a pas d'île atteignable dans la direction on ne fait rien
                                        ==> Sinon on tente de créer un réseau correcte
                                */
                                case 2:
                                    /** On réalise un parcours récursif afin de savoir si on peut créer*/

                                    /** On ne peut pas bloquer une île voisine si le nombre de ponts qu'accepent l'île est > 2*/

                                    if(((Ile)elem).getNum() > 2){
                                        t  = Technique.parcoursBloquageRecursif((Ile)elem, null, matriceBis, taille, voisins);

                                        if(t != null) return t;
                                    }

                                    break;
                                case 3:

                                    /** On ne peut pas bloquer une île voisine si le nombre de ponts qu'accepent l'île est > 4*/

                                    if(((Ile)elem).getNum() > 4){
                                        t  = Technique.parcoursBloquageRecursif((Ile)elem, null, matriceBis, taille, voisins);

                                        if(t != null) return t;
                                    }

                                    break;
                                case 4:

                                    if(((Ile)elem).getNum() > 6){
                                        t  = Technique.parcoursBloquageRecursif((Ile)elem, null, matriceBis, taille, voisins);

                                        if(t != null) return t;
                                    }
                                    break;
                            }
                        }


                    }


                }
            }
        }

        return t;
    }

    /**
        Méthode récursive qui parcourt une matrice d'élément afin de regarder s'il est possible de relier toutes les îles 
        La particularité de cette méthode est qu'au premier appel de celle-ci on décide de bloquer une direction
    */
    static Technique parcoursBloquageRecursif(Ile ileCour, Ile ileOrigine, Element[][] matrice, int taille, ArrayList<Ile> voisins)
    {
        Technique t = new Technique();
        Element[][] matriceBis;

        /* Maintenant on a une fonction qui nous permet d'obtenir les voisins d'une île*/
        /* On bloque les voisins un par un */

        // Premier appel de la méthode donc on doit bloquer les voisins un à un
        if(ileOrigine == null)
        {
            for(Ile i: voisins)
            {
                /** 
                
                
                    Il faut prendre en compte que certaines iles ne peuvent pas être rejointe !!!!!!
                
                
                */






                // On regarde si avec le reste de mes voisins je peux créer un réseau stable

                //if(matriceBis = simulationReseau(matrice, ileCour, voisins, i) != null){
                    // Si le réseau est stable en ayant bloqué le chemin vers l'autre île on appelle récursivement la méthode sur les autres îles
                    
                    //t = parcoursBloquageRecursif()
                }
            //}
        }

        for(Ile i: voisins)
        {

        }
        return null;
    }

    /**
        Méthode qui retoure ue matrice d'élément
        Elle simule l'ajout de pont à partir d'une île vers ses voisins
        Un des voisins n'est pas relié volontairement(il est passé e paramètres)
        Retourne null si la configuration simulée n'est pas viable
    */
    static Element[][] simulationReseau(Element[][] m, Ile ileCour, ArrayList<Ile>voisins, Ile ileBloquee)
    {
        return null;
    }

    /** 
        Méthode qui retourne si toutes les îles de la liste sont accessibles à partir de l'île d'origine
        On ne vérifie pas si elles sont complètes, on vérifie seulement s'il existe un pont entre les 2
    */
    static boolean uneIleAccessible(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {
        for(Ile i: voisins)
        {
            if(Technique.verifCreationPont(ileOrigine, i, uneGrille))
            {
                return(true);
            }
        }
        return(false);
    }

    public static void main(String[] args){
        
        Color c = new Color(0,0,255);

        Grille grilleTest = new Grille();
        
        System.out.println("On affiche la grille");

        System.out.println(grilleTest);

        if(Technique.trouverTechniqueGrille(grilleTest) == null)
        {
            System.out.println("Il n'y a pas de technique appliquable");
        }
        else
        {
            System.out.println("Il y a bien une technique appliquable");
        }

        /**
            On ajoute des îles pour regarder s'il y a des techniques appliquables
        */

        System.out.println("On ajoute des îles");

        Ile ile1 = new Ile(1,1,0,0,c);
        Ile ile2 = new Ile(2,2,0,2,c);
        Ile ile3 = new Ile(3,2,0,9,c);
        Ile ile4 = new Ile(4,2,3,2,c);
        Ile ile5 = new Ile(5,2,3,9,c);
        grilleTest.ajouterIle(ile1);
        grilleTest.ajouterIle(ile2);
        grilleTest.ajouterIle(ile3);
        grilleTest.ajouterIle(ile4);
        grilleTest.ajouterIle(ile5);

        System.out.println("On affiche la grille");
        
        System.out.println(grilleTest);
        
        Technique t = new Technique();

        if(Technique.trouverTechniqueGrille(grilleTest) == null)
        {
            System.out.println("Il n'y a pas de technique appliquable après ajout des îles");
        }
        else
        {
            System.out.println("Il y a bien une technique appliquable après ajout des îles");
        }
        /* 

        // vérification de la réussie de la copie de la matrice d'éléments

        Application.BackEnd.Grille.Element[][] m = Application.BackEnd.Technique.Technique.copierGrille(grilleTest);

        System.out.println(grilleTest);
        
        int i, j;
        String s = "";
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                s += m[i][j].toString() + " ";
            }
            s += "\n";
        }

        System.out.println(s);
        */
    }
}