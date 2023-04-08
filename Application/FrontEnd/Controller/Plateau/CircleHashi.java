package Application.FrontEnd.Controller.Plateau;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import Application.BackEnd.Grille.Ile;
import javafx.scene.text.*;
/**
 * Classe CircleHashi
 * Cette classe permet de créer un cercle avec un nombre à l'intérieur
 * Elle est utilisée pour représenter une île
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class  CircleHashi extends Circle{
	private Text text;
	private Ile ile;
	private List<Line> listeLignes;
	private List<Line> listeLignesHypotheseSauvegarde;

	/**
	 * Constructeur
	 * @param ile : Ile : l'île associée au cercle
	 * @param coordX : double : la coordonnée x
	 * @param coordY : double : la coordonnée y
	 * @param rayon : double : le rayon
	 * @param paint	: javafx.scene.paint.Paint : la couleur
	 */
	public CircleHashi(Ile ile, double coordX, double coordY, double rayon, javafx.scene.paint.Paint paint){
		super(coordX,coordY,rayon,paint);
		this.listeLignes = new ArrayList();
		this.listeLignesHypotheseSauvegarde = new ArrayList();
		this.ile = ile;
		this.text = new Text(this.getCenterX()-6, this.getCenterY()+6, String.valueOf(ile.getNum()));
		this.text.setFont(new Font(20));
		this.text.setMouseTransparent(true);
	}

	/**
	 * Cette méthode permet de changer la couleur du texte
	 * @param couleur : Color
	 */
	public void changerCouleurTexte(Color couleur) {
		this.text.setFill(couleur);
	}

	/**
	 * Cette méthode est utilisée pour vérifier si une ligne est dans la liste de lignes
	 * @param clique : Line : la ligne à vérifier
	 */
	public boolean ligneEstDansListe(Line ligne) {
		for (Line l : listeLignes) {
			if (ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Cette méthode retourne une ligne
	 * @param ligne : Line : la ligne à retourner
	 * @return Line
	 */
	public Line retournerLigne(Line ligne) {
		for (Line l : listeLignes) {
			if (ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY()){
				return l;
			}
		}
		return null;
	}

	/**
	 * Cette méthode retourne la ligne inverse
	 * @param ligne : Line : la ligne à retourner
	 * @return Line
	 */
	public Line retournerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		return retournerLigne(x);
	}

	/**
	 * Cette méthode ajoute une ligne à la liste de lignes
	 * @param ligne : Line : la ligne à ajouter
	 * @return void
	 */
	public void ajouterLigne(Line ligne) {
		listeLignes.add(ligne);
	}

	/**
	 * Cette méthode ajoute la ligne inverse à la liste de lignes
	 * @param ligne : Line : la ligne à ajouter
	 */
	public void ajouterLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		listeLignes.add(x);
	}


	/**
	 * Cette méthode supprime une ligne de la liste de lignes
	 * @param ligne : Line : la ligne à supprimer
	 */
	public void supprimerLigne(Line ligne) {
		listeLignes.removeIf(l -> ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY());
	}

	/**
	 * Cette méthode supprime la ligne inverse de la liste de lignes
	 * @param ligne : Line : la ligne à supprimer
	 */
	public void supprimerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		supprimerLigne(x);
	}

	/**
	 * Cette méthode sauvegarde la liste de lignes mit en hypothèse
	 */ 
	public void sauvegardeInitial() {
		listeLignesHypotheseSauvegarde.addAll(listeLignes);
	}

	/**
	 * Cette méthode permet de switcher entre la liste de lignes sauvegardée et la liste de lignes actuelle
	 * @param modeHypothese : boolean : true si on veut switcher vers la liste de lignes sauvegardée, false si on veut switcher vers la liste de lignes actuelle
	 */
	public void switchSauvegarde(boolean modeHypothese) {
		if(modeHypothese == true){
			listeLignesHypotheseSauvegarde.clear();
			listeLignesHypotheseSauvegarde.addAll(listeLignes);
			System.out.println("Retour à l'initail");
		}
		else{
			listeLignes.clear();
			listeLignes.addAll(listeLignesHypotheseSauvegarde);
			System.out.println("Validaton du mode");
		}
	}

	/**
	 * Cette méthode recupère l'instance de l'île
	 */
	public Ile getIle() {
		return ile;
	}

	/**
	 * Cette méthode recupère l'instance du texte
	 */
	public Text getText() {
		return text;
	}

}