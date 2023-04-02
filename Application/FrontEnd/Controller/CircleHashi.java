package Application.FrontEnd.Controller;

/**
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import Application.BackEnd.Grille.Ile;
import javafx.scene.text.*;

/**
 * Classe CircleHashi
 * This class is used to create a circle with a number in it
 * It is used to represent an island
 */
public class CircleHashi extends Circle{

	/**
	 * Attributs
	 */
	public Text text;
	public Ile ile;
	public ArrayList<Line> listeLignes;

	/**
	 * Constructeur
	 * @param ile : Ile : the island
	 * @param coordX : double : the x coordinate
	 * @param coordY : double : the y coordinate
	 * @param rayon : double : the radius
	 * @param paint	: javafx.scene.paint.Paint : the color
	 */
	CircleHashi(Ile ile ,double coordX , double coordY , double rayon ,  javafx.scene.paint.Paint paint ){
		super(coordX,coordY,rayon,paint);
		this.listeLignes = new ArrayList<>();
		this.ile = ile;
		this.text = new Text(this.getCenterX()-6, this.getCenterY()+6, String.valueOf(ile.getNum()));
		this.text.setFont(new Font(20));
		this.text.setMouseTransparent(true);
	}

	/**
	 * This method is used to change the color of the text
	 * @param couleur : Color
	 */
	public void changerCouleurTexte(Color couleur) {
		this.text.setFill(couleur);
	}

	/**
	 * This method is used to check if a line is in the list of lines
	 * @param ligne : Line : the line to check
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
	 * This method returns the line
	 * @param ligne : Line : the line to reverse
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
	 * This method returns the reverse of a line
	 * @param ligne : Line : the line to reverse
	 * @return Line
	 */
	public Line retournerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		return retournerLigne(x);
	}

	/**
	 * This method is used to add a line to the list of lines
	 * @param ligne : Line : the line to add
	 */
	public void ajouterLigne(Line ligne) {
		listeLignes.add(ligne);
	}

	/**
	 * This method is used to add the reverse of a line to the list of lines
	 * @param ligne : Line : the line to add
	 */
	public void ajouterLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		listeLignes.add(x);
	}

	/**
	 * This method is used to remove a line from the list of lines
	 * @param ligne : Line : the line to remove
	 */
	public void supprimerLigne(Line ligne) {
		Iterator<Line> iter = listeLignes.iterator();
		while (iter.hasNext()) {
			Line l = iter.next();
			if (ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY()){
				iter.remove();
			}
		}
	}

	/**
	 * This method is used to remove the reverse of a line from the list of lines
	 * @param ligne : Line : the line to remove
	 */
	public void supprimerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		supprimerLigne(x);
	}

}