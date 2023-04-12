package Application.BackEnd.Grille;

import Application.BackEnd.Commandes.Action;
import Application.BackEnd.Commandes.ActionHistory;
import Application.BackEnd.Sauvegarde.Parser;
import Application.FrontEnd.Controller.Plateau.CircleHashi;
import javafx.geometry.Orientation;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.regex.*;



/**
 * Classe représentant la grille du jeu
 */
public class Grille {
    private ActionHistory historySvg = new ActionHistory();
    private ActionHistory historyRecup = new ActionHistory();
    private final Element[][] matriceGrille;
    private final ArrayList<Ile> listIle;
    private File fileNiveau;
    private File fileSave;
    private String name;
    private boolean modeHyp;
    private Difficulte difficulte;
    public ArrayList<String> sauvegardeNomListPont;
    public ArrayList<String> sauvegardeNomListPontHypothese;
    public ArrayList<Integer> listeNbPontsHypothese;
    private Grille solution;

    /**
     * Constructeur de la grille qui copie une grille passée en paramètres
     * @param g la grille à copier
     * */
    public Grille(Grille g)
    {
        this.historySvg = g.getHistorySvg();
        this.historyRecup = g.getHistoryRecup();
        this.matriceGrille = g.getMatriceGrille();
        this.fileNiveau = g.getFileNiveau();
        this.fileSave = g.getFileSave();
        this.name = g.getName();
        this.modeHyp = g.isModeHyp();
        this.difficulte = g.getDifficulte();
        this.solution = g.solution;
        this.listIle = new ArrayList<>();
        for (Ile ile: getListIle()) {
            this.ajouterIle(new Ile(ile));
        }
    }
    /**
     * Constructeur de la grille avec un nom de niveau
     * @param name le nom du niveau
     * @throws IOException Fichier/dossier déja créer
     */
    public Grille(String name) throws IOException {
        this.listIle = new ArrayList<>();
        this.listeNbPontsHypothese = new ArrayList<>();
        this.sauvegardeNomListPont = new ArrayList<>();
        this.sauvegardeNomListPontHypothese = new ArrayList<>();
        this.modeHyp = false;
        this.name = name;
        Files.createDirectories(Paths.get("Application/Niveau"));
        Files.createDirectories(Paths.get("Application/Niveau/Niveau"+this.difficulte.getNomDifficute()+ "/"+this.name));
        try{
            Files.createFile(Path.of("Application/Niveau/Niveau"+this.difficulte.getNomDifficute()+ "/"+this.name + "/Niveau.yaml"));
        } catch (IOException e) {
            System.out.println("Fichier de niveau déja créé : " + e.getMessage());
        }
        try {
            Files.createFile(Path.of("Application/Niveau/Niveau"+ this.difficulte.getNomDifficute()+ "/"+this.name + "/Save.yaml"));
        } catch (IOException e) {
            System.out.println("Fichier de sauvegarde déja créé : " + e.getMessage());
        }
        this.fileNiveau = new File("Application/Niveau/Niveau"+this.difficulte.getNomDifficute()+ "/"+this.name +"/Niveau.yaml");
        this.fileSave = new File("Application/Niveau/Niveau"+this.difficulte.getNomDifficute()+ "/"+this.name +"/Save.yaml");
        this.matriceGrille = new Element[10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                matriceGrille[i][j] = Vide.getInstance();
            }
        this.difficulte = Difficulte.getDifficulteFromInt(1);
        this.solution = new Grille();
    }



