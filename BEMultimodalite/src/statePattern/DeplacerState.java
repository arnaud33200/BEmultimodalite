/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statePattern;

/**
 *
 * @author Vincent
 */
public class DeplacerState implements State {
    
    public String toString(){
      return "DEPLACER STATE";
   }

    @Override
    public void doActionDessinForme(Context context) {
        context.updatePosition();
        context.updateSelectedForme();
        context.deplacerForme();
        context.setState(new InitState());
    }

    @Override
    public void doActionVoixRougeBleu(Context context) {
    }

    @Override
    public void doActionVoixDeCetteCouleur(Context context) {
    }

    @Override
    public void doActionVoixIci(Context context) {
    }

    @Override
    public void doActionClick(Context context) {
        context.updatePosition();
        context.updateSelectedForme();
        context.deplacerForme();
        context.setState(new InitState());
    }

    @Override
    public void doActionSelection(Context context) {
    }

    @Override
    public void doActionTimerCreer(Context context) {
    }

    @Override
    public void doActionTimerIci(Context context) {
    }

    @Override
    public void doActionTimerWait(Context context) {
    }

    @Override
    public void doActionColorReceived(Context context) {
    }
    
}
