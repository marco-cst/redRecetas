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
/*
    public Boolean save() throws Exception {
        if (resenia == null) {
            throw new IllegalStateException("ATENCION! ProyectoEnergia no inicializado");
        }
        Integer id = getlistAll().getSize() + 1; //id y tamaño de la lista
        resenia.setIdResenia(id);
        this.persist(resenia); //Guarda el Pr.Enrg
        this.listAll = getlistAll(); // Actualiza lista de Pr.Enrg
        return true;
    }

    public Boolean update() throws Exception {
        try {
            if (resenia == null) {
                throw new IllegalStateException("ATENCION! Proyecto Energia no inicializado");
            }
            // //actualiza el Pr.Enrg
            // Resenia reseniaToUpdate = (Resenia) getlistAll().get(getResenia().getIdResenia() - 1);
            // this.merge(reseniaToUpdate, getResenia().getIdResenia() - 1);

            this.merge(getlistAll(), getResenia().getIdResenia() -1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar el Proyecto Energia.");
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Resenia> list = getlistAll();
        Resenia resenia = get(id);
        if (resenia != null) {
            list.remove(resenia);
            String info = g.toJson(list.toArray());
            save(); //guardarDatos(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Error, No se encontro el proyecto");
            return false;
        }
    }

        
    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        inversionista.setIdInversionista(id);
        this.persist(this.inversionista);
        this.listAll = getlistAll(); 
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getInversionista(), getInversionista().getIdInversionista() - 1);
            this.listAll = getFullList(); 
            System.out.println("Inversionista actualizado correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar inversionista: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Inversionista> list = getlistAll(); 
        Inversionista inversionista= get(id); 
        if (inversionista != null) {
            list.remove(inversionista); 
            String info = g.toJson(list.toArray());
            guardarDatos(info); 
            this.listAll = list;
            return true;
        } else {
            System.out.println("Inversionista con id " + id + " no encontrada.");
            return false;
        }
    }
 */

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        resenia.setIdResenia(id);
        this.persist(this.resenia);
        this.listAll = listAll();  //getListAll();
        return true;
    }

    // public Boolean update() throws Exception {
    //     try {
    //         this.merge(getResenia(), getResenia().getIdResenia() - 1);
    //         this.listAll = getListAll(); 
    //         System.out.println("Inversionista actualizado correctamente.");
    //         return true;
    //     } catch (Exception e) {
    //         System.out.println("Error al actualizar inversionista: " + e.getMessage());
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    
    public Boolean update() throws Exception { // Actualiza el nodo Receta en la lista de objetos
        this.merge(getResenia(), getResenia().getIdResenia() - 1); // Envia la Receta a actualizar con su index
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true;
    }


    // public Boolean delete(Integer id) throws Exception {
    //     LinkedList<Resenia> list = getListAll(); 
    //     Resenia resenia= get(id); 
    //     if (resenia != null) {
    //         list.remove(resenia); 
    //         String info = g.toJson(list.toArray());
    //         saveFile(info); 
    //         this.listAll = list;
    //         return true;
    //     } else {
    //         System.out.println("Inversionista con id " + id + " no encontrada.");
    //         return false;
    //     }
    // }

    
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