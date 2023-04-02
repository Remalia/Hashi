package Application.FrontEnd.Controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import Application.BackEnd.Grille.Ile;
import javafx.scene.text.*;

/**
 * Classe CircleHashi qui herite de Circle
 * Cette classe permet de creer un cercle avec un texte et une ile
 *
 */
public class  CircleHashi extends Circle{
	private Text text;
	private Ile ile;
	private List<Line> listeLignes;

	/**
	 * Constructeur de la classe CircleHashi
	 * @param ile l'ile du cercle
	 * @param coordX la coordonnee x du cercle
	 * @param coordY la coordonnee y du cercle
	 * @param rayon le rayon du cercle
	 * @param paint la couleur du cercle
	 */
	CircleHashi(Ile ile ,double coordX , double coordY , double rayon ,  javafx.scene.paint.Paint paint ){
		super(coordX,coordY,rayon,paint);
		this.listeLignes = new ArrayList();
		this.ile = ile;
		this.text = new Text(this.getCenterX()-6, this.getCenterY()+6, String.valueOf(ile.getNum()));
		this.text.setFont(new Font(20));
		this.text.setMouseTransparent(true);
	}

	/**
	 * Change la couleur du texte
	 * @param couleur la nouvelle couleur du texte
	 */
	public void changerCouleurTexte(Color couleur) {
		this.text.setFill(couleur);
	}


	/**
	 * Verifie si la ligne est dans la liste de lignes
	 * @param ligne la ligne a verifier
	 * @return true si la ligne est dans la liste de lignes, false sinon
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
	 * Retourne la ligne inverse de la ligne passee en parametre
	 * @param ligne la ligne dont on veut la ligne inverse
	 * @return la ligne inverse de la ligne passee en parametre
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
	 * Retourne la ligne inverse de la ligne passee en parametre
	 * @param ligne la ligne dont on veut la ligne inverse
	 * @return la ligne inverse de la ligne passee en parametre
	 */
	public Line retournerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		return retournerLigne(x);
	}

	/**
	 * Ajoute la ligne a la liste de lignes
	 * @param ligne la ligne a ajouter
	 */
	public void ajouterLigne(Line ligne) {
		listeLignes.add(ligne);
	}

	/**
	 * Ajoute la ligne inverse a la liste de lignes
	 * @param ligne la ligne a ajouter
	 */
	public void ajouterLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		listeLignes.add(x);
	}

	/**
	 * Supprime la ligne de la liste de lignes
	 * @param ligne la ligne a supprimer
	 */
	public void supprimerLigne(Line ligne) {
		listeLignes.removeIf(l -> ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY());
	}

	/**
	 * Supprime la ligne inverse de la liste de lignes
	 * @param ligne la ligne a supprimer
	 */
	public void supprimerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		supprimerLigne(x);
	}

	// Getters et Setters
	/**
	 * @return l'ile du cercle
	 */
	public Ile getIle() {
		return ile;
	}

	/**
	 * @return le texte du cercle
	 */
	public Text getText() {
		return text;
	}

}