import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.awt.Color;
import java.util.regex.*;

public class Grille {

    Color c = new Color(0, 0, 255);
    Stack<Pont> pileSvg;
    Stack<Pont> pileRecup;
    private Element[][] matriceGrille;
    private ArrayList<Ile> listIle;
    private File fileNiveau;
    private File fileSave;
    private String name;
    private boolean modeHyp;
    private int difficulte;

    /**
     * Constructeur de la grille
     */

    public Grille(String name) throws IOException {
        this.pileSvg = new Stack<Pont>();
        this.pileRecup = new Stack<Pont>();
        this.listIle = new ArrayList<>();
        this.modeHyp = false;
        this.name = name;
        Files.createDirectories(Paths.get("Niveau"));
        Files.createDirectories(Paths.get("Niveau/"+this.name));
        try{
            Files.createFile(Path.of("Niveau/" + this.name + "/Niveau.yaml"));
        } catch (IOException e) {
            System.out.println("Fichier de niveau déja créé : " + e.getMessage());
        }
        try {
            Files.createFile(Path.of("Niveau/" + this.name + "/Save.yaml"));
        } catch (IOException e) {
            System.out.println("Fichier de sauvegarde déja créé : " + e.getMessage());
        }
        this.fileNiveau = new File("Niveau/"+this.name+"/Niveau.yaml");
        this.fileSave = new File("Niveau/"+this.name+"/Save.yaml");
        matriceGrille = new Element[10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                matriceGrille[i][j] = new Element();
            }
    }

