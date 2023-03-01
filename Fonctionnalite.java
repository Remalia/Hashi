import java.awt.Color;

public class Fonctionnalite{

   Fonctionnalite(){
   } 

   /**
    * 
    * @param grille
    * 
    * Fonction
    */
   public void undo(Grille grille){
      Pont pont = grille.pileSvg.pop();
      grille.retirerPont(pont);
      grille.pileRecup.push(pont);
   }

   /**
    * 
    * @param grille
    * 
    */
   public void redo(Grille grille){
      Pont pont = grille.pileSvg.push(grille.pileRecup.pop());
      grille.ajouterPont(pont.getIle1(), pont.getIle2());
      grille.pileSvg.push(pont);
   }

   /**
    * @return
    */
   public boolean check(Grille grille){
      return false;
   }

   /**
    * 
    * @param grille
    * 
    */
   public void donnerTechnique(Grille grille){

   }

   /**
    * 
    * @param grille
    * 
    */
   public void hypothese(Grille grille){
      
   }

   public static void main(String args[]){
      int[][] init = {
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
         {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
      };

      Grille grille = new Grille(init);
      Fonctionnalite fonc = new Fonctionnalite();
      Color c = new Color(0, 0, 255);
      try {
         grille.ajouterPont(new Ile(1,2,0,0,c), new Ile(2,2,0,10,c));
      } catch (Exception e) {
         e.printStackTrace();
      }
      System.out.println(grille.toString());
      fonc.undo(grille);
      System.out.println(grille.toString());
      fonc.redo(grille);
      System.out.println(grille.toString());
   }

}