    /**
     * Constructeur de la grille
     */
    public Grille(){
        this.listIle = new ArrayList<>();
        this.listeNbPontsHypothese = new ArrayList<>();
        this.matriceGrille = new Element[10][10];

        int i, j;
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                matriceGrille[i][j] = Vide.getInstance();
            }
        }
    }

    /**
     * Cette méthode permet de restaurer tout le jeu à son point d'initiale
     */
    public void suppression(){
        for(Ile ile : listIle){
            for(Pont pont : ile.getListePont()){
                pont.setNbPont(0);
                actualiserPontDansGrille(pont);
            }
        }
        reinitialiserSauvegarde(fileSave);
    }

    /**
     * Cette méthode permet de sauvegarder l'état initiale des ponts des iles avant que le joueur effectue son brouillon d'hypothèse
     */
    public void avantHypothese(){
        for(Ile ile : listIle){
            for(Pont pont : ile.getListePont()){
                listeNbPontsHypothese.add(pont.getNbPont());
            }
        }
        sauvegardeHypothese(fileSave, sauvegardeNomListPontHypothese, "pont");
    }

    /**
     * Cette méthode permet de revenir à l'état de sauvegarde d'avant brouillon de la grille
     */
    public void apresHypothese(){
        for(Ile ile : listIle){
            for(Pont pont : ile.getListePont()){
                pont.setNbPont(listeNbPontsHypothese.get(0));
                listeNbPontsHypothese.remove(0);
                actualiserPontDansGrille(pont);
            }
        }
        reinitialiserSauvegarde(fileSave);
        restaurationHypotheseInitiale(fileSave, sauvegardeNomListPontHypothese);
    }

    public void undoRedoSauvegarde(){
        sauvegardeNomListPont.clear();
        sauvegardeListPont(fileSave, sauvegardeNomListPont);
    }

    /**
     * Retourne le nom de la grille
     * @return le nom de la grille
     * */
    public String getName() { return this.name; }

    /**
     * Retourne la difficulté de la grille
     * @return la difficulté de la grille
     * */
    public Difficulte getDifficulte() { return this.difficulte; }

    public Grille getGrilleSolution(){
        return this.solution;
    }

    /**
     * Retourne la file du niveau
     * @return la file du niveau
     */
    public File getFileNiveau() {
        return fileNiveau;
    }

    /**
     * Retourne l'historique de récupération (Redo)
     * @return l'historique de récupération
     */
    public ActionHistory getHistoryRecup() {
        return historyRecup;
    }

    /**
     * Retourne l'historique de sauvegarde (Undo)
     * @return l'historique de sauvegarde
     */
    public ActionHistory getHistorySvg() {
        return historySvg;
    }

    /**
     * Retourne la file de sauvegarde
     * @return la file de sauvegarde
     */
    public File getFileSave() {
        return fileSave;
    }

    /**
     * Retourne la liste des îles
     * @return la liste des îles
     */
    public ArrayList<Ile> getListIle() {
        return listIle;
    }

    public boolean isModeHyp() {
        return modeHyp;
    }

    /**
     * Méthode d'ajout d'une nouvelle ile dans la matrice
     * @param ile L'ile a ajouter
     */
    public void ajouterIle(Ile ile){
        int abs = ile.getAbs();
        int ord = ile.getOrd();
        listIle.add(ile);
        Element actuel = matriceGrille[abs][ord];
        Ile temp;
        //Une fois l'île créée, on essaye de trouver des îles dans les 4 directions
        for(Direction d : Direction.values()){
            temp = actuel.getIleFromDirection(abs, ord, d ,this.matriceGrille);
            if(temp != null){
                ajouterPont(ile, temp, 0);
            }
        }
        matriceGrille[abs][ord] = ile;
    }

    /**
     * Méthode de retrait d'une ile dans la matrice
     * @param ile L'ile a retirer
     */
    public void removeIle(Ile ile){
        listIle.remove(ile);
        matriceGrille[ile.getAbs()][ile.getOrd()] = Vide.getInstance();
    }

    /**
     * Retourne une ile à partir de sa position
     * @param x l'abscisse de l'île
     * @param y l'ordonnée de l'île
     * @return l'île à la position x,y
     */
    public Ile getIleFromPos(int x, int y){
        Ile result = null;
        for (Ile ile: listIle) {
            if(ile.getAbs() == x && ile.getOrd() == y)
                result = ile;
        }
        return result;
    }

    /**
     * Donne le pont entre deux iles
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return le pont entre les deux îles null si rien
     */
    public Pont chercherPont(Ile ile1, Ile ile2){
        for(Pont p : ile1.getListePont()){
            if(p.getIle1() == ile1 && p.getIle2() == ile2 || p.getIle1() == ile2 && p.getIle2() == ile1)
                return p;
        }
        return null;
    }



    /**
     * Ajoute un pont à la grille ( utilisé SEULEMENT lors de la CREATION de la grille avec le YAML )
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @param nbPonts le nombre de ponts à set
     */
    public void ajouterPont(Ile ile1, Ile ile2, int nbPonts){
        Pont pont;
        // Sinon, on crée un nouveau pont en fonction de l'orientation
        if(getOrientationFrom2Iles(ile1,ile2) == Orientation.HORIZONTAL){
            pont = new PontHorizontal(ile1,ile2);
        }
        else {
            pont = new PontVertical(ile1, ile2);
        }
        pont.setNbPont(nbPonts);
        actualiserGrille();
    }

    public void actualiserGrille(){
        for(Ile ile : listIle){
            for(Pont p : ile.getListePont()){
                if(!collisionCreationPont(p)) {
                    ajouterPontDansGrille(p);
                }
            }
        }
    }

    public void actualiserPontDansGrille(Pont pont){
        if(!collisionCreationPont(pont))
            ajouterPontDansGrille(pont);
    }

    /**
     * Ajoute un pont dans la grille
     * @param pont à ajouter dans la grille
     */
    public void ajouterPontDansGrille(Pont pont){
        Ile ile1 = pont.getIle1();
        Ile ile2 = pont.getIle2();
        Element elem;
        if(pont.getNbPont() == 0)
            elem = Vide.getInstance();
        else
            elem = pont;
        switch (pont.getDirectionFrom(ile1)) {
            case BAS -> {
                for (int i = ile2.getOrd() - 1; i > ile1.getOrd(); i--) {
                    matriceGrille[ile1.getAbs()][i] = elem;
                }
            }
            case HAUT -> {
                for (int i = ile2.getOrd() + 1; i < ile1.getOrd(); i++) {
                    matriceGrille[ile1.getAbs()][i] = elem;
                }
            }
            case DROITE -> {
                for (int i = ile2.getAbs() - 1; i > ile1.getAbs(); i--) {
                    matriceGrille[i][ile1.getOrd()] = elem;
                }
            }
            case GAUCHE -> {
                for (int i = ile2.getAbs() + 1; i < ile1.getAbs(); i++) {
                    matriceGrille[i][ile1.getOrd()] = elem;
                }
            }
        }
    }

    /**
     * Fonction qui vérifie si il y a déjà un pont entre deux îles autre que celui passé en paramètre
     * @param pont pont à vérifier
     * @return true si il y a déjà un pont entre les deux îles avec nbPont > 0
     */
    public boolean collisionCreationPont(Pont pont){
        if(pont == null)
            return false;
        Ile ile1 = pont.getIle1();
        Ile ile2 = pont.getIle2();
        switch (pont.getDirectionFrom(ile1)){
            case BAS :
                for (int i = ile2.getOrd() - 1; i > ile1.getOrd(); i--) {
                    if(matriceGrille[ile1.getAbs()][i].estDifferent(pont))
                        return true;
                }
                return false;
            case HAUT :
                for (int i = ile2.getOrd() + 1; i < ile1.getOrd(); i++) {
                    if(matriceGrille[ile1.getAbs()][i].estDifferent(pont))
                        return true;
                }
                return false;
            case DROITE :
                for (int i = ile2.getAbs() - 1; i > ile1.getAbs(); i--) {
                    if(matriceGrille[i][ile1.getOrd()].estDifferent(pont))
                        return true;
                }
                return false;
            case GAUCHE :
                for (int i = ile2.getAbs() + 1; i < ile1.getAbs(); i++) {
                    if(matriceGrille[i][ile1.getOrd()].estDifferent(pont)) {
                        return true;
                    }
                }
                return false;
        }
        System.out.println("Erreur quelque part dans collisionCreationPont");
        return true;
    }


    /**
     * Donne l'orientation d'un pont entre deux îles
     * @param ile1 première ile du pont
     * @param ile2 seconde ile du pont
     * @return l'orientation du pont
     */
    public Orientation getOrientationFrom2Iles(Ile ile1, Ile ile2){
        if(ile1.getOrd() == ile2.getOrd()){
            return Orientation.HORIZONTAL;
        } else {
            return Orientation.VERTICAL;
        }
    }


    /**
     * Méthode d'affichage de la grille
     * @return la grille sous forme de String
     */
    public String toString(){
        int i, j;
        String s = "";
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                s += matriceGrille[j][i].toString() + " ";
            }
            s += "\n";
        }
        return s;
    }


    /**
     * Retourne la pile des ponts sauvegardés
     * @return la pile des ponts sauvegardés
     */
    public int getTaille(){
        return 10;
    }

    /**
     * Retourne la matrice de la grille
     * @return la matrice de la grille
     */
    public Element[][] getMatriceGrille(){
        return matriceGrille;
    }

    /**
     * Récupère la grille et l'initialise
     * @throws FileNotFoundException Fichier non trouvé
     * @param isItSave Si c'est une sauvegarde ou non
     */
    public void getGrilleFromYAML(Boolean isItSave) throws FileNotFoundException {
        if (!isItSave){
            HashMap<String,String> balises = Parser.getAllBalise(this.fileNiveau);
            if (balises.get("type").equals("fichierNiveau")){
                difficulte = Difficulte.getDifficulteFromInt(Integer.parseInt(String.valueOf(balises.get("difficulte").charAt(0))));
                balises.forEach(this.solution::setupIle);
                balises.forEach(this.solution::setupPont);
                balises.forEach(this::setupIle);
            }
        }else{
            HashMap<String,String> balisesNiveau = Parser.getAllBalise(this.fileNiveau);
            HashMap<String,String> balisesSave = Parser.getAllBalise(this.fileSave);
            if (balisesNiveau.get("type").equals("fichierNiveau")){
                difficulte = Difficulte.getDifficulteFromInt(Integer.parseInt(String.valueOf(balisesNiveau.get("difficulte").charAt(0))));
                balisesNiveau.forEach(this.solution::setupIle);
                balisesNiveau.forEach(this.solution::setupPont);
                balisesNiveau.forEach(this::setupIle);
            }
            if (!balisesSave.isEmpty() && balisesSave.get("type").equals("fichierNiveau")){
                balisesSave.forEach(this::setupPont);
            }
        }
    }

    /**
     * Récupère un pont et l'ajoute à la liste de pont
     * @param key La clé de la balise
     * @param val la valeur de la balise
     */
    private void setupPont(String key,String val){
        if (key.matches("pont[0-9]+")){
            Ile ile1 = null;
            Ile ile2 = null;
            int idIle1 = obtainsIdElement(val.substring(0,val.indexOf("|")-1));
            int idIle2 = obtainsIdElement(val.substring(val.indexOf("|")+2,val.lastIndexOf("|")-1));
            int nbPont = Integer.parseInt(val.substring(val.lastIndexOf("|")+2));
            for (Ile ile : listIle) {
                if (ile.getId() == idIle1)
                    ile1 = ile;
                if (ile.getId() == idIle2)
                    ile2 = ile;
            }
            ajouterPont(ile1,ile2,nbPont);
        }
    }

    /**
     * Récupère une ile et l'ajoute à la liste d'ile
     * @param key La clé de la balise
     * @param val la valeur de la balise
     */
    private void setupIle(String key, String val){
        if (key.matches("ile[0-9]+")){
            int id = obtainsIdElement(key);
            int abs = Integer.parseInt(val.substring(0,val.indexOf("|")-1));
            int ord = Integer.parseInt(val.substring(val.indexOf("|")+2,val.lastIndexOf("|")-1));
            int num = Integer.parseInt(val.substring(val.lastIndexOf("|")+2));
            Ile ile = new Ile(id,num,abs,ord);
            ajouterIle(ile);

        }
    }

    /**
     * Permet de sauvegarder l'état de la grille en fichier YAML
     * @throws IOException Pas d'accès au fichier
     */
    public void saveGrilleToYAML() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileSave));
        writer.write("type: fichierNiveau\n" + "difficulte: ");
        writer.write(this.difficulte.getDifficulteToString() + " #( 1 --> facile | 2 --> Moyen | 3 --> Difficile )");
        writer.write("\ngrille: #( ile --> abs | ord | num ) ( pont --> ileUn | ileDeux | nbPont )\n");
        int idPont = 1;
        List<Pont> listTemp = new ArrayList<>();
        for (Ile ile : listIle) {
            writer.write("  ile" + ile.getId() + ": "+ ile.getAbs() + " | " + ile.getOrd() + " | " + ile.getNum()+"\n");
        }
        for (Ile ile : listIle){
            for (Pont p: ile.getListePont()) {
                if(!listTemp.contains(p) && p.getNbPont() != 0){
                    writer.write("  pont" + idPont + ": ile" +p.getIle1().getId() + " | ile"+ p.getIle2().getId() + " | " + p.getNbPont() + "\n");
                    idPont++;
                    listTemp.add(p);
                }
            }
        }
        writer.write("historySvg: #( actionAjouterPont --> ileUn | ileDeux | modeHyp )\n");
        for (Action a: this.historySvg ) {
            writer.write(a.ecrireAction(true));
        }
        writer.write("historyRecup: #( actionAjouterPont --> ileUn | ileDeux | modeHyp )\n");
        for (Action a: this.historyRecup ) {
            writer.write(a.ecrireAction(false));
        }
        writer.close();
    }

    /**
     * Récupère l'id d'une clé
     * @param key la clé string
     * @return -1 si il n'y a pas d'id sinon l'id
     */
    public int obtainsIdElement(String key){
        int result = -1;
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(key);
        while(m.find()){
            result = Integer.parseInt(m.group(0));
        }
        return result;
    }

    /**
     * Vérifie si la grille est terminée ou non
     * @return true si la grille est terminée.
     */
    public boolean grilleCorrecte(){
        boolean result = true;
        for (Ile ileActuel: listIle) {
            Ile ileSolution = this.getIleSolution(ileActuel);
            for (Pont pActuel: ileActuel.getListePont()) {
                for (Pont pSoluce: ileSolution.getListePont()) {
                    if(pActuel.estSimilaire(pSoluce))
                        if (pActuel.getNbPont() != pSoluce.getNbPont())
                            return false;
                }
            }
        }
        return result;
    }

    /**
     * Methode qui permet à chaque début de jeu, de supprimer tout les sauvegardes de ponts de la précédente jeu.
     * @param file le fichier sauvegarde à restaurer
     */
    public static void reinitialiserSauvegarde(File file) {
        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Création d'un fichier temporaire pour stocker les données sans les lignes commençant par "pont"
            File tempFile = new File("temp.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().startsWith("pont")) { // Vérifie si la ligne ne commence pas par "pont"
                    writer.write(line + System.getProperty("line.separator")); // Écriture de la ligne dans le fichier temporaire
                }
                line = reader.readLine();
            }

            // Fermeture des flux de lecture et d'écriture
            reader.close();
            writer.close();

            // Remplacement du fichier original par le fichier temporaire
            file.delete();
            tempFile.renameTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Methode qui permet de retourner l'abscisse d'une île qui est stocké dans un fichier yaml.
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param name le nom de l'île recherché
     */
    public static int retournerAbscisseIle(File file, String name) {
        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith(name)) { // Vérifie si la ligne commence par le nom donné en paramètre
                    // Récupération du premier numéro après le nom
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        try {
                            int number = Integer.parseInt(word);
                            reader.close();
                            return number;
                        } catch (NumberFormatException e) {
                            // La chaîne n'est pas un nombre, on passe au mot suivant
                        }
                    }
                }
                line = reader.readLine();
            }

            // Fermeture du flux de lecture
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si aucun numéro n'a été trouvé, on retourne -1
        return -1;
    }

    /**
     * Methode qui permet de retourner l'ordonnée d'une île qui est stocké dans un fichier yaml.
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param name le nom de l'île recherché
     */
    public static int retournerOrdonneeIle(File file, String name) {
        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith(name)) { // Vérifie si la ligne commence par le nom donné en paramètre
                    // Récupération du deuxième numéro après le nom
                    String[] words = line.split("\\s+");
                    int count = 0;
                    for (String word : words) {
                        try {
                            int number = Integer.parseInt(word);
                            count++;
                            if (count == 2) { // Si c'est le deuxième nombre, on le retourne
                                reader.close();
                                return number;
                            }
                        } catch (NumberFormatException e) {
                            // La chaîne n'est pas un nombre, on passe au mot suivant
                        }
                    }
                }
                line = reader.readLine();
            }

            // Fermeture du flux de lecture
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si aucun deuxième numéro n'a été trouvé, on retourne -1
        return -1;
    }

    /**
     * Methode qui permet de retourner le 1er sommet du pont.
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param nomPont le nom de du pont en question recherché
     */
    public static String retournerLienPont1(File file, String nomPont) {
        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith(nomPont)) { // Vérifie si la ligne commence par le nom du pont donné en paramètre
                    // Récupération des deux premières chaînes après le nom du pont
                    String[] words = line.split("\\s+");
                    if (words.length > 2) { // Vérifie si au moins deux chaînes sont présentes après le nom du pont
                        reader.close();
                        return words[2];
                    }
                }
                line = reader.readLine();
            }

            // Fermeture du flux de lecture
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si aucune seconde chaîne n'a été trouvée, on retourne une chaîne vide
        return "";
    }

    /**
     * Methode qui permet de retourner le 2nd sommet du pont.
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param nomPont le nom de du pont en question recherché
     */
    public static String retournerLienPont2(File file, String nomPont) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith(nomPont)) {
                    String[] words = line.trim().split("\\s+");
                    if (words.length > 3) { // Vérifie qu'il y ait au moins 3 chaînes après le nom de pont
                        reader.close();
                        return words[3];
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Si le nom de pont n'a pas été trouvé ou s'il n'y a pas de troisième chaîne après le nom, on retourne null
    }

    /**
     * Methode qui permet de un numero qui represente le type de pont qui relie les 2 iles (1 = pont classique ; 2 = double pont).
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param nomPont le nom de du pont en question recherché
     */
    public static int retournerTypeLienPont(File file, String nomPont) {
        int lastNumber = -1;

        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith(nomPont)) { // Vérifie si la ligne commence par le nom du pont donné en paramètre
                    // Récupération des nombres présents sur la ligne
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        try {
                            int number = Integer.parseInt(word);
                            lastNumber = number;
                        } catch (NumberFormatException e) {
                            // Ignorer les chaînes qui ne sont pas des nombres
                        }
                    }
                }
                line = reader.readLine();
            }

            // Fermeture du flux de lecture
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si aucun nombre n'a été trouvé, on retourne -1
        return lastNumber;
    }

    /**
     * Methode qui permet d'extraire tout les ponts sauvegarder dans le fichier yaml.
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param list la liste des ponts à sauvegarder
     */
    public static void sauvegardeListPont(File file, ArrayList<String> list) {
        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith("pont")) { // Vérifie si la ligne commence par "pont"
                    // Récupération de la deuxième chaîne de caractères de la ligne
                    String[] words = line.split("\\s+");
                    if (words.length > 1) {
                        String pontName = words[1];
                        if (pontName.length() > 1) {
                            pontName = pontName.substring(0, pontName.length() - 1); // Supprime le dernier caractère de la chaîne
                            list.add(pontName);
                        }
                    }
                }
                line = reader.readLine();
            }

            // Fermeture du flux de lecture
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode qui permet d'extraire tout les ponts sauvegarder dans le fichier yaml et de les stocker dans une arraylist en fonction du début de la ligne.
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param ligne la liste de ligne avant que le mode hypothèse est lancée
     * @param nom Le debut de la ligne
     */
    public static void sauvegardeHypothese(File file, ArrayList<String> ligne, String nom) {
        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith(nom)) { // Vérifie si la ligne commence par le nom recherché
                    ligne.add(line); // Ajoute la ligne dans l'ArrayList
                }
                line = reader.readLine(); // Passe à la ligne suivante
            }
            reader.close(); // Fermeture du fichier
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode qui permet d'ajouter tout le contnu de l'ArrayList dans le fichier yaml.
     * @param file le fichier yaml auquel on souhaite extraire l'information
     * @param lignes la liste de ligne avant que le mode hypothèse est lancée
     */
    public static void restaurationHypotheseInitiale(File file, ArrayList<String> lignes) {
        try {
            // Ouverture du fichier en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String contenu = ""; // Variable qui contiendra le contenu du fichier

            // Lecture et traitement du fichier ligne par ligne
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().startsWith("historySvg")) { // Vérifie si la ligne commence par "historySvg"
                    // Ajoute tout le contenu de l'ArrayList avant la ligne correspondante
                    for (String l : lignes) {
                        contenu += l + "\n";
                    }
                }
                contenu += line + "\n"; // Ajoute la ligne actuelle dans le contenu
                line = reader.readLine(); // Passe à la ligne suivante
            }
            reader.close(); // Fermeture du fichier

            // Écriture du contenu dans le fichier
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(contenu);
            writer.close(); // Fermeture du fichier
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Récupère le nombre d'erreur dans la grille
     * @return le nombre d'erreur
     */
    public int getNbError() {
        int result = 0;
        for (Ile ile: listIle) {
            Ile ileSolution = getIleSolution(ile);
            for (Pont p: ile.getListePont()) {
                if(p.getNbPont() != 0)
                    for (Pont pSoluce : ileSolution.getListePont()) {
                        if(p.estSimilaire(pSoluce))
                            result += (p.getNbPont() == pSoluce.getNbPont() ? 0 : 1);
                    }
            }
        }
        return result;
    }

    /**
     * Récupère la solution d'une ile recherchée
     * @param ile L'ile chercher
     * @return la solution de l'ile chercher
     */
    private Ile getIleSolution(Ile ile){
        Ile solution = null;
        for (Ile ileSoluce: this.solution.getListIle()) {
            if (ile.equals(ileSoluce)){
                solution = ileSoluce;
            }
        }
        return solution;
    }


    public static void main3(String[] args) throws IOException {
        Grille grilleTest = new Grille("NiveauxFacile/Niveau10");
        grilleTest.getGrilleFromYAML(false);
        System.out.println(grilleTest.getGrilleSolution().toString());
    }
    public static void main2(String[] args){
        Grille grilleTest = new Grille();
        Ile ile1 = new Ile(1,1,4,1);
        Ile ile2 = new Ile(2,2,4,9);
        grilleTest.ajouterIle(ile1);
        grilleTest.ajouterIle(ile2);
        System.out.println(grilleTest);
    }

    public static void main(String[] args){
        Grille grilleTest = new Grille();
        Ile ile1 = new Ile(1,1,4,1);
        Ile ile2 = new Ile(2,2,4,9);
        grilleTest.ajouterIle(ile1);
        grilleTest.ajouterIle(ile2);
        System.out.println(grilleTest);
        Grille grilleCopy = new Grille(grilleTest);
        grilleCopy.removeIle(ile1);
        System.out.println(grilleTest);
    }
}
