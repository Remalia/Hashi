package Application.BackEnd.Grille;

public class Vide extends Element{
    private static Vide instance = null;

    /**
     * Permet de créer une case vide (private car Singleton --> Passage par getInstance)
     */
    private Vide(){
        super();
    }

    /**
     * Permet de récupérer l'unique instance de Vide
     * @return L'instance
     */
    public static Vide getInstance(){
        if (instance == null){
            instance = new Vide();
        }
        return instance;
    }
    @Override
    public boolean estDifferent(Pont pont){
        return false;
    }

    @Override
    public boolean estDisponible(){
        return true;
    }

}
