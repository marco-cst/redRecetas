package controller.Dao.servicies;
import controller.Dao.CategoriaDao;
import controller.tda.list.LinkedList;
import models.Categoria;

public class CategoriaServices {
    private CategoriaDao obj;
    public CategoriaServices(){ 
        obj = new CategoriaDao(); 
    }
    public Boolean save() throws Exception{
        return obj.save(); 
    }

    public LinkedList<Categoria> listAll(){ 
        return obj.getListAll(); 

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
    
}
