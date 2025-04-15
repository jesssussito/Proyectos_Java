/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ej9_pesos_clases;

/**
 * Clase Persona para manejar a Tom, Dick, Harry, etc.
 */
public class Persona {
    
    //Atributos (privados)
    private float peso;
    private float altura;
    
    //Constructores
    public Persona() {
        peso=0;
        altura=0;
    }
    
    public Persona(float ps, float alt){
        
        this.peso = ps;
        this.altura = alt;
       
    }
    //Getter
    public float getPeso() {
        return this.peso;
    }
    
    public float getTalla() {
        return this.altura;
    }
    
    //Setter
    public int setPeso(float ps) {
        if (ps <= 0) {
            return 0;
        } else {
        this.peso = ps;
            return 1;
        }
    }
    
    public void setTalla(float alt) {
        this.altura = alt;
    }
    
    // Ejemplo de método que te indica si el objeto es más alto que el valor recibido como parámetro
    public boolean masAlto(float alt) {
        
        if (this.altura > alt)
            return true;
        else 
            return false;
    }
  
}
