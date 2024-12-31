package controller.Dao.servicies;

import controller.Dao.RecetaDao;
import controller.tda.list.LinkedList;
import models.Receta;
import java.util.Date;

public class RecetaServicies {
    private RecetaDao obj;

    public RecetaServicies() { // Constructor de la clase
        obj = new RecetaDao(); // Instancia un objeto de la clase RecetaDao
    }

    // Validacion 1.0
    public void validarReceta(Receta receta) throws Exception {
        // Validar el nombre
        String nombre = receta.getNombre().trim();
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception(
                    "El nombre de la receta solo puede contener letras y espacios ¡NO UTILICE CARACTERES ESPECIALES NI NÚMEROS!.");
        }
        receta.setNombre(nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase());
    
        // Validar la preparación (sin modificar la enumeración)
        String preparacion = receta.getPreparacion().trim();
        if (preparacion.isEmpty()) {
            throw new Exception("La preparación no puede estar vacía.");
        }
        receta.setPreparacion(preparacion);
    
        // Validar porciones
        if (receta.getPorciones() == null) {
            throw new Exception("Las porciones son obligatorias y deben ser un número entero mayor a cero.");
        }
        if (receta.getPorciones() <= 0) {
            throw new Exception("Las porciones deben ser un número entero mayor a cero.");
        }
    }    

    public Boolean save() throws Exception { // Guarda la variable Receta en la lista de objetos
        Receta receta = obj.getReceta(); // Obtiene la Receta
        receta.setFechaPublicacion(new Date()); // Asigna la fecha actual del sistema
        return obj.save(); // Invoca el método save() de la clase RecetaDao
    }

    public Boolean update() throws Exception { // Guarda la variable Receta en la lista de objetos
        Receta receta = obj.getReceta(); // Obtiene la Receta
        receta.setFechaPublicacion(new Date());
        return obj.update(); // Invoca el método save() de la clase RecetaDao
    }

    public LinkedList<Receta> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase RecetaDao

    }

    public Receta getReceta() { // Obtiene la Receta
        return obj.getReceta(); // Invoca el método getReceta() de la clase RecetaDao
    }

    public void setReceta(Receta receta) { // Recibe un objeto Receta
        obj.setReceta(receta); // Invoca el método setReceta() de la clase RecetaDao y envía el objeto Receta
    }

    public Receta get(Integer id) throws Exception { // Obtiene un objeto Receta por su id
        return obj.get(id); // Invoca el método get() de la clase RecetaDao y envía el id
    }

    public Boolean delete(int id) throws Exception { // Elimina un objeto Receta por su índice
        return obj.delete(id); // Invoca el método delete() de la clase RecetaDao y envía el índice
    }
}
