/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author alfonso
 */
public class Coche {

    private String modelo;
    private String marca;
    private String matricula;
    private int anio;
    private double precioSinIVA;
    private double precioConIVA;
    final double IVA = 21;

    //Constructores
    public Coche(String matricula) {
        this.matricula = matricula;
    }

    public Coche(String modelo, String marca, String matricula, int anio, double precioSinIVA) {
        this.modelo = modelo;
        this.marca = marca;
        this.matricula = matricula;
        this.anio = anio;
        this.precioSinIVA = precioSinIVA;
    }

    public Coche() {
    }

    //métodos getter y setter
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecioSinIVA() {
        return precioSinIVA;
    }

    public void setPrecioSinIVA(double precioSinIVA) {
        this.precioSinIVA = precioSinIVA;
    }

    public double getPrecioConIVA() {
        return calcularprecioConIva();
    }

    public void setPrecioConIVA(double precioConIVA) {
        this.precioConIVA = precioConIVA;
    }

    double calcularprecioConIva() {
        return this.precioConIVA = this.precioSinIVA + (this.precioSinIVA * IVA / 100);
    }

    @Override
    public String toString() {
        //Esta primera linea la genera por defecto Netbeans empleando los atributos definidos
        //return "Coche{" + "modelo=" + modelo + ", marca=" + marca + ", matricula=" + matricula + ", anio=" + anio + ", precioSinIVA=" + precioSinIVA + ", precioConIVA=" + precioConIVA + ", IVA=" + IVA + '}';
        return "\nMatricula: " + matricula
                + "\nMarca: " + marca
                + "\nModelo: " + modelo
                + "\nAño: " + anio
                + "\nPrecio sin IVA: " + precioSinIVA;
    }

}
