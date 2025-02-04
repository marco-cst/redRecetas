package controller.Dao.servicies;
import controller.tda.list.LinkedList;
import models.Ingredientes;
import controller.Dao.IngredientesDao;

public class IngredientesServicies {
    private IngredientesDao obj;
    public IngredientesServicies(){ //Constructor de la clase
        obj = new IngredientesDao(); //Instancia un objeto de la clase ingredientesDao
    }
    public Boolean update() throws Exception{ //Guarda la variable ingredientes en la lista de objetos
        return obj.update(); //Invoca el método save() de la clase ingredientesDao
    }

    public Boolean save() throws Exception{ //Guarda la variable ingredientes en la lista de objetos
        return obj.save(); //Invoca el método save() de la clase ingredientesDao
    }

    public LinkedList<Ingredientes> listAll(){ //Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); //Invoca el método getlistAll() de la clase ingredientesDao

    }

    public Ingredientes getIngredientes(){ //Obtiene la ingredientes
        return obj.getIngredientes(); //Invoca el método getingredientes() de la clase ingredientesDao
    }

    public void setIngredientes( Ingredientes ingredientes){ //Recibe un objeto ingredientes
        obj.setIngredientes(ingredientes); //Invoca el método setingredientes() de la clase ingredientesDao y envía el objeto ingredientes
    }

    public Ingredientes get(Integer id) throws Exception{ //Obtiene un objeto ingredientes por su id
        return obj.get(id); //Invoca el método get() de la clase ingredientesDao y envía el id
    }

    public Boolean delete(int id) throws Exception{ //Elimina un objeto ingredientes por su índice
        return obj.delete(id); //Invoca el método delete() de la clase ingredientesDao y envía el índice
    }
}
