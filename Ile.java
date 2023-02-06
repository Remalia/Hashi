import java.awt.Color;

public class Ile {
    int id; /** identifiant de l'île */
    int num; /** numéro associé à l'île */
    int nbPonts; /** nombre de ponts rattaché à l'île */
    int abs; /** abscisse de l'île */
    int ord; /** ordonnée de l'île */
    Color couleur; /** couleur actuelle de l'île */
    boolean estSelect; /** statut de sélection de l'île */
    
    Ile(int id, int num, int abs, int ord) throws Exception{
        if(num >= 1 && num <= 8){
            this.id = id;
            this.num = num;
            this.abs = abs;
            this.ord = ord;
            this.nbPonts = 0; /** à la création il n'y a pas de ponts */
            this.couleur = new Color(100, 0, 0); /** couleur initiale */
            this.estSelect = false; /** île non selectionée à la création */
        } else{
            throw new Exception("Création d'île impossible, numéro incorrect");
        }
    }

    /**
    * Incrémente de 1 le nombre de ponts attachés à l'île
    *
    */
    void ajouterPont(){
        this.nbPonts += 1;
    }

    /**
    * Décrémente de 2 le nombre de ponts attachés à l'île
    *
    */
    void supprimer2Ponts() throws Exception{
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
    int getId(){
        return this.id;
    }

    /**
    * Retourne le numéro de l'île
    *
    * @return le numéro de l'île
    */
    int getNum(){
        return this.num;
    }

    /**
    * Retourne l'abscisse de l'île
    *
    * @return l'abscisse de l'île
    */
    int getAbs(){
        return this.abs;
    }

    /**
    * Retourne l'ordonnée de l'île
    *
    * @return l'ordonnée de l'île
    */
    int getOrd(){
        return this.ord;
    }

    /**
    * Retourne le nombre de ponts attachés à l'île
    *
    * @return le nombre de ponts attachés à l'île
    */
    int getNbPonts(){
        return this.nbPonts;
    }

    /**
    * Retourne la couleur de l'île
    *
    * @return la couleur de l'île
    */
    Color getCouleur(){
        return this.couleur;
    }

    /**
    * Retourne si l'île est sélectionnée
    *
    * @return true si l'île est sélectionnée, false sinon
    */
    boolean getEstSelect(){
        return this.estSelect;
    }

    /**
    * Affecte un entier correspondant au nombre de ponts attachés à l'île
    *
    * @param  nb  le nombre de ponts attachés à l'île
    */
    void setNbPonts(int nb){
        this.nbPonts = nb;
    }

    /**
    * Affecte une couleur à l'île
    *
    * @param  c  la couleur à affecter
    */
    void setCouleur(Color c){
        this.couleur = c;
    }

    /**
    * Met à jour le statut sélectionné ou non sélectionné de l'île
    *
    * @param  s  true si l'île est sélectionnée, false sinon
    */
    void setEstSelect(boolean s){
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
            Ile ileTest = new Ile(1, 5, 4, 2);
            System.out.println(ileTest.toString());
            ileTest.ajouterPont();
            ileTest.ajouterPont();
            System.out.println(ileTest.toString());
            ileTest.supprimer2Ponts();
            System.out.println(ileTest.toString());
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }

}
