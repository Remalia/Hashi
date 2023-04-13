package Application.BackEnd.Technique;

import Application.BackEnd.Grille.*;

import java.util.ArrayList;

import javafx.geometry.Orientation;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

import java.util.*;


public class Technique{
    
    private String description;
    private Ile ileCour;

    /**
     * Constructeur de la classe Technique
     */
    public Technique()
    {
        this.setDescription("");
        this.setIleCour(null);
    }

    /**
     * Modifie la description d'une technique
     * @param description description de la technique
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Modifie l'île où est applicable une technique
     * @param ile île où est applicable la technique
     */
    public void setIleCour(Ile ile)
    {
        this.ileCour = ile;
    }

    /**
     * Retourne la description d'une technique
     * @return description d'une technique
     */
    public String getDescription()
    {
        return(this.description);
    }

    /**
     * Retourne l'île où une technique est applicable
     * @return île où une technique est applicable
     */
    public Ile getIleCour()
    {
        return(this.ileCour);
    }

    /**
     * Vérifie si une île qui n'a qu'un voisin est rejoignable
     * @param ileOrigine île dont le pont part
     * @param ileDestination île où le pont arrive
     * @param uneGrille grille à laquelle appartiennent les îles
     * @return vrai si les deux îles sont rejoignables, faux sinon
     */
    public boolean unVoisinRejoignable(Ile ileOrigine, Ile ileDestination, Grille uneGrille)
    {
        switch(ileOrigine.getNum())
        {
            case 1:
                return ajoutPontSimple(ileOrigine, ileDestination, uneGrille);
            case 2:
                return ajoutPontDouble(ileOrigine, ileDestination, uneGrille);
        }
        return false;
    }

    /**
     * Vérifie s'il est possible d'ajouter un pont simple entre deux îles
     * @param ileDest île d'où part le pont
     * @param ileOrigine île où arrive le pont
     * @param uneGrille grille auxquelles appartiennent les îles
     * @return vrai si c'est possible, faux sinon
     */
    public boolean ajoutPontSimple(Ile ileDest, Ile ileOrigine, Grille uneGrille)
    {
        // On cherche si un pont existe entre les deux îles passées en paramètres
        Pont p = uneGrille.chercherPont(ileDest, ileOrigine);

        // Si le pont existe on regarde si on peut le rendre simple
        if(p != null)
        {
            if(uneGrille.collisionCreationPont(p))
            {
                return false;
            }

            int valeurPont = p.getNbPont();
            switch(valeurPont)
            {
                case 0:
                    return(!ileDest.estComplete() && !ileOrigine.estComplete());

                case 1:
                    // Il y a déjà un pont qui existe on considère qu'on peut artficiellement l'ajouter
                    return true;

                case 2:
                    // on peut transformer un pont double en pont simple
                    return true;
            }
        }

        else{
            if(!ileDest.estComplete() && !ileOrigine.estComplete())
            {
                // il faut regarder si le pont entre les 2 îles doit être horizontal ou vertical
                // On regarde via les coordonnnées
                if(uneGrille.getOrientationFrom2Iles(ileOrigine,ileDest) == Orientation.HORIZONTAL)
                {
                    return uneGrille.collisionCreationPont(new PontHorizontal(ileDest, ileOrigine));
                }
                else
                {
                    return uneGrille.collisionCreationPont(new PontVertical(ileDest, ileOrigine));
                }
            }
        }
        return false;
    }

