/*
 * Departamento de Informática y Automática
 * Universidad de Salamanca - Programación III
 * Copyright (C) J.R. García-Bermejo Giner
 */
package view;

import controller.Controller;

import static com.coti.tools.DiaUtil.clear;
import static com.coti.tools.Esdia.*;
import com.coti.tools.OpMat;
import static com.coti.tools.OpMat.printToScreen3;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
// THIS IS BRANCH VERSION_05, en español
/*
OBSERVESE QUE NO ES NORMAL DESARROLLAR EN ESPAÑOL
LOS EQUIPOS SON MULTINACIONALES Y EL IDIOMA DE DESARROLLO ES EL 

INGLÉS
 */

/**
 *
 * @author coti
 */
public class View {

    private final Controller c = new Controller();

    public void runMenu(String menu) {
        String option;
        boolean salir = false;
        do {
            clear();
            option = readString(menu).toLowerCase();
            clear();
            switch (option) {
                case "1" ->
                    this.archivos();
                case "2" ->
                    this.clasificacion();
                case "3" ->
                    this.verListado();
                case "4" ->
                    this.crud();// CREATE READ UPDATE DELETE
                case "q" ->
                    salir = siOno("¿Desea salir? ");
            } // Fin de switch()
        } while (!salir);
    } // Fin de runMenu()

    public void importar() {
        underline2("Proceso de Importación");
        out.printf(c.importar());
        out.printf("%n%nLa lista importada consta de %d elementos.%n%n", 
                c.getNumeroDeDiscosDuros());

    } // Fin de importar()

    public void exportar() {
        underline2("Proceso de Exportación");
        System.out.printf(c.exportar());
        out.printf("%n%nLa lista exportada consta de %d elementos.%n%n",
                c.getNumeroDeDiscosDuros());
    } // Fin de exportar()

    // Métodos auxiliares de Vista
    public void verListado() {
        String[][] listado = c.obtenerListado();
        String[] header = c.getNombresDeCampos().toArray(String[]::new);
        underline2("Visualización del listado general del discos duros");

        out.println();
        var tmp = OpMat.concat(header, listado);
        try {
            printToScreen3(tmp);
        } catch (Exception ex) {
            out.printf("%n%nLista vacía - Por favor, de importe o lea la lista%n%n");
        }
        out.println();

    } // Fin de verListado()

    private void clasificacion() {
        boolean salir = false;
        String option;

        String menu
                = "%n%nOrden de clasificación:%n"
                + "%n1.- Fabricante        2.- Megabytes              3.- Modelo"
                + "%n4.- Número de serie   5.- Tasa de Transmisión    6.- Tecnología"
                + "%n7.- Tipo de Interface 8.- Velocidad de Rotación   9.- Código"
                + "%n0.- FabModMeg"
                + "%nq.- Volver"
                + "%n  >";
        do {
            clear();
            option = readString(menu).toLowerCase();
            switch (option) {
                case "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" -> {
                    c.ordenarPor(option);
                    this.verListado();
                }
                case "q" ->
                    salir = true;
                default ->
                    out.printf("%n%nNo es posible ordenar por ese campo%n%n");
            } // Fin de switch
        } while (!salir);
    } // Fin de mostrarListadoOrdenado()

    private void guardar() {
        underline2("Guardar listado de discos duros");
        c.guardar();
        out.printf("%n%nSe han guardado %d registros.%n%n",
                c.getNumeroDeDiscosDuros());
    }

    private void leer() {
        underline2("Lectura de discos duros");
        c.leer();
        out.printf("%n%nSe ha leído %d registros%n%n",
                c.getNumeroDeDiscosDuros());

    }

    private void archivos() {
        boolean salir = false;
        String option;

        String menu
                = "%n%nArchivos:%n"
                + "%n1.- Leer"
                + "%n2.- Guardar"
                + "%n3.- Importar"
                + "%n4.- Exportar"
                + "%nq.- Volver"
                + "%n  >";
        do {
            clear();
            option = readString(menu).toLowerCase();
            switch (option) {
                case "1" ->          this.leer();
                case "2" ->          this.guardar();
                case "3" ->          this.importar();
                case "4" ->          this.exportar();
                case "q" ->          salir = true;
                default ->           out.printf("%n%nOpción incorrecta%n%n");
            } // Fin de switch
        } while (!salir);
    }

