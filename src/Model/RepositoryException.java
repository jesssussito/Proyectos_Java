package Model;

public class RepositoryException extends Exception {
    public RepositoryException(String mensaje,Throwable causa){
        super(mensaje,causa);
    }
    public RepositoryException(String mensaje){
        super(mensaje);
    }
}