    public Grille(){
        // remplissage de la grille temporaire pour les tests */
        this.pileSvg = new Stack<Pont>();
        this.pileRecup = new Stack<Pont>();
        this.listIle = new ArrayList<>();
        matriceGrille = new Element[10][10];
        int i, j;
        // initialisation de la grille en dur TEMPORAIRE*/
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                matriceGrille[i][j] = new Element();
            }
        }
    }

    public File getFileNiveau() {
        return fileNiveau;
    }

    public File getFileSave() {
        return fileSave;
    }

    /**
     * Méthode d'ajout d'une nouvelle ile dans la matrice
     * @param ile L'ile a ajouter
     */
    void ajouterIle(Ile ile){
        int abs = ile.getAbs();
        int ord = ile.getOrd();
        listIle.add(ile);

        Ile temp;
        //Une fois l'île créée on éssaye de trouver des îles dans les 4 directions
        for(Direction d : Arrays.asList(Direction.values()) ){
            temp = matriceGrille[abs][ord].parcoursMatrice(abs, ord, d ,this.matriceGrille);
            if(temp != null){
                ajouterPont(ile, temp, 0);
            }
        }
        matriceGrille[abs][ord] = ile;
    }

    /**
     * Donne le pont entre deux iles
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont 
     * @return le pont entre les deux îles
     */
    public Pont chercherPont(Ile ile1, Ile ile2){
        //Si on peut on vérifie si le pont est horizontal ou vertical
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){
                // verif inter
                    if(matriceGrille[ile1.getAbs()][ile1.getOrd() + 1] instanceof Pont)
                        return (Pont) matriceGrille[ile1.getAbs()][ile1.getOrd() + 1];
                    else if (matriceGrille[ile1.getAbs()][ile1.getOrd() + 1] instanceof Intersection)
                        return ((Intersection) matriceGrille[ile1.getAbs()][ile1.getOrd() + 1]).getPont(ile1, ile2);
            }else{
                if(matriceGrille[ile1.getAbs()][ile1.getOrd() - 1] instanceof Pont)
                    return (Pont) matriceGrille[ile1.getAbs()][ile1.getOrd() - 1];
                else if (matriceGrille[ile1.getAbs()][ile1.getOrd() - 1] instanceof Intersection)
                    return ((Intersection) matriceGrille[ile1.getAbs()][ile1.getOrd() - 1]).getPont(ile1, ile2);
            }
        }else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){
                if(matriceGrille[ile1.getAbs() + 1][ile1.getOrd()] instanceof Pont)
                    return (Pont) matriceGrille[ile1.getAbs() + 1][ile1.getOrd()];
                else if (matriceGrille[ile1.getAbs() + 1][ile1.getOrd()] instanceof Intersection)
                    return ((Intersection) matriceGrille[ile1.getAbs() + 1][ile1.getOrd()]).getPont(ile1, ile2);
            }else{
                if(matriceGrille[ile1.getAbs() - 1][ile1.getOrd()] instanceof Pont)
                    return (Pont) matriceGrille[ile1.getAbs() - 1][ile1.getOrd()];
                else if (matriceGrille[ile1.getAbs() - 1][ile1.getOrd()] instanceof Intersection)
                    return ((Intersection) matriceGrille[ile1.getAbs() - 1][ile1.getOrd()]).getPont(ile1, ile2);
            }
        }
        //System.out.println("N'as pas trouvé de pont entre les deux îles");
        return null;
    }

    // potentiels voisins dans creation ile

    /** 
     * Ajoute un pont à la grille
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return void
     */
    public void  ajouterPont(Ile ile1, Ile ile2,int nbPonts){
        int i;
        Pont pont = chercherPont(ile1,ile2);

        if(pont != null){
            //Si il y'a déjà un pont on incrémente le nombre de ponts
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
                    else{
                        matriceGrille[ile1.getAbs()][i] = pont;
                    }

            }else{
                for(i = ile2.getOrd() + 1; i < ile1.getOrd(); i++)
                    if(matriceGrille[ile1.getAbs()][i] instanceof Pont){
                        matriceGrille[ile1.getAbs()][i] = new Intersection((Pont)matriceGrille[ile1.getAbs()][i], pont);
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
                    }else{
                        matriceGrille[i][ile2.getOrd()] = pont;
                    }

            }else{
                for(i = ile2.getAbs() + 1; i < ile1.getAbs(); i++)
                    if(matriceGrille[i][ile2.getOrd()] instanceof Pont){
                        matriceGrille[i][ile2.getOrd()] = new Intersection((Pont)matriceGrille[i][ile2.getOrd()] , pont);
                    }else{
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
        pileSvg.add(pont);
    }

    /**
     * Retire un pont de la grille
     * @param pont le pont à retirer
     */
    public void retirerPont(Pont pont){
        pont.setNombrePont(0);
        this.pileSvg.push(pont);
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
                s += matriceGrille[i][j].toString() + " ";
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
     * @return Element si trouvé sur le chemin autre que pont ou intersection ( possiblement null ou ile) sinon null
     */
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
                pileSvg.add(p);
            }
            if(regex.contains("pontRecup")){
                Pont p = chercherPont(ile1,ile2);
                pileRecup.add(p);
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
                if(!listTemp.contains(p)){
                    writer.write("  pont" + idPont + ": ile" +p.getIle1().getId() + " | ile"+ p.getIle2().getId() + " | " + p.getNombrePont() + "\n");
                    idPont++;
                    listTemp.add(p);
                }
            }
        }
        writer.write("pileSvg: #( pont --> ileUn | ileDeux | nbPont )\n");
        for (Pont p: this.pileSvg) {
            writer.write("  pontSvg" + idPont + ": ile" +p.getIle1().getId() + " | ile"+ p.getIle2().getId() + " | " + p.getNombrePont() + "\n");
            idPont++;
        }
        writer.write("pileRecup: #( pont --> ileUn | ileDeux | nbPont )\n");
        for (Pont p: this.pileRecup) {
            writer.write("  pontRecup" + idPont + ": ile" +p.getIle1().getId() + " | ile"+ p.getIle2().getId() + " | " + p.getNombrePont() + "\n");
            idPont++;
        }
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
        System.out.println(grilleTest);
    }
    public static void main2(String[] args){
        Grille grilleTest = new Grille();
        Color c = new Color(0, 0, 255);
        
        try {
            Ile ile1 = new Ile(1,1,4,1,c);
            Ile ile2 = new Ile(2,2,4,9,c);
            Ile ile3 = new Ile(3,3,1,5,c);
            Ile ile4 = new Ile(4,4,9,5,c);
            grilleTest.ajouterIle(ile1);
            grilleTest.ajouterIle(ile2);
            grilleTest.ajouterIle(ile3);
            grilleTest.ajouterIle(ile4);
            //grilleTest.ajouterPont(ile3,ile4);
            //grilleTest.ajouterPont(ile3,ile4);
            //grilleTest.ajouterPont(ile1,ile2,1);
            //grilleTest.ajouterPont(ile1,ile2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(grilleTest.toString());
        
    }
}
