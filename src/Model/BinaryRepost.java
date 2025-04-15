package Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
public class BinaryRepost implements IRepository {
    private String rutaAbin;
    private List<Task> Tasks = new ArrayList<>();
    private ArrayList<Task> OrdenT = new ArrayList<>();
    public BinaryRepost()throws RepositoryException{
        this.rutaAbin =System.getProperty("user.home")+File.separator + "tasks.bin";
        File f = new File(this.rutaAbin);
        if(f==null||f.getPath().isEmpty())
        {
            throw new RepositoryException("La ruta al archivo bin es nula o vacia");
        }
        if (!f.exists()) 
        {
            try {
                    f.createNewFile();
            } catch (Exception e) {
                throw new RepositoryException("No se pudo crear el archivo"+e.getMessage());
            }   
        }
        this.Tasks = loadTaskdearchivo();
    }
    @SuppressWarnings("unchecked")
    public List<Task> loadTaskdearchivo()throws RepositoryException{
        File f = new File(this.rutaAbin);
        if(!f.exists())
        {
            
            return new ArrayList<>();
        }
        if(f.length()==0){
            
            return new ArrayList<>();
        }
        try {
                FileInputStream fileInputStream = new FileInputStream(f);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                List<Task> Tasks = (List<Task>) objectInputStream.readObject();
                objectInputStream.close();
                return Tasks;

        } catch (Exception e) {
                throw new RepositoryException("Error al cargar en binario desde el repositorio:",e);
        }
    }
    void saveTaskalarchivo(List<Task> guardar)throws RepositoryException{
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(rutaAbin);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(guardar);
            objectOutputStream.close();
        } catch (Exception e) {
            throw new RepositoryException("Error al salvar en binario hacia el repositorio:",e);
        }
    }
    @Override
    public Task addTask(Task t) throws RepositoryException{
        if (t==null) {
            return new Task();
        }
        if(noRepetido(t.getIdentifier())==false)
        {
            throw new RepositoryException("El identificador estaba duplicado:");
        }
        if (Tasks == null) 
        {
            Tasks = new ArrayList<>();
        }
        return t;
    }  
    public boolean noRepetido(int identificador){
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
    @Override 
    public void removeTask(Task t) throws RepositoryException{
            if(t==null)
            {
                throw new RepositoryException("La tarea a borrar era nula\n");
            }
            if (Tasks == null)
            {
                Tasks = new ArrayList<>();   
            }
            remove(t.getIdentifier());
        }
    public void remove(int index){
            Iterator<Task> iT = Tasks.iterator();
            while (iT.hasNext()) 
            {
                Task t = iT.next();
                if (t != null && t.getIdentifier()==index) 
                {
                    iT.remove();
                    break;
                    
                }   
            }
    }    
    @Override
    public void modifyTask(Task t) throws RepositoryException{
            if (t==null) 
            {
                throw new RepositoryException("La tarea a modificar es nula.\n");
            }
            if (t.getDate()==null)
            {
                throw new RepositoryException("La tarea proporcionada contenía una fecha nula.\n");
            }  
            for(int i=0;i<Tasks.size();i++){
                Task almacenada = Tasks.get(i);
                if (almacenada.getIdentifier()==t.getIdentifier()) {
                    Tasks.set(i, t);
                    return;   
                }
            }
            throw new RepositoryException("La tarea a modificar tenia un Identificador que no esta en la Lista.\n");
            
        }      
    @Override
    public ArrayList<Task> getAllTasks() throws RepositoryException{
                    if(Tasks == null)
                        {
                            throw new RepositoryException("El Listado de tareas es null.\n");
                        }
                        OrdenT = new ArrayList<Task>();
                        OrdenT.clear();
                        for (Task t : Tasks) 
                        {
                            if (t==null) 
                            {
                                throw new RepositoryException("En el Listado de tareas una Tarea es null.\n");
                            }
                            else
                            {
                                OrdenT.add(t);
                            }
                        }
                        OrdenT.sort(Comparator.comparingInt(Task::getIdentifier).reversed());
                        return OrdenT;
               
        }
    @Override
    public int CoP(int indice) {
            if (Tasks != null) 
            {
                for (Task t : Tasks) 
                {
                    if ( t != null && t.getIdentifier() == indice )
                    {
                        if (t.isCompleted() == true) 
                            {
                                t.setCompleted(false);
                                return 0;
                            }
                            else
                            {
                                t.setCompleted(true);
                                return 0;
                            }
                    }
                    else if (t==null) {
                        return -1;
                    } 
                }
                return 3;
            }
            else
                Tasks = new ArrayList<>();
                return 2;
        
    }
   
    }