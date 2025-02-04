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

    public Boolean update() throws Exception {
        this.merge(getImagen(), getImagen().getIdImagen() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) throws Exception {
        LinkedList<Imagen> list = getlistAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getIdImagen() == id) {
                this.supreme(id);
                this.listAll = listAll();
                return true;
            }
        }
        return false;
    }


    
    
}
