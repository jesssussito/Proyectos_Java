/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package excepciones;

import com.coti.tools.Rutas;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Excepciones y Log
 *
 * @author Loza
 */
public class Excepciones {

    // Deberíamos tener un siempre un logger para llevar un log de 
    // lo que ocurre en la aplicación. Existen muchas bibliotecas
    // para este cometido dependiendo de la plataforma (Log4j, etc)
    // Por norma general permiten añadir las excepciones para realizar el log
    // Las bibliotecas de Log suelen tener varios niveles (info, debug, error, etc.)
    // Por los que se podrá filtrar posteriormente
    static Logger LOG = Logger.getLogger(Excepciones.class.getName());

    /**
     * Algunos ejemplos de excepciones
     *
     * @param args Ninguno
     */
    public static void main(String[] args) {

        // LOG Level de la aplicación (Mostrará mensajes de log con mayor nivel del elegido)
        /**
         * SEVERE(Más alto) WARNING INFO CONFIG FINE FINER FINEST(Más bajo)
         *
         * Incluyendo ALL y OFF
         *
         */
        // Probad distintos niveles de log (aquí y en las excepciones)
        LOG.setLevel(Level.CONFIG);

        Persona p = new Persona("Fulano", "Mengano", 22);
        File f = Rutas.fileToFileOnDesktop("fichero3.bin");

        try {

            //EJEMPLOS:
            ejemploManejarExcepcion();
            //serializaObjeto(p, f);
            deserializaObjeto(f);

            // Podríamos pensar que acaba aquí el programa...
            // Pero el finally siempre se ejecuta despues de un bloque try catch
            return;

        } catch (SerializationException ex) {

            // Mensaje al usuario (no tiene por qué mostrar información de la traza)
            System.out.println("Se ha producido un error de serialización");

            // Traza para el desarrollador
            LOG.log(Level.SEVERE, "Error de serialización", ex);
            //ex.printStackTrace(System.err);
        } finally {
            System.out.println("Paso por el finally siempre");
        }
    }

    /**
     *
     * Función para ejemplificar el manejo de una excepción
     *
     */
    public static void ejemploManejarExcepcion() {

        File f = Rutas.fileToFileOnDesktop("ficheroQueNoExiste.bin");

        try {
            List<String> lineas = Files.readAllLines(f.toPath());

        } catch (IOException ex) {
            // Decidir que hacer aquí
            // Por ejemplo mensaje a un log
            LOG.log(Level.SEVERE, "El fichero no existe", ex);
        }

    }

    /**
     * *
     * Serializa el objeto pasado por parámetro
     *
     * Empleamos try catch with resources Java 7 cerrará el stream especificado
     * justo después
     *
     * @param p objeto persona a Serializar
     * @param f objeto
     * @throws excepciones.SerializationException
     */
    public static void serializaObjeto(Persona p, File f) throws SerializationException {

        try ( ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            oos.writeObject(p);
        } catch (IOException e) {
            // Envolver la excepcion IOException con una propia
            // añadiendo un mensaje propio
            throw new SerializationException("I/O error serializando", e);
        }

    }

    /**
     * *
     * Serializa el objeto pasado por parámetro
     *
     * Empleamos try catch with resources Java 7 cerrará el stream especificado
     * justo después
     *
     * @param f fichero en el que se encuentra el objeto a deserializar
     * @return objeto Persona deserializado
     * @throws excepciones.SerializationException Error de serialización
     */
    public static Persona deserializaObjeto(File f) throws SerializationException {

        try ( ObjectInputStream oos = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(f)))) {
            return (Persona) oos.readObject();
        } catch (IOException ex) {
            // Envolver la excepcion y añadir mensaje
            throw new SerializationException("Problema de I/O", ex);
        } catch (ClassNotFoundException ex) {
            // Envolver la excepcion sin añadir mensaje
            throw new SerializationException(ex);
        }

    }

}
