import java.util.ArrayList;
import java.awt.Color;

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
            if(uneGrille.verifCreationPont(ileOrigine, i) != null)
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

        return( !ileDestination.estComplete() && uneGrille.verifCreationPont(ileOrigine, ileDestination) == null);
    }

    /**
        Méthode qui retourne une Technique
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
        Méthode qui retourne une Technique
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
          /*  if(obj.getClass() == Ile.class)
            {
                System.out.println("\n\n\nIl y a une île");
                if(((Ile)obj).getNum() <= 2 && !((Ile)obj).estComplete() )
                {
                    if(nbVoisins((Ile) obj, uneGrille) == 1){
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
    static boolean parcoursGrille(int xIle, int yIle, int direction, int taille, Object [][] matriceGrille){
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
                            Il ne faut pas vérifier maintenant si l'île trouvée est pleine
                            On le vérifiera plus tard 
                        */
                        /*if(((Ile) matriceGrille[xIle][y]).estComplete())
                        {
                            return(false);
                        }*/
                        /** Si l'île peut encore accepter un pont on retourne vrai*/
                        return(true);
                    }
                    /** 
                        Si au contraire il y a un pont on regarde si le pont est accueili par l'île d'origine
                    */

                    /*if(matriceGrille[xIle][y].getClass() == Pont.class)
                    {
                        //On doit regarder si une des deux îles du pont est la même que celle d'origine  

                        if( ((Pont)matriceGrille[xIle][y]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[xIle][y]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);
                    }*/
                }
                break;
            
            case GAUCHE:
                for(int x = xIle - 1; x >= 0; x--)
                {
                    if(matriceGrille[x][yIle].getClass() == Ile.class)
                    {
                        /*if(((Ile) matriceGrille[x][yIle]).estComplete())
                        {
                            return(false);
                        }*/
                        return(true);
                    }/*
                    if(matriceGrille[x][yIle].getClass() == Pont.class)
                    {
                        if( ((Pont)matriceGrille[x][yIle]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[x][yIle]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);                    
                    }*/
                }
                break;
            
            case BAS:
                for(int y = yIle + 1; y < taille; y++)
                {
                    if(matriceGrille[xIle][y].getClass() == Ile.class)
                    {
                        /*
                        if(((Ile) matriceGrille[xIle][y]).estComplete())
                        {
                            return(false);
                        }*/
                        return(true);
                    }/*
                    if(matriceGrille[xIle][y].getClass() == Pont.class)
                    {
                        if( ((Pont)matriceGrille[xIle][y]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[xIle][y]).getIle2().equals(ileOrigine) ){
                            return(true);
                        }
                        return(false);
                    }*/
                }
                break;
            
            case DROITE:
                for(int x = xIle + 1; x < taille; x++)
                {
                    if(matriceGrille[x][yIle].getClass() == Ile.class)
                    {/*
                        if(((Ile) matriceGrille[x][yIle]).estComplete()){
                            return(false);
                        }*/
                        return(true);
                    }/*
                    if(matriceGrille[x][yIle].getClass() == Pont.class)
                    {
                        if( ((Pont)matriceGrille[x][yIle]).getIle1().equals(ileOrigine) ||  ((Pont)matriceGrille[x][yIle]).getIle2().equals(ileOrigine) ){
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
    static ArrayList<Ile> listeIlesVoisines(Ile ileOrigine, Grille uneGrille)
    {
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
    static Ile recupIleGrille(int xIle, int yIle, int direction, int taille, Object [][] matriceGrille){
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

    /**
        Méthode qui cherche les îles voisines auxquelles une île peut se connecter
    */
    ArrayList<Ile> trouverVoisins(int abs, int ord, Grille uneGrille)
    {
        ArrayList<Ile> voisins = new ArrayList<Ile>();
        int tailleGrille = uneGrille.getTaille();

        /** 
            On parcourt les 4 directions pour récupérer les îles valables
        */
        for(int direction: listeDirections)
        {
            if(parcoursGrille(abs, ord, direction, tailleGrille, uneGrille.getMatriceGrille()))
            {
                /**
                    Si dans une direction il y a une île on récupère l'île de la direction parcourue
                */
                voisins.add( recupIleGrille(abs, ord, direction, tailleGrille, uneGrille.getMatriceGrille()) );
            }
        }

        return(voisins);
    }

    /**
        Méthode qui retourne une technique applicable sur la grille
        Si aucune technique n'est applicable alors on retourne une technique qui indique que la grille actuelle ne permet pas d'appliquer de techniques
    */
    
    Technique trouverTechniqueGrille(Grille uneGrille)
    {
        ArrayList<Ile> voisins = new ArrayList<Ile>();
        Object[][] matrice = uneGrille.getMatriceGrille();
        Object obj;

        Technique t = new Technique();
        
        /**
            On parcourt toutes les cases de la grille une à une

        */
        for(int i = 0; i < uneGrille.getTaille(); i++)
        {
            for(int j = 0; j < uneGrille.getTaille(); j++)
            {
                
                obj = matrice[i][j];
                /** 
                    On vérifie que la case courrante du parcours est une île avant d'effectuer une recherche à partir des coordonnées de celle-ci
                */
                if(obj.getClass() == Ile.class)
                {
                    /**
                        On vérifie que l'île peut encore accepter au moins un pont sinon on considère l'île comme complète
                        On ne réalise pas de recherche à partir de celle-ci
                    */
                    if(!((Ile)obj).estComplete())
                    {
                        voisins = trouverVoisins(i, j, uneGrille);

                        switch(voisins.size())
                        {
                            case 1:
                                // on retourne la technique seulement si elle existe, on ne retourne pas une technique null

                                /*
                                t = Technique.unVoisinBis((Ile)obj, voisins, uneGrille);
                                if(t != null)
                                {
                                    return(t);
                                }
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
                                //return(Technique.deuxVoisins(voisins));
                                
                            case 3:
                                //return(Technique.troisVoisins(voisins));
                                
                            case 4:
                                //return(Technique.quatreVoisins(voisins));
                            default:
                        }
                    }
                }
            }
        }
        /**
            Si aucune technique n'a été détectée sur l'ensemble de la grille
            On retourne une technique indiquant que la grille actuelle ne permet pas d'appliquer une quelconque technique
        */

        //return(Technique.aucuneTechnique());

        //temporaire pour l'instant
        return(null);
    }

    public static void main(String[] args){
        
        Color c = new Color(0,0,255);

        int[][] init1 = {
            {2, -1, 2, -1, 2, -1, -1, -1, -1, 4},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {4, -1, -1, -1, -1, -1, 2, -1, -1, 4},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {4, -1, 2, -1, 2, -1, 7, -1, 2, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, 1, -1, 1, -1, 4, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, 2, -1, 5, -1, 6, -1, 2, -1},
            {2, -1, -1, -1, -1, -1, -1, -1, -1, 3}

        };
        int[][] init2 = {
            {2, -1, -1, -1, 2, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, 2, -1, -1, -1, -1, -1},
            {2, -1, -1, -1, -1, -1, -1, -1, -1, 2}

        };
        Grille grilleTest = new Grille(init2);
        /*Color c = new Color(0, 0, 255);
        try {
            grilleTest.ajouterPont(new Ile(1,2,0,0,c), new Ile(2,2,0,10,c));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println(grilleTest.toString());
        /*
        try{
            Ile ile1 = new Ile(1,2,0,3,c);
            Ile ile2 = new Ile(2,2,0,9,c);
            Ile ile3 = new Ile(3,2,8,3,c);
            grilleTest.ajouterPont(ile1,ile2);
            grilleTest.ajouterPont(ile1,ile3);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(grilleTest.toString());*/
        if(Technique.unVoisin(grilleTest))
        {
            System.out.println("Il y a bien une île qui a un seul voisin");
        }
        else
        {
            System.out.println("Il n'y a pas une île qui a un seul voisin");
        }

        Technique t = new Technique();

        System.out.println("description avant modif : "+t.getDescription());
        t.setDescription("123 test");
        System.out.println("description après modif : "+t.getDescription());
        //System.out.println(t.getIleCour().toString());
    }
}