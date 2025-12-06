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