package MODELS;

import java.io.Serializable;
import java.util.List;

public class Muestra implements Serializable {
    private static final long serialVersionUID = 1L; // Añadir serialVersionUID

    private List<List<Integer>> matrix;
    private String codigo;
    private String descripcion;
    private String estado;
    private String codigoCSV;

    public Muestra(List<List<Integer>> matrix, String codigo, String descripcion, String estado, String codigoCSV) {
        this.matrix = matrix;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.codigoCSV = codigoCSV;
    }

    public List<List<Integer>> getMatrix() {
        return matrix;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoCSV() {
        return codigoCSV;
    }

    public void setCodigoCSV(String codigoCSV) {
        this.codigoCSV = codigoCSV;
    }
}