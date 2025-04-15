package ej9_pesos_clases;

import java.util.Scanner;

/**
 *
 * 
 * Tom, Dick y Harry introducen sus pesos y tallas en el ordenador. ¿Quien es el más alto?
¿Quién pesa mas? Consultar al profesor el uso de la sentencia if-else. Repetir este
ejercicio usando clases
 * 
 * 
 */
public class Ej9_pesos_clases {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        float peso;
        float altura;
        float pesoMax,alturaMax;
        int indNombrePeso = 0, indNombreAltura = 0;
         
        //Instanciamos Scanner        
        Scanner teclado = new Scanner(System.in);
                
        String []nombres= {"Tom","Dick","Harry"};
        
        //Creamos un array para objetos de tipo Persona
        Persona pers[] = new Persona[nombres.length];
        
        // Para cada persona pedimos los datos e instanciamos un objeto de tipo Persona
        for(var i=0; i<nombres.length; i++) {
            System.out.println("Introduce los datos de: "+nombres[i]);
            System.out.printf("Peso: ");
            peso=Float.parseFloat(teclado.nextLine());
            System.out.printf("Altura: ");
            altura=Float.parseFloat(teclado.nextLine());
                
            pers[i] = new Persona(peso, altura);
        }
        
        // Una de las opciones que hay para determinar cuál es el más alto y el más pesado                 
        pesoMax=pers[0].getPeso();
        alturaMax=pers[0].getTalla();
           
        for(var i=0;i<pers.length;i++){
             
            if(pesoMax < pers[i].getPeso()){
                pesoMax=pers[i].getPeso();
                indNombrePeso = i;
            }
             
            if(pers[i].masAlto(alturaMax)) {
                alturaMax=pers[i].getTalla();
                indNombreAltura = i;
            }            
         } 
             
         //Imprimimos resultados por pantalla
         System.out.println("");
         System.out.println(" "+nombres[indNombrePeso]+"  es el que mas pesa.");
         System.out.println(" "+nombres[indNombreAltura]+"  es el que mas mide.");
        
        
    }
    
}
