package controller.Dao.servicies;
import controller.Dao.PersonaDao;
import controller.tda.list.LinkedList;
import models.Persona;

public class PersonaServicies {
    private PersonaDao obj;
    public PersonaServicies(){ //Constructor de la clase
        obj = new PersonaDao(); //Instancia un objeto de la clase PersonaDao
    }
    public Boolean save() throws Exception{ //Guarda la variable persona en la lista de objetos
        return obj.save(); //Invoca el método save() de la clase PersonaDao
    }

  
    public LinkedList<Persona> listAll(){ //Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); //Invoca el método getlistAll() de la clase PersonaDao

    }

    public Persona getPersona(){ //Obtiene la persona
        return obj.getPersona(); //Invoca el método getPersona() de la clase PersonaDao
    }

    public void setPersona( Persona persona){ //Recibe un objeto Persona
        obj.setPersona(persona); //Invoca el método setPersona() de la clase PersonaDao y envía el objeto Persona
    }

    @SuppressWarnings("rawtypes")
    public LinkedList order(Integer type_order, String atributo){ //Ordena la lista de objetos por apellido
        return obj.order(type_order, atributo); //Invoca el método order_ape() de la clase PersonaDao
    }

    public Persona get(Integer id) throws Exception{ //Obtiene un objeto persona por su id
        return obj.get(id); //Invoca el método get() de la clase PersonaDao y envía el id
    }

    public Boolean delete(int id) throws Exception{ //Elimina un objeto persona por su índice
        return obj.delete(id); //Invoca el método delete() de la clase PersonaDao y envía el índice
    }

    public boolean update() throws Exception {
        return obj.update();
    }

    public Persona buscar_identificacion(String texto ){
        return obj.buscar_identificacion(texto);
    }
    
}