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
     * @param ile1 L'ile n°1
     * @param ile2 L'ile n°2
     * @param nbPonts Le nombre de pont APRES action
     * @param oldNbPonts Le nombre de pont AVANT l'action
     */
    public ActionAjouterPont(Plateau plateau,int id, Ile ile1, Ile ile2,int nbPonts,int oldNbPonts,boolean hypo) {
        super(plateau,id);
        this.ile1 = ile1;
        this.ile2 = ile2;
        this.nbPonts = nbPonts;
        this.oldNbPonts = oldNbPonts;
        this.hypo = hypo;
    }
    public ActionAjouterPont(Plateau plateau,int id){
        super(plateau,id);
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
     * Permet de construire une action d'ajout de pont depuis 1 seule ligne
     * @param ligne La ligne en question
     * @return une nouvelle action d'ajout de pont
     */
    @Override
    public ActionAjouterPont lireAction(Plateau p,int id,String ligne) {
        Ile ile1 = null;
        Ile ile2 = null;
        int idIle1 = p.getGrille().obtainsIdElement(ligne.substring(0,ligne.indexOf("|")-1));
        int idIle2 = p.getGrille().obtainsIdElement(ligne.substring(ligne.indexOf("|")+2,ligne.lastIndexOf("|")-1));
        int nbPonts = Integer.parseInt(ligne.substring(ligne.lastIndexOf("|")+2));
        int oldNbPonts = Integer.parseInt(ligne.substring(ligne.lastIndexOf("|")+2));
        boolean hypo = true;
        ActionAjouterPont a = new ActionAjouterPont(p,id,ile1,ile2,nbPonts,oldNbPonts,hypo);
        return a;
    }
}
