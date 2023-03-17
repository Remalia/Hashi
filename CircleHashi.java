package application;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CircleHashi{
	
	Circle cercle;
	ArrayList<Line> listeLignes;

	CircleHashi(Circle cercle){
		this.cercle = cercle;
		this.listeLignes = new ArrayList<Line>();
	}
	
	public boolean ligneEstDansListe(Line ligne) {
	    for (Line l : listeLignes) {
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

}
