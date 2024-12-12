package controller.Dao.servicies;

import controller.Dao.ReseniaDao;
import controller.tda.list.LinkedList;
import models.Resenia;

public class ReseniaServices {
    private ReseniaDao obj;

    public ReseniaServices() {
        this.obj = new ReseniaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList<Resenia> listAll() {
        return obj.listAll();
    }

    public Resenia getResenia() {
        return obj.getResenia();
    }

    public void setResenia(Resenia resenia) {
        obj.setResenia(resenia);
    }

    public Resenia get(Integer id) throws Exception {
        // return obj.get(id);
        return (Resenia) obj.get(id);
    }


    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id);
    }

    
}
