package Application.BackEnd.Technique;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import Application.BackEnd.Grille.*;
import javafx.scene.paint.Color;

public class SurbrillanceVoisins extends Surbrillance {
	/** 
     * Constructeur de SurbrillanceVoisins
	 * @param grille la grille sur laquelle on applique la surbrillance
	 * @param base couleur de base sans la surbrillance activée
	 * @param surbri couleur de la surbrillance
     */
	public SurbrillanceVoisins(Grille grille, Color base, Color surbri) {

		super(grille, base, surbri);
	}
	
	/** 
     * Active la surbrillance des voisins de l'île source
	 * @param ile île source
     * @return void
     */ 
	@Override
    public void activer(Ile ile){
		desactiver(); /* deux surbrillances des voisins ne doivent pas s'afficher en même temps */
		this.ile = ile;
		/*for(Direction d: Direction.values()){
			Ile i = ile.grille.getIleFromDirection(ile.getAbs(), ile.getOrd(), d);
			if(i != null) {
				i.setCouleur(cSurbri);
			}
		}*/
    }
	
	/** 
     * Desactive la surbrillance des voisins de l'île source
     * @return void
     */ 
	@Override
    public void desactiver(){
		if(ile != null){
			/*for(Direction d: Arrays.asList(Direction.values()){
				Ile i = grille.parcoursMatrice(ile.getAbs(), ile.getOrd(), d);
				if(i != null) {
					i.setCouleur(cBase);
				}
			}*/
		}
    }
}
