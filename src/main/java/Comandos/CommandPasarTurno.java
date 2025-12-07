/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comandos;

import Servidor.ThreadServidor;

/**
 *
 * @author lacay
 */
public class CommandPasarTurno extends Command{

    public CommandPasarTurno(String[] args) {
        super(CommandType.PASARTURNO, args);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
