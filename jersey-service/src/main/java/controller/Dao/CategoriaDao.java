package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Categoria;

public class CategoriaDao extends AdapterDao<Categoria> {

    private Categoria categoria = new Categoria(); 
    private LinkedList listAll;
    
    public CategoriaDao(){
        super(Categoria.class);
    }
    public Categoria getCategoria(){
        if (categoria == null) {
            categoria = new Categoria(); 
        }
        return this.categoria; 
    }

    public void setCategoria(Categoria categoria){ 
        this.categoria = categoria;
    }

    public LinkedList<Categoria> getListAll(){  
        if (listAll == null) { 
            this.listAll = listAll();
        }
        return listAll; 
    }

    public Boolean save() throws Exception{ 
        Integer id = getListAll().getSize()+1;
        categoria.setIdCategoria(id); 
        this.persist(this.categoria); 
        this.listAll = listAll(); 
        return true; 
    }

    /*

        public Boolean save() throws Exception {
            Integer id = getListAll().getSize() + 1;
            categoria.setIdCategoria(id);
            this.persist(this.categoria);
            this.listAll = listAll();  //getListAll();
            return true;
        }
    */

    public Boolean update() throws Exception {
        try {
            this.merge(getCategoria(), getCategoria().getIdCategoria() - 1);
            this.listAll = getListAll(); 
            System.out.println("Inversionista actualizado correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar inversionista: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Categoria> list = getListAll(); 
        Categoria categoria= get(id); 
        if (categoria != null) {
            list.remove(categoria); 
            String info = g.toJson(list.toArray());
            saveFile(info); 
            this.listAll = list;
            return true;
        } else {
            System.out.println("Inversionista con id " + id + " no encontrada.");
            return false;
        }
    }
    
}