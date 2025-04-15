package View;
import Controller.Controller;
import Model.Task;
public abstract class BaseView {
    protected Controller c; // Se usa 'protected' para permitir el acceso desde subclases

    public void setController(Controller controller) {
        this.c = controller;
    }
    public abstract void SacoPantalla(String[] sacar);
    
   
    public abstract int pedirId();
    public abstract void init();
    public abstract void showMessage(String message);
    public abstract void showErrorMessage(String message);
    public abstract void end();
    public abstract Task pedirDatos();
    public abstract Task modifyTask(int identificador);
    public abstract String pedirRuta(boolean csvOjson,boolean paraExportar);
}