package Model;
import java.util.List;
public interface IExporter {
    void export(List<Task> tasks,String ruta)throws ExporterException;
    List<Task> importTasks(String ruta)throws ImporterException;
} 
