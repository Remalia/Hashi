package Application.BackEnd.Technique;
import java.util.ArrayList;
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

	public List<Integer> abscisseIle;
	public List<Integer> ordonneeIle;

	public List<Ile> listIleSurbrillance;

	public SurbrillanceVoisins(Grille grille, Color base, Color surbri) {

		super(grille, base, surbri);
		this.abscisseIle = new ArrayList<Integer>();
		this.ordonneeIle = new ArrayList<Integer>();
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
		listIleSurbrillance = ile.getIlesVoisines();
		while (!listIleSurbrillance.isEmpty()) {
			Ile i = listIleSurbrillance.remove(0);
			int abscisseIleX = i.getAbs();
			int ordonneeIleX = i.getOrd();
			i.setCouleur(cSurbri);
			abscisseIle.add(abscisseIleX);
			ordonneeIle.add(ordonneeIleX);
		}
	}

	/**
	 * Desactive la surbrillance des voisins de l'île source
	 * @return void
	 */
	@Override
	public void desactiver(){
		/*if(ile != null){
			for(Direction d: Direction.values()){
				Ile i = ile.getIleFromDirection(ile.getAbs(), ile.getOrd(), d, grille.getMatriceGrille());
				if(i != null) {
					i.setCouleur(cBase);
				}
			}
		}
		abscisseIle.clear();
		ordonneeIle.clear();*/
		// NE PAS EFFACER CE PROTOTYPE NON FONCTIONNEL
	}

}


