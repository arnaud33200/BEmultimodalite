/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statePattern;

/**
 *
 * @author ladoucar
 */
public class IciState implements State {
    
    public String toString(){
      return "ICI STATE";
   }

    @Override
    public void doActionDessinForme(Context context) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doActionVoixRougeBleu(Context context) {
        
    }

    @Override
    public void doActionVoixDeCetteCouleur(Context context) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doActionVoixIci(Context context) {
    }

    @Override
    public void doActionClick(Context context) {
        context.updatePosition();
        context.setState(new WaitState());
    }

    @Override
    public void doActionSelection(Context context) {
    }

    @Override
    public void doActionTimerCreer(Context context) {
    }

    @Override
    public void doActionTimerIci(Context context) {
        context.setState(new InitState());
    }

    @Override
    public void doActionTimerWait(Context context) {
    }

    @Override
    public void doActionColorReceived(Context context) {
    }
    
}
