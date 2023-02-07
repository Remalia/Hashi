import java.awt.Color;

public class Ile {
    private int id; /** identifiant de l'île */
    private int num; /** numéro associé à l'île */
    private int nbPonts; /** nombre de ponts rattaché à l'île */
    private int abs; /** abscisse de l'île */
    private int ord; /** ordonnée de l'île */
    private Color couleur; /** couleur actuelle de l'île */
    private boolean estSelect; /** statut de sélection de l'île */
    
    public Ile(int id, int num, int abs, int ord, Color c) throws Exception{
        if(num >= 1 && num <= 8){
            this.id = id;
            this.num = num;
            this.abs = abs;
            this.ord = ord;
            this.nbPonts = 0; /** à la création il n'y a pas de ponts */
            this.couleur = c; /** couleur initiale */
            this.estSelect = false; /** île non selectionée à la création */
        } else{
            throw new Exception("Création d'île impossible, numéro incorrect");
        }
    }

    /**
    * Vérifie si l'île en paramètre est la même île
    *
    * @return true si l'île est la même, sinon false
    */
    public boolean equals(Ile ile){
        return ((this.abs == ile.abs) && (this.ord == ile.ord));
    }

    /**
    * Vérifie si l'île est complète
    *
    * @return true si l'île est complète, sinon false
    */
    public boolean estComplete(){
        return (this.num == this.nbPonts);
    }

    /**
    * Incrémente de 1 le nombre de ponts attachés à l'île
    *
    */
    public void ajouterPont(){
        this.nbPonts += 1;
    }

    /**
    * Décrémente de 2 le nombre de ponts attachés à l'île
    *
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
    *
    * @return l'identifiant de l'île
    */
    public int getId(){
        return this.id;
    }

    /**
    * Retourne le numéro de l'île
    *
    * @return le numéro de l'île
    */
    public int getNum(){
        return this.num;
    }

    /**
    * Retourne l'abscisse de l'île
    *
    * @return l'abscisse de l'île
    */
    public int getAbs(){
        return this.abs;
    }

    /**
    * Retourne l'ordonnée de l'île
    *
    * @return l'ordonnée de l'île
    */
    public int getOrd(){
        return this.ord;
    }

    /**
    * Retourne le nombre de ponts attachés à l'île
    *
    * @return le nombre de ponts attachés à l'île
    */
    public int getNbPonts(){
        return this.nbPonts;
    }

    /**
    * Retourne la couleur de l'île
    *
    * @return la couleur de l'île
    */
    public Color getCouleur(){
        return this.couleur;
    }

    /**
    * Retourne si l'île est sélectionnée
    *
    * @return true si l'île est sélectionnée, false sinon
    */
    public boolean getEstSelect(){
        return this.estSelect;
    }

    /**
    * Affecte un entier correspondant au nombre de ponts attachés à l'île
    *
    * @param  nb  le nombre de ponts attachés à l'île
    */
    public void setNbPonts(int nb){
        this.nbPonts = nb;
    }

    /**
    * Affecte une couleur à l'île
    *
    * @param  c  la couleur à affecter
    */
    public void setCouleur(Color c){
        this.couleur = c;
    }

    /**
    * Met à jour le statut sélectionné ou non sélectionné de l'île
    *
    * @param  s  true si l'île est sélectionnée, false sinon
    */
    public void setEstSelect(boolean s){
        this.estSelect = s;
    }

    public String toString(){
        String s = "Ile" + this.id + "\n";
        s += "numéro : " + this.num + "\n";
        s += "(" + this.abs + "," + this.ord + ")\n";
        s += this.couleur.toString() + "\n";
        if(this.estSelect)
            s += "sélectionnée\n";
        else 
            s += "non sélectionnée\n";
        s += "nombre de ponts : " + this.nbPonts;
        return s;
    }

    public static void main(String[] args){
        try {
            Color c = new Color(100, 0, 0);
            Ile ileTest = new Ile(1, 5, 4, 2, c);
            System.out.println(ileTest.toString());
            ileTest.ajouterPont();
            ileTest.ajouterPont();
            System.out.println(ileTest.toString());
            ileTest.supprimer2Ponts();
            System.out.println(ileTest.toString());
            System.out.println(ileTest.getClass());
            if(ileTest.getClass() == Ile.class) 
                System.out.println("bjr");
            System.out.println(ileTest.equals(ileTest));
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }

}
