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

    public Boolean estIncrementable(Ile ile1, Ile ile2){
        if(ile1 == pont1.getIle1() && ile2 == pont1.getIle2() || ile1 == pont1.getIle2() && ile2 == pont1.getIle1()){
            
    }
}