package Application.BackEnd.Sauvegarde;

import Application.BackEnd.Commandes.Action;
import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Pont;

import java.io.*;
import java.util.*;

/**
 * Classement des scores en fonction des niveaux
 */

public class Classement {
    ArrayList<Score> scores;
    File file;

    /**
     * Fonction de récupération des classements sur un niveau depuis le path du fichier. Remplit la liste de score.
     */
    public void getScoresFromYAML() throws FileNotFoundException {
        HashMap<String,String> balises = Parser.getAllBalise(file);
        if (balises.get("type").equals("fichierScore"))
            balises.forEach((key, val) -> {
                if (key.matches("score[0-9]+")){
                    scores.add(new Score(Integer.parseInt(val.substring(val.lastIndexOf("|")+2)),val.substring(0,val.indexOf("|")-1),val.substring(val.indexOf("|")+2,val.lastIndexOf("|")-1)));
                }
            });
    }

    public void saveClassementToYAML()throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
        int idScore = 1;
        writer.write("type: fichierScore\n");
        writer.write("nb: "+idScore+"\n");
        for (Score s: this.scores) {
            writer.write(s.yamlInfo());
            idScore++;
        }
        writer.close();
    }


    /**
     * Construit un Classement
     * @param path Emplacement où le classement sera écrit et stocké.
     */
    public Classement(String path) throws FileNotFoundException {
        this.scores = new ArrayList<>();
        this.file = new File(path);
        this.getScoresFromYAML();
    }

    /**
     * Testeur de classement
     * @param args arguments
     * @throws FileNotFoundException Fichier Introuvable
     */
    public static void main(String[] args) throws FileNotFoundException {
        Classement tester = new Classement("Application\\Niveau\\NiveauTest\\Score.yaml");
        System.out.println(tester.scores);
    }
}
