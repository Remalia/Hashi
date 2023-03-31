package Application.BackEnd.Commandes;

import java.awt.Color;
import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Pont;

public class Fonctionnalite{

   Fonctionnalite(){
   } 

   /**
    * 
    * @param grille
    * 
    * Fonction
    */
   /*public void undo(Grille grille){
      Pont pont = grille.pileSvg.pop();
      //grille.retirerPont(pont);
      grille.pileRecup.push(pont);
   }*/

   /**
    * 
    * @param grille
    * 
    */
   /*public void redo(Grille grille){
      Pont pont = grille.pileSvg.push(grille.pileRecup.pop());
      grille.ajouterPont(pont.getIle1(), pont.getIle2(),pont.getNombrePont());
      grille.pileSvg.push(pont);
   }*/

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

      Grille grille = new Grille();
      Fonctionnalite fonc = new Fonctionnalite();
      Color c = new Color(0, 0, 255);
      try {
         grille.ajouterPont(new Ile(1,2,0,0,c), new Ile(2,2,0,10,c),1);
      } catch (Exception e) {
         e.printStackTrace();
      }
      System.out.println(grille);
   }

}
