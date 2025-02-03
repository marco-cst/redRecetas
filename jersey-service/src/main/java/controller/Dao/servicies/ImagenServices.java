package controller.Dao.servicies;

import controller.Dao.ImagenDao;
import controller.tda.list.LinkedList;
import models.Imagen;
import models.Receta;
import java.util.HashMap;

public class ImagenServices {

    public Object[] listShowall() throws Exception {
        if (!obj.getlistAll().isEmpty()) {
            Imagen[] lista = (Imagen[]) obj.getlistAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for (int i = 0; i < lista.length; i++) {
                Receta p = new RecetaServicies().get(lista[i].getIdReceta());
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", lista[i].getIdImagen());
                map.put("url", lista[i].getImagen());
                map.put("receta", p);
                respuesta[i] = map;
            }
            return respuesta;
        }
        return new Object[] {};
    }

    private ImagenDao obj;

    public ImagenServices() {
        obj = new ImagenDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList<Imagen> listAll() {
        return obj.getlistAll();

    }

    public Imagen getImagen() {
        return obj.getImagen();
    }

    public void setImagen(Imagen imagen) {
        obj.setImagen(imagen);
    }

    public Imagen get(int id) throws Exception {
        return obj.get(id);
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean delete(int id) throws Exception {
        return obj.delete(id);
    }
}
