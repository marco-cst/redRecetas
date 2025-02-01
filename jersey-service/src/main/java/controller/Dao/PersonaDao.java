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


    public Boolean save() throws Exception {
        // Validar entradas vacías o solo espacios en blanco
        if (persona.getDNI().trim().isEmpty()) {
            throw new Exception("El DNI no puede estar vacío o contener solo espacios.");
        }
        if (persona.getApodo().trim().isEmpty()) {
            throw new Exception("El apodo no puede estar vacío o contener solo espacios.");
        }
        // Validar que no existan duplicados en el DNI y el Apodo
        LinkedList<Persona> allRecords = (LinkedList<Persona>) listAll(); // Lanzará una excepción si el tipo no coincide

        for (int i = 0; i < allRecords.getSize(); i++) {
            Object record = allRecords.get(i); // Obtener el registro actual
        
            if (record instanceof Persona) { // Verificar si es una instancia de Persona
                Persona existingPersona = (Persona) record;
        
                // Validar duplicado de DNI
                if (existingPersona.getDNI().equalsIgnoreCase(persona.getDNI())) {
                    throw new Exception("El DNI ya está registrado.");
                }
        
                // Validar duplicado de Apodo
                if (existingPersona.getApodo().equalsIgnoreCase(persona.getApodo())) {
                    throw new Exception("El apodo ya está registrado.");
                }
            }
        }
        
        // Asignar ID y guardar el registro
        Integer id = listAll().getSize() + 1; // Obtener el tamaño de la lista actual y sumarle 1 para el nuevo ID
        persona.setIdPersona(id); // Asignar el ID único
        this.persist(this.persona); // Guardar el objeto persona en la lista y el archivo JSON
    
        return true; // Retornar verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception{ //Actualiza el nodo Persona en la lista de objetos
        this.merge(getPersona(), getPersona().getIdPersona()-1);  //Envia la persona a actualizar con su index 
        this.listAll = listAll();  //Actualiza la lista de objetos
        return true; 
    }

    public Boolean delete(int id) throws Exception {
        LinkedList<Persona> list = getlistAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getIdPersona() == id) {
                this.supreme(i);
                this.listAll = listAll(); // Actualiza la lista de objetos
                return true; // Retorna verdadero si se eliminó correctamente
            }
        }
        return false; // Retorna falso si no se encontró el ID
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


    public Persona buscar_identificacion(String texto) {
        Persona persona = null;
        LinkedList<Persona> listita = listAll();
        if (!listita.isEmpty()) {
            Persona[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {

                if (aux[i].getDNI().equals(texto)) {

                    persona = aux[i];
                    break;
                }
            }
        }
        return persona;
    }



    
    
}