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
        scrollPersonajes.setBounds((newAncho - 600)/2, 100, 600, 600);
        btnSeleccionarPersonajes.setBounds((newAncho - 191)/2, 720, 191, 27);
        //Mapa:
        Ranking.setBounds(0, 0, 250, 230);
        Against.setBounds(0, 230, 250, 230);
        Status.setBounds(0, 460, 250, 230);
        Consola.setBounds(0, 690, 1200, 110);
        attacked.setBounds(250, 0, 500, 345);
        attack.setBounds(250, 345, 500, 345);
        team.setBounds(750, 0, 450, 690);
        
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
    
    public void startGame(){
        cardLayout = (CardLayout) (getContentPane().getLayout());
        cardLayout.show(getContentPane(), "card4");
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
        Ranking = new javax.swing.JScrollPane();
        rankingText = new javax.swing.JTextArea();
        Against = new javax.swing.JScrollPane();
        AgainstText = new javax.swing.JTextArea();
        Status = new javax.swing.JScrollPane();
        StatusText = new javax.swing.JTextArea();
        Consola = new javax.swing.JScrollPane();
        consoltaEntry = new javax.swing.JTextArea();
        attacked = new javax.swing.JPanel();
        attack = new javax.swing.JPanel();
        team = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 800));
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

        Mapa.setLayout(null);

        Ranking.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Ranking.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Ranking.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        rankingText.setColumns(20);
        rankingText.setRows(5);
        Ranking.setViewportView(rankingText);

        Mapa.add(Ranking);
        Ranking.setBounds(0, 0, 250, 212);

        Against.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Against.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Against.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        AgainstText.setColumns(20);
        AgainstText.setRows(5);
        Against.setViewportView(AgainstText);

        Mapa.add(Against);
        Against.setBounds(0, 218, 250, 248);

        Status.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        StatusText.setColumns(20);
        StatusText.setRows(5);
        Status.setViewportView(StatusText);

        Mapa.add(Status);
        Status.setBounds(0, 473, 250, 176);

        Consola.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        consoltaEntry.setColumns(20);
        consoltaEntry.setRows(5);
        Consola.setViewportView(consoltaEntry);

        Mapa.add(Consola);
        Consola.setBounds(0, 655, 1200, 145);

        attacked.setBackground(new java.awt.Color(102, 102, 255));
        attacked.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        attacked.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout attackedLayout = new javax.swing.GroupLayout(attacked);
        attacked.setLayout(attackedLayout);
        attackedLayout.setHorizontalGroup(
            attackedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        attackedLayout.setVerticalGroup(
            attackedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );

        Mapa.add(attacked);
        attacked.setBounds(256, 0, 587, 361);

        attack.setBackground(new java.awt.Color(102, 102, 255));
        attack.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        attack.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout attackLayout = new javax.swing.GroupLayout(attack);
        attack.setLayout(attackLayout);
        attackLayout.setHorizontalGroup(
            attackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        attackLayout.setVerticalGroup(
            attackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );

        Mapa.add(attack);
        attack.setBounds(256, 389, 587, 260);

        team.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout teamLayout = new javax.swing.GroupLayout(team);
        team.setLayout(teamLayout);
        teamLayout.setHorizontalGroup(
            teamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 317, Short.MAX_VALUE)
        );
        teamLayout.setVerticalGroup(
            teamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Mapa.add(team);
        team.setBounds(883, 0, 317, 649);

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
    private javax.swing.JScrollPane Against;
    private javax.swing.JTextArea AgainstText;
    private javax.swing.JScrollPane Consola;
    private javax.swing.JButton Jugar;
    private javax.swing.JPanel Mapa;
    private javax.swing.JPanel MenuInicio;
    private javax.swing.JScrollPane Ranking;
    private javax.swing.JButton Salir;
    private javax.swing.JPanel SeleccionPersonajesMenu;
    private javax.swing.JScrollPane Status;
    private javax.swing.JTextArea StatusText;
    private javax.swing.JLabel TituloMenu;
    private javax.swing.JPanel attack;
    private javax.swing.JPanel attacked;
    private javax.swing.JButton btnSeleccionarPersonajes;
    private javax.swing.JTextArea consoltaEntry;
    private javax.swing.JTextArea rankingText;
    private javax.swing.JScrollPane scrollPersonajes;
    private javax.swing.JPanel scrollPersonajesPanel;
    private javax.swing.JLabel seleccionTitle;
    private javax.swing.JPanel team;
    // End of variables declaration//GEN-END:variables
}
