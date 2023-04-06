package Application.BackEnd.Grille;

public class Vide extends Element{
    private static Vide instance = null;

    /**
     * Permet de créer une case vide (private car Singleton --> Passage par getInstance)
     */
    private Vide(){

    }

    /**
     * Permet de nettoyer un Vide
     */
    @Override
    public void nettoyerCase() {

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
}
