package models.enumedores;

// public enum Sabor {
//     SALADO, DULCE;
// }

public enum TipoCategoria {
    SALADO("SALADO"), DULCE("DULCE");
    private String tipo;

    //Constructor
    TipoCategoria(String tipo) {
        this.tipo = tipo;
    }
    
    //Getter
    public String getTipo() {
        return tipo;
    }
}