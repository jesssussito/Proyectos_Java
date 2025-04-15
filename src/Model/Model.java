package Model;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
public class Model {
    private List<Task> Tasks = new ArrayList<>();
    private List<Task> Importacion = new ArrayList<>();
    
    private IExporter exp;
    private IRepository rep;
    public Model() {
        this.Tasks = new ArrayList<>();
    }
    public List<Task> getTasks(){
        return Tasks;
    }
    public void setRepository(IRepository rep){
        this.rep = rep;
    }
    public IRepository getRepository(){
        return rep;
    }
    public void setExporter(IExporter exp){
        this.exp=exp;
    }
    public String[] loadData() throws RepositoryException{
        if (rep instanceof BinaryRepost) 
        {
            Tasks = ((BinaryRepost)rep).loadTaskdearchivo();
            return sacarporPantalla(((BinaryRepost)rep).loadTaskdearchivo());   
        }
        else if (rep instanceof NotionRepost) 
        {
            Tasks = ((NotionRepost)rep).getAllTasks(); 
            return sacarporPantalla(Tasks);
        }
        else{
            return null;
        }
    }
    public void saveData()throws RepositoryException{
        if (rep instanceof BinaryRepost) 
        {   
            try{
                
                ((BinaryRepost)rep).saveTaskalarchivo(Tasks); 
            }catch(Exception e){
                throw e;
            }
        }
        else if (rep instanceof NotionRepost) 
        {   
            //Como es notion se guardan en internet al momento
            //return ((NotionRepost)rep).loadTaskdearchivo(); 
            
        }
        else{
            throw new RepositoryException("Se ha intentado guradar en un repositorio no valido");
        }
    }
    public void exportTasks(String ruta)throws ExporterException{
        if (exp == null) 
        {
            throw new ExporterException("No se ha establecido el exportador\n");
        }
        else    
            exp.export(Tasks,ruta);
    }
    public void elimino(int ident)throws RepositoryException{
        if(Tasks != null)
        {
            try {
                for (Task t : Tasks) 
                {
                    if (t.getIdentifier()==ident) 
                    {
                        rep.removeTask(t);
                    }
                }
            } catch (RepositoryException e) {
                throw e;
            }
         }
        
    }
    public void darAlta(Task task)throws RepositoryException{
        try {
            Task nueva=rep.addTask(task);
            if (Tasks==null) 
            {
                Tasks = new ArrayList<>();
                Tasks.add(nueva);
            }
            else{
                Tasks.add(nueva);
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Task> imporTasks(String ruta)throws ImporterException{
        if(exp == null)
        {
            throw new ImporterException("No se ha establecido el exportador\n");   
        }
        else
        {
            this.Importacion =  exp.importTasks(ruta);
            if(Importacion != null)
            {
                Iterator<Task> i = Importacion.iterator();
                while(i.hasNext())
                {
                    Task t = i.next();
                    if(t!=null)
                    {
                        t.sacarApantalla();
                        if (noRepetidoM(t.getIdentifier()))
                        {
                            Tasks.add(t);
                        }
                    }
                }
            }
            if (Importacion.isEmpty()||Importacion==null) {
                throw new ImporterException("La Lista Importacion no es valida\n");
                
            }
            return Tasks;
        }
    }
    public int CoPM(int indice)throws RepositoryException{
        try {
            switch (rep.CoP(indice)) {
                case 0:
                    return 0;
                case -1:
                    return -1;
                case 3:
                    return 3;
                default:
                    return 2;
        } 
    }catch (Exception e) {
            throw e;
        }
    }
    public boolean noRepetidoM(int identificador){
        if (Tasks != null && !Tasks.isEmpty()) 
        {
           for(Task t :Tasks)
           {
                if (t.getIdentifier()==identificador) 
                {
                    return false;
                }
           }
        }
        return true;
    }
    public String[] sacarporPantalla(List<Task> Lista){
        String[] sacar = new String[Lista.size()];
        int cont=0;   
        for(Task t : Lista)
            {
                if(!(t == null))
                {
                    sacar[cont]=t.sacarApantalla();
                    cont++;
                }
            }
        return sacar;
        }
    public void Modifico(Task task) throws RepositoryException{
        if(Tasks == null || Tasks.isEmpty())
        {
            throw new RepositoryException("La Lista esta vacia o no inicializada.\n");
        }
        rep.modifyTask(task);
    }
    public String[] ListaOrden(boolean Tipo)throws RepositoryException{
        if (Tipo) 
        {
            try {
                if (Tasks == null) 
                {
                    throw new RepositoryException("Error en la creacion del Listado.\n");
                }
                else{
                    return sacarporPantalla(rep.getAllTasks());
                }
            } catch (Exception e) {
                throw e;
            }
        }
        else
        {
            try {
                if (Tasks == null) 
                {
                    throw new RepositoryException("Error en la creacion del Listado.\n");
                }
                else{
                    return sacarporPantalla(listadoParcial());
                }
            } catch (Exception e) {
                throw e;
            }
        }
        
    }
    public List<Task> listadoParcial()throws RepositoryException{
            ArrayList<Task> listar = rep.getAllTasks();
            if(listar == null)
            {
                throw new RepositoryException("El Listado de tareas es null.\n");
            }
            ArrayList<Task> OrdenP = new ArrayList<>();
            for (Task t : listar) 
            {
                if (!(t==null)&&(t.isCompleted()==false))
                {
                    OrdenP.add(t);
                }
                else if (t==null) 
                {
                    throw new RepositoryException("En el Listado de tareas una Tarea es null.\n");
                }
            }
            OrdenP.sort(Comparator.comparingInt(Task::getPriority).reversed());
            return OrdenP;
        }
    }
    