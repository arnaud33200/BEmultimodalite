
import FormRecognizer.GestureListener;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Event;
import java.util.Timer;
import java.util.TimerTask;
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

    private int selectedFormX = 0;
    private int selectedFormY = 0;

    private int deplacementX = 0;
    private int deplacementY = 0;

    private Timer timerWait;
    private Timer timerCreer;
    private Timer timerIci;
    private Timer timerDeCetteCouleur;
    private Timer timerDeplacer;

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

            @Override
            public void GestureLeftRecognized(Event e) {
                deplacementX = -100;
                context.getDaState().doActionDessinForme(context);
            }

            @Override
            public void GestureRightRecognized(Event e) {
                deplacementX = 100;
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus = new Ivy("Multimodalite", "Multimodalite Ready", null);

        bus.bindMsg("^Palette:MouseReleased x=(.*) y=(.*)$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                //System.out.println("IVY Mouse Released" + " x:" + ((args.length > 0) ? args[0] : "") + " y:" + ((args.length > 0) ? args[1] : ""));
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);

                context.getDaState().doActionClick(context);

            }
        });
        bus.bindMsg("^Palette:MousePressed x=(.*) y=(.*)$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                //System.out.println("IVY Mouse Pressed" + " x:" + ((args.length > 0) ? args[0] : "") + " y:" + ((args.length > 0) ? args[1] : ""));
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
                //System.out.println("IVY Rectangle" + ((args.length > 0) ? args[0] : ""));
                forme = "rectangle";
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus.bindMsg("^ICAR cercle$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                //System.out.println("IVY Cercle" + ((args.length > 0) ? args[0] : ""));
                forme = "circle";
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus.bindMsg("^ICAR gauche$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                deplacementX = -20;
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus.bindMsg("^ICAR droite$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                deplacementX = 20;
                context.getDaState().doActionDessinForme(context);
            }
        });

        bus.bindMsg("^sra5 Parsed=Action:peindre Couleur:(.*) Confidence=(.*)$", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                //System.out.println("IVY Couleur " + ((args.length > 0) ? args[0] : ""));
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
                //System.out.println("IVY De cette couleur " + ((args.length > 0) ? args[0] : ""));
                context.getDaState().doActionVoixDeCetteCouleur(context);
            }
        });

        bus.bindMsg("^sra5 Parsed=Action:ici", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                //System.out.println("IVY Ici " + ((args.length > 0) ? args[0] : ""));
                context.getDaState().doActionVoixIci(context);
            }
        });

        bus.bindMsg("^sra5 Parsed=Action:deplacer", new IvyMessageListener() {
            public void receive(IvyClient client, String[] args) {
                //System.out.println("IVY Ici " + ((args.length > 0) ? args[0] : ""));
                context.getDaState().doActionVoixDeplacer(context);
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
                selectedFormX = Integer.parseInt(args[1]);
                selectedFormY = Integer.parseInt(args[2]);

                context.getDaState().doActionInfoReceived(context);

            }
        });

        bus.start("127.255.255.255:2010");
        state = new InitState();
        context = this;

        bus.sendMsg("Palette:CreerEllipse x=" + 380 + " y=" + 0 + " couleurFond=" + "green");
        bus.sendMsg("Palette:CreerEllipse x=" + 380 + " y=" + 55 + " couleurFond=" + "yellow");
        bus.sendMsg("Palette:CreerEllipse x=" + 380 + " y=" + 110 + " couleurFond=" + "blue");
        bus.sendMsg("Palette:CreerEllipse x=" + 380 + " y=" + 165 + " couleurFond=" + "red");

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

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gestureViewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gestureViewPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

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
    public void updateCouleur() {
        switch (context.getDaState().toString()) {
            case "DE CETTE COULEUR STATE":

                try {
                    bus.sendMsg("Palette:DemanderInfo nom=" + selectedFormUpdate);
                } catch (IvyException ex) {
                    Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            default:
                this.colorUpdate = color;
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
        //System.out.println("CURRENT Position X: "+xUpdate+" Y: "+yUpdate);
    }

    @Override
    public void updateForme() {

        this.formeUpdate = forme;
        //System.out.println("CURRENT Form: "+formeUpdate);
    }

    @Override
    public void updateSelectedForme() {
        this.selectedFormUpdate = selectedForm;
        switch (context.getDaState().toString()) {
            case "FORM SELECT STATE": {
                try {
                    bus.sendMsg("Palette:DemanderInfo nom=" + selectedFormUpdate);
                } catch (IvyException ex) {
                    Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            default:
                break;
        }

        //System.out.println("CURRENT Selected Form: "+selectedFormUpdate);
    }

    @Override
    public void deplacerForme() {
        switch (context.getDaState().toString()) {
            case "WAIT POSITION STATE":
                int deplacementClickX = x - selectedFormX;
                int deplacementClickY = y - selectedFormY;
                 {
                    try {
                        bus.sendMsg("Palette:DeplacerObjet nom=" + selectedFormUpdate + " x=" + deplacementClickX + " y=" + deplacementClickY);
                    } catch (IvyException ex) {
                        Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;

            case "FORM SELECT STATE":
                try {
                    bus.sendMsg("Palette:DeplacerObjet nom=" + selectedFormUpdate + " x=" + deplacementX + " y=" + deplacementY);
                } catch (IvyException ex) {
                    Logger.getLogger(MainFramePalette.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }

        deplacementX = 0;
        deplacementY = 0;
        selectedFormX = 0;
        selectedFormY = 0;
    }

    @Override
    public void startTimerWait() {
        timerWait = new Timer();
        timerWait.schedule(new TimerTask() {
            @Override
            public void run() {
                context.getDaState().doActionTimerWait(context);
            }
        }, 4000);
    }

    @Override
    public void startTimerCreer() {
        timerCreer = new Timer();
        timerCreer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.getDaState().doActionTimerCreer(context);
            }
        }, 4000);
    }

    @Override
    public void startTimerIci() {
        timerIci = new Timer();
        timerIci.schedule(new TimerTask() {
            @Override
            public void run() {
                context.getDaState().doActionTimerIci(context);
            }
        }, 4000);
    }

    @Override
    public void startTimerDeCetteCouleur() {
        timerDeCetteCouleur = new Timer();
        timerDeCetteCouleur.schedule(new TimerTask() {
            @Override
            public void run() {
                context.getDaState().doActionTimerDeCetteCouleur(context);
            }
        }, 4000);
    }

    @Override
    public void startTimerDeplacer() {
        timerDeplacer = new Timer();
        timerDeplacer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.getDaState().doActionTimerDeplacer(context);
            }
        }, 4000);
    }

}
