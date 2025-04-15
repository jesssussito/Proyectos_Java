package Model;

public class ImporterException extends Exception {
    public ImporterException(String mensaje){
        super(mensaje);
    }
    public ImporterException(String mensaje,Throwable causa){
        super(mensaje,causa);
    }
}
