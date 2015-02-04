/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormRecognizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author ladoucar
 */
public class FormeView extends javax.swing.JPanel {

    private boolean highlight;
    private ArrayList<PointGeste> shape;
    private String text;
    
    /**
     * Creates new form FormeView
     */
    public FormeView() {
        initComponents();
        setHighlight(false);
        setShape(null);
        setText("text");
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
        if (highlight) {
            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 204, 0), 4, true));
            jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
            jLabel1.setForeground(new java.awt.Color(153, 204, 0));
        } else {
             setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
             jLabel1.setFont(new java.awt.Font("Tahoma", 0, 11));
             jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        }
        repaint();
    }

    public ArrayList<PointGeste> getShape() {
        return shape;
    }
    
    private void recadrer() {
        ArrayList<PointGeste> newShape = new ArrayList<>();
        Rectangle B = BoundingBox();
        for (PointGeste p : shape) {
            float qx = (float) (p.x * (80 / B.getWidth()));
            float qy = (float) (p.y * (80 / B.getHeight()));
            newShape.add(new PointGeste((int) qx, (int) qy));
        }
        shape = new ArrayList<>(newShape);
        /*PointGeste c = Centroid(step3points);
        System.out.println("Step4 : centre = " + c.x + " : " + c.y);
        for (PointGeste p : step3points) {
            float qx = (float) (p.x - c.x + 150);
            float qy = (float) (p.y - c.y + 150);
            step4points.add(new PointGeste((int) qx, (int) qy));
        }*/
    }
    
    private Rectangle BoundingBox() {
        float minX = (float) 1e9;
        float maxX = (float) -1e9;
        float minY = (float) 1e9;
        float maxY = (float) -1e9;
        for (PointGeste p : shape) {
            minX = (float) Math.min(p.x, minX);
            maxX = (float) Math.max(p.x, maxX);
            minY = (float) Math.min(p.y, minY);
            maxY = (float) Math.max(p.y, maxY);
        }
        return new Rectangle((int) minX, (int) minY, (int) (maxX - minX), (int) (maxY - minY));
    }

    public void setShape(ArrayList<PointGeste> shape) {
        this.shape = shape;
        if (shape != null) {
            recadrer();
            repaint();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        jLabel1.setText(text);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        if (shape == null) {
            return;
        }
        int size = 3;
        if (highlight) {
            g.setColor(new java.awt.Color(153, 204, 0));
            size = 5;
        } else {
            g.setColor(Color.black);
            size = 3;
        }
        for (PointGeste p : shape) {
            g.fillOval((int)p.getX()-(size/2), (int)p.getY()+14-(size/2), size, size);
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

        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        setPreferredSize(new java.awt.Dimension(100, 114));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 98, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
