package cuentasbanco;

import static com.coti.tools.Esdia.*;

public class CuentasBanco {


    public static void main(String[] args) {
        
        String titular;
        double cantidad;
        int DIM = 2;

        Cuenta cuentas[] = new Cuenta[DIM];
        
        for(int i = 0; i < cuentas.length; i++){
            titular = readString(String.format("Introduce el nombre del titular de la cuenta %d: ", i+1));
            cantidad = readDouble(String.format("Introduce la cantidad de dinero que tiene el titular de la cuenta %d: ", i+1));
            cuentas[i] = new Cuenta(titular, cantidad);
        }
        
        int opcion, modificar;
        double cant;
        do{
            modificar = readInt("A que titular deseas modificar su dinero: ") -1;
            opcion = readInt(String.format("Que deseas hacer con su capital: \n 1.) Ingresar \n 2.) Retirar \n 0.) SALIR\n" ));
            switch(opcion){
                case 1:
                    cant = readDouble("Que cantidad deseas ingresar: ");
                    cuentas[modificar].ingresar(cant);
                    break;
                case 2:
                    cant = readDouble("Que cantidad deseas retirar: ");
                    cuentas[modificar].retirar(cant);
                    break;
                case 0:
                    System.out.println("Saliendo del programa");
                    break;
            }
        }while(opcion != 0);
        
        System.out.println("Balance total de cuentas: ");
        for(int i = 0; i< cuentas.length; i++){
            
            System.out.println(cuentas[i].balanceFinal());
        }
    }
    
}
