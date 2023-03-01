import java.awt.Color;

public class Surbrillance {

    Color c = new Color(255,0,0);

    boolean pontsPotentielsActif;
    boolean reseauActif;

    /*
     * Constructeur de Surbrillance
     */
    public Surbrillance(){

        pontsPotentielsActif = false;
        reseauActif = false;
    }

    /*
     * Active la surbrillance des ponts potentiels
     * @param grille la grille du jeu
     * @param ile l'île sur laquelle on veut activer la surbrillance
     * @return void
     */ 
    void reseau(Grille grille, Ile ile){
        if(reseauActif){
            ile.setCouleur(c);
            if(ile.getNbPonts() == 0)
                return;
            else{
            }

        }
    }

}
