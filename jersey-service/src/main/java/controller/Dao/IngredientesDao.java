package controller.Dao;

import models.Ingredientes;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class IngredientesDao extends AdapterDao<Ingredientes> {
    private Ingredientes ingredientes = new Ingredientes(); 
    private LinkedList<Ingredientes> listAll;
    
    public IngredientesDao(){
        super(Ingredientes.class);
    }
    public Ingredientes getIngredientes(){ //Obtiene la familia
        if (ingredientes == null) {
            ingredientes = new Ingredientes(); //En caso de que la familia sea nula, crea una nueva instancia de Familia
        }
        return this.ingredientes; //Devuelve la familia
    }

    public void setIngredientes(Ingredientes ingredientes){ //Establece la familia con un objeto Familia
        this.ingredientes = ingredientes; //Asigna el objeto Familia a la variable familia
    }

    public LinkedList<Ingredientes> getlistAll(){  //Obtiene la lista de objetos
        if (listAll == null) { //Si la lista es nula 
            this.listAll = listAll(); //Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; //Devuelve la lista de objetos de la variable listAll
    }

    
public Boolean save() throws Exception {
    // Validar el nombre (solo letras y espacios)
    if (!ingredientes.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
        throw new IllegalArgumentException("El nombre solo puede contener letras y espacios.");
    }

    // Validar la cantidad (solo números)
    if (ingredientes.getCantidad() <= 0) {
        throw new IllegalArgumentException("La cantidad debe ser un número mayor a 0.");
    }

    // Validar la unidad de medida (solo letras)
    if (!ingredientes.getUnidadMedida().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$")) {
        throw new IllegalArgumentException("La unidad de medida solo puede contener letras.");
    }

    // Generar un nuevo ID
    Integer id = getlistAll().getSize() + 1; // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo ID
    ingredientes.setIdIngrediente(id); // Asigna el ID al ingrediente

    // Guardar el ingrediente en la lista de objetos y en el archivo JSON
    this.persist(this.ingredientes);

    // Actualizar la lista de objetos
    this.listAll = listAll();

    return true; // Retorna verdadero si se guardó correctamente
}


    public Boolean update() throws Exception{ //Actualiza el nodo Familia en la lista de objetos
        this.merge(getIngredientes(), getIngredientes().getIdIngrediente()-1);  //Envia la familia a actualizar con su index 
        this.listAll = listAll();  //Actualiza la lista de objetos
        return true; 
    }

    public Boolean delete(int id) throws Exception {
        LinkedList<Ingredientes> list = getlistAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getIdIngrediente() == id) {
                this.supreme(i);
                this.listAll = listAll(); // Actualiza la lista de objetos
                return true; // Retorna verdadero si se eliminó correctamente
            }
        }
        return false; // Retorna falso si no se encontró el ID
    }
}

    



