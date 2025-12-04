/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.socketsconsola;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 *
 * @author lacay
 */
public class Juego extends javax.swing.JFrame {
    
    private int alto = 800;
    private int ancho = 1200;
    
    private final GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private boolean isFullScreen = false;
    private Insets originalInsets; 
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Juego.class.getName());

    /**
     * Creates new form Juego
     */
    public Juego() {
        initComponents();
        this.setSize(ancho, alto);
        configurarPantallaCompleta();
        iniciarElementos(ancho, alto);
    }
    
    private void iniciarElementos(int newAncho, int newAlto){
        TituloMenu.setBounds((newAncho - 42)/2, 10, 42, 16);
        Jugar.setBounds((newAncho - 72)/2, 70, 72, 23);
        Salir.setBounds((newAncho - 72)/2, 150, 72, 23);
    }
    
    private void configurarPantallaCompleta() {
        InputMap inputMap = getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        KeyStroke f11Key = KeyStroke.getKeyStroke("F11");

        inputMap.put(f11Key, "toggleFullScreen");

        actionMap.put("toggleFullScreen", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                toggleFullScreen(); 
            }
        });
    }
    
private void toggleFullScreen() {
    if (graphicsDevice.isFullScreenSupported()) {
        if (!isFullScreen) {
            this.setVisible(false); 
            this.dispose(); 
            
            this.setUndecorated(true); 
            
            graphicsDevice.setFullScreenWindow(this);
            
            this.setVisible(true);
            
            isFullScreen = true;
            
            int x = this.getWidth();
            int y = this.getHeight();
            iniciarElementos(x,y);
            
        } else {
            this.setVisible(true); 
            graphicsDevice.setFullScreenWindow(null);
            this.dispose(); 

            this.setUndecorated(false);
            this.setSize(this.ancho, this.alto); 
            System.out.println(this.getWidth());
            System.out.println(this.getHeight());
            this.setLocationRelativeTo(null); 
            
            this.setVisible(true);
            
            isFullScreen = false;
            iniciarElementos(this.ancho, this.alto);
        }
        
    } else {
        System.err.println("El modo de pantalla completa no es compatible con este dispositivo.");
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuInicio = new javax.swing.JPanel();
        TituloMenu = new javax.swing.JLabel();
        Jugar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        SeleccionPersonajesMenu = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(400, 300));
        getContentPane().setLayout(new java.awt.CardLayout());

        MenuInicio.setMinimumSize(new java.awt.Dimension(400, 300));
        MenuInicio.setLayout(null);

        TituloMenu.setText("Sockets");
        MenuInicio.add(TituloMenu);
        TituloMenu.setBounds(180, 20, 40, 16);

        Jugar.setText("Jugar");
        MenuInicio.add(Jugar);
        Jugar.setBounds(160, 80, 72, 23);

        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        MenuInicio.add(Salir);
        Salir.setBounds(160, 150, 72, 23);

        getContentPane().add(MenuInicio, "card2");

        javax.swing.GroupLayout SeleccionPersonajesMenuLayout = new javax.swing.GroupLayout(SeleccionPersonajesMenu);
        SeleccionPersonajesMenu.setLayout(SeleccionPersonajesMenuLayout);
        SeleccionPersonajesMenuLayout.setHorizontalGroup(
            SeleccionPersonajesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        SeleccionPersonajesMenuLayout.setVerticalGroup(
            SeleccionPersonajesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        getContentPane().add(SeleccionPersonajesMenu, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Juego().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jugar;
    private javax.swing.JPanel MenuInicio;
    private javax.swing.JButton Salir;
    private javax.swing.JPanel SeleccionPersonajesMenu;
    private javax.swing.JLabel TituloMenu;
    // End of variables declaration//GEN-END:variables
}
