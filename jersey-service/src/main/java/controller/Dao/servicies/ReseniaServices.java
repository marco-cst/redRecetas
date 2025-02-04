package controller.Dao.servicies;

import controller.Dao.ReseniaDao;
import controller.tda.list.LinkedList;

import models.Resenia;

public class ReseniaServices {
    private ReseniaDao obj;

    public ReseniaServices() {
        this.obj = new ReseniaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList<Resenia> listAll() {
        return obj.listAll();
    }

    public Resenia getResenia() {
        return obj.getResenia();
    }

    public void setResenia(Resenia resenia) {
        obj.setResenia(resenia);
    }

    public Resenia get(Integer id) throws Exception {
        // return obj.get(id);
        return (Resenia) obj.get(id);
    }


    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id);
    }

    
    // Validacion 1.0
    public void validarResenia(Resenia resenia) throws Exception {
        // Validar el nombre
        String comentario = resenia.getComentario().trim();
        if (!comentario.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception(
                    "La resenia solo puede contener letras y espacios ¡NO UTILICE CARACTERES ESPECIALES NI NÚMEROS!.");
        }
        resenia.setComentario(comentario.substring(0, 1).toUpperCase() + comentario.substring(1).toLowerCase());
    
        // Validar comentario
        String preparacion = resenia.getComentario().trim();
        if (preparacion.isEmpty()) {
            throw new Exception("La preparación no puede estar vacía.");
        }
        resenia.setComentario(preparacion);
    
        // Validar calificacion
        if (resenia.getCalificacion() < 1 || resenia.getCalificacion() > 0) {
            throw new Exception("Las porciones deben ser un número entero mayor a cero.");
        }
    }    

    
}
