package INTERFACES;
import MODELS.Muestra;

import javax.swing.*;
import javax.swing.table.*;

import java.util.List;

public class MatrizEnJTable extends JFrame {
    private JTable table;
    private List<Muestra> muestras; // Lista de objetos Muestra que contienen la matriz y el código

    public MatrizEnJTable(List<Muestra> muestras) {
        this.muestras = muestras;

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Código", "Descripción", "Estado", "Ver en HTML"}, 0);

        // Llenar la tabla con los datos
        for (Muestra muestra : muestras) {
            model.addRow(new Object[]{muestra.getCodigo(), muestra.getDescripcion(), muestra.getEstado(), "Ver en HTML"});
        }

        table = new JTable(model);

        // Configurar la columna del botón
        TableColumn verEnHTMLColumn = table.getColumnModel().getColumn(3);
        verEnHTMLColumn.setCellRenderer(new ButtonRenderer());
        verEnHTMLColumn.setCellEditor(new ButtonEditor(new JCheckBox(), muestras));

        // Configuración básica del JFrame
        setTitle("Matriz en JTable");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Añadir la tabla al JFrame
        add(new JScrollPane(table));

        // Mostrar el JFrame
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        List<List<Integer>> matrizEjemplo = List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9)
        );

        Muestra muestra1 = new Muestra(matrizEjemplo, "12345", "Descripción 1", "Activo", "12345.csv");
        Muestra muestra2 = new Muestra(matrizEjemplo, "67890", "Descripción 2", "Inactivo", "67890.csv");

        new MatrizEnJTable(List.of(muestra1, muestra2));
    }
}