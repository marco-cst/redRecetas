package models;

public class Categoria {
    private int idCategoria;
    private String tipo;
    private Boolean estado;

    public Categoria() {
    }
    
    public Categoria(int idCategoria, String tipo, Boolean estado) {
        this.idCategoria = idCategoria;
        this.tipo = tipo;
        this.estado = estado;
    }
    public int getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override   
    public String toString() {
        return "Categoria{" +
            "idCategoria=" + idCategoria +
            ", tipo='" + tipo + '\'' +
            ", estado=" + estado +
            '}';
    }
        
}