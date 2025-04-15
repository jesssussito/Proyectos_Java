/*
 * Departamento de Informática y Automática
 * Universidad de Salamanca - Programación III
 * Copyright (C) J.R. García-Bermejo Giner
 */
package discosduros;

/*
Refactorizado respecto a version Version_03, ahora con menús
jerárquicos.
 */
import static com.coti.tools.DiaUtil.*;
import static com.coti.tools.Esdia.underline2;
import view.View;

public class DiscosDuros {
// Branch V_5 Español

    public static void main(String[] args) {

        View v = new View();

        clear();
        underline2("Discos Duros");
        v.runMenu("%n1.- Archivos"
                + "%n2.- Clasificación"
                + "%n3.- Listados"
                + "%n4.- CRUD"
                + "%nq.- Salir"
                + "%n  > ");
        showFinalTime();
    } // End of method main()

} // End of class DiscosDuros