    private void crud() {
        boolean salir = false;
        String option;

        String menu
                = "%n%nCRUD:%n"
                + "%n1.- Create (Añadir)"
                + "%n2.- Read (Consultar)"
                + "%n3.- Update (Actualizar)"
                + "%n4.- Delete (Eliminar)"
                + "%nq.- Volver"
                + "%n  >";
        do {
            clear();
            option = readString(menu).toLowerCase();
            switch (option) {
                case "1" ->        this.create();
                case "2" ->        this.read();
                case "3" ->        this.update();
                case "4" ->        this.delete();
                case "q" ->        salir = true;
                default ->         out.printf("%n%nOpción incorrecta%n%n");
            } // Fin de switch
        } while (!salir);
    }

    /*
  Se añaden a continuación los métodos del menú CRUD
     */
    private void create() {
        clear();
        underline2("Añadir un nuevo disco duro");
        var nombres = c.getNombresDeCampos();

        //List<String> datos = nombres.stream().map(n -> readString(n + " : ")).toList();
        List<String> datos;
        datos = new ArrayList<>();
        for(int i=0;i<nombres.size();i++) {
            datos.add(readString(nombres.get(i)+ " : "));
        }
        var tmp = c.agregadoDiscoApartirDe(datos);
        if (tmp) {
            out.printf("%n%nSe ha creado un disco duro con los datos introducidos%n%n");
        } else {

            out.printf("%n%nLos datos introducidos no eran correctos, "
                    + "no se ha creado un registro%n%n");
            out.printf("%n%n%s%n", c.porQueSeDescarta(datos));
        }
    }

    // Buscar y mostrar un disco duro en la lista
    private void read() {
        clear();
        underline2("Búsquedas");
        var posibleCodigo = readString_ne("Escriba el código del disco duro"
                + " cuyos datos quiere consultar : ");
        List<String> datos = c.obtenerListaDeDatosParaElDiscoQueTieneEste(posibleCodigo);
        if (null == datos) {
            out.printf("%n%nEl código solicitado no existe%n%n");
        } else {
            out.printf("Los datos del disco solicitado son como sigue :%n%n");
            out.println(datos);
        }
    }

    // Actualizar un disco duro de la lista
    private void update() {
        clear();
        underline2("Modificar un disco duro");
        var nombres = c.getNombresDeCampos();
        var posibleCodigo = readString_ne("Escriba el código del disco duro que quiere modificar : ");
        var datosActuales = c.obtenerListaDeDatosParaElDiscoQueTieneEste(posibleCodigo);
        if (null == datosActuales) {
            out.printf("%n%nPerdon ese codigo no existe");
            return;
        }
        out.printf("Los datos actuales son como sigue: " + datosActuales.toString());
        out.printf("Escriba los datos que desea modificar o ENTER para no modificar un dato");
        // No se permite modificar el código
        for (int n = 0; n < datosActuales.size() - 1; n++) {
            out.printf("Valor actual de %s : [%s]%n", nombres.get(n),
                    datosActuales.get(n));
            var tmp = readString("Nuevo valor [o ENTER para no modificar] : ");
            if (!tmp.isEmpty()) {
                datosActuales.set(n, tmp);
            }
        }
        var tmp = c.agregadoDiscoApartirDe(datosActuales);
        if (tmp) {
            out.printf("%n%nSe han modificado correctamente los datos%n%n");
        } else {
            out.printf("%n%nLos datos modificados no eran correctos, el registro no se ha modificado%n%n");
            out.printf("%n%n%s%n", c.porQueSeDescarta(datosActuales));
        }

    }

    // Eliminar un disco duro de la lista
    private void delete() {
        underline2("Borrar un disco duro de la lista");
        var posibleCodigo = readString_ne("Escriba el código del disco duro"
                + " que quiere eliminar : ");
        boolean tmp = c.eliminadoDiscoConCodigo(posibleCodigo);
        if (tmp) {
            out.printf("%n%nSe ha borrado correctamente el disco indicado");
        } else {
            out.printf("%n%nEl código solicitado no existe%n%n");
        }

    }

} // End of class View

