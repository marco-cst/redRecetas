package models;

public class Imagen {
    private int idImagen;
    private int idReceta;
    private String imagen;

    public Imagen(int idimagen, int idReceta, String imagen){
        this.idImagen = idimagen;
        this.idReceta = idReceta;
        this.imagen = imagen;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
