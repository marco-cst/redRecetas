package controller.Dao.servicies;
import controller.Dao.CategoriaDao;
import controller.tda.list.LinkedList;
import models.Categoria;
import models.enumedores.TipoCategoria;

public class CategoriaServices {
    private CategoriaDao obj;
    public CategoriaServices(){ 
        obj = new CategoriaDao(); 
    }
    public Boolean save() throws Exception{
        return obj.save(); 
    }

    // public LinkedList<Categoria> listAll(){ 
    //     return obj.getlistAll(); 

    // }

    public LinkedList<Categoria> listAll() {
    // Simulación de una lista de categorías
    LinkedList<Categoria> lista = new LinkedList<>();
    lista.add(new Categoria(1, TipoCategoria.SALADO));
    lista.add(new Categoria(2, TipoCategoria.DULCE));
    return lista;
}

    public LinkedList<Categoria> busquedaLinCategoria(String texto) {
        LinkedList<Categoria> listaFiltrada = new LinkedList<>();
        LinkedList<Categoria> listaCompleta = listAll(); // Obtener todas las categorías

        for (Categoria categoria : listaCompleta) {
           if (categoria.getTipo().toString().equalsIgnoreCase(texto)) {
                listaFiltrada.add(categoria); // Agregar a la lista si coincide
            }
        } 

    return listaFiltrada;
}

    public Categoria getCategoria(){
        return obj.getCategoria(); 
    }

    public void setCategoria( Categoria categoria){ 
        obj.setCategoria(categoria); 
    }

    public Boolean update() throws Exception{ 
        return obj.update(); 
    }

    public Boolean delete(Integer id) throws Exception{ 
        return obj.delete(id); 
    }

    public Categoria get(Integer id) throws Exception {
        // return (Categoria) obj.get(id);
        return obj.get(id);
    }

    // public LinkedList<Categoria> busquedaLinCategoria(String texto) {
    //     return obj.busquedaLinCategoria(texto);
    // }

    

    
}
