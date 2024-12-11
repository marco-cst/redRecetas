package models;

import java.sql.Date;

public class Receta {

    private Integer id_Receta;
    private String Nombre;
    private Date fecha_Publicacion;
    private String preparacion;
    private Integer porciones;
    private Boolean favoritos;


    public Receta() {
    }


    public Integer getId_Receta() {
        return id_Receta;
    }

    public void setId_Receta(Integer id_Receta) {
        this.id_Receta = id_Receta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Date getFecha_Publicacion() {
        return fecha_Publicacion;
    }

    public void setFecha_Publicacion(Date fecha_Publicacion) {
        this.fecha_Publicacion = fecha_Publicacion;
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

}
