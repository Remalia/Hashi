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

    /**
     * Récupère et définit les valeurs du plateau depuis un fichier YAML
     * @param isNew Définit si on prends une nouvelle grille ou une grille sauvegarder
     */
    public void getPlateauFromYAML(boolean isNew) throws FileNotFoundException {
        File file;
        if(isNew)
            file = this.grille.getFileNiveau();
        else
            file = this.grille.getFileSave();
        this.grille.getGrilleFromYAML(file);
        HashMap<String,String> balises = Parser.getAllBalise(file);
        balises.forEach(this::setupAction);
    }

    /**
     * Permet de setup et d'initialiser une action
     * @param key la clé de la ligne
     * @param val la valeur de la ligne
     */
    private void setupAction(String key,String val){
        if (key.matches("actionSvg[0-9]+")){
            this.grille.getHistorySvg().push(new ActionAjouterPont(this,this.grille.getHistorySvg().length()+1,val));
        }
        if (key.matches("actionRecup[0-9]+")){
            this.grille.getHistoryRecup().push(new ActionAjouterPont(this,this.grille.getHistorySvg().length()+1,val));
        }
    }

    public static void main(String[] args) throws IOException {
        Grille grilleTest = new Grille("NiveauTest");
        Plateau plateau = new Plateau(grilleTest);
        plateau.getPlateauFromYAML(false);
        System.out.println(plateau);
    }
}