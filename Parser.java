import java.io.*;
import java.util.*;

public class Parser {
    /**
     *
     * @param file Fichier à lire
     * @return une liste HashMap clé/valeur avec toutes les balises et les valeurs associées sous forme de string
     * @throws FileNotFoundException Fichier Introuvable
     */
    public static HashMap<String,String> getAllBalise(File file) throws FileNotFoundException {
        HashMap<String, String> balises = new HashMap<>();
        try {
        Scanner scan = new Scanner(file);
        String line,key;
        while(scan.hasNextLine()){
            line = scan.nextLine();
            key = line.substring(line.indexOf("|") + 1, line.indexOf(":"));
            if((line.indexOf(":") + 2) < line.length())
                balises.put(key,line.substring(line.indexOf(":") + 2));
        }
        } catch (IOException e){
            throw new FileNotFoundException("Fichier Introuvable");
        }
        return balises;
    }
}
