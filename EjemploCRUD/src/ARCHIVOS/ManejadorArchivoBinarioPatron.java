package ARCHIVOS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import MODELS.Patron;

public class ManejadorArchivoBinarioPatron {

    public void agregarPatron(String rutaArchivo, Patron patron) {
        try {
            List<Patron> listadoPatrones = obtenerPatrones(rutaArchivo);
            listadoPatrones.add(patron);

            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoPatrones);
            }
        } catch (Exception e) {
            System.out.println("Error al agregar patrón: " + e.getMessage());
        }
    }

    public void eliminarPatron(String rutaArchivo, String codigo) {
        try {
            List<Patron> listadoPatrones = obtenerPatrones(rutaArchivo);
            boolean encontrado = false;

            for (int i = 0; i < listadoPatrones.size(); i++) {
                if (listadoPatrones.get(i).getCodigo().equals(codigo)) {
                    listadoPatrones.remove(i);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                     ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                    salidaObjeto.writeObject(listadoPatrones);
                }
                JOptionPane.showMessageDialog(null, "Patrón eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Patrón con código " + codigo + " no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar patrón: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Patron> obtenerPatrones(String rutaArchivo) {
        ArrayList<Patron> respuesta = new ArrayList<>();
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists() && archivo.length() > 0) {
                try (FileInputStream entradaArchivo = new FileInputStream(rutaArchivo);
                     ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo)) {
                    respuesta = (ArrayList<Patron>) entradaObjeto.readObject();
                }
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los patrones: " + e.getMessage());
        }
        return respuesta;
    }

    public void imprimirPatrones(String rutaArchivo) {
        ArrayList<Patron> patrones = obtenerPatrones(rutaArchivo);
        for (Patron patron : patrones) {
            System.out.println("Código: " + patron.getCodigo() + ", Nombre: " + patron.getNombre() + ", Contenido CSV: " + patron.getCsvContent());
        }
    }

    public void borrarContenido(String rutaArchivo) {
        try {
            List<Patron> listadoPatrones = new ArrayList<>();
            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoPatrones);
            }
        } catch (Exception e) {
            System.out.println("Error al borrar contenido: " + e.getMessage());
        }
    }
}