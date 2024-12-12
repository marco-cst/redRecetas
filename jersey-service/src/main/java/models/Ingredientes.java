package models;

public class Ingredientes {
    private int idIngrediente;
    private String nombre;
    private float cantidad;
    private String unidadMedida;

    public Ingredientes(int idIngrediente, String nombre, float cantidad, String unidadMedida) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    
}