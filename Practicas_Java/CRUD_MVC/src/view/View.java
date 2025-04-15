package view;

import static com.coti.tools.Esdia.*;
import static com.coti.tools.DiaUtil.clear;
import controler.


public class View {
    
    public void runMenu(String menu){
        
        Controller c = new Controller();
        
        boolean salir = false;
        String option;
        do {
            option = readString(menu).toLowerCase();
            clear();
            switch (option) {
                case "1" ->
                    this.altaPersona();
                case "2" ->
                    this.addPersona();
                case "3" ->
                    this.deletePersona();
                case "4" ->
                    this.();
                case "q" ->
                    salir = true;
        }while(salir==false);
                       
    
    }
    
    
}
