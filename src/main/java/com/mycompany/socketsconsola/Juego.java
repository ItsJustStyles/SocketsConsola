/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.socketsconsola;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author lacay
 */
public class Juego extends javax.swing.JFrame {
    
    private List<Personajes> todosLosPersonajes;
    
    
    private int alto = 800;
    private int ancho = 1200;
    private CardLayout cardLayout = new CardLayout();
    
    private final GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private boolean isFullScreen = false;
    private Insets originalInsets; 
    
    private final Set<String> idsSeleccionados = new HashSet<>(); 
    private final Map<String, JComponent> componentesSeleccionados = new HashMap<>(); 
    private final Border BORDE_SELECCION = BorderFactory.createLineBorder(new Color(13, 35, 71), 3);
    private final Border BORDE_NORMAL = BorderFactory.createEmptyBorder();
    
    public SonidoMenu menuPersonajes = new SonidoMenu("/Musica/BalatroMainTheme.wav");;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Juego.class.getName());

    /**
     * Creates new form Juego
     */
    public Juego() throws IOException, LineUnavailableException {
        initComponents();
        menuPersonajes.loop();
        this.setSize(ancho, alto);
        configurarPantallaCompleta();
        iniciarElementos(ancho, alto);
        this.todosLosPersonajes = GestorJson.cargarPersonajes();
        cargarPersonajesEnScrollPanel();
    }
    
    private void iniciarElementos(int newAncho, int newAlto){
        //Menu inicio:
        TituloMenu.setBounds((newAncho - 42)/2, 10, 42, 16);
        Jugar.setBounds((newAncho - 72)/2, 70, 72, 23);
        Salir.setBounds((newAncho - 72)/2, 150, 72, 23);
        //Escoger jugadores:
        seleccionTitle.setBounds((newAncho - 274)/2, 30, 274, 29);
        scrollPersonajes.setSize(600, 600);
        scrollPersonajes.setBounds((newAncho - 600)/2, 100, 600, 600);
        btnSeleccionarPersonajes.setBounds((newAncho - 191)/2, 720, 191, 27);
        //Mapa:
        
    }
    
    private void colocarFondos(){
        
    }
    
    private void cargarPersonajesEnScrollPanel(){
        try{
            for(Personajes p : this.todosLosPersonajes){
                JPanel panelPersonaje = new JPanel();
                panelPersonaje.setOpaque(false);
                panelPersonaje.setBorder(null);
                panelPersonaje.setLayout(new javax.swing.BoxLayout(panelPersonaje, javax.swing.BoxLayout.Y_AXIS));
                
                
                JLabel lblNombre = new JLabel("Nombre: " + p.getNombre());
                lblNombre.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(p.getImagen()));
                ImageIcon iconoRedimensionado;
                
                JLabel lblIcono = new JLabel();             
                if(p.getImagen().contains(".gif")){
                    iconoRedimensionado = iconoOriginal;
                }else{
                    iconoRedimensionado = new ImageIcon(iconoOriginal.getImage().getScaledInstance(125, 125, java.awt.Image.SCALE_SMOOTH));
                }

                lblIcono.setIcon(iconoRedimensionado);
                lblIcono.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblTipo = new JLabel("Tipo: " + p.getTipo());
                lblTipo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                
                panelPersonaje.add(lblNombre);
                panelPersonaje.add(Box.createVerticalStrut(10));
                panelPersonaje.add(lblIcono);
                panelPersonaje.add(Box.createVerticalStrut(10));
                panelPersonaje.add(lblTipo);
                
                panelPersonaje.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        manejarSeleccion(panelPersonaje, p.getNombre());
                    }
                });
                
                scrollPersonajesPanel.add(panelPersonaje);
                
            }
            scrollPersonajesPanel.revalidate();
            scrollPersonajesPanel.repaint();
            
        }catch(Exception e){
            System.err.println("Error al cargar personajes en la interfaz: " + e.getMessage());
        }
    }
    
        private void manejarSeleccion(JComponent componenteActual, String idPersonaje) {
            if (componentesSeleccionados.containsKey(idPersonaje)) {
                componenteActual.setBorder(BORDE_NORMAL); 
                idsSeleccionados.remove(idPersonaje);
                componentesSeleccionados.remove(idPersonaje);

            } 
            else {
                if (idsSeleccionados.size() < 3) {

                    componenteActual.setBorder(BORDE_SELECCION); 
                    idsSeleccionados.add(idPersonaje);
                    componentesSeleccionados.put(idPersonaje, componenteActual); 

                } else {
                    JOptionPane.showMessageDialog(this, "Solo puedes seleccionar un máximo de 3 personajes.");
                }
            }
            System.out.println("Personajes seleccionados: " + idsSeleccionados.size());
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
        seleccionTitle = new javax.swing.JLabel();
        scrollPersonajes = new javax.swing.JScrollPane();
        scrollPersonajesPanel = new javax.swing.JPanel();
        btnSeleccionarPersonajes = new javax.swing.JButton();
        Mapa = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 800));
        setPreferredSize(new java.awt.Dimension(1200, 800));
        setSize(new java.awt.Dimension(400, 300));
        getContentPane().setLayout(new java.awt.CardLayout());

        MenuInicio.setMinimumSize(new java.awt.Dimension(1200, 800));
        MenuInicio.setLayout(null);

        TituloMenu.setText("Sockets");
        MenuInicio.add(TituloMenu);
        TituloMenu.setBounds(180, 20, 40, 16);

        Jugar.setText("Jugar");
        Jugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JugarActionPerformed(evt);
            }
        });
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

        SeleccionPersonajesMenu.setLayout(null);

        seleccionTitle.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        seleccionTitle.setText("Selección jugadores");
        SeleccionPersonajesMenu.add(seleccionTitle);
        seleccionTitle.setBounds(490, 30, 280, 29);

        scrollPersonajes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPersonajes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        scrollPersonajesPanel.setLayout(new java.awt.GridLayout(0, 3, 5, 25));
        scrollPersonajes.setViewportView(scrollPersonajesPanel);

        SeleccionPersonajesMenu.add(scrollPersonajes);
        scrollPersonajes.setBounds(200, 110, 800, 570);

        btnSeleccionarPersonajes.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnSeleccionarPersonajes.setText("Seleccionar personajes");
        btnSeleccionarPersonajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarPersonajesActionPerformed(evt);
            }
        });
        SeleccionPersonajesMenu.add(btnSeleccionarPersonajes);
        btnSeleccionarPersonajes.setBounds(530, 750, 200, 27);

        getContentPane().add(SeleccionPersonajesMenu, "card3");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jScrollPane4.setViewportView(jTextArea4);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setForeground(new java.awt.Color(0, 153, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 587, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setForeground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 317, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout MapaLayout = new javax.swing.GroupLayout(Mapa);
        Mapa.setLayout(MapaLayout);
        MapaLayout.setHorizontalGroup(
            MapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MapaLayout.createSequentialGroup()
                .addGroup(MapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane4)
        );
        MapaLayout.setVerticalGroup(
            MapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MapaLayout.createSequentialGroup()
                .addGroup(MapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MapaLayout.createSequentialGroup()
                        .addGroup(MapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(MapaLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MapaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(Mapa, "card4");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    private void JugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JugarActionPerformed
        // TODO add your handling code here:
        cardLayout = (CardLayout) (getContentPane().getLayout());
        cardLayout.show(getContentPane(), "card3");
    }//GEN-LAST:event_JugarActionPerformed

    private void btnSeleccionarPersonajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarPersonajesActionPerformed
        // TODO add your handling code here:
        cardLayout = (CardLayout) (getContentPane().getLayout());
        cardLayout.show(getContentPane(), "card4");
    }//GEN-LAST:event_btnSeleccionarPersonajesActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Juego().setVisible(true);
            } catch (IOException ex) {
                System.getLogger(Juego.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (LineUnavailableException ex) {
                System.getLogger(Juego.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jugar;
    private javax.swing.JPanel Mapa;
    private javax.swing.JPanel MenuInicio;
    private javax.swing.JButton Salir;
    private javax.swing.JPanel SeleccionPersonajesMenu;
    private javax.swing.JLabel TituloMenu;
    private javax.swing.JButton btnSeleccionarPersonajes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JScrollPane scrollPersonajes;
    private javax.swing.JPanel scrollPersonajesPanel;
    private javax.swing.JLabel seleccionTitle;
    // End of variables declaration//GEN-END:variables
}
