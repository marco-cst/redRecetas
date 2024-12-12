package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Imagen;

public class ImagenDao extends AdapterDao<Imagen> {
    private Imagen imagen = new Imagen();
    private LinkedList<Imagen> listAll;
    public ImagenDao() {
        super(Imagen.class);
    }

    public Imagen getImagen() {
        if(imagen == null) {
            imagen = new Imagen();
        }
        return this.imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public LinkedList<Imagen> getlistAll() {
        if(listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        imagen.setIdImagen(id);
        this.persist(this.imagen);
        this.listAll = listAll();
        return true;
    }


    
    
}
