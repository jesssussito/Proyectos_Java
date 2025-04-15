package View;
import static com.coti.tools.Esdia.*;

import java.io.File;
import java.util.Calendar;

import Controller.Controller;
import Model.Task;
public class InteractiveView extends BaseView {
    private Calendar calendar = Calendar.getInstance();
    @Override
    public void setController(Controller controller) {
        this.c = controller;
    }
    @Override
    public void init(){
    boolean salir = false;

    do {
        System.out.println("____________________________________");
        System.out.println("  BIENVENIDO A LA PRACTICA FINAL    ");
        System.out.println("____________________________________");
        System.out.println("   [1]       MENU CRUD              ");
        System.out.println("____________________________________");
        System.out.println("   [2]  MENU EXPORTACION/IMPORTACION");
        System.out.println("____________________________________");
        System.out.println("   [3]          SALIR               ");
        System.out.println("____________________________________\n");
        int op = readInt(ANSI_BLACK, 1, 3);
        switch (op) {
            case 1:
                espaciar();
                MenuCRUD();
                break;

            case 2:
                espaciar();
                ExportImport();
                break;

            case 3:
                espaciar();
                salir = true;
                break;

            default:
                showErrorMessage("Opción no válida.");
                espaciar();
                break;
        }
    } while (!salir);
}
private void ExportImport() {
    boolean salir3 = false;

    do {
        System.out.println("____________________________________");
        System.out.println("  MENU EXPORTACION / IMPORTACION   ");
        System.out.println("____________________________________");
        System.out.println("   [1] EXPORTAR ARCHIVO             ");
        System.out.println("____________________________________");
        System.out.println("   [2] IMPORTAR ARCHIVO             ");
        System.out.println("____________________________________");
        System.out.println("   [3] SALIR                        ");
        System.out.println("____________________________________");

        int opcion = readInt(ANSI_BLACK, 1, 3);

        switch (opcion) {
            case 1:
                espaciar();
                try {
                    switch ( c.manejadoraIE(pedirIE())) {
                        case 0:
                            showErrorMessage("Error en la seleccion de IExporter");
                            break;
                        case 1:
                            c.export(false);
                            break;
                        case 2:
                            c.export(true);
                            break;
                        default:
                            break;
                       }
                } catch (Exception e) {
                    showErrorMessage("Error :"+e.getMessage());
                }
                break;
            case 2:
                espaciar();
                try {
                    switch ( c.manejadoraIE(pedirIE())) {
                        case 0:
                            showErrorMessage("Error en la seleccion de IExporter");
                            break;
                        case 1:
                        //csv
                            c.importer(false);
                            break;
                        case 2:
                        //json
                            c.importer(true);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    showErrorMessage("Error :"+e.getMessage());
                }
                break;
            case 3:
                espaciar();
                salir3 = true;
                break;

            default:
                showErrorMessage("Opción no válida.");
                espaciar();
                break;
        }
    } while (!salir3);
}
    public void MenuCRUD(){
    System.out.println("________________________________________");
    System.out.println("          MENU CRUD                     ");
    System.out.println("________________________________________");
    System.out.println("    [1]   DAR DE ALTA UNA TAREA         ");
    System.out.println("________________________________________");
    System.out.println("    [ ]      LISTADO DE TAREAS          ");
    System.out.println("    \t[2]      COMPLETO                ");
    System.out.println("    \t[3]    SIN COMPLETAR POR PRIORIDAD");
    System.out.println("________________________________________");
    System.out.println("    [4]    SALIR                        ");
    System.out.println("________________________________________");

    int op2 = readInt(ANSI_BLACK, 1, 4);

    switch (op2) 
    {
        case 1:
            espaciar();
            try {
                c.alta();
            } catch (Exception e) {
                showErrorMessage("Error al dar de alta: " + e.getMessage());
            }
            break;

        case 2:
            espaciar();
            manejarListado(true);
            break;

        case 3:
            espaciar();
            manejarListado(false);
            break;

        case 4:
        default:
            espaciar();
            break;
    }
}

private void manejarListado(boolean completo) {
    c.Listar(completo);
    System.out.println("____________________________________________");
    System.out.println(" [1] Marcar Tarea como Completada/Incompleta");
    System.out.println("____________________________________________");
    System.out.println(" [2]          Modificar  Tarea              ");
    System.out.println("____________________________________________");
    System.out.println(" [3]           Eliminar  Tarea              ");
    System.out.println("____________________________________________");
    System.out.println(" [4]              Volver                    ");
    System.out.println("____________________________________________");

    int op = readInt(ANSI_BLACK, 1, 4);

    switch (op) {
        case 1:
            espaciar();
            c.CompletaoIncompleta();
            break;

        case 2:
            espaciar();
            c.Modifico(pedirId());
            break;

        case 3:
            espaciar();
            c.elimino();
            break;

        case 4:
        default:
            espaciar();
            break;
    }
}

    @Override
    public void showMessage(String message){
        System.out.println(message + "\n");
    }
    @Override
    public void showErrorMessage(String message){
        System.err.println(message+"\n");
    }
    @Override
    public void SacoPantalla(String[] sacar){
        for (String s : sacar) {
            System.out.println(s+"\n");
        }
    }
    @Override
    public int pedirId(){
        int identifier = readInt("Introduzca el identificador que tiene la tarea:\n");
        return identifier;
    }
    public String pedirIE(){
        String IE = readString_ne("Que tipo de IExporter vas a usar:\n ");
        return IE;
    }
    @Override
    public String pedirRuta(boolean csvOjson,boolean paraExportar){
        String ruta= null;
        if (csvOjson) 
        {
            while (true) 
            {
                ruta = readString_ne("Ingrese la ruta al archivo .json\n");
                File f =  new File(ruta);
                if (paraExportar) 
                {
                    File padre = f.getParentFile();
                    if (padre==null) {
                        padre = new File(".");
                        continue;
                    }
                    
                    if (!padre.exists() && !padre.mkdirs()) {
                        showErrorMessage("El directorio de destinado a exportarse no existe y no se pudo crear.\n");
                        continue;
                    }
                    if (!ruta.toLowerCase().endsWith(".json")) {
                        showErrorMessage("Formato de archivo no valido\n");  
                        continue;
                    }
                    showMessage("Ruta valida"+ruta);
                    break;
                }
                else
                {
                    if (!f.exists()) {
                        showErrorMessage("El archivo no existe.\n");
                        continue;
                    }
                    if (!f.isFile()) {
                        showErrorMessage("La ruta no corresponde a un archivo.\n"); 
                        continue; 
                    }
                    if (!f.canRead()) {
                        showErrorMessage("El archivo no posee permisos de lectura.\n"); 
                        continue;   
                    }
                    if (!ruta.toLowerCase().endsWith(".json")) {
                        showErrorMessage("Formato de archivo no valido\n");  
                        continue;
                    }
                    showMessage("Ruta valida"+ruta);
                    break;
                }
            }
            return ruta;
        }
        else{
            while (true) 
            {
                ruta = readString_ne("Ingrese la ruta al archivo .csv\n");
                File f =  new File(ruta);
                if (paraExportar) 
                {
                    File padre = f.getParentFile();
                    if (padre==null) {
                        padre = new File(".");
                        continue;
                    }
                    
                    if (!padre.exists() && !padre.mkdirs()) {
                        showErrorMessage("El directorio de destinado a exportarse no existe y no se pudo crear.\n");
                        continue;
                    }
                    if (!ruta.toLowerCase().endsWith(".csv")) {
                        showErrorMessage("Formato de archivo no valido\n");  
                        continue;
                    }
                    showMessage("Ruta valida"+ruta);
                    break;
                }
                else
                {
                    if (!f.exists()) {
                        showErrorMessage("El archivo no existe.\n");
                        continue;
                    }
                    if (!f.isFile()) {
                        showErrorMessage("La ruta no corresponde a un archivo.\n"); 
                        continue; 
                    }
                    if (!f.canRead()) {
                        showErrorMessage("El archivo no posee permisos de lectura.\n"); 
                        continue;   
                    }
                    if (!ruta.toLowerCase().endsWith(".csv")) {
                        showErrorMessage("Formato de archivo no valido\n");  
                        continue;
                    }
                    showMessage("Ruta valida"+ruta);
                    break;
                }
            }
            return ruta;
        }   
    }

    public Task pedirDatos(){
        int priority,estimatedDuration;
        int identifier;
        String title,content;
        boolean completed;
        title = readString_ne("Ingrese el nuevo Titulo:\n");
        priority = readInt("Introduce la nueva prioridad:\n",1,5);
        estimatedDuration = readInt("Introduce el nuevo estimo de duracion:\n",1,60);
        content = readString_ne("Ingrese el nuevo content:\n");
        completed = yesOrNo("Esta la tarea completada?:[ y / n ] \n");
        identifier = readInt("Ingrese el nuevo identificador:\n");
        Task newTask = new Task(identifier,title,null,content,priority,estimatedDuration,completed);
        ObtenerFechaV(newTask);
        return newTask;
    }
    public Task modifyTask(int identificador){
        int priority,estimatedDuration;
        String title,content;
        boolean completed;
        title = readString_ne("Ingrese el nuevo Titulo:\n");
        priority = readInt("Introduce la nueva prioridad:\n",1,5);
        estimatedDuration = readInt("Introduce el nuevo estimo de duracion:\n",1,60);
        content = readString_ne("Ingrese el nuevo content:\n");
        completed = yesOrNo("Esta la tarea completada?:[ y / n ] \n");
        Task newTask = new Task(identificador,title,null,content,priority,estimatedDuration,completed);
        ObtenerFechaV(newTask);
        return newTask;
    }
     public void ObtenerFechaV(Task task){
        
        int dia=0;
        int mes,año;
        año = readInt("Introduce el año:\n",0,2024);
        if (año % 4 == 0) 
        {
            mes = readInt("Introduce el mes:\n",1,12);
            switch (mes) {
                case 1,3,5,7,8,10,12:
                    dia = readInt("Introduce el dia:\n",1,31);
                    break;
            
                case 4,6,9,11:
                    dia = readInt("Introduce el dia:\n",1,30);
                    break;
                case 2:
                    dia = readInt("Introduce el dia:\n",1,29);
                    break;
            } 
        }
        else
        {
            mes = readInt("Introduce el mes:\n",1,12);
            switch (mes) 
            {
                case 1,3,5,7,8,10,12:
                    dia = readInt("Introduce el dia:\n",1,31);
                    break;
            
                case 4,6,9,11:
                    dia = readInt("Introduce el dia:\n",1,30);
                    break;
                case 2:
                    dia = readInt("Introduce el dia:\n",1,28);
                    break;
             }
        } 
        
        if (task.getDate()== null) {
             calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, año);
            calendar.set(Calendar.MONTH, mes - 1); 
            calendar.set(Calendar.DAY_OF_MONTH, dia);
            task.setDate(calendar.getTime());
        }
        
    }
    private void espaciar(){
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");

    }
    @Override
    public void end(){}
}
