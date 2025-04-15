package excepciones;

import java.io.Serializable;

/**
 *
 * @author Loza
 */
public class Persona implements Serializable {
    
    private String nombre;
    private String apellido;
    private int anioNacimiento;

    public Persona(String nombre, String apellido, int anioNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.anioNacimiento = anioNacimiento;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    
    
    
    
}
