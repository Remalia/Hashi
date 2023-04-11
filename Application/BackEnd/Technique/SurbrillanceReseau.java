package Application.BackEnd.Technique;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Application.BackEnd.Grille.*;

public class SurbrillanceReseau extends Surbrillance implements Iterable<List<Element>>{
	
	/** 
     * Constructeur de SurbrillanceReseau
     */
	public SurbrillanceReseau(Grille grille) {
		super(grille);
	}
	
	/** 
     * Active la surbrillance du réseau auquel appartient l'île en paramètre
     * @return void
     */ 
	@Override
    public void activer(Ile ile){
		desactiver(); /* deux surbrillances de réseau ne doivent pas s'afficher en même temps */
		this.ile = ile;
		Iterator<List<Element>> itr = iterator();
		while(itr.hasNext()) {
			for(Element e: itr.next()) {
				e.toString();
				e.setCouleur(c);
			}
		}
    }
	
	/** 
     * Desactive la surbrillance du réseau auquel appartient l'île en paramètre
     * @return void
     */ 
	@Override
    public void desactiver(){
		if(ile != null) {
			Iterator<List<Element>> itr = iterator();
			while (itr.hasNext()) {
				for (Element e : itr.next()) {
					e.setCouleur(couleurNormale);
				}
			}
		}
    }

	@Override
	public String toString(){
		String s = ile.toStringConsole();
		return s;
	}

	/** Iterateur qui parcours le reseau de l'île source
	 * @return Iterateur qui parcours les ponts et îles connectées à l'île source
	 */
	public Iterator<List<Element>> iterator(){
		return new IterateurReseau();
	}

	/* Iterateur qui parcours le reseau de l'île source */
	class IterateurReseau implements Iterator<List<Element>>{

		ArrayList<Pont> pontTraverse; /* liste des ponts déjà traversés par l'itérateur */
		ArrayList<Ile> ileTraverse; /* liste des îles déjà traversées par l'itérateur */
		ArrayList<Ile> ileCour; /* liste des dernières îles atteintes par l'itérateur */

		/**
		 * Constructeur de IterateurReseau
		 */
		public IterateurReseau() {
			pontTraverse = new ArrayList<Pont>();
			ileTraverse = new ArrayList<Ile>();
			this.ileCour = new ArrayList<Ile>();
			this.ileCour.add(SurbrillanceReseau.this.ile);
		}

		/**
		 * Retourne true si les îles courantes sont reliées à des ponts
		 * non traversés par l'itérateur
		 * @return boolean
		 */
		public boolean hasNext() {
			/* récupération des ponts reliés aux îles courantes */
			List<Pont> pontIleCour = nonTraverse();
			/* si une ile courante n'a pas été traversé l'itérateur peut encore boucler */
			for(Ile i: ileCour)
				if(!ileTraverse.contains(i)){
					return true;
				}
			return false;
		}

		public List<Element> next() {
			/* récupération des ponts reliés aux îles courantes */
			List<Pont> pontIleCour = nonTraverse();
			/* retour des îles courantes et de leurs ponts non traversés */
			List<Element> retour = new ArrayList<Element>();
			retour.addAll(ileCour);
			for(Ile i: ileCour){
				if(!ileTraverse.contains(i)){
					ileTraverse.add(i);
				}
			}
			ileCour.clear();
			/* ileCour devient la liste des îles à l'autre bout des ponts non traversés */
			for(Pont p : pontIleCour) {
				/* ajout de l'île si elle n'est pas déjà dans la liste */
				if(retour.contains(p.getIle1()) && !ileCour.contains(p.getIle2()))
					ileCour.add(p.getIle2());
				else if(!ileCour.contains(p.getIle1()))
					ileCour.add(p.getIle1());
			}
			retour.addAll(pontIleCour);
			return retour;
		}

		/**
		 * Retourne les ponts non traversés des îles courantes par l'itérateur
		 * @return List<Pont> les ponts non traversés
		 */
		private List<Pont> nonTraverse() {
			/* récupération des ponts reliés aux iles courantes */
			List<Pont> pontIleCour = new ArrayList<Pont>();
			for(Ile i: ileCour) {
				for(Pont p: i.getListePont()){
					if(p.getNbPont() > 0){
						pontIleCour.add(p);
					}
				}
			}
			List<Pont> pontNonTraverse = new ArrayList<Pont>();
			/*
			 * parcours des ponts de l'île
			 * si un des ponts n'a pas déjà été traversé:
			 * le réseau n'a pas été parcouru en entier
			 */
			for(Pont pIleCour : pontIleCour) {
				if(!pontTraverse.contains(pIleCour) && !pontNonTraverse.contains(pIleCour))
					/* ajout du pont non traversé à la liste si il n'y est pas déjà*/
					pontNonTraverse.add(pIleCour);
			}
			return pontNonTraverse;
		}
	}
}