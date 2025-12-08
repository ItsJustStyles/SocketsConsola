/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comandos;

import Cliente.Client;
import Servidor.Server;
import Servidor.ThreadServidor;

/**
 *
 * @author lacay
 */
public class CommandAttack extends Command{

    public CommandAttack(String[] args) {
        super(CommandType.ATAQUE, args);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        // =============================================================
        // VALIDAR TURNO DEL JUGADOR
        // =============================================================
        Server server = threadServidor.getRefServer();

        if (server.turnSystemEnabled) {
            ThreadServidor jugadorActual = server.getCurrentTurnPlayer();
            
            if (jugadorActual != threadServidor) {
                // NO ES SU TURNO → rechazo del ataque
                System.out.println("NO ES TU TURNO");
                try {
                    threadServidor.objectSender.writeObject(
                        new CommandMessage(new String[]{
                            "SERVER", "NO ES TU TURNO"
                        })
                    );
                } catch (Exception e) {}

                return; 
            }
        }
        String[] params = this.getParameters();
        
        String p;
        String arma;
        String objetivo;
        
        try{
            p = params[1];
            arma = params[2];
            objetivo = params[3];
            
        }catch(ArrayIndexOutOfBoundsException e){
            String msg = "Parametros para el ataque imcompletos";
            server.getRefFrame().writeConsola(msg);
            this.setIsBroadcast(false);
            return;
        }
        
        ThreadServidor targetThread = threadServidor.getRefServer().getClientByName(objetivo);
        ThreadServidor selfThread = threadServidor.getRefServer().getClientByName(threadServidor.name);
        
        if(targetThread != null){
            
            String attackerName = threadServidor.name;
            boolean personajeValido = threadServidor.esPersonajeValido(p);
            if(!personajeValido){
                String msg = "El personaje no es valido";
                server.getRefFrame().writeConsola(msg);
                this.setIsBroadcast(false);
                return;
            }
            
            boolean armaValida = threadServidor.esArmaValida(arma, p);
            if(!armaValida){
                String msg = "El arma no es valida";
                server.getRefFrame().writeConsola(msg);
                this.setIsBroadcast(false);
                return;
            }
            
            String damage = threadServidor.obtenerDano(arma, p) + "";
            CommandHit hitCommand = new CommandHit(p, arma, attackerName, damage);
            try {
                targetThread.objectSender.writeObject(hitCommand);
                targetThread.objectSender.flush();
            } catch (java.io.IOException ex) {
                threadServidor.getRefServer().getRefFrame().writeConsola("Error al enviar ataque a " + objetivo);
            }
            
            String[] broadcastParams = new String[]{threadServidor.name, objetivo, p, arma};
            CommandAttack broadcastCommand = new CommandAttack(broadcastParams);
            threadServidor.getRefServer().broadcast(broadcastCommand);
            
        }else{
            this.setIsBroadcast(false);
            return;
        }
        
        server.avanzarTurno();
        
    }

    @Override
    public void processInClient(Client client) {
        String[] params = this.getParameters();
        String attackerName = params[0];
        String targetName = params[1];
        String personaje = params[2];
        String weapon = params[3];
        
        String bitacora = "El jugador " + attackerName + " atacó al jugador " + targetName + " usando el personaje: " + personaje + " y el arma: " + weapon;
        
        if(!client.name.equals(targetName)){
            client.getRefFrame().writeBitacora(bitacora);
        }
        
    }
      
}