    /**
     * Vérifie s'il est possible d'ajouter un pont double entre deux îles
     * @param ileDest île d'où part le pont
     * @param ileOrigine île où arrive le pont
     * @param uneGrille grille auxquelles appartiennent les îles
     * @return vrai s'il est possible, faux sinon
     */
    public boolean ajoutPontDouble(Ile ileDest, Ile ileOrigine, Grille uneGrille)
    {
        // On cherche si un pont existe entre les deux îles passées en paramètres
        Pont p = uneGrille.chercherPont(ileDest, ileOrigine);

        // Si le pont existe on regarde si on peut le rendre double
        if(p != null)
        {
            if(uneGrille.collisionCreationPont(p))
            {
                return false;
            }

            int valeurPont = p.getNbPont();
            switch(valeurPont)
            {
                case 0:
                    // On regarde si les 2 îles peuvent accepter un pont
                    return( (ileDest.getNum() - ileDest.sommeValPont()) >= 2 && (ileOrigine.getNum() - ileOrigine.sommeValPont()) >= 2);

                case 1:
                    // On regarde si on peut transformer le pont simple en pont double ==> vérifier si les îles sont complètes
                    return(!ileDest.estComplete() && !ileOrigine.estComplete());

                case 2:
                    // pont double déjà existant --> Vrai
                    return true;
            }
        }
        // Si le pont n'existe pas on regarde si on peut le créer
        else
        {
            if((ileDest.getNum() - ileDest.sommeValPont()) >= 2 && (ileOrigine.getNum() - ileOrigine.sommeValPont()) >= 2)
            {
                if(uneGrille.getOrientationFrom2Iles(ileOrigine, ileDest) == Orientation.HORIZONTAL)
                {
                    return uneGrille.collisionCreationPont(new PontHorizontal(ileOrigine, ileDest));
                }
                else
                {
                    return uneGrille.collisionCreationPont(new PontVertical(ileOrigine, ileDest));
                }
            }
        }

        return false;
    }

    /**
     * Vérifie si pour les îles d'une grille il est possible de trouver une technique simple
     * @param listeIles listes des îles de la grille, celles-ci sont mélangées
     * @param uneGrille grille à laquelle appartient la liste des îles
     * @param nbVoisins nombre des voisins qui nous intéresse (1 à 4)
     * @return une technique simple applicable sur la grille ou null s'il n'en existe pas
     */
    public Technique checkVoisins(ArrayList<Ile> listeIles, Grille uneGrille, int nbVoisins)
    {
        Technique t;

        for(Ile i: listeIles)
        {
            // On vérifie que l'île n'est pas complète sinon on ne peut plus ajouter de ponts donc pas de technique applicable
            if(i.getNbVoisins() == nbVoisins && !i.estComplete())
            {
                switch(nbVoisins)
                {
                    case 1:
                        if( (t = unVoisin(i, i.getIlesVoisines(), uneGrille)) != null) return t;
                        break;
                    case 2:
                        if( (t = deuxVoisins(i, i.getIlesVoisines(), uneGrille)) != null) return t;
                        break;
                    case 3:
                        if( (t = troisVoisins(i, i.getIlesVoisines(), uneGrille)) != null) return t;
                        break;
                    case 4:
                        if( (t = quatreVoisins(i, i.getIlesVoisines(), uneGrille)) != null) return t;
                        break;
                }
            }
        }

        return null;
    }


