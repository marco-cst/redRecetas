package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Persona;

public class PersonaDao extends AdapterDao<Persona> {
    private Persona persona = new Persona(); 
    private LinkedList listAll;
    
    public PersonaDao(){
        super(Persona.class);
    }
    public Persona getPersona(){ //Obtiene la persona
        if (persona == null) {
            persona = new Persona(); //En caso de que la persona sea nula, crea una nueva instancia de Persona
        }
        return this.persona; //Devuelve la persona
    }

    public void setPersona(Persona persona){ //Establece la persona con un objeto Persona
        this.persona = persona; //Asigna el objeto Persona a la variable persona
    }

    public LinkedList getlistAll(){  //Obtiene la lista de objetos
        if (listAll == null) { //Si la lista es nula 
            this.listAll = listAll(); //Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; //Devuelve la lista de objetos de la variable listAll
    }
    public Boolean save() throws Exception{ //Guarda la variable persona en la lista de objetos
        Integer id = getlistAll().getSize()+1; //Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        persona.setIdPersona(id); //Asigna el id a persona
        this.persist(this.persona); //Guarda la persona en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); //Actualiza la lista de objetos
        return true; //Retorna verdadero si se guardó correctamente
    }

    public LinkedList order(Integer type_order, String atributo){
        LinkedList listita = listAll(); //Obtiene la lista de objetos
        if (!listAll().isEmpty()) {
            Persona[] lista = (Persona[]) listita.toArray();
            listita.reset();

            for (int i = 0; i < lista.length; i++) {
                Persona aux = lista[i];
                int j = i - 1;
                while (j>= 0 && (verify(lista[j], aux, type_order, "apellido"))) {
                    lista[j + 1] = lista[j--];
                }
                lista[j + 1] = aux;
            }

            listita.toList(lista);
        }
        return listita;
    }

    private Boolean verify(Persona a, Persona b, Integer type_order, String atributo){
        if (type_order == 1) {
            if (atributo.equalsIgnoreCase("apellido")) {
                return a.getApellido().compareTo(b.getApellido()) > 0;
            } else if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getApellido()) > 0;
            }
        } else {
            if (atributo.equalsIgnoreCase("apellido")) {
                return a.getApellido().compareTo(b.getApellido()) < 0;
            } else if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getApellido()) < 0;
            }
        }
        return false;
    }
    
    
}