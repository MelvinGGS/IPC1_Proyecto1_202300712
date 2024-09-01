package ARCHIVOS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import MODELS.Patron;

public class ManejadorArchivoBinarioPatron {

    public void agregarPatron(String ruta_archivo, Patron patron) {
        try {
            List<Patron> listado_patrones = this.obtenerPatrones(ruta_archivo);
            listado_patrones.add(patron);

            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_patrones);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al agregar patrón: " + e.getMessage());
        }
    }

    public void eliminarPatron(String ruta_archivo, String codigo) {
        try {
            List<Patron> listado_patrones = this.obtenerPatrones(ruta_archivo);
            boolean encontrado = false;
            
            for (int i = 0; i < listado_patrones.size(); i++) {
                if (listado_patrones.get(i).getCodigo().equals(codigo)) {
                    listado_patrones.remove(i);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
                ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
                salidaObjeto.writeObject(listado_patrones);
                salidaArchivo.close();
                salidaObjeto.close();
                JOptionPane.showMessageDialog(null, "Patrón eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Patrón con código " + codigo + " no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar patrón: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Patron> obtenerPatrones(String ruta_archivo) {
        ArrayList<Patron> respuesta = new ArrayList<>();
        try {
            File archivo = new File(ruta_archivo);            
            if (archivo.exists() && archivo.length() > 0) {
                FileInputStream entradaArchivo = new FileInputStream(ruta_archivo);
                ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo);
                respuesta = (ArrayList<Patron>) entradaObjeto.readObject();
                entradaArchivo.close();
                entradaObjeto.close();
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los patrones: " + e.getMessage());
        }
        return respuesta;
    }

    public void imprimirPatrones(String ruta_archivo) {
        ArrayList<Patron> patrones = obtenerPatrones(ruta_archivo);
        for (Patron patron : patrones) {
            System.out.println("Codigo: " + patron.getCodigo() + ", Nombre: " + patron.getNombre() + ", Contenido CSV: " + patron.getCsvContent());
        }
    }

    public void borrarContenido(String ruta_archivo) {
        try {
            List<Patron> listado_patrones = new ArrayList<>();
            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_patrones);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al borrar contenido: " + e.getMessage());
        }
    }
}
