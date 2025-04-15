package Model;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class JSONexporter implements IExporter {
    @Override
    public void export(List<Task> Tasks,String ruta)throws ExporterException{
        List<Task> Exportacion = new ArrayList<>();
        if (Tasks != null && Exportacion != null)
        {
            Exportacion.clear();
            for (Task t : Tasks) 
            {
                if(!(t == null) && t.getDate()!=null)
                {  
                    Exportacion.add(t); 
                }
            } 
            Exportacion.sort(Comparator.comparing(Task::getDate).reversed());
            File f = new File(ruta);
            if (f.getParentFile().canWrite())
            {   
                if (!Exportacion.isEmpty()) 
                {
                    Gson gson = new Gson();
                    String json = gson.toJson(Exportacion);
                    try(FileWriter writer = new FileWriter(f)){
                        writer.write(json);
                    }catch (IOException e) {
                        throw new ExporterException("Error al escribir el archivo\n"+e.getMessage());
                    }
                }
                else{
                    throw new ExporterException("No existen Tareas que exportar\n");
                }
            }
            else
            {
                throw new ExporterException("No existe Directorio padre\n");
            }           
        }
    }
    @Override
    public List<Task> importTasks(String ruta)throws ImporterException{
       File f = new File(ruta);
       String json;
       try {
            json=new String(Files.readAllBytes(f.toPath()),StandardCharsets.UTF_8);

       } catch (Exception e) {
            throw new ImporterException("1. Error al leer el archivo JSON:"+e.getMessage());
       }
       List<Task> Importacion = new ArrayList<>();
       try {
            Gson gson = new Gson();
            Type tipoLista =new TypeToken<List<Task>>(){}.getType();
            Importacion = gson.fromJson(json,tipoLista);
       } catch (Exception e) {
            throw new ImporterException("El archivo JSON tiene un formato invalido:"+e.getMessage());
       }
       if (Importacion==null || Importacion.isEmpty()) 
       {
            throw new ImporterException("El archivo a Importar no posee informaciom¡n.\n");
       }
       Iterator<Task> i = Importacion.iterator();
       while(i.hasNext())
       {
            Task task = i.next();
            if (task==null) 
            {
                i.remove();
            }
       }
       return Importacion;
    }

}
