package Controller;
import Model.ExporterException;
import Model.ExporterFactory;
import Model.IExporter;
import Model.IRepository;
import Model.ImporterException;
import Model.Model;
import Model.NotionRepost;
import Model.RepositoryException;
import View.BaseView;

import Model.BinaryRepost;
public class Controller{
    private Model m;
    private BaseView v;
    

    public Controller(Model model, BaseView view,String[] args) {
        this.m = model;
        this.v = view;
        this.v.setController(this);
        IRepository repository = configuraRepository(args);
        this.m.setRepository(repository);
    }
    public IRepository configuraRepository(String[] args){
        if (args.length>1 && args[0].equals("--repository")) 
        {
            String tipo = args[1]; 
            if (tipo.equalsIgnoreCase("notion")&& args.length == 4) 
            {
                String apiKey = args[2];
                String dataBase = args[3];
                v.showMessage("Usando Notion repository\n");
                return new NotionRepost(apiKey,dataBase);
            }
            else if (tipo.equalsIgnoreCase("bin")) 
            {
                try {
                    v.showMessage("Usando Bin repository\n");
                    return new BinaryRepost();
                } catch (Exception e) {
                    v.showErrorMessage("Error durante la carga de repositorio:"+e.getMessage());
                }
            }
            else
            {
                v.showErrorMessage("Argumentos invalidos.\n");
                System.exit(1);
            }
        }
        try {
            v.showMessage("\nUsando Bin repository por defecto\n");
            return new NotionRepost("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", "15950b0d112f80ae9bdef1929ff64172");
        } catch (Exception e) {
            v.showErrorMessage("Error durante la carga de repositorio:"+e.getMessage());
        }
                v.showMessage("Estoy en caso null");
                return null;
                
       

    }
    public int manejadoraIE(String tipo) throws ExporterException{
        try {
            IExporter exp = ExporterFactory.getExporter(tipo.toLowerCase());
            m.setExporter(exp);
        } catch (ExporterException e) {
            throw e;
        }
        if (tipo.toLowerCase().equals("csv")){
            return 1;
        }
        else if (tipo.toLowerCase().equals("json"))
        {
            return 2;
        }
        else
            return 0;
    }
    public void export(boolean csvOjson){
        try {
            m.exportTasks(v.pedirRuta(csvOjson,true));
            v.showMessage("Exportacion realizada con exito \n");
        } catch (ExporterException e) {
            v.showErrorMessage("Error:"+e.getMessage());
        }
    }
    public void importer(boolean csvOjson){
        try {
            m.imporTasks(v.pedirRuta(csvOjson,false));
            v.showMessage("Importacion realizada con exito \n");
        } catch (ImporterException e) {
            v.showErrorMessage("Error:"+e.getMessage());
        }
    }
    public void iniciar(){
        v.init();
    }
    public void alta() throws RepositoryException{
        v.showMessage("Usted ha elegido dar de alta\n");
        try {
            m.darAlta(v.pedirDatos());
            v.showMessage("Task creada con exito.\n");
        } catch (Exception e) {
            throw e;
        }
    }
   public void Listar(boolean tipoListado){
        try {
            v.SacoPantalla(m.ListaOrden(tipoListado));
        } catch (Exception e) {
            v.showErrorMessage("Error en el Listado:"+e.getMessage());
        }
   }
    public void CompletaoIncompleta(){
        try {
            switch ((m.CoPM(v.pedirId()))) {
                case -1:
                    v.showErrorMessage("Una tarea era null\n");
                    break;
                case 0:
                    v.showMessage("Cambio completado correctamente\n");
                    break;
                case 2:
                    v.showErrorMessage("La coleccion OrdenP no existía, vuelva a probar\n");
                    break;
                case 3:
                    v.showErrorMessage("Indentificador no encontrado\n");
                    break;
                default:
                    break;
            }    
        } catch (Exception e) {
            v.showErrorMessage("Error:"+e.getMessage());
        }
    }
    public void Modifico(int identificador){
       v.showMessage("Usted ha elegido modificar la tarea con id:"+identificador+"\n");
        try {
            m.Modifico(v.modifyTask(identificador));
       } catch (Exception e) {
            v.showErrorMessage("Error al modificar la tarea:"+e.getMessage());
       }       
    }
    public void elimino(){
        try {
            m.elimino(v.pedirId());
        } catch (RepositoryException e) {
            v.showErrorMessage("Error en la eliminacion:"+e.getMessage());
        }
    }
    public void cargarDelRepost(){
        try {
                if(m.loadData()==null)
                {
                    v.showErrorMessage("Repositorio no valido\n");
                }
                else{
                    v.showMessage("Repositorio ejecutado correctamente\n");
                    v.showMessage("Se han cargado las tareas\n");
                }
        } catch (Exception e) {
            v.showErrorMessage("Error durante la carga del repositorio"+e.getMessage()+"\n");
        }
    }
    public void fin(){
        try {
            m.saveData();
            v.showMessage("Tareas guardadas correctamente en el repositorio.\n");
        } catch (Exception e) {
            v.showErrorMessage("Error durante la carga al repositorio:"+e.getMessage());
        }
    }
}