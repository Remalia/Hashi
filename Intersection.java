
/**
 * Classe d'intersection entre deux ponts
 * @see Element
 * @see Pont
 */
public class Intersection extends Element {
    private Pont pont1;
    private Pont pont2;
    
    /**
     * Constructeur de la classe Intersection
     * @param p1 premier pont
     * @param p2 deuxième pont
     */
    public Intersection(Pont p1, Pont p2){
        pont1 = p1;
        pont2 = p2;
    }
    
    /**
     * @return le premier pont de l'intersection
     */
    public Pont getPont1(){
        return pont1;
    }
    /**
     * @return le deuxième pont de l'intersection
     */
    public Pont getPont2(){
        return pont2;
    }
    /**
     * @param p Pont à set en premier pont de l'intersection
     */
    public void setPont1(Pont p){
        pont1 = p;
    }

    /**
     * @param p Pont à set en deuxième pont de l'intersection
     */
    public void setPont2(Pont p){
        pont2 = p;
    }

    /**
     * @return true si le pont p de l'intersection peut etre incrementé
     */
    public Boolean estIncrementable(Pont p){
        if(p == pont1){
            return pont2.getNombrePont() == 0;
        }else{
            return pont1.getNombrePont() == 0;
        }
    }
    /**
     * @param ile1 une ile de l'intersection
     * @param ile2 une ile de l'intersection
     * @return true si le pont p de l'intersection peut etre incrementé
     */
    public Boolean estIncrementable(Ile ile1, Ile ile2){
        // si on est sur le pont 1
        if(ile1 == pont1.getIle1() && ile2 == pont1.getIle2() || ile1 == pont1.getIle2() && ile2 == pont1.getIle1()){
            return estIncrementable(this.pont1);
        // si on est sur le pont 2
        }else if(ile1 == pont2.getIle1() && ile2 == pont2.getIle2() || ile1 == pont2.getIle2() && ile2 == pont2.getIle1()){
            return estIncrementable(this.pont2);
        }
        System.out.println("une des iles donnée n'est pas dans l'intersection");
        return false;
    }

    /**
     * @param ile1 une ile de l'intersection
     * @param ile2 une ile de l'intersection
     * @return true si le pont p de l'intersection peut etre decrementé
     */
    public Pont getPont(Ile ile1, Ile ile2){
        // si on est sur le pont 1
        if(ile1 == pont1.getIle1() && ile2 == pont1.getIle2() || ile1 == pont1.getIle2() && ile2 == pont1.getIle1()){
            return this.pont1;
        // si on est sur le pont 2
        }else if(ile1 == pont2.getIle1() && ile2 == pont2.getIle2() || ile1 == pont2.getIle2() && ile2 == pont2.getIle1()){
                return this.pont2;
        }
        System.out.println("une des iles donnée n'est pas dans l'intersection");
        return null;
    }

    /**
     * 
     * @return l'etat de l'intersection (pont1, pont2 ou intersection)
     */
    public Element getEtat(){
        if(pont1.getNombrePont() > 0){
            return pont1;
        }else if(pont2.getNombrePont() > 0){
            return pont2;
        }else{
            return this;
        }
    }

    
    /**
     * Retire le pont des lists de ponts des îles
     */
    @Override
    public void nettoyerCase(){
        this.pont1.nettoyerCase();;
        this.pont2.nettoyerCase();;                                                                                  
    }


    /**
     * @return la chaine de caractère de l'intersection
     */
    public String toString(){
        if(pont1.getNombrePont() > 0){
            return pont1.toString();
        }else if(pont2.getNombrePont() > 0){
            return pont2.toString();
        }else{
            return "┼";
        }
    }

    /**
     * 
     * @param ile1 premiere ile du pont
     * @param ile2 deuxieme ile du pont
     * @return null si Element quelconque
     */
    public Pont donePont(Ile ile1, Ile ile2){
        return this.getPont(ile1, ile2);
    }
}