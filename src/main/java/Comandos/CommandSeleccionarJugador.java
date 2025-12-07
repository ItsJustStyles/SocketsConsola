/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comandos;

import Cliente.Client;
import Servidor.Server;
import Servidor.ThreadServidor;
import com.mycompany.socketsconsola.Personajes;
import java.util.List;

/**
 *
 * @author lacay
 */
public class CommandSeleccionarJugador extends Command{

    public CommandSeleccionarJugador(String[] args) {
        super(CommandType.SELECCIONARJUGADOR, args);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        Server server = threadServidor.getRefServer();
        String[] params = this.getParameters();
        
        String command;
        String objetivo;
        try{
            command = params[0];
            objetivo = params[1];
        }catch(ArrayIndexOutOfBoundsException e){
            this.setIsBroadcast(false);
            return;
        }
        
        ThreadServidor targetThread = threadServidor.getRefServer().getClientByName(objetivo);
        ThreadServidor selfThread = threadServidor.getRefServer().getClientByName(threadServidor.name);
        
        List<Personajes> heroesDelObjetivo = targetThread.heroes;
        CommandEnviarHeroes enviarCommand = new CommandEnviarHeroes(heroesDelObjetivo);
        
        try {
            targetThread.objectSender.writeObject(enviarCommand);
            targetThread.objectSender.flush();
        } catch (java.io.IOException ex) {
                // Manejar la desconexión del objetivo
            //threadServidor.getRefServer().getRefFrame().writeMessage("Error al enviar ataque a " + objetivo);
        }
    }

    @Override
    public void processInClient(Client client) {
         
    }
    
    
    
}
