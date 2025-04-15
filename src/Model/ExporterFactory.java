package Model;

public class ExporterFactory{
    public static IExporter getExporter(String tipo) throws ExporterException{
        switch (tipo) {
            case "csv":
                return new CSVexporter();
            case "json":
                return new JSONexporter();
            default:
                throw new ExporterException("tipo de IE no valido\n");
        }
    }
}
