package Application.BackEnd.Technique;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import Application.BackEnd.Grille.*;

public class SurbrillanceVoisins extends Surbrillance {
	/** 
     * Constructeur de SurbrillanceVoisins
     */
	public SurbrillanceVoisins(Grille grille) {
		super(grille);
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
				i.setCouleur(c);
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
					i.setCouleur(couleurNormale);
				}
			}*/
		}
    }
}
