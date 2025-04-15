package App;
import Model.Model;
import View.BaseView;
import Controller.Controller;
import View.InteractiveView;

public class App {
    
    public static void main(String[] args) {
        Model model = new Model();
        BaseView view = new InteractiveView();
        Controller controller = new Controller(model, view,args);
        controller.cargarDelRepost();
        controller.iniciar();
        controller.fin();        
    }
}
