/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statePattern;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ladoucar
 */
public class WaitState implements State {

    public String toString() {
        return "WAIT STATE";
    }

    @Override
    public void doActionDessinForme(Context context) {
    }

    @Override
    public void doActionVoixRougeBleu(Context context) {
    }

    @Override
    public void doActionVoixDeCetteCouleur(Context context) {
        context.startTimerDeCetteCouleur();
        context.setState(new DeCetteCouleurState());
    }

    @Override
    public void doActionVoixIci(Context context) {
    }

    @Override
    public void doActionClick(Context context) {
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
        context.dessinerForme();
        context.setState(new InitState());
    }

    @Override
    public void doActionInfoReceived(Context context) {
    }

    @Override
    public void doActionVoixDeplacer(Context context) {

    }

    @Override
    public void doActionTimerDeCetteCouleur(Context context) {
    }
    
    @Override
    public void doActionTimerDeplacer(Context context) {
    }

}
