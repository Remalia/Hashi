package Application.BackEnd.Commandes;

import java.util.Iterator;
import java.util.Stack;

public class ActionHistory implements Iterable<Action>{
    private Stack<Action> history = new Stack<>();

    /**
     * Ajoute en haut de la pile une action
     * @param a l'action a ajouter.
     */
    public void push(Action a){
        history.push(a);
    }

    /**
     * Permet d'enlever l'action le plus haut de la stack d'historique et la renvoie
     * @return l'action pop
     */
    public Action pop(){
        return history.pop();
    }

    /**
     * Permet de vérifier si la pile d'historique est vide ou non
     * @return True / False en fonction de si la pile est vide
     */
    public boolean isEmpty(){
        return history.isEmpty();
    }

    /**
     * Permet de vider la pile d'historique
     */
    public void clear(){
        history.clear();
    }

    /**
     * Récupère la taille de la liste des historiques
     * @return la taille
     */
    public int length(){
        return history.size();
    }

    /**
     * Permet d'itérer sur chacune des valeurs d'une actionHistory
     * @return l'iterator
     */
    @Override
    public Iterator<Action> iterator() {
        return history.iterator();
    }
}
