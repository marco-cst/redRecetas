package controller.Dao.servicies;



import controller.Dao.CuentaDao;
import controller.tda.list.LinkedList;
import models.Cuenta;

public class CuentaServices {
    private CuentaDao obj;

    public CuentaServices(){
        obj = new CuentaDao();
        
    }

    public Boolean save() throws Exception{
        return obj.save();
    }

   public Boolean update () throws Exception{
        return obj.update();
    }

  
    public LinkedList<Cuenta> listAll(){
        return obj.getListAll();
    }


    public Cuenta getCuenta(){
        return obj.getCuenta();
    }

    public void setCuenta( Cuenta cuenta){
        obj.setCuenta(cuenta);
    }

    public Cuenta get(Integer id) throws Exception{
        return obj.get(id);
    }
    


    
}
