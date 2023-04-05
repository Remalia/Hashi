package Application.BackEnd.Commandes;
import Application.BackEnd.Grille.Plateau;
import Application.BackEnd.Grille.Pont;

public abstract class Action {
    private Plateau plateau;
    private int id;

    /**
     * Créer une Action
     * @param plateau Le plateau sur lequel elle s'éxecute
     */
    Action(Plateau plateau,int id){
        this.plateau = plateau;
        this.id = id;
    }

    /**
     * Execute l'action
     */
    public abstract void execute();

    /**
     * Annule l'action
     */
    public abstract void undo();

    /**
     * Permet d'écrire l'action dans un fichier YAML
     */
    public String ecrireAction(boolean svg){
        return "  action" + (svg ? "Svg" : "Recup") + this.id +": ";
    }

    /**
     * Permet de lire l'action dans un fichier YAML
     */
    public abstract Action lireAction(Plateau p,int id,String ligne);
}
