/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormRecognizer;

import java.awt.Event;
import java.util.EventListener;

/**
 *
 * @author ladoucar
 */
public abstract class GestureListener implements EventListener {
    public abstract void GestureRectangleRecognized(Event e);
    public abstract void GestureEllispeRecognized(Event e);
}
