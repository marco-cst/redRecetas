package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Resenia;

public class ReseniaDao extends AdapterDao<Resenia> {

    private Resenia resenia = new Resenia();
    private LinkedList listAll;

    public ReseniaDao(){
        super(Resenia.class);
        // this.listAll = new LinkedList<>();
    }

    public Resenia getResenia(){
        if (resenia == null) {
            resenia = new Resenia();
        }
        return this.resenia;
    }

    public void setResenia(Resenia resenia){
        this.resenia = resenia;
    }

    public LinkedList getlistAll(){
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }
    
    //save, update, delete

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        resenia.setIdResenia(id);
        this.persist(this.resenia);
        this.listAll = listAll();  //getListAll();
        return true;
    }

    public Boolean update() throws Exception { // Actualiza el nodo Receta en la lista de objetos
        this.merge(getResenia(), getResenia().getIdResenia() - 1); // Envia la Receta a actualizar con su index
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true;
    }
    
    public Boolean delete(int id) throws Exception {
        LinkedList<Resenia> list = getlistAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getIdResenia() == id) {
                this.supreme(i);
                this.listAll = listAll(); // Actualiza la lista de objetos
                return true; // Retorna verdadero si se eliminó correctamente
            }
        }
        return false; // Retorna falso si no se encontró el ID
    }
    
}