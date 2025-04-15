package Model;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
public class CSVexporter implements IExporter {
    private List<Task> Exportacion = new ArrayList<>();
    private List<Task> Importacion = new ArrayList<>();
    Model m;
    public String toCSV(Task t){
        return String.valueOf(t.getIdentifier())+","+
               "\""+t.getTitle()+"\""+","+
               String.valueOf(t.getDate())+","+
               "\""+t.getContent()+"\""+","+
               String.valueOf(t.getPriority())+","+
               String.valueOf(t.getEstimatedDuration())+","+
               Boolean.toString(t.isCompleted());
    }
    

    public static Task fromCSV(String aDelimitar)throws ImporterException{
        String[] chunk = aDelimitar.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        SimpleDateFormat d = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
        if (chunk.length == 7) 
        {
            try {
                int id=Integer.parseInt(chunk[0]);
                String title = chunk[1].replace("\"", "");
                Date fecha = d.parse(chunk[2]);
                String content = chunk[3].replace("\"", "");
                int priority = Integer.parseInt(chunk[4]);
                int estimatedD= Integer.parseInt(chunk[5]);
                boolean completed = Boolean.parseBoolean(chunk[6]);
                Task t = new Task(id, title, fecha, content, priority, estimatedD, completed);
                return t;
            } catch (Exception e) {
                throw new ImporterException("Error en la importacion de la tarea:\n",e);
            }
        }
        throw new ImporterException("Error en la importacion de los campos de la tarea:\n");
    }

    @Override
    public void export(List<Task> Tasks,String ruta)throws ExporterException{
         if (Tasks != null && Exportacion != null)
        {
            Exportacion.clear();
            for (Task t : Tasks) 
            {
                if(!(t == null))
                {  
                    Exportacion.add(t); 
                }
            } 
            Exportacion.sort(Comparator.comparing(Task::getDate).reversed());
            File f = new File(ruta);
            if (f.getParentFile().canWrite())
            {
                try(FileWriter writer = new FileWriter(f)){
                    for (Task t : Exportacion) 
                    {
                        if (t!=null) 
                        {
                            writer.write(toCSV(t) + "\n");
                        } 
                    }
                }catch (IOException e) {
                    throw new ExporterException("Error en la exportacion a CSV:\n",e);
                }
            }
            else
                throw new ExporterException("El directorio no tiene permisos de escritura\n");
        }
        else
            throw new ExporterException("La lista no esta inicializada\n");
    }
    @Override
    public List<Task> importTasks(String ruta)throws ImporterException{
            Importacion = new ArrayList<Task>();
            try {
                List<String> lineas = Files.readAllLines(Paths.get(ruta));
                for (String linea : lineas) 
                {
                    Task t = fromCSV(linea);
                    if (t!=null) {
                        Importacion.add(t);
                    }
                }
                return Importacion;
            } catch (Exception ex) 
            {
                throw new ImporterException("Error durante la importacion.\n",ex);
            }
    }
}
