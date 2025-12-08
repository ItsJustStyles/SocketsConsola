/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comandos;

import Servidor.Server;
import Servidor.ThreadServidor;

/**
 *
 * @author lacay
 */
public class CommandUsarComodin extends Command{

    public CommandUsarComodin(String[] args) {
        super(CommandType.USARCOMODIN, args);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        CommandUnicast comandoARealizar = new CommandUnicast("USARCOMODIN", threadServidor.name);
        Server server = threadServidor.getRefServer();
        ThreadServidor jugadorActual = server.getCurrentTurnPlayer();
        
        try {
            jugadorActual.objectSender.writeObject(comandoARealizar);
            jugadorActual.objectSender.flush();
        } catch (java.io.IOException ex) {
            //threadServidor.getRefServer().getRefFrame().writeMessage("Error al enviar ataque a " + threadServidor.name);
        }
    }
    
}
