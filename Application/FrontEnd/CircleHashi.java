package Application.FrontEnd;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import Application.BackEnd.Grille.Ile;
import javafx.scene.text.*;

public class  CircleHashi extends Circle{
	public Text text;

	public Ile ile;
	public ArrayList<Line> listeLignes;

	CircleHashi(Ile ile ,double coordX , double coordY , double rayon ,  javafx.scene.paint.Paint paint ){
		super(coordX,coordY,rayon,paint);
		this.listeLignes = new ArrayList<Line>();
		this.ile = ile;
		this.text = new Text(this.getCenterX()-6, this.getCenterY()+6, String.valueOf(ile.getNum()));
		this.text.setFont(new Font(20));
		this.text.setMouseTransparent(true);
	}

	public void changerCouleurTexte(Color couleur) {
		this.text.setFill(couleur);
	}


	public boolean ligneEstDansListe(Line ligne) {
		for (Line l : listeLignes) {
			if (ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY()) {
				return true;
			}
		}
		return false;
	}


	public Line retournerLigne(Line ligne) {
		for (Line l : listeLignes) {
			if (ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY()){
				return l;
			}
		}
		return null;
	}

	public Line retournerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		return retournerLigne(x);
	}

	public void ajouterLigne(Line ligne) {
		listeLignes.add(ligne);
	}

	public void ajouterLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		listeLignes.add(x);
	}

	public void supprimerLigne(Line ligne) {
		Iterator<Line> iter = listeLignes.iterator();
		while (iter.hasNext()) {
			Line l = iter.next();
			if (ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY()){
				iter.remove();
			}
		}
	}

	public void supprimerLigneInverse(Line ligne) {
		Line x = new Line(ligne.getEndX(), ligne.getEndY(), ligne.getStartX(), ligne.getStartY());
		supprimerLigne(x);
	}

}