package ARCHIVOS;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import MODELS.Investigador;
import MODELS.Muestra;
import MODELS.Asignacion;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ManejadorArchivoBinarioInvestigador {

    public void guardarContenido(String rutaArchivo, ArrayList<Investigador> investigadores) {
        try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
             ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
            salidaObjeto.writeObject(investigadores);
        } catch (Exception e) {
            System.out.println("Error al guardar contenido: " + e.getMessage());
        }
    }
    
    public void guardarMuestras(String rutaArchivo, ArrayList<Muestra> muestras) {
        try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
             ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
            salidaObjeto.writeObject(muestras);
        } catch (Exception e) {
            System.out.println("Error al guardar muestras: " + e.getMessage());
        }
    }
    
    public void agregarContenido(String rutaArchivo, Investigador investigador) {
        try {
            List<Investigador> listadoInvestigador = obtenerContenido(rutaArchivo);
            listadoInvestigador.add(investigador);

            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoInvestigador);
            }
        } catch (Exception e) {
            System.out.println("Error al agregar contenido: " + e.getMessage());
        }
    }
    
    public void modificarContenido(String rutaArchivo, String codigo, Investigador investigador) {
        try {
            List<Investigador> listadoInvestigador = obtenerContenido(rutaArchivo);
            
            for (Investigador invest : listadoInvestigador) {
                if (invest.getCodigo().equals(codigo)) {
                    invest.setNombre(investigador.getNombre());
                    invest.setGenero(investigador.getGenero());
                    invest.setContrasenia(investigador.getContrasenia());
                }
            }

            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoInvestigador);
            }
        } catch (Exception e) {
            System.out.println("Error al modificar contenido: " + e.getMessage());
        }
    }
    
    public void eliminarContenido(String rutaArchivo, String codigo) {
        try {
            List<Investigador> listadoInvestigador = obtenerContenido(rutaArchivo);
            
            listadoInvestigador.removeIf(investigador -> investigador.getCodigo().equals(codigo));

            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoInvestigador);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar contenido: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Investigador> obtenerContenido(String rutaArchivo) {
        ArrayList<Investigador> respuesta = new ArrayList<>();
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists() && archivo.length() > 0) {
                try (FileInputStream entradaArchivo = new FileInputStream(rutaArchivo);
                     ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo)) {
                    respuesta = (ArrayList<Investigador>) entradaObjeto.readObject();
                }
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el contenido: " + e.getMessage());
        }
        return respuesta;
    }

    public void imprimirContenido(String rutaArchivo) {
        ArrayList<Investigador> investigadores = obtenerContenido(rutaArchivo);
        for (Investigador investigador : investigadores) {
            System.out.println("Codigo: " + investigador.getCodigo() + ", Nombre: " + investigador.getNombre() + ", Genero: " + investigador.getGenero() + ", Contraseña: " + investigador.getContrasenia() + ", Experimento: " + investigador.getExperimento());
        }
    }

    public void borrarContenido(String rutaArchivo) {
        try {
            List<Investigador> listadoInvestigador = new ArrayList<>();
            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoInvestigador);
            }
        } catch (Exception e) {
            System.out.println("Error al borrar contenido: " + e.getMessage());
        }
    }

    public JFreeChart generarGraficaTop3(String rutaArchivo) {
        ArrayList<Investigador> investigadores = obtenerContenido(rutaArchivo);

        Collections.sort(investigadores, Comparator.comparingInt(Investigador::getExperimento).reversed());

        List<Investigador> top3Investigadores = investigadores.subList(0, Math.min(3, investigadores.size()));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Investigador investigador : top3Investigadores) {
            dataset.addValue(investigador.getExperimento(), "Experimentos", investigador.getNombre());
        }

        return ChartFactory.createBarChart(
                "Top 3 Investigadores con más Experimentos",
                "Investigador",
                "Cantidad de Experimentos",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }

    public void agregarMuestra(String rutaArchivo, Muestra muestra) {
        try {
            List<Muestra> listadoMuestras = obtenerMuestras(rutaArchivo);
            listadoMuestras.add(muestra);

            try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
                 ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
                salidaObjeto.writeObject(listadoMuestras);
            }
        } catch (Exception e) {
            System.out.println("Error al agregar muestra: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Muestra> obtenerMuestras(String rutaArchivo) {
        ArrayList<Muestra> respuesta = new ArrayList<>();
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists() && archivo.length() > 0) {
                try (FileInputStream entradaArchivo = new FileInputStream(rutaArchivo);
                     ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo)) {
                    respuesta = (ArrayList<Muestra>) entradaObjeto.readObject();
                }
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las muestras: " + e.getMessage());
        }
        return respuesta;
    }

    public void guardarAsignaciones(String rutaArchivo, ArrayList<Asignacion> asignaciones) {
        try (FileOutputStream salidaArchivo = new FileOutputStream(rutaArchivo);
             ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
            salidaObjeto.writeObject(asignaciones);
        } catch (Exception e) {
            System.out.println("Error al guardar asignaciones: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Asignacion> obtenerAsignaciones(String rutaArchivo) {
        ArrayList<Asignacion> respuesta = new ArrayList<>();
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists() && archivo.length() > 0) {
                try (FileInputStream entradaArchivo = new FileInputStream(rutaArchivo);
                     ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo)) {
                    respuesta = (ArrayList<Asignacion>) entradaObjeto.readObject();
                }
            } else {
                System.out.println("El archivo no existe o está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las asignaciones: " + e.getMessage());
        }
        return respuesta;
    }
}