package MODELS;

import java.io.Serializable;

public class Patron implements Serializable {
    private static final long serialVersionUID = 1L; // Asegúrate de que este valor sea constante

    private String codigo;
    private String nombre;
    private String csvContent;

    public Patron(String codigo, String nombre, String csvContent) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.csvContent = csvContent;
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCsvContent() {
        return csvContent;
    }

    public void setCsvContent(String csvContent) {
        this.csvContent = csvContent;
    }
}