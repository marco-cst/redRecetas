package models;

import models.enumedores.TipoCategoria;

public class Categoria {
    private Integer idCategoria;
    private TipoCategoria tipo; // private String tipo;
    // private Boolean estado;

    public Categoria() {
    }
    
    public Categoria(Integer idCategoria, TipoCategoria tipo) { //, Boolean estado
        this.idCategoria = idCategoria;
        this.tipo = tipo;
        // this.estado = estado;
    }
    public Integer getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public TipoCategoria getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    // public Boolean getEstado() {
    //     return this.estado;
    // }

    // public void setEstado(Boolean estado) {
    //     this.estado = estado;
    // }

    // @Override   
    // public String toString() {
    //     return "Categoria{" +
    //         "idCategoria=" + idCategoria +
    //         ", tipo='" + tipo + '\'' +
    //         ", estado=" + estado +
    //         '}';
    // }
        
}