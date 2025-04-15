/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author alfonso
 */
public class Modelo {

    ArrayList<Coche> coches = new ArrayList<>();

    public void crearCoche(String matricula, String marca, String modelo, int anio, double precioSinIVA) {
        Coche coche = new Coche(modelo, marca, matricula, anio, precioSinIVA);
        coches.add(coche);
    }

    public ArrayList<Coche> leerCoches() {
        return coches;
    }

    public void actualizarCoche(String matricula, String matriculaNueva, String mar, String mod, int a, float p) {
        for (int i = 0; i < coches.size(); i++) {
            Coche car = coches.get(i);
            if (car.getMatricula().equals(matricula)) {
                if (!matriculaNueva.isBlank()) {
                    car.setMatricula(matriculaNueva);
                } else if (mar != null) {
                    car.setMarca(mar);
                } else if (!mod.isBlank()) {
                    car.setModelo(mod);
                }else if (p!=0) {
                    car.setAnio(a);
                }else if (a!=0) {
                    car.setPrecioConIVA(p);
                }
            }
        }

    }

}
