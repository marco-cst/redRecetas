package models;

import java.util.Date;

import models.enumedores.TipoCategoria;

public class Receta {

    private Integer idReceta;
    private String nombre;
    private Date fechaPublicacion;
    private String preparacion;
    private Integer porciones;
    private Boolean favoritos;

    private TipoCategoria tipo;

    
    

    public Receta() {
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    public Integer getidReceta() {
        return idReceta;
    }

    public void setidReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public Integer getPorciones() {
        return porciones;
    }

    public void setPorciones(Integer porciones) {
        this.porciones = porciones;
    }

    public Boolean getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }

    @Override
    public String toString() {
        return "Receta [idReceta=" + idReceta + ", nombre=" + nombre + ", fechaPublicacion=" + fechaPublicacion
                + ", preparacion=" + preparacion + ", porciones=" + porciones + ", favoritos=" + favoritos + "]";
    }
}