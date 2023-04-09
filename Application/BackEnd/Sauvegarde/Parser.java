package Application.BackEnd.Sauvegarde;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Classe de
 */

public class Parser {
    /**
     * @param file Fichier à lire
     * @return une liste HashMap clé/valeur avec toutes les balises et les valeurs associées sous forme de string
     * @throws FileNotFoundException Fichier Introuvable
     */
    public static HashMap<String,String> getAllBalise(File file) throws FileNotFoundException {
        HashMap<String, String> balises = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (!lines.isEmpty()){
                String key;
                for (String line: lines) {
                    line = deleteStartSpace(line);
                    key = line.substring(0,line.indexOf(":"));
                    if((line.indexOf(":") + 2) < line.length())
                        balises.put(key,line.substring(line.indexOf(":") + 2));
                }
            }
        } catch (IOException e){
            throw new FileNotFoundException("Fichier Introuvable");
        }
        return balises;
    }

    /**
     * Supprime les espaces en début de chaine de caractère
     * @param chaine la chaine en question
     * @return la chaine sans les espaces
     */
    private static String deleteStartSpace(String chaine) {
        int debut = 0;
        while (debut < chaine.length() && chaine.charAt(debut) == ' ') {
            debut++;
        }
        return chaine.substring(debut);
    }
}
