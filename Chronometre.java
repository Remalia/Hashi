/*
 * Chronomètre utilisé pour affecter un score au joueur
 */
public class Chronometre {

    private long tempsDepart=0;
    private long tempsFin=0;
    private long pauseDepart=0;
    private long pauseFin=0;
    private long duree=0;

    Chronometre(){
    }

    /**
     * Commence le chronomètre à 0
     */
    public void start(){
        tempsDepart=System.currentTimeMillis();
        razVal(tempsFin, pauseDepart, pauseFin, duree);
    }

    /*
     * Met pause au temps du chronomètre
     */
    public void pause(){
        if(tempsDepart!=0){
        pauseDepart=System.currentTimeMillis();
        }
    }

    /*
     * Reprend le chronomètre à la valeur de pause
     */
    public void reprendre(){
        if(tempsDepart!=0 || pauseDepart!=0){
        pauseFin=System.currentTimeMillis();
        tempsDepart=tempsDepart+pauseFin-pauseDepart;
        razVal(tempsFin, pauseDepart, pauseFin, duree);
        }
    }
       
    /*
     * Arrète le chrono et le met à 0
     */
    public void stop(){
        if(tempsDepart!=0){
            tempsFin=System.currentTimeMillis();
            duree=(tempsFin-tempsDepart) - (pauseFin-pauseDepart);
            razVal(tempsDepart, tempsFin, pauseDepart, pauseFin);
        }
    }

    /*
     * Met à 0 les long passés en paramètre
     */
    private void razVal(Long l1, Long l2, Long l3, Long l4){
        l1 = 0L;
        l2 = 0L;
        l3 = 0L;
        l4 = 0L;
    }
    
    /*
     * @return durée du chrono en milliseconde
     */
    public long getDureeMs(){
        return duree;
    }  

    /*
     * @return durée du chrono en seconde
     */
    public long getDureeSec(){
        return duree/1000;
    }
}
