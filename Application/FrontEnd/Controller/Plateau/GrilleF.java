package Application.FrontEnd.Controller.Plateau;

import javafx.scene.paint.Color;

public class GrilleF {

    private int NB_CERCLES;
    private int RAYON;
    private double ESPACE;

    private CircleHashi[] cerclesHashi;

    private CircleHashi premierCercle = null;
    private CircleHashi deuxiemeCercle = null;
    private boolean premierCercleClique = false;
    private Integer indicePremierCercle;
    private Integer indiceSecondCercle;


    public int getNB_CERCLES() {
        return NB_CERCLES;
    }

    public int getRAYON() {
        return RAYON;
    }

    public double getESPACE() {
        return ESPACE;
    }

    public CircleHashi[] getCerclesHashi() {
        return cerclesHashi;
    }

    public CircleHashi getPremierCercle() {
        return premierCercle;
    }

    public CircleHashi getDeuxiemeCercle() {
        return deuxiemeCercle;
    }

    public boolean isPremierCercleClique() {
        return premierCercleClique;
    }

    public Integer getIndicePremierCercle() {
        return indicePremierCercle;
    }

    public Integer getIndiceSecondCercle() {
        return indiceSecondCercle;
    }


    public void setIndiceSecondCercle(int indice) {
        this.indiceSecondCercle = indice;
    }

    public void setDeuxiemeCercle(CircleHashi cercle) {
        this.deuxiemeCercle=cercle;
    }

    public void setPremierCercle(CircleHashi cercle) {
        this.premierCercle=cercle;
    }

    /**
     * Cette méthode permet de réinitialiser les cercles
     */
    public void reinitialiserCercles(){
        premierCercle.setFill(Color.YELLOW);
        premierCercle = null;
        deuxiemeCercle = null;
        premierCercleClique = false;
        indicePremierCercle = null;
        indiceSecondCercle = null;
    }

    /**
     * Cette méthode permet de trouver l'indice d'un cercle dans le tableau de cercles
     * @param cercle : le cercle dont on cherche l'indice
     * @return l'indice du cercle dans le tableau de cercles
     */
    public int trouverIndiceCercle(CircleHashi cercle) {
        for (int i = 0; i < cerclesHashi.length; i++) {
            if (cerclesHashi[i] != null) {
                if((cerclesHashi[i].getCenterX() == cercle.getCenterX()) && (cerclesHashi[i].getCenterY() == cercle.getCenterY())) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Cette méthode permet de vérifier si deux cercles sont sur la même ligne ou la même colonne
     * @param cercle1 : le premier cercle
     * @param cercle2 : le deuxième cercle
     * @return true si les deux cercles sont sur la même ligne ou la même colonne, false sinon
     */
    public boolean memeLigneOuColonne(CircleHashi cercle1, CircleHashi cercle2) {
        double c1x = cercle1.getCenterX();
        double c2x = cercle2.getCenterX();
        double c1y = cercle1.getCenterY();
        double c2y = cercle2.getCenterY();

        return (c1x == c2x || c1y == c2y);
    }

    public CircleHashi getVal_cercles(Integer indice){
        return cerclesHashi[indice];
    }

    public void setPremierCercleClique(boolean b) {
        this.premierCercleClique=b;
    }

    public void setIndicePremierCercle(int i) {
        this.indicePremierCercle=i;
    }

    public void setNB_CERCLES(int i) {
        this.NB_CERCLES=i;
    }

    public void setRAYON(int i) {
        this.RAYON=i;
    }

    public void setESPACE(double i) {
        this.ESPACE=i;
    }

    public void setCirclesHashi(CircleHashi[] circleHashis) {
        this.cerclesHashi=circleHashis;
    }
    public void setCircleHashi(Integer i, CircleHashi cercle){
        this.cerclesHashi[i]=cercle;
    }

    public CircleHashi getCircleHashi(int position){ return this.cerclesHashi[position]; }

}
