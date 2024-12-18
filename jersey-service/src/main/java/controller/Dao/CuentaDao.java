package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Cuenta;

public class CuentaDao extends AdapterDao<Cuenta> {
    private Cuenta cuenta;
    private LinkedList<Cuenta> listAll;

    public CuentaDao() {
        super(Cuenta.class);
    }

    public Cuenta getCuenta() {
        if (cuenta == null) {
            cuenta = new Cuenta();
        }
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public LinkedList<Cuenta> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean update() throws Exception {
        this.merge(getCuenta(), getCuenta().getIdCuenta() - 1);
        this.listAll = this.listAll();
        return true;
    }

    public Boolean save() throws Exception {
        Integer id = listAll().getSize() + 1;
        cuenta.setIdCuenta(id);
        this.persist(this.cuenta);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) throws Exception {
        LinkedList<Cuenta> list = getListAll();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getIdCuenta() == id) {
                this.supreme(id);
                this.listAll = listAll(); // Actualiza la lista de objetos
                return true; // Retorna verdadero si se eliminó correctamente
            }
        }
        return false; // Retorna falso si no se encontró el ID
    }

}
