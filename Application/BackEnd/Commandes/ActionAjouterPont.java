package Application.BackEnd.Commandes;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Pont;

public class ActionAjouterPont extends Action{
    private Ile ile1;
    private Ile ile2;
    private boolean hypo;

    /**
     * Permet la construction d'une action d'ajout de pont
     * @param grille La grille sur laquelle elle s'applique
     * @param id l'id de l'action
     * @param ile1 L'ile n°1
     * @param ile2 L'ile n°2
     * @param hypo Si le mode hypothèse est activé
     */
    public ActionAjouterPont(Grille grille, int id, Ile ile1, Ile ile2, boolean hypo) {
        super(grille,id);
        this.ile1 = ile1;
        this.ile2 = ile2;
        this.hypo = hypo;
    }

    /**
     * Permet de construire des actions depuis une ligne de string
     * @param grille La grille sur laquelle elle s'applique
     * @param id l'id de l'action
     * @param line La ligne de création
     */
    public ActionAjouterPont(Grille grille,int id,String line){
        super(grille,id);
        this.lireAction(grille, id, line);
    }

    /**
     * Ajoute le pont entre les deux îles
     */
    @Override
    public boolean execute() {
        Pont pont = this.getGrille().chercherPont(ile1,ile2);
        if(pont != null) {
            pont.incrementerPont();
            this.getGrille().actualiserPontDansGrille(pont);
            this.ecrireAction(true);
            return true;
        }else{
            System.out.println("Erreur dans l'action d'ajout de pont : Pont non trouvé");
            return false;
        }

    }

    /**
     * Annule l'action fait entre deux ponts (remise à l'ancienne valeur)
     */
    @Override
    public boolean undo() {
        Pont pont = this.getGrille().chercherPont(ile1,ile2);
        if(pont != null) {
            pont.decrementerPont();
            this.getGrille().actualiserPontDansGrille(pont);
            this.ecrireAction(false);
            return true;
        }else{
            System.out.println("Erreur dans l'undo d'ajout de pont : Pont non trouvé");
            return false;
        }
    }

    /**
     * Permet d'écrire une action d'ajout de pont dans le Yaml de sauvegarde de la grille
     * @param svg True --> Sauvegarde pour le Undo / False --> Sauvegarde pour le Redo
     */
    @Override
    public String ecrireAction(boolean svg){
        String result = super.ecrireAction(svg);
        result += " ile" + this.ile1.getId() + " | ile" + this.ile2.getId() + " | ";
        result += (this.hypo ? "T" : "F") + "\n";
        return result;
    }

    /**
     * Permet d'assigner les valeurs d'une action d'ajout de pont depuis 1 seule ligne
     * @param ligne La ligne en question
     * @param id id de l'action
     * @param g la grille en question
     */
    public void lireAction(Grille g,int id,String ligne) {
        Ile ile1 = null;
        Ile ile2 = null;
        int idIle1 = g.obtainsIdElement(ligne.substring(0,ligne.indexOf("|")-1));
        int idIle2 = g.obtainsIdElement(ligne.substring(ligne.indexOf("|")+2,ligne.lastIndexOf("|")-1));
        this.hypo = ligne.substring(ligne.lastIndexOf("|")+2).equals("T");
        for ( Ile ile : g.getListIle() ) {
            if (ile.getId() == idIle1)
                ile1 = ile;
            if (ile.getId() == idIle2)
                ile2 = ile;
        }
        this.ile1 = ile1;
        this.ile2 = ile2;
    }
}
