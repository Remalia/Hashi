
import java.util.ArrayList;

public class Difficulte {
    public String nomDifficute;
    public ArrayList<Long> tempsEtoiles;
    public Integer nbCheck;
    public Integer nbTech;

    private Difficulte(String nom,ArrayList<Long> tempsEtoiles,Integer nbCheck,Integer nbTech){
        this.nomDifficute = nom;
        this.tempsEtoiles = new ArrayList<Long>();
        this.nbCheck = nbCheck;
        this.nbTech = nbTech;
    }


    public Difficulte creerFacile(){
        return new Difficulte("Facile",this.liste(30000L,35000L,40000L),-1,-1);
    }
    public Difficulte creerMoyen(){
        return new Difficulte("Moyen",this.liste(15000L,20000L,30000L),3,3);
    }

    public Difficulte creerDifficile(){
        return new Difficulte("Difficile",this.liste(10000L,12500L,15000L),0,0);
    }

    private ArrayList<Long> liste(Long l1,Long l2,Long l3){
        ArrayList<Long> liste = new ArrayList<>();
        liste.add(l1);
        liste.add(l2);
        liste.add(l3);
        return liste;
    }
}
