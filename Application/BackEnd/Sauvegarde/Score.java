package Application.BackEnd.Sauvegarde;

/**
 * Classe Score représentant un score de jeu
 */
public class Score {
    private String name;
    private int pts;
    private String timer;

    /**
     * Construit un score
     * @param pts Nombre de points
     * @param name Nom du joueur
     * @param timer Temps réalisé
     */
    Score(int pts,String name, String timer){
        this.pts = pts;
        this.name = name;
        this.timer = timer;
    }

    /**
     * Récupère le timer du score sous forme de String
     * @return Temps réalisé
     */
    public String getTimer() {
        return timer;
    }

    /**
     * Assigne un timer sous forme de string au score
     * @param timer Nouveau timer
     */
    public void setTimer(String timer) {
        this.timer = timer;
    }

    /**
     * Récupère les points du score
     * @return Nombre de points
     */
    public int getPts() {
        return pts;
    }

    /**
     * Assigne des nouveaux pts au score
     * @param pts Nouveaux pts
     */
    public void setPts(int pts) {
        this.pts = pts;
    }

    /**
     * Récupère le nom du joueur pour le score
     * @return Nom du joueur
     */
    public String getName() {
        return name;
    }

    /**
     * Assigne un nouveau nom de joueur pour le score
     * @param name Nouveau nom de joueur
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Fonction d'affichage dans la console des scores
     * @return String à afficher
     */
    @Override
    public String toString() {
        return "Score : " + name + ", " + pts + " pts en " + timer + " ";
    }
}
