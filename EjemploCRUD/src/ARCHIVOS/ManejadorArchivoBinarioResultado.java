package ARCHIVOS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import MODELS.Resultado;

public class ManejadorArchivoBinarioResultado {

    public void agregarResultado(String rutaArchivo, Resultado resultado) {
        try {
            List<Resultado> listadoResultados = obtenerResultados(rutaArchivo);
            listadoResultados.add(resultado);

            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoResultados);
            }
        } catch (Exception e) {
            System.out.println("Error al agregar resultado: " + e.getMessage());
        }
    }

    public void eliminarResultado(String rutaArchivo, int numero) {
        try {
            List<Resultado> listadoResultados = obtenerResultados(rutaArchivo);
            boolean encontrado = false;

            for (int i = 0; i < listadoResultados.size(); i++) {
                if (listadoResultados.get(i).getNumero() == numero) {
                    listadoResultados.remove(i);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                     ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                    salidaObjeto.writeObject(listadoResultados);
                }
                JOptionPane.showMessageDialog(null, "Resultado eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Resultado con número " + numero + " no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar resultado: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Resultado> obtenerResultados(String rutaArchivo) {
        ArrayList<Resultado> respuesta = new ArrayList<>();
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists() && archivo.length() > 0) {
                try (FileInputStream entradaArchivo = new FileInputStream(rutaArchivo);
                     ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo)) {
                    respuesta = (ArrayList<Resultado>) entradaObjeto.readObject();
                }
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los resultados: " + e.getMessage());
        }
        return respuesta;
    }

    public void imprimirResultados(String rutaArchivo) {
        ArrayList<Resultado> resultados = obtenerResultados(rutaArchivo);
        for (Resultado resultado : resultados) {
            System.out.println("Número: " + resultado.getNumero() + ", Muestra: " + resultado.getCodigoMuestra() + ", Patrón: " + resultado.getCodigoPatron() + ", Fecha: " + resultado.getFecha() + ", Hora: " + resultado.getHora() + ", Resultado: " + resultado.getResultado());
        }
    }

    public void borrarContenido(String rutaArchivo) {
        try {
            List<Resultado> listadoResultados = new ArrayList<>();
            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoResultados);
            }
        } catch (Exception e) {
            System.out.println("Error al borrar contenido: " + e.getMessage());
        }
    }
}