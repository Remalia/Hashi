package Application.BackEnd.Grille;

import Application.BackEnd.Commandes.Action;
import Application.BackEnd.Commandes.ActionHistory;
import Application.BackEnd.Sauvegarde.Parser;
import javafx.geometry.Orientation;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.regex.*;

/**
 * Classe représentant la grille du jeu
 */
public class Grille {

    Color c = Color.rgb(0, 0, 255);
    private final ActionHistory historySvg = new ActionHistory();
    private final ActionHistory historyRecup = new ActionHistory();
    private Element[][] matriceGrille;
    private final ArrayList<Ile> listIle;
    private File fileNiveau;
    private File fileSave;
    private String name;
    private boolean modeHyp;
    private int difficulte;

    private Grille solution;

    /**
     * Constructeur de la grille avec un nom de niveau
     * @param name le nom du niveau
     * @throws IOException Fichier/dossier déja créer
     */
    public Grille(String name) throws IOException {
        this.listIle = new ArrayList<>();
        this.modeHyp = false;
        this.name = name;
        Files.createDirectories(Paths.get("Application/Niveau"));
        Files.createDirectories(Paths.get("Application/Niveau/"+this.name));
        try{
            Files.createFile(Path.of("Application/Niveau/" + this.name + "/Niveau.yaml"));
        } catch (IOException e) {
            System.out.println("Fichier de niveau déja créé : " + e.getMessage());
        }
        try {
            Files.createFile(Path.of("Application/Niveau/" + this.name + "/Save.yaml"));
        } catch (IOException e) {
            System.out.println("Fichier de sauvegarde déja créé : " + e.getMessage());
        }
        this.fileNiveau = new File("Application/Niveau/"+this.name+"/Niveau.yaml");
        this.fileSave = new File("Application/Niveau/"+this.name+"/Save.yaml");
        this.matriceGrille = new Element[10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                matriceGrille[i][j] = Vide.getInstance();
            }
        this.solution = new Grille();
    }

    /**
     * Constructeur de la grille
     */
    public Grille(){
        this.listIle = new ArrayList<>();
        this.matriceGrille = new Element[10][10];
        int i, j;
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                matriceGrille[i][j] = Vide.getInstance();
            }
        }
    }

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
        //Une fois l'île créée on éssaye de trouver des îles dans les 4 directions
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
        // Sinon on crée un nouveau pont en fonction de l'orientation
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
     * ajoute un pont dans la grille
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
     * Incrémente la valeur d'un pont
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     */
    public boolean incrementerPont(Ile ile1, Ile ile2){
        Pont pont = chercherPont(ile1,ile2);
        if(pont != null) {
            pont.incrementerPont();
            actualiserPontDansGrille(pont);
            return true;
        }
        else{
            System.out.println("Erreur dans incrementerPont");
            return false;
        }
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
     * @param file le fichier à parser
     */
    public void getGrilleFromYAML(File file) throws FileNotFoundException {
        HashMap<String,String> balises = Parser.getAllBalise(file);
        if (balises.get("type").equals("fichierNiveau")){
            difficulte = Integer.parseInt(balises.get("difficulte"));
            balises.forEach(this.solution::setupIle);
            balises.forEach(this::setupIle);
            balises.forEach(this.solution::setupPont);
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
        writer.write(String.valueOf(this.difficulte));
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
        writer.write("historySvg: #( actionAjouterPont --> { ileUn } { ileDeux } nbPonts | oldNbPonts | modeHyp )\n");
        for (Action a: this.historySvg ) {
            writer.write(a.ecrireAction(true));
        }
        writer.write("historyRecup: #( actionAjouterPont --> { ileUn } { ileDeux } nbPonts | oldNbPonts | modeHyp )\n");
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
     * Vérifie si la grille est correcte
     */
    public boolean grilleCorrecte(){
        return this == this.solution;
    }

    public static void main(String[] args) throws IOException {
        Grille grilleTest = new Grille("NiveauTest");
        grilleTest.getGrilleFromYAML(grilleTest.getFileNiveau());
        System.out.println(grilleTest.toString());
    }
    public static void main2(String[] args){
        Grille grilleTest = new Grille();
        Ile ile1 = new Ile(1,1,4,1);
        Ile ile2 = new Ile(2,2,4,9);
        grilleTest.ajouterIle(ile1);
        grilleTest.ajouterIle(ile2);
        System.out.println(grilleTest.toString());


        
    }
}
