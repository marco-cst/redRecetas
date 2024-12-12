package controller.Dao.servicies;

import controller.Dao.ImagenDao;
import controller.tda.list.LinkedList;
import models.Imagen;

public class ImagenServices {
    private ImagenDao obj;
    public ImagenServices(){
        obj = new ImagenDao();
    }
    public Boolean save() throws Exception{
        return obj.save();
    }

    public LinkedList<Imagen> listAll(){     
        return obj.getlistAll();

    }
    
    public Imagen getImagen(){
        return obj.getImagen();
    }

    public void setImagen( Imagen imagen){
        obj.setImagen(imagen);
    }
}
