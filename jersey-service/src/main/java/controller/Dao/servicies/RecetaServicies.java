package controller.Dao.servicies;
import controller.Dao.RecetaDao;
import controller.tda.list.LinkedList;
import models.Receta;

public class RecetaServicies {
    private RecetaDao obj;
    public RecetaServicies(){ //Constructor de la clase
        obj = new RecetaDao(); //Instancia un objeto de la clase ReseniaDao
    }
    public Boolean update() throws Exception{ //Guarda la variable Resenia en la lista de objetos
        return obj.update(); //Invoca el método save() de la clase ReseniaDao
    }

    public Boolean save() throws Exception{ //Guarda la variable Resenia en la lista de objetos
        return obj.save(); //Invoca el método save() de la clase ReseniaDao
    }

    public LinkedList<Receta> listAll(){ //Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); //Invoca el método getlistAll() de la clase ReseniaDao

    }

    public Receta getReceta(){ //Obtiene la Resenia
        return obj.getReceta(); //Invoca el método getResenia() de la clase ReseniaDao
    }

    public void setReceta( Receta receta){ //Recibe un objeto Resenia
        obj.setReceta(receta); //Invoca el método setResenia() de la clase ReseniaDao y envía el objeto Resenia
    }

    public Receta get(Integer id) throws Exception{ //Obtiene un objeto Resenia por su id
        return obj.get(id); //Invoca el método get() de la clase ReseniaDao y envía el id
    }

    public Boolean delete(int id) throws Exception{ //Elimina un objeto Resenia por su índice
        return obj.delete(id); //Invoca el método delete() de la clase ReseniaDao y envía el índice
    }
}

