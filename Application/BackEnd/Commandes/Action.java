package Application.BackEnd.Commandes;
import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Pont;

public abstract class Action {
    private Grille grille;
    private int id;

    /**
     * Créer une Action
     * @param grille La grille sur laquel elle s'éxecute
     */
    Action(Grille grille,int id){
        this.grille = grille;
        this.id = id;
    }

    public Grille getGrille() {
        return grille;
    }

    public int getId() {
        return id;
    }

    /**
     * Execute l'action
     */
    public abstract boolean execute();

    /**
     * Annule l'action
     */
    public abstract boolean undo();

    /**
     * Permet d'écrire l'action dans un fichier YAML
     */
    public String ecrireAction(boolean svg){
        return "  action" + (svg ? "Svg" : "Recup") + this.id +": ";
    }

    /**
     * Permet de lire l'action dans un fichier YAML
     */
    public abstract void lireAction(Grille p,int id,String ligne);
}
