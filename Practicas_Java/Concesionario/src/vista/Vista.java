/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.coti.tools.Esdia;
import controlador.Controlador;
import java.util.ArrayList;
import modelo.Coche;

/**
 *
 * @author alfonso
 */
public class Vista {

    Controlador c = new Controlador();

    public void menu(String menu) {

        System.out.println("\tBIENVENIDOS A AUTOS-SALAMANCA");
        System.out.println("\t-----------------------------\n ");

        String opcion;
        boolean decision = false;

        while (!decision) {
            opcion = Esdia.readString(menu);
            switch (opcion) {
                case "1":
                    System.out.println("Crear");
                    crearCoche();
                    break;
                case "2":
                    System.out.println("Leer");
                    leerCoches();
                    break;
                case "3":
                    System.out.println("Actualizar");
                    actualizarCoches();
                    break;
                case "4":
                    System.out.println("Borrar");
                    borrarCoche();
                    break;
                case "5":
                    decision = Esdia.yesOrNo("¿Desea salir?");
                    break;
                default:
                    System.out.println("Disculpe, pero no existe esa opción");
            }
        }

    }

    private void crearCoche() {
        String matricula = Esdia.readString_ne("Por favor, introduzca la matricula del coche: ");
        String marca = Esdia.readString_ne("Por favor, introduzca la marca del coche: ");
        String modelo = Esdia.readString_ne("Por favor, introduzca el modelo del coche: ");
        int anio = Esdia.readInt("Por favor, introduzca el año de fabricación del coche: ");
        double precioSinIVA = Esdia.readDouble("Por favor, introduzca el precio sin iva del coche: ");

        c.crearCoche(matricula, marca, modelo, anio, precioSinIVA);

    }

    private void leerCoches() {
        ArrayList<Coche> coches = new ArrayList<>();

        coches = c.leerCoches();
        for (Coche car : coches) {

            System.out.println(car.toString());

        }
        /*for (Coche car : coches) {
            System.out.println("\nMatricula: "+car.getMatricula()+
                    "\nMarca: "+car.getMarca()+
                    "\nModelo: "+car.getModelo());

        }*/

    }

    private void actualizarCoches() {

        
        String matriculaNueva=null,mar=null,mod=null;
        int a=0;
        float p=0;
        String matricula = Esdia.readString("Introduzca la Matricula de coche a actualiza: ");

        String menu = "\n1. Matricula"
                + "\n2. Marca"
                + "\n3. Modelo"
                + "\n4. Año"
                + "\n5. Precio sin IVA\n";

        String opcion = Esdia.readString(menu);
        switch (opcion) {
            case "1":
                matriculaNueva = Esdia.readString("Introduzca la nueva Matricula: ");
                break;
            case "2":
                 mar = Esdia.readString("Introduzca la Marca: ");
                break;
            case "3":
                 mod = Esdia.readString("Introduzca el Modelo: ");
                break;
            case "4":
                 a = Esdia.readInt("Introduzca el Año: ");
                break;
            case "5":
                 p = Esdia.readFloat("Introduzca el Precio sin IVA: ");
                break;
            default:
                System.out.println("Disculpe, pero no existe esa opción");
        }
        c.actulizarCoche(matricula, matriculaNueva,mar,mod,a,p);
    }


private void borrarCoche() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
