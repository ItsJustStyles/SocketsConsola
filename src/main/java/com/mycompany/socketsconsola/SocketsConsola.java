/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.socketsconsola;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author lacay
 */
public class SocketsConsola {
    public static void main(String[] args) {

        LogPartida logJuan = new LogPartida("Juan");
        logJuan.registrarComando("SELECCIONAR_PERSONAJE", "Guerrero", "OK");
        logJuan.registrarAtaque("Guerrero", "Espada", "Enemigo1", 45, true);
        

        LogPartida logMaicol = new LogPartida("Maicol");
        logMaicol.registrarComando("SELECCIONAR_PERSONAJE", "Mago", "OK");
        
        
        LogPartida logCarlos = new LogPartida("Carlos");
        logCarlos.registrarComando("SELECCIONAR_PERSONAJE", "Guerrero", "OK");
        
        logCarlos.registrarFin("Juan");
        logMaicol.registrarFin("Juan");
        logJuan.registrarFin("Juan");
        
        // mostrar el mas reciente
        String logReciente = LogPartida.obtenerLogMasReciente("Juan");
        System.out.println(logReciente);

    }
    }

