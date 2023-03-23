import java.awt.Color;
import java.util.*;

public class Surbrillance {

    Color c = new Color(255,200,200);

    boolean pontsPotentielsActif;
    boolean reseauActif;

    /** 
     * Constructeur de Surbrillance
     */
    public Surbrillance(){

        pontsPotentielsActif = false;
        reseauActif = false;
    }

    //test git eclipse

    /** 
     * Active la surbrillance du réseau auquel appartient l'île en paramètre
     * @param grille la grille du jeu
     * @param ile l'île dont on active 
     * @return void
     */ 
    void reseau(Grille grille, Ile ile){
        if(reseauActif){
            ile.setCouleur(c);
            if(ile.getNbPonts() == 0)
                return;
            else{
                /** parcours des ponts de la grille */
                Iterator<Pont> itr_svg = grille.pileSvg.iterator();
                while(itr_svg.hasNext()){
                    Pont pont_cour = itr_svg.next();
                    /** recherche des ponts reliant l'île ciblée à d'autres îles */
                    if(pont_cour.getIle1().equals(ile)){
                        pont_cour.setCouleur(c);
                        /** 
                         * si l'île reliée à l'île ciblée n'est pas déjà en surbrillance
                         * activation de la surbrillance de son réseau 
                         */
                        if(!pont_cour.getIle2().getCouleur().equals(c)){
                            reseau(grille, pont_cour.getIle2());
                        }
                    }
                    if(pont_cour.getIle2().equals(ile)){
                        pont_cour.setCouleur(c);
                        /** 
                         * si l'île reliée à l'île ciblée n'est pas déjà en surbrillance
                         * activation de la surbrillance de son réseau 
                         */
                        if(!pont_cour.getIle1().getCouleur().equals(c)){
                            reseau(grille, pont_cour.getIle1());
                        }
                    }
                }
            }
        }
    }

    
}
