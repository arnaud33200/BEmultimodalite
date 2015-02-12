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
public interface State {
   public void doActionDessinForme(Context context);
   public void doActionVoixRougeBleu(Context context);
   public void doActionVoixDeCetteCouleur(Context context);
   public void doActionVoixIci(Context context);
   public void doActionVoixDeplacer(Context context);
   public void doActionClick(Context context);
   public void doActionSelection(Context context);
   public void doActionTimerCreer(Context context);
   public void doActionTimerIci(Context context);
   public void doActionTimerWait(Context context);
   public void doActionInfoReceived(Context context);
}
