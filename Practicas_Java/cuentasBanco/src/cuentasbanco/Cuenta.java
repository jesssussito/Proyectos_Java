package cuentasbanco;

public class Cuenta {
    private String titular;
    private double cantidad;

    public Cuenta(String titular, double cantidad) {
        this.titular = titular;
        this.cantidad = cantidad;
    }
    
    
    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    public void ingresar(double cantidad){
        if(cantidad >= 0){
            this.cantidad += cantidad;
        }
        else{
            System.out.println("El valor no puede ser negativo");
        }
    }
    
    public void retirar(double cantidad){
        if((this.cantidad - cantidad) <0){
            this.cantidad = 0;
        }
        else{
            this.cantidad -= cantidad;
        }
    }
    
    public String balanceFinal(){
        return String.format("|%10s|%.2f|", this.titular,this.cantidad);
    }
    
}
