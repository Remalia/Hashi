package Application.BackEnd.Grille;

import Application.BackEnd.Commandes.Action;

public class Plateau{
    private Grille grille;

    Plateau(Grille grille){
        this.grille = grille;
    }

    /**
     * Récupère la grille du plateau
     * @return La grille du plateau
     */
    public Grille getGrille() {
        return grille;
    }

    /**
     * Permet de Undo dans le cas ou c'est possible
     */
    private void undo(){
        if(!this.grille.getHistorySvg().isEmpty()){
            Action a = this.grille.getHistorySvg().pop();
            a.undo();
        }

    }

    /**
     * Permet de Redo dans le cas ou c'est possible
     */
    private void redo(){
        if(!this.grille.getHistoryRecup().isEmpty()){
            Action a = this.grille.getHistoryRecup().pop();
            a.execute();
        }
    }

    /**
     * Permet d'éxecuter une action, appelle /!\ à appeler directement depuis l'action
     * @param a l'action à executer
     */
    private void executeAction(Action a){
        a.execute();
        this.grille.getHistorySvg().push(a);
        this.grille.getHistoryRecup().clear();
    }
}