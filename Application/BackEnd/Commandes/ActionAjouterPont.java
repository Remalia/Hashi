package Application.BackEnd.Commandes;

import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Plateau;

public class ActionAjouterPont extends Action{
    private Ile ile1;
    private Ile ile2;
    private int nbPonts;
    private int oldNbPonts;

    /**
     * Permet la construction d'une action d'ajout de pont
     * @param plateau Le plateau sur laquelle elle s'applique
     * @param ile1 L'ile n°1
     * @param ile2 L'ile n°2
     * @param nbPonts Le nombre de pont APRES action
     * @param oldNbPonts Le nombre de pont AVANT l'action
     */
    public ActionAjouterPont(Plateau plateau, Ile ile1, Ile ile2,int nbPonts,int oldNbPonts) {
        super(plateau);
        this.ile1 = ile1;
        this.ile2 = ile2;
        this.nbPonts = nbPonts;
        this.oldNbPonts = oldNbPonts;
    }

    /**
     * Ajoute le pont entre les deux îles
     */
    @Override
    public void execute() {
        this.ecrireAction(true);
    }

    /**
     * Annule l'action fait entre deux ponts (remise à l'ancienne valeur)
     */
    @Override
    public void undo() {
        this.ecrireAction(false);
    }

    /**
     * Permet d'écrire une action d'ajout de pont dans le Yaml de sauvegarde de la grille
     * @param svg True --> Sauvegarde pour le Undo / False --> Sauvegarde pour le Redo
     */
    @Override
    public void ecrireAction(boolean svg) {
        //TODO Ecrire l'action ajouter Pont dans le YAML de sauvegarde
    }

    /**
     * Permet de construire une action d'ajout de pont depuis 1 seule ligne
     * @param ligne La ligne en question
     * @return une nouvelle action d'ajout de pont
     */
    @Override
    public ActionAjouterPont lireAction(String ligne) {
        ActionAjouterPont a = null;
        return a;
    }
}
