package Application.BackEnd.Grille;

import Application.BackEnd.Commandes.Action;
import Application.BackEnd.Sauvegarde.Parser;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.awt.Color;
import java.util.regex.*;

/**
 * Classe représentant la grille du jeu
 */
public class Grille {

    Color c = new Color(0, 0, 255);
    public Stack<Action> pileSvg;
    public Stack<Action> pileRecup;
    private Element[][] matriceGrille;
    private ArrayList<Ile> listIle;
    private File fileNiveau;
    private File fileSave;
    private String name;
    private boolean modeHyp;
    private int difficulte;

    /**
     * Constructeur de la grille avec un nom de niveau
     * @param name le nom du niveau
     */
    public Grille(String name) throws IOException {
        this.pileSvg = new Stack<>();
        this.pileRecup = new Stack<>();
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
                matriceGrille[i][j] = new Element();
            }
    }

    /**
     * Constructeur de la grille
     */
    public Grille(){
        // remplissage de la grille temporaire pour les tests */
        this.pileSvg = new Stack<>();
        this.pileRecup = new Stack<>();
        this.listIle = new ArrayList<>();
        this.matriceGrille = new Element[10][10];
        int i, j;
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                matriceGrille[i][j] = new Element();
            }
        }
    }

    public Grille (Grille grilleSolution){
        this.pileSvg = new Stack<>();
        this.pileRecup = new Stack<>();
        this.listIle = grilleSolution.getListIle();
        this.matriceGrille = new Element[10][10];
        int i, j;
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                matriceGrille[i][j] = grilleSolution.getMatriceGrille()[i][j].donneIle();
            }
        }

    }

    /**
     * Retourne la file du niveau
     * @return la file du niveau
     */
    public File getFileNiveau() {
        return fileNiveau;
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
        matriceGrille[abs][ord].nettoyerCase();
        Element actuel = matriceGrille[abs][ord];
        Ile temp;
        //Une fois l'île créée on éssaye de trouver des îles dans les 4 directions
        for(Direction d : Arrays.asList(Direction.values()) ){
            temp = actuel.parcoursMatrice(abs, ord, d ,this.matriceGrille);
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
        matriceGrille[ile.getAbs()][ile.getOrd()].nettoyerCase();
        matriceGrille[ile.getAbs()][ile.getOrd()] = new Element();
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
        //Si on peut on vérifie si le pont est horizontal ou vertical
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){
                return matriceGrille[ile1.getAbs()][ile1.getOrd() + 1].donnePont(ile1, ile2);
            }else{
                return matriceGrille[ile1.getAbs()][ile1.getOrd() - 1].donnePont(ile1, ile2);
            }
        }else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){
                return matriceGrille[ile1.getAbs() + 1][ile1.getOrd()].donnePont(ile1, ile2);
            }else{
                return matriceGrille[ile1.getAbs() - 1][ile1.getOrd()].donnePont(ile1, ile2);
            }
        }
        return null;
    }



    /** 
     * Ajoute un pont à la grille
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return void
     */
    public void  ajouterPont(Ile ile1, Ile ile2, int nbPonts){
        int i;
        Pont pont = chercherPont(ile1,ile2);

        if(pont != null){
            //Si il y'a déjà un pont on incrémente le nombre de ponts
            System.out.println("pont trouvé entre"+ile1.getAbs()+","+ile1.getOrd()+" et "+ile2.getAbs()+","+ile2.getOrd());
            pont.setNombrePont(nbPonts);
            return;
        }

        //Si il n'existe pas de pont on en créé un
        pont = new Pont(ile1,ile2,nbPonts);
        //Si on peut on vérifie si le pont est horizontal ou vertical
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){
                for(i = ile1.getOrd() + 1; i < ile2.getOrd() ; i++)
                    //Si il y'a déjà un pont on crée une intersection
                    if(matriceGrille[ile1.getAbs()][i] instanceof Pont){
                        matriceGrille[ile1.getAbs()][i] = new Intersection((Pont)matriceGrille[ile1.getAbs()][i], pont);
                    }
                    else if(matriceGrille[ile1.getAbs()][i] instanceof Intersection){
                        ((Intersection)matriceGrille[ile1.getAbs()][i]).setPont(ile1, ile2, pont);
                    }
                    else{
                        matriceGrille[ile1.getAbs()][i] = pont;
                    }

            }else{
                for(i = ile2.getOrd() + 1; i < ile1.getOrd(); i++)
                    if(matriceGrille[ile1.getAbs()][i] instanceof Pont){
                        matriceGrille[ile1.getAbs()][i] = new Intersection((Pont)matriceGrille[ile1.getAbs()][i], pont);
                    }
                    else if(matriceGrille[ile1.getAbs()][i] instanceof Intersection){
                        ((Intersection)matriceGrille[ile1.getAbs()][i]).setPont(ile1, ile2, pont);
                    }
                    else{
                        matriceGrille[ile1.getAbs()][i] = pont;
                    }

            }
        } else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){
                for(i = ile1.getAbs() + 1; i < ile2.getAbs(); i++)

                    if(matriceGrille[i][ile2.getOrd()] instanceof Pont){
                        matriceGrille[i][ile2.getOrd()] = new Intersection((Pont)matriceGrille[i][ile2.getOrd()] , pont);
                    }
                    else if(matriceGrille[i][ile2.getOrd()] instanceof Intersection){
                        ((Intersection)matriceGrille[i][ile2.getOrd()]).setPont(ile1, ile2, pont);
                    }
                    else{
                        matriceGrille[i][ile2.getOrd()] = pont;
                    }

            }else{
                for(i = ile2.getAbs() + 1; i < ile1.getAbs(); i++)
                    if(matriceGrille[i][ile2.getOrd()] instanceof Pont){
                        matriceGrille[i][ile2.getOrd()] = new Intersection((Pont)matriceGrille[i][ile2.getOrd()] , pont);
                    }
                    else if(matriceGrille[i][ile2.getOrd()] instanceof Intersection){
                        ((Intersection)matriceGrille[i][ile2.getOrd()]).setPont(ile1, ile2, pont);
                    }
                    else{
                        matriceGrille[i][ile2.getOrd()] = pont;
                    }
            }
        }

    }


    /**
     * Incrémente la valeur d'un pont
     * @param pont le pont à incrémenter
     */
    public void incrementerPont(Pont pont){
        pont.ajoutNombrePont();
        //pileSvg.add(pont);
        //TODO Ajouter Action D'ajout de pont
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
     * Vérifie si la création d'un pont est possible
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return Application.BackEnd.Grille.Element si trouvé sur le chemin autre que pont ou intersection ( possiblement null ou ile) sinon null
     */
    @Deprecated
    public Element verifCreationPont(Ile ile1, Ile ile2){
        int i;
        // si pont vertical
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){ // si ile1 est en haut
                for(i = ile1.getOrd() + 1; i < ile2.getOrd() - 1; i++){
                    // on peut tomber sur : ile , pont , intersection
                    if(matriceGrille[ile1.getAbs()][i] != null){
                        // si intersection ou pont
                        if((matriceGrille[ile1.getAbs()][i] instanceof Intersection && ((Intersection) matriceGrille[ile1.getAbs()][i]).estIncrementable(ile1,ile2) ) || matriceGrille[ile1.getAbs()][i] instanceof Pont){
                            // System.out.println("pont possible sur la case "+ ile1.getAbs() + " " + i );
                        }else{
                            return matriceGrille[ile1.getAbs()][i];
                        }
                    }
                }
            }else{ // si ile2 est en haut
                for(i = ile2.getOrd() + 1; i < ile1.getOrd() - 1; i++){
                    // on peut tomber sur : ile , pont , intersection
                    if(matriceGrille[ile1.getAbs()][i] != null){
                        // si intersection ou pont
                        if((matriceGrille[ile1.getAbs()][i] instanceof Intersection && ((Intersection) matriceGrille[ile1.getAbs()][i]).estIncrementable(ile1,ile2) ) || matriceGrille[ile1.getAbs()][i] instanceof Pont){
                            // System.out.println("pont possible sur la case "+ ile1.getAbs() + " " + i );
                        }else{
                            return matriceGrille[ile1.getAbs()][i];
                        }
                    }
                }
            }
        // si horizontal
        }else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){ //  si ile1 est à gauche
                for(i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++){
                    // on peut tomber sur : ile , pont , intersection
                    if(matriceGrille[ile1.getOrd()][i] != null){
                        // si intersection ou pont
                        if((matriceGrille[ile1.getOrd()][i] instanceof Intersection && ((Intersection) matriceGrille[ile1.getOrd()][i]).estIncrementable(ile1,ile2) ) || matriceGrille[ile1.getOrd()][i] instanceof Pont){
                            // System.out.println("pont possible sur la case "+ ile1.getOrd() + " " + i );
                        }else{
                            return matriceGrille[ile1.getOrd()][i];
                        }
                    }
                }

            }else{ // si ile2 est à gauche
                for(i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++){
                    // on peut tomber sur : ile , pont , intersection
                    if(matriceGrille[ile1.getOrd()][i] != null){
                        // si intersection ou pont
                        if((matriceGrille[ile1.getOrd()][i] instanceof Intersection && ((Intersection) matriceGrille[ile1.getOrd()][i]).estIncrementable(ile1,ile2) ) || matriceGrille[ile1.getOrd()][i] instanceof Pont){
                            // System.out.println("pont possible sur la case "+ ile1.getOrd() + " " + i );
                        }else{
                            return matriceGrille[ile1.getOrd()][i];
                        }
                    }
                }
            }
        } // traitement si null
        System.out.println("pont possible à créer");
        return null;
    }

    public boolean estIncrementable(Ile ile1,Ile ile2) {
        int i;
        // si pont vertical
        if (ile1.getAbs() == ile2.getAbs()) {
            if (ile1.getOrd() < ile2.getOrd()) { // si ile1 est en haut
                for (i = ile1.getOrd() + 1; i < ile2.getOrd() - 1; i++) {
                    if (matriceGrille[ile1.getAbs()][i].estIncrementable(ile1,ile2) == false) {
                        System.out.println("1Incrementation impossible sur la case " + ile1.getAbs() + " " + i + " car " + matriceGrille[ile1.getAbs()][i].getClass().getName());
                        return false;
                    }
                }
            } else { // si ile2 est en haut
                for (i = ile2.getOrd() + 1; i < ile1.getOrd() - 1; i++) {
                    if (matriceGrille[ile1.getAbs()][i].estIncrementable(ile1,ile2) == false) {
                        System.out.println("2Incrementation impossible sur la case " + ile2.getAbs() + " " + i + " car " + matriceGrille[ile2.getAbs()][i].getClass().getName());
                        return false;
                    }
                }
            }
        }
        // si horizontal
        else if (ile1.getOrd() == ile2.getOrd()) {
            if (ile1.getAbs() < ile2.getAbs()) { //  si ile1 est à gauche
                for (i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++) {
                    if (matriceGrille[i][ile1.getOrd()].estIncrementable(ile1,ile2) == false) {
                        System.out.println("3Incrementation impossible sur la case " + i + " " + ile1.getOrd() + " car " + matriceGrille[i][ile1.getOrd()].getClass().getName());
                        return false;
                    }
                }

            } else { // si ile2 est à gauche
                for (i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++) {
                    if (matriceGrille[i][ile1.getOrd()].estIncrementable(ile1,ile2) == false) {
                        System.out.println("4Incrementation impossible sur la case " + i + " " + ile1.getOrd() + " car " + matriceGrille[i][ile1.getOrd()].getClass().getName());
                        return false;
                    }
                }
            }
        } // traitement si null
        return true;
    }

    /**
     * Récupère la grille et l'initialise terminer
     * @throws FileNotFoundException Fichier non trouvé
     */
    public void getGrilleFromYAML(File file) throws FileNotFoundException {
        HashMap<String,String> balises = Parser.getAllBalise(file);
        if (balises.get("type").equals("fichierNiveau")){
            difficulte = Integer.parseInt(balises.get("difficulte"));
            balises.forEach((key, val) -> {
                if (key.matches("ile[0-9]+")){
                    int id = obtainsIdElement(key);
                    int abs = Integer.parseInt(val.substring(0,val.indexOf("|")-1));
                    int ord = Integer.parseInt(val.substring(val.indexOf("|")+2,val.lastIndexOf("|")-1));
                    int num = Integer.parseInt(val.substring(val.lastIndexOf("|")+2));
                    Ile ile = new Ile(id,num,abs,ord);
                    ajouterIle(ile);
                }
            });
            balises.forEach((key, val) -> {
                setupPontFromRegex("pont[0-9]+",key,val);
                setupPontFromRegex("pontSvg[0-9]+",key,val);
                setupPontFromRegex("pontRecup[0-9]+",key,val);
            });
        }
    }

    /**
     * Permet depuis un regex en particulier récupère un pont et l'ajoute soit à la pile de recup, sauvegarde ou la liste de pont
     * @param regex Le regex (3 disponibles : 'pont[0-9]+'  |  'pontSvg[0-9]+'  |  'pontRecup[0-9]+' )
     * @param key La clé de la balise
     * @param val la valeur de la balise
     */
    private void setupPontFromRegex(String regex,String key,String val){
        if (key.matches(regex)){
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
            if(regex.contains("pont["))
                this.ajouterPont(ile1,ile2,nbPont);
            if(regex.contains("pontSvg")) {
                Pont p = chercherPont(ile1,ile2);
                //pileSvg.add(p);
                //TODO Action d'ajout de pont
            }
            if(regex.contains("pontRecup")){
                Pont p = chercherPont(ile1,ile2);
                //pileRecup.add(p);
                //TODO Action d'ajout de pont
            }
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
                if(!listTemp.contains(p) && p.getNombrePont() != 0){
                    writer.write("  pont" + idPont + ": ile" +p.getIle1().getId() + " | ile"+ p.getIle2().getId() + " | " + p.getNombrePont() + "\n");
                    idPont++;
                    listTemp.add(p);
                }
            }
        }
        writer.write("pileSvg: #( pont --> ileUn | ileDeux | nbPont )\n");
        /*for (Pont p: this.pileSvg) {
            //TODO Adapter la pile de recup^et de sauvegarde avec des actions
            writer.write("  pontSvg" + idPont + ": ile" +p.getIle1().getId() + " | ile"+ p.getIle2().getId() + " | " + p.getNombrePont() + "\n");
            idPont++;
        }
        writer.write("pileRecup: #( pont --> ileUn | ileDeux | nbPont )\n");
        for (Pont p: this.pileRecup) {
            writer.write("  pontRecup" + idPont + ": ile" +p.getIle1().getId() + " | ile"+ p.getIle2().getId() + " | " + p.getNombrePont() + "\n");
            idPont++;
        }*/
        writer.close();
    }

    /**
     * Récupère l'id d'une clé
     * @param key la clé string
     * @return -1 si il n'y a pas d'id sinon l'id
     */
    private int obtainsIdElement(String key){
        int result = -1;
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(key);
        while(m.find()){
            result = Integer.parseInt(m.group(0));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Grille grilleTest = new Grille("NiveauTest");
        grilleTest.getGrilleFromYAML(grilleTest.getFileNiveau());
        grilleTest.saveGrilleToYAML();
        System.out.println(new Grille(grilleTest));
    }
    public static void main2(String[] args){
        Grille grilleTest = new Grille();
        Color c = new Color(0, 0, 255);
        Ile ile1 = new Ile(1,1,4,1,c);
        Ile ile2 = new Ile(2,2,4,9,c);
        Ile ile3 = new Ile(3,3,1,5,c);
        Ile ile4 = new Ile(4,4,9,5,c);
        Ile ile5 = new Ile(5,5,4,3);
        grilleTest.ajouterIle(ile1);
        grilleTest.ajouterIle(ile3);
        System.out.println("nb ponts de l'ile 1 : "+ile1.getListePont().size());
        System.out.println("nb ponts de l'ile 5 : "+ile2.getListePont().size());
        System.out.println(grilleTest.toString());
        grilleTest.ajouterIle(ile2);
        grilleTest.ajouterIle(ile4);
        grilleTest.ajouterIle(ile5);
        System.out.println("nb ponts de l'ile 1 : "+ile1.getListePont().size());
        System.out.println("nb ponts de l'ile 3 : "+ile3.getListePont().size());
        System.out.println(grilleTest.toString());
        
    }
}
