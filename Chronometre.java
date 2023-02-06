public class Chronometre {

    private long tempsDepart=0;
    private long tempsFin=0;
    private long pauseDepart=0;
    private long pauseFin=0;
    private long duree=0;

    Chronometre(){
    }

    public void start(){
        tempsDepart=System.currentTimeMillis();
        razVal(tempsFin, pauseDepart, pauseFin, duree);
    }

    public void pause(){
        if(tempsDepart!=0){
        pauseDepart=System.currentTimeMillis();
        }
    }

    public void reprendre(){
        if(tempsDepart!=0 || pauseDepart!=0){
        pauseFin=System.currentTimeMillis();
        tempsDepart=tempsDepart+pauseFin-pauseDepart;
        razVal(tempsFin, pauseDepart, pauseFin, duree);
        }
    }
        
    public void stop(){
        if(tempsDepart!=0){
            tempsFin=System.currentTimeMillis();
            duree=(tempsFin-tempsDepart) - (pauseFin-pauseDepart);
            razVal(tempsDepart, tempsFin, pauseDepart, pauseFin);
        }
    }

    private void razVal(Long l1, Long l2, Long l3, Long l4){
        l1 = 0L;
        l2 = 0L;
        l3 = 0L;
        l4 = 0L;
    }
    
    public long getDureeMs(){
        return duree;
    }  

    public long getDureeSec(){
        return duree/1000;
    }
}