    /**
     * Technique qui vérifie si une île qui n'a qu'un voisin peut appliquer une technique
     * @param ileOrigine île où on cherche une technique
     * @param voisins îles voisines
     * @param uneGrille grille de l'île
     * @return technique applicable si elle existe, null sinon
     */
    public Technique unVoisin(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {
        Technique t = new Technique();

        /*
            On récupère l'unique île voisine de la liste
        */
        Ile premVois = voisins.get(0);

        /*
            On vérifie juste que l'île d'arrivée est libre et qu'il n'y a pas de ponts entre les 2 îles
        */
        if(unVoisinRejoignable(ileOrigine, premVois, uneGrille))
        {
            t.setDescription("Il y a une île qui n'a qu'un seul voisin, vous devriez les rejoindre !");
            t.setIleCour(ileOrigine);
            return(t);
        }

        return(null);
    }

    /**
     * Technique qui vérifie si une île qui a deux voisins peut appliquer une technique
     * @param ileOrigine île où on cherche une technique
     * @param voisins îles voisines
     * @param uneGrille grille de l'île
     * @return technique applicable si elle existe, null sinon
     */
    public Technique deuxVoisins(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {
        Technique t = new Technique();

        /*
            On récupère les îles voisines
        */

        Ile premVois = voisins.get(0);
        Ile scdVois = voisins.get(1);

        /*
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
                if( (premVois.getNum() == 1 && !premVois.estComplete() && ajoutPontSimple(scdVois, ileOrigine, uneGrille) && scdVois.getNum() >= 2) || (scdVois.getNum() == 1 && !scdVois.estComplete() && ajoutPontSimple(premVois, ileOrigine, uneGrille) && premVois.getNum() >= 2))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui ne peut créer qu'un pont au maximum. Cependant un de ses voisins ne peut accepter qu'un pont, il faut donc la relier à l'autre île par un pont simple.");
                    return t;
                }
                break;
            case 2:
                if( (premVois.getNum() == 1 && premVois.getNbVoisins() == 1) || (scdVois.getNum() == 1 && scdVois.getNbVoisins() == 1) )
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer deux ponts au maximum. Cependant un de ses voisins (qui a pour unique voisin l'île en question) ne peut accepter qu'un pont au maximum, il faut donc relier l'île aux 2 autres îles par un pont simple");
                    return t;
                }
                else if( (premVois.getNum() == 1 && ajoutPontSimple(scdVois,ileOrigine, uneGrille)) || (scdVois.getNum() == 1 && ajoutPontSimple(premVois, ileOrigine, uneGrille)) )
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer deux ponts au maximum. Cependant un de ses voisins ne peut accepter qu'un pont au maximum, il faut donc relier l'île à l'autre île par un pont simple");
                    return t;
                }
                break;
            case 3:
                if(ajoutPontSimple(premVois, ileOrigine, uneGrille) && ajoutPontSimple(scdVois, ileOrigine, uneGrille))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement deux voisins qui peut créer trois ponts au maximum. Il faut donc relier cette île à ses 2 voisines par des ponts simples.");
                    return t;
                }
                else
                {
                    System.out.println("Pas de technique à 2 voisins et 3 ponts");
                    if(ajoutPontSimple(premVois, ileOrigine, uneGrille))
                    {
                        System.out.println("La première île est accessible");
                    }
                    if(ajoutPontSimple(scdVois, ileOrigine, uneGrille))
                    {
                        System.out.println("La seconde île est accessible");
                    }
                }
                break;
            case 4:
                if(ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille))
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
     * Technique qui vérifie si une île qui a trois voisins peut appliquer une technique
     * @param ileOrigine île où on cherche une technique
     * @param voisins îles voisines
     * @param uneGrille grille de l'île
     * @return technique applicable si elle existe, null sinon
     */
    public Technique troisVoisins(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {

        Technique t = new Technique();

        /*
            Il y a 3 cas possibles:
                - L'île d'origine accepte 5 ponts                                               => 1 pont avec chaque île
                - L'île d'origine accepte 5 ponts et possède un voisin qui n'accepte qu'un pont => 1 pont avec l'île qui n'en accepte qu'un + des ponts doubles avec les autres îles
                - L'île d'origine accepte 6 ponts                                               => double ponts avec chaque île
        */

        /*
            On récupère les îles voisines
        */

        Ile premVois = voisins.get(0);
        Ile scdVois = voisins.get(1);
        Ile trsmVois = voisins.get(2);


        switch(ileOrigine.getNum())
        {
            case 5:
                if((premVois.getNum() == 1 && ajoutPontSimple(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(trsmVois, ileOrigine, uneGrille)) || (scdVois.getNum() == 1 && ajoutPontSimple(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(trsmVois, ileOrigine, uneGrille))|| (trsmVois.getNum() == 1 && ajoutPontSimple(trsmVois, ileOrigine, uneGrille) && ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille)))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement trois voisins qui peut créer cinq ponts. Parmi ses trois voisins, une île n'accepte qu'un pont au maximum. L'île doit donc être reliée à cette dernière par un pont simple. L'île doit être reliée aux deux autres par des ponts double.");
                    return t;
                }
                else
                {
                    if(ajoutPontSimple(premVois, ileOrigine, uneGrille) && ajoutPontSimple(scdVois, ileOrigine, uneGrille) && ajoutPontSimple(trsmVois, ileOrigine, uneGrille))
                    {
                        t.setIleCour(ileOrigine);
                        t.setDescription("Il y a une île qui a exactement trois voisins qui peut créer cinq ponts. L'île doit donc se relier à chaque île par un pont simple minimum.");
                        return t;
                    }
                }
                break;
            case 6:
                if(ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(trsmVois, ileOrigine, uneGrille))
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
     * Technique qui vérifie si une île qui a quatre voisins peut appliquer une technique
     * @param ileOrigine île où on cherche une technique
     * @param voisins îles voisines
     * @param uneGrille grille de l'île
     * @return technique applicable si elle existe, null sinon
     */
    public Technique quatreVoisins(Ile ileOrigine, ArrayList<Ile> voisins, Grille uneGrille)
    {

        Technique t = new Technique();

        /*
            Il y a cas possibles:
                - 7 ponts                        => un pont simple avec chaque
                - 7 ponts dont un voisin avec un => un pont simple avec le un + pont double avec les autres
                - 8 ponts                        => pont double avec chaque voisin
        */

        /*
            On récupère les îles voisines
        */

        Ile premVois = voisins.get(0);
        Ile scdVois  = voisins.get(1);
        Ile trsmVois = voisins.get(2);
        Ile qtrmVois = voisins.get(3);


        switch(ileOrigine.getNum())
        {
            case 7:
                if((premVois.getNum() == 1 && ajoutPontSimple(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(trsmVois, ileOrigine, uneGrille) && ajoutPontDouble(qtrmVois, ileOrigine, uneGrille)) || (scdVois.getNum() == 1 && ajoutPontSimple(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(trsmVois, ileOrigine, uneGrille) && ajoutPontDouble(qtrmVois, ileOrigine, uneGrille)) || (trsmVois.getNum() == 1 && ajoutPontSimple(trsmVois, ileOrigine, uneGrille) && ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(qtrmVois, ileOrigine, uneGrille)) || (qtrmVois.getNum() == 1 && ajoutPontSimple(qtrmVois, ileOrigine, uneGrille) && ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(qtrmVois, ileOrigine, uneGrille)))
                {
                    t.setIleCour(ileOrigine);
                    t.setDescription("Il y a une île qui a exactement quatre voisins qui doit créer 7 ponts. Un de ses voisins ne doit créer qu'un pont au maximum. L'île doit donc rejoindre la dernière via un pont simple et les autres via des ponts double.");
                    return t;
                }
                else
                {
                    if(ajoutPontSimple(premVois, ileOrigine, uneGrille) && ajoutPontSimple(scdVois, ileOrigine, uneGrille) && ajoutPontSimple(trsmVois, ileOrigine, uneGrille) && ajoutPontSimple(qtrmVois, ileOrigine, uneGrille))
                    {
                        t.setIleCour(ileOrigine);
                        t.setDescription("Il y a une île qui a exactement quatre voisins qui doit créer 7 ponts. Elle doit donc rejoindre les autres îles via au moins un pont simple.");
                        return t;
                    }
                }
                break;
            case 8:
                if(ajoutPontDouble(premVois, ileOrigine, uneGrille) && ajoutPontDouble(scdVois, ileOrigine, uneGrille) && ajoutPontDouble(trsmVois, ileOrigine, uneGrille) && ajoutPontDouble(qtrmVois, ileOrigine, uneGrille))
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
     * Méthode qui cherche si une technique est applicable sur une grille qu'elle soit simple ou avancée
     * @param uneGrille grille où on cherche la technique
     * @return une technique
     */
    public Technique trouverTechniqueGrille(Grille uneGrille)
    {
        ArrayList<Ile> iles = uneGrille.getListIle();

        Technique t;

        //On randomize pour ne pas parcourir de haut gauche vers bas droite
        Collections.shuffle(iles);

        //Recherche d'une technique simple sur la grille
        for(int nbVoisins = 1; nbVoisins <= 4; nbVoisins += 1)
        {
            if( (t = checkVoisins(iles, uneGrille, nbVoisins)) != null) return t;
        }

        //On teste de trouver un réseau en bloquant la direction à partir d'une île
        t = bloquagePont(uneGrille);

        return t;
    }

    /**
     * Méthode qui regarde si à partir de la grille on peut trouver une île à partir de laquelle on peut trouver un réseau stable en bloquant à l'origine une direction.
     * @param uneGrille grille où l'on cherche une technique
     * @return une technique
     */
    public Technique bloquagePont(Grille uneGrille)
    {
        ArrayList<Ile> iles = uneGrille.getListIle();
        ArrayList<Ile> voisins;

        Technique t = new Technique();

        for(Ile i: iles)
        {
            // Si l'île est complète ça ne sert à rien de chercher une configuration viable à partir d'elle
            if(!i.estComplete())
            {
                voisins = i.getIlesVoisines();

                // Si l'île n'a qu'une voisine on ne peut pas bloquer une île voisine et réussir à obtenir un réseau stable
                if(voisins.size() > 1)
                {

                    // On parcourt récursivement la grille à partir de chaque île
                    if(parcoursBloquageRecursif(i, null, uneGrille, voisins))
                    {
                        t.setIleCour(i);
                        t.setDescription("On peut créer un réseau entier en bloquant une direction à partir d'une île");
                        return t;
                    }
                }
            }
        }

        // On n'a pas trouvé une île à partir de laquelle on peut créer un réseau stable
        t.setIleCour(null);
        t.setDescription("Il n'y a pas d'île à partir de laquelle on peut créer un réseau stable en bloquant une des directions");
        return t;
    }

    /**
     * Méthode récursive qui regarde s'il est possible à partir de la grille passée en paramètres de trouver une grille correcte
     * @param ileCour île pour laquelle on va simuler de nouvelles configurations
     * @param ileOrigine île voisine de ileCour, c'est l'ancienne ileCour
     * @param uneGrille grille auxquelles appartiennent les îles
     * @param voisins liste îles voisines de ileCour
     * @return vrai si la grille passée en paramètres permet de trouver une grille correcte, faux si toutes les configurations possibles à partir de la grille mènent à un échec
     */
    public boolean parcoursBloquageRecursif(Ile ileCour, Ile ileOrigine, Grille uneGrille, ArrayList<Ile> voisins)
    {

        Grille grilleBis; // copie de la grille passée en paramètres

        // Si on trouve une configuration viable on arrête la récursion
        if(uneGrille.grilleCorrecte())
        {
            return true;
        }

        // Sinon on parcourt les voisins afin de chercher une configuration viable
        for(Ile i: voisins)
        {
            // On copie la grille
            grilleBis = new Grille(uneGrille);

            /*
                Nombre de configurations possibles avec les autres voisins (n - 1 car un des voisins est l'île d'où on vient)
                Pour chaque voisin il y a 3 configurations possibles : un pont à 0, à 1 ou à 2
                Il y a donc 3 puissance (n - 1) configuration possible pour une île
            */
            int maxConfig = (int) Math.pow(3, (voisins.size() - 1));

            for(int indiceConfig = 0; indiceConfig < maxConfig; indiceConfig++)
            {
                //On vérifie que l'île testée ne soit pas celle d'origine car sinon cela reviendrait à revenir en arrière
                if(ileOrigine != null || i != ileOrigine)
                {
                    // On simule la présence d'un nouveau réseau à partir d'une certaine configuration
                    grilleBis = simulationReseau(grilleBis, ileCour, voisins, i, indiceConfig);

                    // Si le réseau est stable on continue les appels récursifs sur celui-ci
                    if(grilleBis != null)
                    {
                        if(parcoursBloquageRecursif(i, ileCour, grilleBis, i.getIlesVoisines())) return true;
                    }
                    else
                    {
                        // Si au contraire le réseau n'est pas stable on le réinitialise à partir du dernier stable
                        grilleBis = new Grille(uneGrille);
                    }
                }
            }
        }

        return false;
    }

    /**
     * Méthode qui simule une nouvelle grille
     * @param g grille d'origine
     * @param ileCour île à partir de laquelle on va créer ou modifier des ponts
     * @param voisins îles voisines de ileCour
     * @param ileBloquee île pour laquelle on ne crée pas de pont à partir ileCour
     * @param indiceConfig numéro de la configuration à créer
     * @return une grille modifiée si elle est en adéquation avec la configuration voulue, null sinon
     */
    public Grille simulationReseau(Grille g, Ile ileCour, ArrayList<Ile> voisins, Ile ileBloquee, int indiceConfig)
    {
        Pont p;

        // On compte le nombre de ponts créés pour savoir si la configuration est viable
        int nbPontsCreables = 0;

        /*
        On sauvegarde le numéro de l'itération
        Cela nous permet de savoir quelle valeur utiliser pour ajouter un pont
         */
        int valIteration = 0;

        /*
        Valeurs des ponts à partir de la configuration
         */
        int valeurPontUn    = indiceConfig % 3;
        int valeurPontDeux  = (indiceConfig % 9) /3;
        int valeurPontTrois = indiceConfig / 9;

        // On parcourt les voisins pour ajouter les ponts
        for(Ile i: voisins)
        {
            // Si c'est l'île bloquée il n'y a pas de traitement
            if(ileBloquee != i)
            {
                // On regarde la valeur à utiliser
                switch(valIteration)
                {
                    case 0:
                        /*
                        On cherche un pont existant, s'il n'existe pas on vérifie si on peut l'ajouter
                        Si c'est le cas on l'ajoute à la grille
                         */
                        if((p = g.chercherPont(i, ileCour)) != null)
                        {
                            if(simulationPont(valeurPontUn, ileCour, i, g))
                            {
                                g.ajouterPont(ileCour, i, valeurPontUn);
                                // On tient à jour le nombre de ponts créés
                                nbPontsCreables += valeurPontUn;
                            }
                        }
                        else
                        {
                            //Si la valeur existante du pont est différente de la valeur théorique du pont dans la configuration cela signifie que la configuration n'est pas viable
                            if(p.getNbPont() != valeurPontUn) return null;
                            //Si le pont est correcte dans la configuration on ajoute sa valeur au nombre de ponts créés
                            nbPontsCreables += p.getNbPont();
                        }
                        break;
                    case 1:
                        if((p = g.chercherPont(i, ileCour)) != null)
                        {
                            if(simulationPont(valeurPontDeux, ileCour, i, g))
                            {
                                g.ajouterPont(ileCour, i, valeurPontDeux);
                                nbPontsCreables += valeurPontDeux;
                            }
                        }
                        else
                        {
                            if(p.getNbPont() != valeurPontDeux) return null;
                            nbPontsCreables += p.getNbPont();
                        }
                        break;
                    case 2:
                        if((p = g.chercherPont(i, ileCour)) != null)
                        {
                            if(simulationPont(valeurPontTrois, ileCour, i, g))
                            {
                                g.ajouterPont(ileCour, i, valeurPontTrois);
                                nbPontsCreables += valeurPontTrois;
                            }
                        }
                        else
                        {
                            if(p.getNbPont() != valeurPontTrois) return null;
                            nbPontsCreables += p.getNbPont();
                        }
                        break;
                }

                valIteration += 1;
            }
        }

        /*
        Si à la fin l'île courrante a autant de ponts qu'elle doit en avoir dans la configuration alors on retourne la grille modifiée afin de continuer à partir d'elle
        Sinon on arrête le parcours de cette possibilité
         */
        if(nbPontsCreables == ileCour.getNum())
        {
            return g;
        }

        return null;
    }


    /**
     * Méthode qui vérifie si entre deux îles on peut créer un pont
     * @param valeurPont valeur du pont à créer
     * @param ileCour île d'origine du pont
     * @param ileDest île d'arrivée du pont
     * @param g grille où on cherche à ajouter le pont
     * @return vrai si on peut créer le pont, faux sinon
     */
    public boolean simulationPont(int valeurPont, Ile ileCour, Ile ileDest, Grille g)
    {
        switch(valeurPont)
        {
            case 0:
                return true;
            case 1:
                return(ajoutPontSimple(ileDest, ileCour, g));
            case 2:
                return(ajoutPontDouble(ileDest, ileCour, g));
        }

        return false;
    }
}