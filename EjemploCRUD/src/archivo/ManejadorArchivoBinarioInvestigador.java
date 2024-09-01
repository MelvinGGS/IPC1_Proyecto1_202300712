package archivo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Investigador;
import modelo.Muestra;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Collections;
import java.util.Comparator;

public class ManejadorArchivoBinarioInvestigador {

    public void agregarContenido(String ruta_archivo, Investigador investigador){
        try {
            // Se obtiene el listado de investigador
            List<Investigador> listado_investigador = this.obtenerContenido(ruta_archivo);
            listado_investigador.add(investigador);

            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_investigador);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al agregar contenido: " + e.getMessage());
        }  
    }
    
    public void modificarContenido(String ruta_archivo, String codigo, Investigador investigador){
        try {
            // Se obtiene el listado de investigador
            List<Investigador> listado_investigador = this.obtenerContenido(ruta_archivo);
            
            for (Investigador invest : listado_investigador) {
                if (invest.getCodigo().equals(codigo)) {
                    invest.setNombre(investigador.getNombre());
                    invest.setGenero(investigador.getGenero());
                    invest.setContrasenia(investigador.getContrasenia());
                }
            }

            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_investigador);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al modificar contenido: " + e.getMessage());
        }  
    }
    
    public void eliminarContenido(String ruta_archivo, String codigo){
        try {
            // Se obtiene el listado de investigador
            List<Investigador> listado_investigador = this.obtenerContenido(ruta_archivo);
            
            for (int i = 0; i < listado_investigador.size(); i++) {
                if (listado_investigador.get(i).getCodigo().equals(codigo)) {
                    listado_investigador.remove(i);
                }
            }

            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_investigador);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al eliminar contenido: " + e.getMessage());
        }  
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Investigador> obtenerContenido(String ruta_archivo) {
        ArrayList<Investigador> respuesta = new ArrayList<>();
        try {
            // Verificar si el archivo existe
            File archivo = new File(ruta_archivo);            
            if (archivo.exists() && archivo.length() > 0) {
                FileInputStream entradaArchivo = new FileInputStream(ruta_archivo);
                ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo);
                respuesta = (ArrayList<Investigador>) entradaObjeto.readObject();
                entradaArchivo.close();
                entradaObjeto.close();
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el contenido: " + e.getMessage());
        }
        return respuesta;
    }

    public void imprimirContenido(String ruta_archivo) {
        ArrayList<Investigador> investigadores = obtenerContenido(ruta_archivo);
        for (Investigador investigador : investigadores) {
            System.out.println("Codigo: " + investigador.getCodigo() + ", Nombre: " + investigador.getNombre() + ", Genero: " + investigador.getGenero() + ", Contraseña: " + investigador.getContrasenia() + ", Experimento: " + investigador.getExperimento());
        }
    }

    public void borrarContenido(String ruta_archivo) {
        try {
            // Crear una lista vacía
            List<Investigador> listado_investigador = new ArrayList<>();

            // Escribir la lista vacía en el archivo
            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_investigador);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al borrar contenido: " + e.getMessage());
        }
    }

    public JFreeChart generarGraficaTop3(String ruta_archivo) {
        ArrayList<Investigador> investigadores = obtenerContenido(ruta_archivo);

        // Ordenar la lista por la cantidad de experimentos en orden descendente
        Collections.sort(investigadores, new Comparator<Investigador>() {
            @Override
            public int compare(Investigador o1, Investigador o2) {
                return Integer.compare(o2.getExperimento(), o1.getExperimento());
            }
        });

        // Seleccionar los top 3 investigadores
        List<Investigador> top3Investigadores = investigadores.subList(0, Math.min(3, investigadores.size()));

        // Crear el dataset para la gráfica
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Investigador investigador : top3Investigadores) {
            dataset.addValue(investigador.getExperimento(), "Experimentos", investigador.getNombre());
        }

        // Crear la gráfica de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Top 3 Investigadores con más Experimentos",
                "Investigador",
                "Cantidad de Experimentos",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

        public void agregarMuestra(String ruta_archivo, Muestra muestra) {
        try {
            List<Muestra> listado_muestras = this.obtenerMuestras(ruta_archivo);
            listado_muestras.add(muestra);

            FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
            salidaObjeto.writeObject(listado_muestras);
            salidaArchivo.close();
            salidaObjeto.close();
        } catch (Exception e) {
            System.out.println("Error al agregar muestra: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Muestra> obtenerMuestras(String ruta_archivo) {
        ArrayList<Muestra> respuesta = new ArrayList<>();
        try {
            File archivo = new File(ruta_archivo);
            if (archivo.exists() && archivo.length() > 0) {
                FileInputStream entradaArchivo = new FileInputStream(ruta_archivo);
                ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo);
                respuesta = (ArrayList<Muestra>) entradaObjeto.readObject();
                entradaArchivo.close();
                entradaObjeto.close();
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las muestras: " + e.getMessage());
        }
        return respuesta;
    }
}