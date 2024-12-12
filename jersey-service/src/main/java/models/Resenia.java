package models;

import java.util.Date;

public class Resenia {
    private int idResenia;
    private String comentario;
    private float calificacion;
    private Date fecha;
    private int idReceta;
    private int idPersona;

    public Resenia() {
    
    }

    public Resenia(int idResenia, String comentario, float calificacion, Date fecha, int idReceta, int idPersona) {
        this.idResenia = idResenia;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha = fecha;
        this.idReceta = idReceta;
        this.idPersona = idPersona;
    }

    public int getIdResenia() {
        return this.idResenia;
    }

    public void setIdResenia(int idResenia) {
        this.idResenia = idResenia;
    }

    public String getComentario() {
        return comentario;
        
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdReceta() {
        return this.idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public int getIdPersona() {
        return this.idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "{" +
            " idResenia='" + getIdResenia() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", calificacion='" + getCalificacion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", idReceta='" + getIdReceta() + "'" +
            ", idPersona='" + getIdPersona() + "'" +
            "}";
    }

}