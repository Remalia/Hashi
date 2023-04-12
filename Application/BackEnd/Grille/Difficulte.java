package Application.BackEnd.Grille;

import java.util.ArrayList;

public class Difficulte {
    private final String nomDifficute;
    private final ArrayList<Long> tempsEtoiles;
    private final Integer nbCheck;
    private final Integer nbTech;

    /**
     * @param nom nom de la dofficulté 
     * @param tempsEtoiles list de temps pour avoir 1,2 ou 3 étoiles
     * @param nbCheck nombre de checks disponibles
     * @param nbTech nombre de techniques disponibles
     */
    private Difficulte(String nom,ArrayList<Long> tempsEtoiles,Integer nbCheck,Integer nbTech){
        this.nomDifficute = nom;
        this.tempsEtoiles = new ArrayList<>();
        this.nbCheck = nbCheck;
        this.nbTech = nbTech;
    }

    /**
     * Permet de récupérer la nom de la difficulté
     * @return Le nom de la difficulté
     */
    public String getNomDifficute() {
        return nomDifficute;
    }

    /**
     * Permet de récupérer la liste du temps pour les étoiles
     * @return La liste des temps
     */
    public ArrayList<Long> getTempsEtoiles() {
        return tempsEtoiles;
    }

    /**
     * Permet de récupérer le nombre de check disponible
     * @return Le nombre de check dispo
     */
    public Integer getNbCheck() {
        return nbCheck;
    }

    /**
     * Permet de récupérer le nombre de technique possible
     * @return Le nombre de technique disponible
     */
    public Integer getNbTech() {
        return nbTech;
    }

    /**
     * @return une difficulté Facile
     */
    public static Difficulte Facile(){
        ArrayList<Long> array = new ArrayList<>();
        array.add(30000L);
        array.add(35000L);
        array.add(40000L);
        return new Difficulte("Facile",array,-1,-1);
    }

    /**
     * @return une difficulté Moyenne
     */
    public static Difficulte Moyen(){
        ArrayList<Long> array = new ArrayList<>();
        array.add(15000L);
        array.add(20000L);
        array.add(30000L);
        return new Difficulte("Moyen",array,3,3);
    }

    /**
     * @return une difficulté Difficile
     */
    public static Difficulte Difficile(){
        ArrayList<Long> array = new ArrayList<>();
        array.add(10000L);
        array.add(12500L);
        array.add(15000L);
        return new Difficulte("Difficile",array,0,0);
    }

    /**
     * Récupère la bonne difficulté en fonction de l'entier de difficulté
     * @param i la grandeur de la difficulté
     * @return la difficulté adéquate
     */
    public static Difficulte getDifficulteFromInt(int i){
        switch (i) {
            case 1 -> {
                return Difficulte.Facile();
            }
            case 2 -> {
                return Difficulte.Moyen();
            }
            case 3 -> {
                return Difficulte.Difficile();
            }
            default -> {
                return null;
            }
        }
    }

    public String getDifficulteToString(){
        return switch (this.nomDifficute) {
            case "Facile" -> "1";
            case "Moyen" -> "2";
            case "Difficile" -> "3";
            default -> null;
        };
    }
}
