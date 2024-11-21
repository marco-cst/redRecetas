package models;

import java.sql.Date;

public class Resenia {
    private int idResenia;
    private String comentario;
    private float calificacion;
    private Date fecha;

    public Resenia() {
    }

    //Constructor
    public Resenia(int idResenia, String comentario, float calificacion, Date fecha) {
        this.idResenia = idResenia;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha = fecha;
    }

    //Getters y setters
    public int getIdResenia() {
        return this.idResenia;
    }

    public void setIdResenia(int idResenia) {
        this.idResenia = idResenia;
    }

    public String getComentario() {
        return this.comentario;
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

}
