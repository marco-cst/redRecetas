package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Receta;

public class RecetaDao extends AdapterDao<Receta> {
    private Receta receta = new Receta(); 
    private LinkedList listAll;
    
    public RecetaDao(){
        super(Receta.class);
    }
    public Receta getReceta(){ //Obtiene la familia
        if (receta == null) {
            receta = new Receta(); //En caso de que la familia sea nula, crea una nueva instancia de Familia
        }
        return this.receta; //Devuelve la familia
    }

    public void setReceta(Receta receta){ //Establece la familia con un objeto Familia
        this.receta = receta; //Asigna el objeto Familia a la variable familia
    }

    public LinkedList getlistAll(){  //Obtiene la lista de objetos
        if (listAll == null) { //Si la lista es nula 
            this.listAll = listAll(); //Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; //Devuelve la lista de objetos de la variable listAll
    }
    public Boolean save() throws Exception{ //Guarda la variable familia en la lista de objetos
        Integer id = getlistAll().getSize()+1; //Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        receta.setidReceta(id); //Asigna el id a familia
        this.persist(this.receta); //Guarda la familia en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); //Actualiza la lista de objetos
        return true; //Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception{ //Actualiza el nodo Familia en la lista de objetos
        this.merge(getReceta(), getReceta().getidReceta()-1);  //Envia la familia a actualizar con su index 
        this.listAll = listAll();  //Actualiza la lista de objetos
        return true; 
    }

    public Boolean delete(int id) throws Exception {
        LinkedList<Receta> list = getlistAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getidReceta() == id) {
                this.supreme(i);
                this.listAll = listAll(); // Actualiza la lista de objetos
                return true; // Retorna verdadero si se eliminó correctamente
            }
        }
        return false; // Retorna falso si no se encontró el ID
    }
}