/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statePattern;

/**
 *
 * @author fornervi
 */
public interface Context {
   
   public void setState(State state);
   public State getDaState();
   
   public void startTimerWait();
   public void startTimerCreer();
   public void startTimerIci();
   public void startTimerDeCetteCouleur();
   public void startTimerDeplacer();

    public void updateCouleur();

    public void dessinerForme();

    public void updatePosition();
    
    public void updateForme();
    
    public void updateSelectedForme();
    
    public void deplacerForme();
}
