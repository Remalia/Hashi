
import java.util.ArrayList;

public class Difficulte {
    public String nomDifficute;
    public ArrayList<Long> tempsEtoiles;
    public Integer nbCheck;
    public Integer nbTech;

    /**
     * @param nom nom de la dofficulté 
     * @param tempsEtoiles list de temps pour avoir 1,2 ou 3 étoiles
     * @param nbCheck nombre de checks disponibles
     * @param nbTech nombre de techniques disponibles
     */
    private Difficulte(String nom,ArrayList<Long> tempsEtoiles,Integer nbCheck,Integer nbTech){
        this.nomDifficute = nom;
        this.tempsEtoiles = new ArrayList<Long>();
        this.nbCheck = nbCheck;
        this.nbTech = nbTech;
    }


    /**
     * @return une difficulté Facile
     */
    public Difficulte creerFacile(){
        return new Difficulte("Facile",this.liste(30000L,35000L,40000L),-1,-1);
    }
    /**
     * @return une difficulté Moyenne
     */
    public Difficulte creerMoyen(){
        return new Difficulte("Moyen",this.liste(15000L,20000L,30000L),3,3);
    }

    /**
     * @return une difficulté Difficile
     */
    public Difficulte creerDifficile(){
        return new Difficulte("Difficile",this.liste(10000L,12500L,15000L),0,0);
    }

    /**
     * @param l1 temps pour la première étoile
     * @param l2 temps pour la deuxième étoile
     * @param l3 temps pour la troisième étoile
     * @return une liste contenant les temps pour les étoiles
     */
    private ArrayList<Long> liste(Long l1,Long l2,Long l3){
        ArrayList<Long> liste = new ArrayList<>();
        liste.add(l1);
        liste.add(l2);
        liste.add(l3);
        return liste;
    }
}
