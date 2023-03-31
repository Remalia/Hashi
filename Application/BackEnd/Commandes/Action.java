package Application.BackEnd.Commandes;
import Application.BackEnd.Grille.Plateau;

public abstract class Action {
    public Plateau plateau;

    public Action(Plateau plateau){
        this.plateau = plateau;
    }

    public abstract void execute();

    public void ecrireAction(){
        //TODO ajouter a la fin du fichier l'action
    }
}
