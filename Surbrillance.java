import java.awt.Color;

public class Surbrillance {

    Color c = new Color(255,0,0);

    boolean pontsPotentielsActif;
    boolean reseauActif;

    public Surbrillance(){

        pontsPotentielsActif = false;
        reseauActif = false;
    }

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
