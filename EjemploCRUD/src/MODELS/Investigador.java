package MODELS;

import java.io.Serializable;

/**
 *
 * @author Douglas Soch
 */
public class Investigador implements Serializable{

    String codigo;
    String nombre;
    String genero;
    int experimento;
    String contrasenia;
    
    public Investigador(String codigo, String nombre, String genero, int experimento,String contrasenia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.experimento = experimento;
        this.contrasenia = contrasenia;
    }

    public Investigador() {
    }

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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getExperimento() {
        return experimento;
    }

    public void setExperimento(int experimento) {
        this.experimento = experimento;
    }
    
}
