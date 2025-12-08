/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socketsconsola;

/**
 *
 * @author lacay
 */
import java.io.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
/**
 *
 * @author lacay
 */
public class GestorJson {
    private static final String ARCHIVO = "/Personajes.json";
    private static final String ARCHIVO2 = "/Jugadores.json";
    private static final String ARCHIVO_ARMAS = "/Armas.json";
    
    public static List<Personajes> cargarPersonajes() throws IOException{
        
        InputStream stream = GestorJson.class.getResourceAsStream(ARCHIVO);
        
        if (stream == null) {
            System.err.println("Error: Archivo no encontrado en el classpath: " + ARCHIVO);
            return Collections.emptyList();
        }
        
        try(Reader reader = new InputStreamReader(stream)){

            Gson gson = new Gson();
        
            Type listType = new TypeToken<List<Personajes>>() {}.getType();
            List<Personajes> listaPersonajes = gson.fromJson(reader, listType);
            return (listaPersonajes != null) ? listaPersonajes : Collections.emptyList();
        
        }catch(Exception e){
            System.err.println("Error al procesar el JSON: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    public static List<Jugador> cargarJugador(){
        InputStream stream = GestorJson.class.getResourceAsStream(ARCHIVO2);
        if (stream == null) {
            System.err.println("Error: Archivo no encontrado en el classpath: " + ARCHIVO2);
            return Collections.emptyList();
        }
        try(Reader reader = new InputStreamReader(stream)){
            Gson gson = new Gson();
        
            Type listType = new TypeToken<List<Jugador>>() {}.getType();
            List<Jugador> jugador = gson.fromJson(reader, listType);
            return (jugador != null) ? jugador : Collections.emptyList();
        
        }catch(Exception e){
            System.err.println("Error al procesar el JSON: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    private static void guardarJugadores(List<Jugador> jugadores) {
        try {
            URL resourceUrl = GestorJson.class.getResource(ARCHIVO2);

            if (resourceUrl == null) {
                System.err.println("Error: No se puede encontrar el archivo");
                return;
            }

            String rutaTarget = resourceUrl.getPath();
            rutaTarget = java.net.URLDecoder.decode(rutaTarget, "UTF-8");

            if (rutaTarget.startsWith("/") && rutaTarget.contains(":")) {
                rutaTarget = rutaTarget.substring(1);
            }

            String rutaSrc = rutaTarget.replace("target/classes", "src/main/resources");

            File fileSrc = new File(rutaSrc);
            File fileTarget = new File(rutaTarget);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(jugadores);

            try (FileWriter writer = new FileWriter(fileSrc)) {
                writer.write(json);
                writer.flush();
            }

            try (FileWriter writer = new FileWriter(fileTarget)) {
                writer.write(json);
                writer.flush();
            }

            System.out.println("JSON guardado en ambas ubicaciones");

        } catch (Exception e) {
            System.err.println("Error al guardar el JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Buscar jugador por nombre
    private static Jugador buscarJugador(List<Jugador> jugadores, String nombre) {
        for (Jugador j : jugadores) {
            if (j.getNombre().equalsIgnoreCase(nombre)) {
                return j;
            }
        }
        return null;
    }
    
    // Métodos para incrementar atributos
    
    public static void incrementarWins(String nombreJugador, int cantidad) {
        List<Jugador> jugadores = cargarJugador();
        Jugador jugador = buscarJugador(jugadores, nombreJugador);
        
        if (jugador != null) {
            jugador.setWins(jugador.getWins() + cantidad);
            guardarJugadores(jugadores);
            System.out.println("Wins actualizado para " + nombreJugador);
        } else {
            System.err.println("Jugador no encontrado: " + nombreJugador);
        }
    }
    
    public static void incrementarLoses(String nombreJugador, int cantidad) {
        List<Jugador> jugadores = cargarJugador();
        Jugador jugador = buscarJugador(jugadores, nombreJugador);
        
        if (jugador != null) {
            jugador.setLoses(jugador.getLoses() + cantidad);
            guardarJugadores(jugadores);
            System.out.println("Loses actualizado para " + nombreJugador);
        } else {
            System.err.println("Jugador no encontrado: " + nombreJugador);
        }
    }
    
    public static void incrementarAttacks(String nombreJugador, int cantidad) {
        List<Jugador> jugadores = cargarJugador();
        Jugador jugador = buscarJugador(jugadores, nombreJugador);
        
        if (jugador != null) {
            jugador.setAttacks(jugador.getAttacks() + cantidad);
            guardarJugadores(jugadores);
            System.out.println("Attacks actualizado para " + nombreJugador);
        } else {
            System.err.println("Jugador no encontrado: " + nombreJugador);
        }
    }
    
    public static void incrementarSuccess(String nombreJugador, int cantidad) {
        List<Jugador> jugadores = cargarJugador();
        Jugador jugador = buscarJugador(jugadores, nombreJugador);
        
        if (jugador != null) {
            jugador.setSuccess(jugador.getSuccess() + cantidad);
            guardarJugadores(jugadores);
            System.out.println("Success actualizado para " + nombreJugador);
        } else {
            System.err.println("Jugador no encontrado: " + nombreJugador);
        }
    }
    
    public static void incrementarFailed(String nombreJugador, int cantidad) {
        List<Jugador> jugadores = cargarJugador();
        Jugador jugador = buscarJugador(jugadores, nombreJugador);
        
        if (jugador != null) {
            jugador.setFailed(jugador.getFailed() + cantidad);
            guardarJugadores(jugadores);
            System.out.println("Failed actualizado para " + nombreJugador);
        } else {
            System.err.println("Jugador no encontrado: " + nombreJugador);
        }
    }
    
    public static void incrementarGiveup(String nombreJugador, int cantidad) {
        List<Jugador> jugadores = cargarJugador();
        Jugador jugador = buscarJugador(jugadores, nombreJugador);
        
        if (jugador != null) {
            jugador.setGiveup(jugador.getGiveup() + cantidad);
            guardarJugadores(jugadores);
            System.out.println("Giveup actualizado para " + nombreJugador);
        } else {
            System.err.println("Jugador no encontrado: " + nombreJugador);
        }
    }
    public static void agregarJugador(String nombre) {
        List<Jugador> jugadores = cargarJugador();

        if (buscarJugador(jugadores, nombre) != null) {
            System.err.println("El jugador '" + nombre + "' ya existe en el sistema");
            return;
        }

        Jugador nuevoJugador = new Jugador();
        nuevoJugador.setNombre(nombre);
        nuevoJugador.setWins(0);
        nuevoJugador.setLoses(0);
        nuevoJugador.setAttacks(0);
        nuevoJugador.setSuccess(0);
        nuevoJugador.setFailed(0);
        nuevoJugador.setGiveup(0);

        jugadores.add(nuevoJugador);

        guardarJugadores(jugadores);

        System.out.println("Jugador '" + nombre + "' agregado exitosamente");
    }
    
    public static Map<String, List<Armas>> cargarCatalogoArmas() {
        InputStream stream = GestorJson.class.getResourceAsStream(ARCHIVO_ARMAS);
        if (stream == null) {
            return Collections.emptyMap(); // Devuelve un mapa vacío en lugar de una lista vacía
        }

        try(Reader reader = new InputStreamReader(stream)){
            Gson gson = new Gson();
            
            // Definición del tipo complejo (Mapa de String a Lista de Armas)
            Type tipoCatalogo = new TypeToken<Map<String, List<Armas>>>(){}.getType();
            
            Map<String, List<Armas>> catalogo = gson.fromJson(reader, tipoCatalogo);
            
            // Nota: Si el error de Random persiste, es probable que se deba a 
            // la dependencia de Gson en una versión de Java moderna.
            
            return (catalogo != null) ? catalogo : Collections.emptyMap();

        }catch(Exception e){
            System.err.println("Error al procesar el JSON de Armas: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
    
  
}