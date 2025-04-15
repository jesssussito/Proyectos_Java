/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concesionario;

import vista.Vista;

/**
 *
 * @author alfonso
 */
public class ConcesarioMVC {

    public static void main(String[] args) {

        Vista v = new Vista();
        String menu ="\n1. Crear"
                + "\n2. Leer"
                + "\n3. Actualizar"
                + "\n4. Borrar"
                + "\n5. Salir\n";
        v.menu(menu);
    }
}
