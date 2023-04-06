import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SurbrillanceVoisins extends Surbrillance {
	/** 
     * Constructeur de SurbrillanceVoisins
     */
	public SurbrillanceVoisins(Grille grille, Ile i) {
		super(grille, i);
	}
	
	/** 
     * Active la surbrillance des voisins de l'île source
     * @return void
     */ 
	@Override
    public void activer(){
		/*for(Direction d: Arrays.asList(Direction.values()){
			Ile i = grille.parcoursMatrice(ile.getAbs(), ile.getOrd(), d);
			if(i != null) {
				i.setCouleur(c);
			}
		} */
    }
	
	/** 
     * Desactive la surbrillance des voisins de l'île source
     * @return void
     */ 
	@Override
    public void desactiver(){
		/*for(Direction d: Arrays.asList(Direction.values()){
			Ile i = grille.parcoursMatrice(ile.getAbs(), ile.getOrd(), d);
			if(i != null) {
				i.setCouleur(couleurNormale);
			}
		}*/
    }
}
