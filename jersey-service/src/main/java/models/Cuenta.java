package models;

public class Cuenta {
    
    private String correo;
    private Integer idCuenta;
    private String clave;
    private Integer idPersona;
    private boolean estado;

  

    public Cuenta() {
    }

    public String getCorreo() {
        return correo;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public String getClave() {
        return clave;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

   
    
}
