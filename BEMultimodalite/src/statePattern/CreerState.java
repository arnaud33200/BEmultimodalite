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
public class CreerState implements State {
    
    public String toString(){
      return "CREER STATE";
   }

    @Override
    public void doActionDessinForme(Context context) {
    }

    @Override
    public void doActionVoixRougeBleu(Context context) {
        context.updateCouleur();
        context.dessinerForme();
        context.setState(new InitState());
    }

    @Override
    public void doActionVoixDeCetteCouleur(Context context) {
        context.setState(new DeCetteCouleurState());
    }

    @Override
    public void doActionVoixIci(Context context) {
        context.setState(new IciState());
    }

    @Override
    public void doActionClick(Context context) {
    }

    @Override
    public void doActionSelection(Context context) {

    }

    @Override
    public void doActionTimerCreer(Context context) {
        context.setState(new InitState());
        
    }

    @Override
    public void doActionTimerIci(Context context) {
        context.setState(new IciState());
    }

    @Override
    public void doActionTimerWait(Context context) {

    }

    @Override
    public void doActionColorReceived(Context context) {
    }

    
}
