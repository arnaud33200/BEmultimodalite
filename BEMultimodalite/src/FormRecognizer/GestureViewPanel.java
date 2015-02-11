/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormRecognizer;

import fr.dgac.ivy.BindType;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author ladoucar
 */
public class GestureViewPanel extends javax.swing.JPanel {

    private static Ivy bus;
    private static HashMap<String, IvyMessageListener> bindings;
    private static IvyMessageListener daListener;
    private static DefaultListModel listModel = new DefaultListModel();

    private Gesture myGesture;

    public GestureViewPanel() {
        initComponents();
        myGesture = new Gesture();
        formeView1.setShape(myGesture.circle);
        formeView2.setShape(myGesture.rectangle);
        bindings = new HashMap<String, IvyMessageListener>();
        bus = new Ivy("TEST", "Hellooooo", null);

        try {
            bus.start("127.255.255.255:666");
        } catch (IvyException ex) {
            //Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        addNewBinding(".*", new IvyMessageListener() {
            @Override
            public void receive(IvyClient ic, String[] strings) {
                String m = "";
                if (strings.length > 0) {
                    m += strings[0];
                }
                System.out.println(">>> - " + ic.getHostName() + " : " + m);
            }
        });
    }
    
    public void addGestureListener(GestureListener l) {
        myGesture.addGestureListener(l);
    }

    private static void addNewBinding(String filter, IvyMessageListener l) {
        bindings.put(filter, l);
        try {
            bus.bindAsyncMsg(filter, l, BindType.SWING);
        } catch (IvyException ex) {
            //Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void drawRectangle(int x, int y, int w, int h, int back, int border) {
        try {
            bus.sendMsg("Palette:CreerRectangle x=" + x + " y=" + y);
        } catch (IvyException ex) {

        }
    }

    private void drawCircle(int x, int y, int w, int h, int back, int border) {
        try {
            bus.sendMsg("Palette:CreerEllipse x=" + x + " y=" + y + "Longueur=" + w + " hauteur=" + h);
        } catch (IvyException ex) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formeView1 = new FormRecognizer.FormeView();
        formeView2 = new FormRecognizer.FormeView();
        jPanel1 = new javax.swing.JPanel();

        formeView1.setText("Ellispe");

        formeView2.setText("Rectangle");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formeView1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formeView2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(formeView1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(formeView2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        //myGesture = new Gesture();
        formeView1.setHighlight(false);
        formeView2.setHighlight(false);
        Graphics g = evt.getComponent().getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval(evt.getX() - 5, evt.getY() - 5, 11, 11);
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        myGesture.collectNewPoint(new PointGeste((double)evt.getX(), (double)evt.getY()));
        
        Graphics g = evt.getComponent().getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval(evt.getX()-3, evt.getY()-3, 7, 7);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseReleased
        myGesture.finishCollection();
        
        if (myGesture.forme == Gesture.SHAPECIRCLE) {
            drawCircle(100, 100, 100, 100, 100, 100);
            formeView1.setHighlight(true);
        }
        else if (myGesture.forme == Gesture.SHAPERECTANGLE) {
            drawRectangle(100, 100, 100, 100, 100, 100);
            formeView2.setHighlight(true);
        } 
        repaint();
    }//GEN-LAST:event_jPanel1MouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private FormRecognizer.FormeView formeView1;
    private FormRecognizer.FormeView formeView2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
