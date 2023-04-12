package Application.BackEnd.Grille;

import Application.BackEnd.Commandes.Action;
import Application.BackEnd.Commandes.ActionAjouterPont;
import Application.BackEnd.Sauvegarde.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Plateau{
    private Grille grille;

    public Plateau(Grille grille){
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
    public void undo(){
        if(!this.grille.getHistorySvg().isEmpty()){
            Action a = this.grille.getHistorySvg().pop();
            a.undo();
            this.grille.getHistoryRecup().push(a);
            try {
                this.grille.saveGrilleToYAML();
            }catch (IOException e){
                System.out.println(e);
            }
            System.out.println(this.grille);
        }
    }

    /**
     * Permet de Redo dans le cas ou c'est possible
     */
    public void redo(){
        if(!this.grille.getHistoryRecup().isEmpty()){
            Action a = this.grille.getHistoryRecup().pop();
            a.execute();
            this.grille.getHistorySvg().push(a);
            try {
                this.grille.saveGrilleToYAML();
            }catch (IOException e){
                System.out.println(e);
            }
            System.out.println(this.grille);
        }
    }

    /**
     * Permet d'éxecuter une action, appelle /!\ à appeler directement depuis l'action
     * @param a l'action à executer
     */
    public boolean executeAction(Action a){
        boolean result = a.execute();
        if(result){
            this.grille.getHistorySvg().push(a);
            this.grille.getHistoryRecup().clear();
            try {
                this.grille.saveGrilleToYAML();
            }catch (IOException e){
                System.out.println(e);
            }
        }
        return result;
    }

    public boolean incrementerPont(Ile ile1, Ile ile2){
        Action a = new ActionAjouterPont(this.getGrille(), this.getGrille().getHistorySvg().length(),ile1,ile2,this.getGrille().isModeHyp());
        return executeAction(a);
    }

    /**
     * Récupère et définit les valeurs du plateau depuis un fichier YAML
     * @param isNew Définit si on prends une nouvelle grille ou une grille sauvegarder
     */
    public void getPlateauFromYAML(boolean isNew) throws FileNotFoundException {
        if(isNew){
            this.grille.getGrilleFromYAML(false);
        }else{
            this.grille.getGrilleFromYAML(true);
            HashMap<String,String> balises = Parser.getAllBalise(this.grille.getFileSave());
            balises.forEach(this::setupAction);
        }
    }

    /**
     * Permet de setup et d'initialiser une action
     * @param key la clé de la ligne
     * @param val la valeur de la ligne
     */
    private void setupAction(String key,String val){
        if (key.matches("actionSvg[0-9]+")){
            this.grille.getHistorySvg().push(new ActionAjouterPont(this.grille,this.grille.getHistorySvg().length(),val));
        }
        if (key.matches("actionRecup[0-9]+")){
            this.grille.getHistoryRecup().push(new ActionAjouterPont(this.grille,this.grille.getHistorySvg().length(),val));
        }
    }

    public static void main(String[] args) throws IOException {
        Grille grilleTest = new Grille("NiveauTest",Difficulte.Facile());
        Plateau plateau = new Plateau(grilleTest);
        plateau.getPlateauFromYAML(false);
        System.out.println(plateau);
    }
}