package Application.BackEnd.Grille;

import javafx.scene.paint.Color;
import java.util.*;

/**
 * Classe représentant une île du jeu
 * @see Element
 */
public class Ile extends Element {
    private int id; /** identifiant de l'île */
    private int num; /** numéro associé à l'île */
    private int nbPonts; /** nombre de ponts rattaché à l'île */
    private final int abs; /** abscisse de l'île */
    private final int ord; /** ordonnée de l'île */
    private boolean estSelect; /** statut de sélection de l'île */
    private List<Pont> listePont;
    
    /**
     * Constructeur de la classe Application.BackEnd.Grille.Ile
     * @param id l'identifiant de l'île
     * @param num le numéro de l'île
     * @param abs l'abscisse de l'île
     * @param ord l'ordonnée de l'île
     * @param c la couleur de l'île
     */
    public Ile(int id, int num, int abs, int ord, Color c){
        super(c);
        this.id = id;
        this.num = num;
        this.abs = abs;
        this.ord = ord;
        this.listePont = new ArrayList<>();
        this.nbPonts = 0; // à la création il n'y a pas de ponts
        this.estSelect = false; // île non selectionée à la création
    }

    /**
     * Constructeur de la classe Application.BackEnd.Grille.Ile sans la couleur
     * @param id l'identifiant de l'île
     * @param num le numéro de l'île
     * @param abs l'abscisse de l'île
     * @param ord l'ordonnée de l'île
     */
    public Ile(int id, int num, int abs,int ord){
        super();
        this.id = id;
        this.num = num;
        this.abs = abs;
        this.ord = ord;
        this.listePont = new ArrayList<>();
        this.nbPonts = 0; // à la création il n'y a pas de ponts
        this.estSelect = false; // île non selectionée à la création
    }

    /**
     * Retourne la liste des ponts rattachés à l'île
     * @return la liste des ponts rattachés à l'île
     */
    public List<Pont> getListePont(){
        return this.listePont;
    }

    /**
    * Vérifie si l'île en paramètre est la même île
    * @return true si l'île est la même, sinon false
    */
    public boolean equals(Ile ile){
        return ((this.abs == ile.abs) && (this.ord == ile.ord));
    }

    /**
    * Vérifie si l'île est complète
    * @return true si l'île est complète, sinon false
    */
    public boolean estComplete(){
        return (this.num == this.nbPonts);
    }

    /**
    * Incrémente de 1 le nombre de ponts attachés à l'île
    */
    public void ajouterPont(Pont p){
        this.nbPonts += 1;
        listePont.add(p);
    }

    /**
    * Décrémente de 2 le nombre de ponts attachés à l'île
    */
    public void supprimer2Ponts() throws Exception{
        if(this.nbPonts >= 2){
            this.nbPonts -= 2;
        }
        else{
            throw new Exception("Suppression de ponts impossible");
        }
    }

    /**
    * Retourne l'identifiant de l'île
    * @return l'identifiant de l'île
    */
    public int getId(){
        return this.id;
    }

    /**
    * Retourne le numéro de l'île
    * @return le numéro de l'île
    */
    public int getNum(){
        return this.num;
    }

    /**
    * Retourne l'abscisse de l'île
    * @return l'abscisse de l'île
    */
    public int getAbs(){
        return this.abs;
    }

    /**
    * Retourne l'ordonnée de l'île
    * @return l'ordonnée de l'île
    */
    public int getOrd(){
        return this.ord;
    }

    /**
    * Retourne le nombre de ponts attachés à l'île
    * @return le nombre de ponts attachés à l'île
    */
    public int getNbPonts(){
        return this.nbPonts;
    }



    /**
    * Retourne si l'île est sélectionnée
    * @return true si l'île est sélectionnée, false sinon
    */
    public boolean getEstSelect(){
        return this.estSelect;
    }

    /**
    * Affecte un entier correspondant au nombre de ponts attachés à l'île
    * @param  nb  le nombre de ponts attachés à l'île
    */
    public void setNbPonts(int nb){
        this.nbPonts = nb;
    }

    /**
     * Affecte un numéro à l'île
     * @param num le numéro de l'île
     */
    public void setNum(int num){
        this.num = num;
    }

    /**
     * Affecte un identifiant à l'île
     * @param id l'identifiant de l'île
     */
    public void setId(int id){
        this.id = id;
    }

    /**
    * Met à jour le statut sélectionné ou non sélectionné de l'île
    * @param  s  true si l'île est sélectionnée, false sinon
    */
    public void setEstSelect(boolean s){
        this.estSelect = s;
    }

    public String toStringConsole(){
        String s = "Ile" + this.id + "\n";
        s += "numéro : " + this.num + "\n";
        s += "(" + this.abs + "," + this.ord + ")\n";
        s += this.getCouleur().toString() + "\n";
        if(this.estSelect)
            s += "sélectionnée\n";
        else 
            s += "non sélectionnée\n";
        s += "nombre de ponts : " + this.nbPonts;
        return s;
    }

    /**
     * Métode toString pour l'affichage graphique
     */
    @Override
    public String toString(){
        return Integer.toString(this.getNum());
    }

    /**
     * Retourne l'île si l'île est sélectionnée, sinon retourne null
     */
    @Override
    public Ile getIleFromDirection(int x, int y, Direction d, Element Grille[][]){
        return this;
    }

    /**
     * Retourne la liste des îles voisines de l'instance
     */
    public List<Ile> getIlesVoisines()
    {
        Ile i;
        List<Ile> voisins = new ArrayList<Ile>();
        for(Pont p: this.listePont)
        {
            i = p.getIle1();

            if(equals(i))
            {
                voisins.add(p.getIle2());
            }
            else
            {
                voisins.add(i);
            }
        }

        return voisins;
    }

    /**
     * Retourne le nombre de voisins de l'île même s'ils ne sont pas accessibles
     */
    public int getNbVoisins()
    {
        return this.listePont.size();
    }

    public static void main(String[] args){
        try {
            Color c = Color.rgb(100, 0, 0);
            Ile ileTest = new Ile(1, 5, 4, 2, c);
            System.out.println(ileTest.toStringConsole());
            System.out.println(ileTest.toStringConsole());
            ileTest.supprimer2Ponts();
            System.out.println(ileTest.toStringConsole());
            System.out.println(ileTest.getClass());
            if(ileTest.getClass() == Ile.class) 
                System.out.println("bjr");
            System.out.println(ileTest.equals(ileTest));
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }

}
