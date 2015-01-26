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
import FormRecognizer.Gesture;
import FormRecognizer.PointGeste;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author ladoucar
 */
public class Main extends javax.swing.JFrame{

    private static Ivy bus;
    private static HashMap<String, IvyMessageListener> bindings;
    private static IvyMessageListener daListener;
    private static DefaultListModel listModel = new DefaultListModel();
    
    private Gesture myGesture;

    private static void addNewBinding(String filter, IvyMessageListener l) {
        bindings.put(filter, l);
        try {
            bus.bindAsyncMsg(filter, l, BindType.SWING);
        } catch (IvyException ex) {
            //Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Creates new form Main
     */
    public Main() {
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
    
    private void drawRectangle(int x, int y, int w, int h, int back, int border ) {
        try {
            bus.sendMsg("Palette:CreerRectangle x=" + x + " y=" + y);
        } catch (IvyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void drawCircle(int x, int y, int w, int h, int back, int border ) {
        try {
            bus.sendMsg("Palette:CreerEllipse x=" + x + " y=" + y + "Longueur=" + w + " hauteur=" + h);
        } catch (IvyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        /*if (myGesture != null) {
            g.setColor(Color.BLUE);
            ArrayList<Point> points = myGesture.getPoints();
            for (Point p : points) {
                g.fillOval(p.x, p.y, 3, 3);
            }
        }*/
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 300));
        jPanel1.setRequestFocusEnabled(false);
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
            .addGap(0, 296, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        jButton2.setText("1");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2MouseReleased(evt);
            }
        });

        jButton3.setText("2");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton3MouseReleased(evt);
            }
        });

        jButton6.setText("0");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton6MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton6MouseReleased(evt);
            }
        });

        jButton7.setText("3");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton7MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton7MouseReleased(evt);
            }
        });

        jButton8.setText("4");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton8MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton8MouseReleased(evt);
            }
        });

        jButton1.setText("Print Consol");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        myGesture = new Gesture();
        formeView1.setHighlight(false);
        formeView2.setHighlight(false);
        Graphics g = evt.getComponent().getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval(evt.getX()-5, evt.getY()-5, 11, 11);
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

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        Graphics g = jPanel1.getGraphics();
        g.setColor(Color.blue);
        ArrayList<PointGeste> points = myGesture.getStep1points();
        for (PointGeste p : points) {
            g.fillOval((int)p.getX()-3, (int)p.getY()-3, 7, 7);
        }
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseReleased
        jPanel1.repaint();
    }//GEN-LAST:event_jButton2MouseReleased

    private void jButton6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MousePressed
        Graphics g = jPanel1.getGraphics();
        g.setColor(Color.red);
        ArrayList<PointGeste> points = myGesture.getPoints();
        for (PointGeste p : points) {
            g.fillOval((int)p.getX()-3, (int)p.getY()-3, 7, 7);
        }
    }//GEN-LAST:event_jButton6MousePressed

    private void jButton6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseReleased
         jPanel1.repaint();
    }//GEN-LAST:event_jButton6MouseReleased

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        Graphics g = jPanel1.getGraphics();
        g.setColor(Color.blue);
        ArrayList<PointGeste> points = myGesture.getStep2points();
        for (PointGeste p : points) {
            g.fillOval((int)p.getX()-3, (int)p.getY()-3, 7, 7);
        }
    }//GEN-LAST:event_jButton3MousePressed

    private void jButton3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseReleased
        jPanel1.repaint();
    }//GEN-LAST:event_jButton3MouseReleased

    private void jButton7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MousePressed
        Graphics g = jPanel1.getGraphics();
        g.setColor(Color.blue);
        ArrayList<PointGeste> points = myGesture.getStep3points();
        for (PointGeste p : points) {
            g.fillOval((int)p.getX()-3, (int)p.getY()-3, 7, 7);
        }
    }//GEN-LAST:event_jButton7MousePressed

    private void jButton7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseReleased
        jPanel1.repaint();
    }//GEN-LAST:event_jButton7MouseReleased

    private void jButton8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MousePressed
        Graphics g = jPanel1.getGraphics();
        g.setColor(Color.blue);
        ArrayList<PointGeste> points = myGesture.getStep4points();
        for (PointGeste p : points) {
                g.fillOval((int)p.getX()-3, (int)p.getY()-3, 7, 7);
        }
    }//GEN-LAST:event_jButton8MousePressed

    private void jButton8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseReleased
        jPanel1.repaint();
    }//GEN-LAST:event_jButton8MouseReleased

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        if (myGesture != null) {
            System.out.println("=========================");
            for (PointGeste p : myGesture.getStep4points()) {
                System.out.println(p.getX() + ";" + p.getY());
            }
        }
    }//GEN-LAST:event_jButton1MouseReleased

/*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }*/
    
    private FormeView formeView1;
    private FormeView formeView2;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
