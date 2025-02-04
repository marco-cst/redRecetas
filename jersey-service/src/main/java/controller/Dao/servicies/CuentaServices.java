package controller.Dao.servicies;

import java.util.HashMap;

import controller.Dao.CuentaDao;
import controller.tda.list.LinkedList;
import models.Cuenta;
import models.Persona;

public class CuentaServices {

    public Object[] listShowall() throws Exception {
        if (!obj.getListAll().isEmpty()) {
            Cuenta[] lista = (Cuenta[]) obj.getListAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for (int i = 0; i < lista.length; i++) {
                Persona p = new PersonaServicies().get(lista[i].getIdPersona());
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", lista[i].getIdCuenta());
                map.put("correo", lista[i].getCorreo());
                map.put("clave", lista[i].getClave());
                map.put("estado", lista[i].getEstado());
                map.put("persona", p);
                respuesta[i] = map;
            }
            return respuesta;
        }
        return new Object[] {};
    }

    private CuentaDao obj;

    public CuentaServices() {
        obj = new CuentaDao();

    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList<Cuenta> listAll() {
        return obj.getListAll();
    }

    public Cuenta getCuenta() {
        return obj.getCuenta();
    }

    public void setCuenta(Cuenta cuenta) {
        obj.setCuenta(cuenta);
    }

    public Cuenta get(Integer id) throws Exception {
        return obj.get(id);
    }

    public Boolean delete(int id) throws Exception {
        return obj.delete(id);
    }

}
