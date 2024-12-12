package models;

public class Cuenta {
    
    private String correo;
    private int idCuenta;
    private String clave;
    private int idPersona;
    private boolean estado;

    public Cuenta(String correo, int idCuenta, String clave, int idPersona, boolean estado) {
        this.correo = correo;
        this.idCuenta = idCuenta;
        this.clave = clave;
        this.idPersona = idPersona;
        this.estado = estado;
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

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "correo=" + correo + ", idCuenta=" + idCuenta + ", clave=" + clave + ", idPersona=" + idPersona + ", estado=" + estado + '}';
    }
    
}
