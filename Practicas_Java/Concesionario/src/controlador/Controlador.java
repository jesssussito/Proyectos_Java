/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import modelo.Coche;
import modelo.Modelo;

/**
 *
 * @author alfonso
 */
public class Controlador {

    Modelo m = new Modelo();

    public void crearCoche(String matricula, String marca, String modelo, int anio, double precioSinIVA) {
        m.crearCoche(matricula, marca, modelo, anio, precioSinIVA);
    }

    public ArrayList<Coche> leerCoches() {
        return m.leerCoches();

    }

    public void actulizarCoche(String matriculaIdentificador, String matriculaNueva, String mar, String mod, int a, float p) {
        m.actualizarCoche(matriculaIdentificador, matriculaNueva, mar, mod, a, p);
    }

}
