package Application.FrontEnd;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import Application.BackEnd.Grille.Ile;
import javafx.scene.text.*;

public class CircleHashi{
	public Text text;

	public Ile ile;
	public Circle cercle;
	public ArrayList<Line> listeLignes;

	CircleHashi(Circle cercle, Ile ile){
		this.cercle = cercle;
		this.listeLignes = new ArrayList<Line>();
		this.ile = ile;
		this.text = new Text(cercle.getCenterX()-6, cercle.getCenterY()+6, String.valueOf(ile.getNbPonts()));
		this.text.setFont(new Font(20));
		this.text.setMouseTransparent(true);
	}

	public void changerCouleurTexte(Color couleur) {
		this.text.setFill(couleur);
	}


	public boolean ligneEstDansListe(Line ligne) {
		Iterator<Line> iterator = listeLignes.iterator();
		while (iterator.hasNext()) {
			Line l = iterator.next();
			if (ligne.getStartX() == l.getStartX() && ligne.getStartY() == l.getStartY() && ligne.getEndX() == l.getEndX() && ligne.getEndY() == l.getEndY()){
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