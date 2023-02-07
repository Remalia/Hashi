import java.util.*;
import java.awt.Color;

public class Fonctionnalite{

   Fonctionnalite(){
   } 

   /**
   * Fonction
   */
   public void undo(Grille grille){
      Pont pont = grille.pileSvg.pop();
      grille.pileRecup.push(pont);
   }

   /**
    * 
    */
   public void redo(Grille grille){
      grille.pileSvg.push(grille.pileRecup.pop());
   }

   /**
    * @return
    */
   public boolean check(Grille grille){
      return false;
   }

   /**
    * 
    */
   public void donnerTechnique(Grille grille){

   }

   /**
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
      try {
         grille.pileSvg.push(new Pont(new Ile(1,3,1,1,Color.black), new Ile(2,2,5,1,Color.black), Color.BLACK));
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      System.out.println(grille.toString());
   }

}
