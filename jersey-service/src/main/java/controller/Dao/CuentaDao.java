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
        // Validar campos nulos
        //if (cuenta.getCorreo() == null || cuenta.getClave() == null || cuenta.getIdPersona() == null) {
         //   throw new Exception("Los campos correo, clave y persona son obligatorios.");
       // }
    
        // Validar entradas vacías o solo espacios en blanco
        if (cuenta.getCorreo().trim().isEmpty()) {
            throw new Exception("El correo no puede estar vacío o contener solo espacios.");
        }
        if (cuenta.getClave().trim().isEmpty()) {
            throw new Exception("La clave no puede estar vacía o contener solo espacios.");
        }
    
        // Validar formato del correo
        if (!cuenta.getCorreo().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new Exception("El correo no tiene un formato válido.");
        }
    
        // Validar longitud de la clave
        if (cuenta.getClave().length() < 8) {
            throw new Exception("La clave debe tener al menos 8 caracteres.");
        }
        if (cuenta.getClave().length() > 20) {
            throw new Exception("La clave no puede tener más de 20 caracteres.");
        }
    
        // Validar complejidad de la clave
        if (!cuenta.getClave().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new Exception("La clave debe contener al menos una letra mayúscula, una minúscula, un número y un carácter especial.");
        }
    
        // Validar duplicados en el correo
        LinkedList<Cuenta> allRecords = (LinkedList<Cuenta>) listAll();
        for (int i = 0; i < allRecords.getSize(); i++) {
            Object record = allRecords.get(i);
            if (record instanceof Cuenta) {
                Cuenta existingCuenta = (Cuenta) record;
    
                // Validar duplicado de correo
                if (existingCuenta.getCorreo().equalsIgnoreCase(cuenta.getCorreo())) {
                    throw new Exception("El correo ya está registrado.");
                }
    
                // Validar que la persona no esté asignada a otra cuenta
                if (existingCuenta.getIdPersona() == cuenta.getIdPersona()) {
                    throw new Exception("Esta persona ya está asignada a otra cuenta.");
                }
            }
        }
    
        // Asignar un ID único
        Integer id = allRecords.getSize() + 1;
        cuenta.setIdCuenta(id);
    
        // Persistir la cuenta
        this.persist(this.cuenta);
        this.listAll = listAll();
        return true;
    }
    









    public Boolean delete(Integer id) throws Exception {
        LinkedList<Cuenta> list = getListAll();
        for (int i = 0; i < list.getSize(); i++) { // Iterar sobre la lista
            if (list.get(i).getIdCuenta().equals(id)) { // Comparar IDs
                list.remove(i); // Eliminar el elemento en la posición i
                saveFile(g.toJson(list.toArray())); // Guardar la lista actualizada en el archivo
                return true; // Retornar verdadero si se eliminó correctamente
            }
        }
        return false; // Retornar falso si no se encontró el ID
    }

}
