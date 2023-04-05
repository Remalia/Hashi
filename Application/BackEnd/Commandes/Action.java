package Application.BackEnd.Commandes;
import Application.BackEnd.Grille.Plateau;
import Application.BackEnd.Grille.Pont;

public abstract class Action {
    private Plateau plateau;

    /**
     * Créer une Action
     * @param plateau Le plateau sur lequel elle s'éxecute
     */
    public Action(Plateau plateau){
        this.plateau = plateau;
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
    public abstract void ecrireAction(boolean svg);

    /**
     * Permet de lire l'action dans un fichier YAML
     */
    public abstract Action lireAction(String ligne);
}
