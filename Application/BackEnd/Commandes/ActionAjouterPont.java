package Application.BackEnd.Commandes;

import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Plateau;

public class ActionAjouterPont extends Action{
    private Ile ile1;
    private Ile ile2;
    private int nbPonts;
    private int oldNbPonts;
    private boolean hypo;

    /**
     * Permet la construction d'une action d'ajout de pont
     * @param plateau Le plateau sur laquelle elle s'applique
     * @param id l'id de l'action
     * @param ile1 L'ile n°1
     * @param ile2 L'ile n°2
     * @param nbPonts Le nombre de pont APRES action
     * @param oldNbPonts Le nombre de pont AVANT l'action
     * @param hypo Si le mode hypothèse est activé
     */
    public ActionAjouterPont(Plateau plateau,int id, Ile ile1, Ile ile2,int nbPonts,int oldNbPonts,boolean hypo) {
        super(plateau,id);
        this.ile1 = ile1;
        this.ile2 = ile2;
        this.nbPonts = nbPonts;
        this.oldNbPonts = oldNbPonts;
        this.hypo = hypo;
    }

    /**
     * Permet de construire des actions depuis une ligne de string
     * @param plateau Le plateau sur laquelle elle s'applique
     * @param id l'id de l'action
     * @param line La ligne de création
     */
    public ActionAjouterPont(Plateau plateau,int id,String line){
        super(plateau,id);
        this.lireAction(plateau, id, line);
    }

    /**
     * Ajoute le pont entre les deux îles
     */
    @Override
    public void execute() {
        //TODO executer l'action
        this.ecrireAction(true);
    }

    /**
     * Annule l'action fait entre deux ponts (remise à l'ancienne valeur)
     */
    @Override
    public void undo() {
        //TODO rollback l'action
        this.ecrireAction(false);
    }

    /**
     * Permet d'écrire une action d'ajout de pont dans le Yaml de sauvegarde de la grille
     * @param svg True --> Sauvegarde pour le Undo / False --> Sauvegarde pour le Redo
     */
    @Override
    public String ecrireAction(boolean svg){
        String result = super.ecrireAction(svg);
        result += "{ ile" + this.ile1.getId() + "ile" + this.ile2.getId() + "} ";
        result += this.nbPonts + " | " + this.oldNbPonts + " | " + (this.hypo ? "T" : "F");
        return result;
    }

    /**
     * Permet d'assigner les valeurs d'une action d'ajout de pont depuis 1 seule ligne
     * @param ligne La ligne en question
     * @param id id de l'action
     * @param p le plateau en question
     */
    public void lireAction(Plateau p,int id,String ligne) {
        Ile ile1 = null;
        Ile ile2 = null;
        int idIle1 = p.getGrille().obtainsIdElement(ligne.substring(ligne.indexOf("{")+2,ligne.indexOf("}")-1));
        int idIle2 = p.getGrille().obtainsIdElement(ligne.substring(ligne.lastIndexOf("{")+2,ligne.lastIndexOf("}")-1));
        this.nbPonts = Integer.parseInt(ligne.substring(ligne.lastIndexOf("}")+2,ligne.indexOf("|")-1));
        this.oldNbPonts = Integer.parseInt(ligne.substring(ligne.indexOf("|")+2,ligne.lastIndexOf("|")-1));
        this.hypo = ligne.substring(ligne.lastIndexOf("|")+2).equals("T");
        for ( Ile ile : p.getGrille().getListIle() ) {
            if (ile.getId() == idIle1)
                ile1 = ile;
            if (ile.getId() == idIle2)
                ile2 = ile;
        }
        this.ile1 = ile1;
        this.ile2 = ile2;
    }
}
