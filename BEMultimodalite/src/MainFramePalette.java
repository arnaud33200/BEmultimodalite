
import FormRecognizer.GestureListener;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Event;
import java.util.logging.Level;
import java.util.logging.Logger;
import statePattern.Context;
import statePattern.DeCetteCouleurState;
import statePattern.InitState;
import statePattern.State;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ladoucar
 */
public class MainFramePalette extends javax.swing.JFrame implements Context {

    private State state;
    private Context context;
    private Ivy bus;

    /* Variables mises à jour à chaque évènement recu sur le bus IVY */
    private int x = 0;
    private int y = 0;
    private String forme = "rectangle";
    private String color = "white";
    private String selectedForm = "none";
    
    /* Variable mise à jour dans les fonctions updatePosition(), updateCouleur(), updateForme() */
    private int xUpdate;
    private int yUpdate;
    private String formeUpdate;
    private String colorUpdate;
    private String selectedFormUpdate;
    

    public MainFramePalette() throws IvyException {
        initComponents();
        
        gestureViewPanel1.addGestureListener(new GestureListener() {

            @Override
            public void GestureRectangleRecognized(Event e) {
                forme = "rectangle";
                context.getDaState().doActionDessinForme(context);
            }

            @Override
            public void GestureEllispeRecognized(Event e) {
                forme = "circle";
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus = new Ivy("Multimodalite", "Multimodalite Ready", null);

        bus.bindMsg("^Palette:MouseReleased x=(.*) y=(.*)$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY Mouse Released" + " x:" + ((args.length > 0) ? args[0] : "") + " y:" + ((args.length > 0) ? args[1] : ""));
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
                context.getDaState().doActionClick(context);

            }
        });
        bus.bindMsg("^Palette:MousePressed x=(.*) y=(.*)$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY Mouse Pressed" + " x:" + ((args.length > 0) ? args[0] : "") + " y:" + ((args.length > 0) ? args[1] : ""));
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
                try {
                    bus.sendMsg("Palette:TesterPoint x=" + x + " y=" + y);
                } catch (IvyException ex) {
                    Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        bus.bindMsg("^ICAR rectangle$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY Rectangle" + ((args.length > 0) ? args[0] : ""));
                forme = "rectangle";
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus.bindMsg("^ICAR cercle$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY Cercle" + ((args.length > 0) ? args[0] : ""));
                forme = "circle";
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus.bindMsg("^sra5 Parsed=Action:peindre Couleur:(.*) Confidence=(.*)$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY Couleur " + ((args.length > 0) ? args[0] : ""));
                switch (args[0]) {
                    case "rouge":
                        color = "red";
                        break;
                    case "bleu":
                        color = "blue";
                        break;
                    default:
                        color = "white";
                        break;
                }
                context.getDaState().doActionVoixRougeBleu(context);
            }
        });
        
        bus.bindMsg("^sra5 Parsed=Action:de cette couleur", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY De cette couleur " + ((args.length > 0) ? args[0] : ""));
                context.getDaState().doActionVoixDeCetteCouleur(context);
            }
        });
        
        bus.bindMsg("^sra5 Parsed=Action:ici", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY Ici " + ((args.length > 0) ? args[0] : ""));
                context.getDaState().doActionVoixIci(context);
            }
        });

        bus.bindMsg("Palette:ResultatTesterPoint x=(.*) y=(.*) nom=(.*)", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                System.out.println("IVY Forme " + ((args.length > 0) ? args[2] : ""));
                selectedForm = args[2];
                context.getDaState().doActionSelection(context);
            }
        });
        
        bus.bindMsg("Palette:Info nom=(.*) x=(.*) y=(.*) longueur=(.*) hauteur=(.*) couleurFond=(.*) couleurContour=(.*)", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                colorUpdate = args[5];
                if(colorUpdate != null){
                System.out.println("IVY Selected couleur " + colorUpdate);
                context.getDaState().doActionColorReceived(context);
                }
            }
        });

        bus.start("127.255.255.255:2010");
        state = new InitState();
        context = this;
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        gestureViewPanel1 = new FormRecognizer.GestureViewPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(90, 222, 90));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });

        jButton2.setText("jButton1");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2MouseReleased(evt);
            }
        });

        jButton3.setText("jButton1");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton3MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gestureViewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gestureViewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        context.getDaState().doActionDessinForme(context);
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseReleased

    private void jButton3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFramePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFramePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFramePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFramePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFramePalette().setVisible(true);
                } catch (IvyException ex) {
                    Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private FormRecognizer.GestureViewPanel gestureViewPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setState(State state) {
        this.state = state;
        System.out.println(state.toString());
    }

    @Override
    public State getDaState() {
        return state;
    }

    @Override
    public void startTimerIci() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCouleur() {
        switch(context.getDaState().toString()){
            case "DE CETTE COULEUR STATE":
        
            try {
                bus.sendMsg("Palette:DemanderInfo nom=" + selectedFormUpdate);
            } catch (IvyException ex) {
                Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        
                break;
            default:
                this.colorUpdate = color;
        System.out.println("CURRENT Color: "+colorUpdate);
                break;
        
        }
    }

    @Override
    public void dessinerForme() {
        switch (formeUpdate) {
            case "rectangle":
                try {
                    bus.sendMsg("Palette:CreerRectangle x=" + xUpdate + " y=" + yUpdate + " couleurFond=" + colorUpdate);
                } catch (IvyException ie) {
                    System.out.println("can't send my message !");
                }
                break;
            case "circle": {
                try {
                    bus.sendMsg("Palette:CreerEllipse x=" + xUpdate + " y=" + yUpdate + " couleurFond=" + colorUpdate);
                } catch (IvyException ex) {
                    Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            default:
                break;
        }
        this.xUpdate = 0;
        this.yUpdate = 0;
        this.formeUpdate = "rectangle";
        this.colorUpdate = "white";
        this.selectedFormUpdate = "none";
    }

    @Override
    public void updatePosition() {
        this.xUpdate = x;
        this.yUpdate = y;
        System.out.println("CURRENT Position X: "+xUpdate+" Y: "+yUpdate);
    }

    @Override
    public void updateForme() {
        this.formeUpdate = forme;
        System.out.println("CURRENT Form: "+formeUpdate);
    }

    @Override
    public void updateSelectedForme() {
       this.selectedFormUpdate = selectedForm;
        System.out.println("CURRENT Selected Form: "+selectedFormUpdate);
    }

}
