package models;

import java.util.Date;

public class Resenia {
    private Integer idResenia;
    private String comentario;
    private Integer calificacion;
    private Date fecha;
    private Integer idReceta;
    private Integer idPersona;

    public Resenia() {
    
    }

    public Resenia(Integer idResenia, String comentario, Integer calificacion, Date fecha, Integer idReceta, Integer idPersona) {
        this.idResenia = idResenia;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha = fecha;
        this.idReceta = idReceta;
        this.idPersona = idPersona;
    }

    public Integer getIdResenia() {
        return this.idResenia;
    }

    public void setIdResenia(Integer idResenia) {
        this.idResenia = idResenia;
    }

    public String getComentario() {
        return comentario;
        
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdReceta() {
        return this.idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public Integer getIdPersona() {
        return this.idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    // @Override
    // public String toString() {
    //     return "{" +
    //         " idResenia='" + getIdResenia() + "'" +
    //         ", comentario='" + getComentario() + "'" +
    //         ", calificacion='" + getCalificacion() + "'" +
    //         ", fecha='" + getFecha() + "'" +
    //         ", idReceta='" + getIdReceta() + "'" +
    //         ", idPersona='" + getIdPersona() + "'" +
    //         "}";
    // }

}