package Application.BackEnd.Sauvegarde;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Paramètre {
    private static final File levelDirectories = new File("Application/Niveau");

    /**
     * Permet de remettre le jeu à zero (fichier de sauvegarde)
     * @param uniquementClassement Si true remet uniquement le classement à zero
     */
    public static void mettreJeuAZero(boolean uniquementClassement) throws IOException {
        String regex = "^S.*";
        if(uniquementClassement)
            regex = "Score.*";
        for(File dirNiveau : levelDirectories.listFiles()){
            if(dirNiveau.isDirectory() && dirNiveau.getName().matches("Niveaux.*")){
                for(File niveau : dirNiveau.listFiles()){
                    if(niveau.isDirectory() && niveau.getName().matches("Niveau[0-9]+")){
                        for (File fileNiveau: niveau.listFiles()) {
                            if(fileNiveau.getName().matches(regex)){
                                FileWriter eraser = new FileWriter(fileNiveau,false);
                                eraser.flush();
                                eraser.close();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        mettreJeuAZero(true);
    }
}
