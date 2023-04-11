package Application.BackEnd.Technique;
import javafx.scene.paint.Color;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import Application.BackEnd.Grille.*;

public abstract class Surbrillance {

    Color cSurbri ; /* Couleur de la surbrillance */
    Color cBase = Color.rgb(255,255,255); /* Couleur normale */
    Grille grille; /* Grille du jeu */
    Ile ile; /* Ile source de la surbrillance */

    /** 
     * Constructeur de Surbrillance
     * @param grille la grille sur laquelle on applique la surbrillance
     * @param base couleur de base sans la surbrillance activée
     * @param surbri couleur de la surbrillance
     */
    public Surbrillance(Grille grille, Color base, Color surbri ){
    	this.ile = null;
        this.grille = grille;
        this.cBase = base;
        this.cSurbri = surbri;
    }

    /** 
     * Active la surbrillance
     * @param i l'île source de la surbrillance
     * @return void
     */ 
    public abstract void activer(Ile i);
    
    /** 
     * Desactive la surbrillance
     * @return void
     */ 
    public abstract void desactiver();
    
}
