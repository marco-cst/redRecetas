package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.Dao.servicies.CategoriaServices;
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

    public LinkedList<Categoria> getlistAll(){  
        if (listAll == null) { 
            this.listAll = listAll();
        }
        return listAll; 
    }

    public Boolean save() throws Exception{ 
        Integer id = getlistAll().getSize()+1;
        categoria.setIdCategoria(id); 
        this.persist(this.categoria); 
        this.listAll = listAll(); 
        return true; 
    }

    /*

        public Boolean save() throws Exception {
            Integer id = getlistAll().getSize() + 1;
            categoria.setIdCategoria(id);
            this.persist(this.categoria);
            this.listAll = listAll();  //getlistAll();
            return true;
        }
    */

    public Boolean update() throws Exception {
        try {
            this.merge(getCategoria(), getCategoria().getIdCategoria() - 1);
            this.listAll = getlistAll(); 
            System.out.println("Inversionista actualizado correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar inversionista: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(int id) throws Exception {
        LinkedList<Categoria> list = getlistAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getIdCategoria() == id) {
                this.supreme(i);
                this.listAll = listAll(); // Actualiza la lista de objetos
                return true; // Retorna verdadero si se eliminó correctamente
            }
        }
        return false; // Retorna falso si no se encontró el ID
    }
    
    
    public LinkedList<Categoria> busquedaLinCategoria(String texto) {
        LinkedList<Categoria> lista = new LinkedList<>();
        LinkedList<Categoria> listita = listAll();
        if (!listita.isEmpty() && texto != null) {
            for (Categoria Categoria : listita.toArray()) {
                if (Categoria != null && Categoria.getTipo() != null &&
                    // Categoria.getTipo().toLowerCase().contains(texto.toLowerCase())) {
                    Categoria.getTipo().toString().contains(texto.toLowerCase())) {
                    lista.add(Categoria);
                }
            }
        }
        return lista;
    }

    // public LinkedList<Receta> buscarPorSabor(Sabor sabor) {
    //     LinkedList<Receta> lista = new LinkedList<>();
    //     LinkedList<Receta> listita = listAll();  // Obtener todas las recetas
    
    //     if (!listita.isEmpty()) {
    //         for (Receta receta : listita) {
    //             if (receta != null && receta.getSabor() == sabor) {
    //                 lista.add(receta);
    //             }
    //         }
    //     }
    //     return lista;
    // }

    //TEST
    public static void main(String[] args) {
    CategoriaServices services = new CategoriaServices();
    LinkedList<Categoria> resultado = services.busquedaLinCategoria("SALADO");

    for (Categoria categoria : resultado) {
        System.out.println("ID: " + categoria.getIdCategoria() + ", Tipo: " + categoria.getTipo());
    }
}
}