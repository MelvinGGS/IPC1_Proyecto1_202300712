package ARCHIVOS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import MODELS.Patron;

public class ManejadorArchivoBinarioPatron {

    public void agregarPatron(String ruta_archivo, Patron patron) {
        try {
            // Se obtiene el listado de patrones
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
            // Se obtiene el listado de patrones
            List<Patron> listado_patrones = this.obtenerPatrones(ruta_archivo);
            
            for (int i = 0; i < listado_patrones.size(); i++) {
                if (listado_patrones.get(i).getCodigo().equals(codigo)) {
                    listado_patrones.remove(i);
                }
            }

            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_patrones);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al eliminar patrón: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Patron> obtenerPatrones(String ruta_archivo) {
        ArrayList<Patron> respuesta = new ArrayList<>();
        try {
            // Verificar si el archivo existe
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
            // Crear una lista vacía
            List<Patron> listado_patrones = new ArrayList<>();

            // Escribir la lista vacía en el archivo
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