package INTERFACES;
import MODELS.Muestra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Clase para manejar la acción del botón en la tabla
class ButtonEditor extends DefaultCellEditor {
    private String label;
    private boolean clicked;
    private List<Muestra> muestras;
    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, List<Muestra> muestras) {
        super(checkBox);
        this.muestras = muestras;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        selectedRow = row;
        JButton button = new JButton(label);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                try {
                    generarHTML(muestras.get(selectedRow));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            // Acción cuando se hace clic en el botón
            clicked = false;
        }
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    private void generarHTML(Muestra muestra) throws IOException {
        String nombreArchivo = "Patron_" + muestra.getCodigo() + ".html";
        File archivoHTML = new File(nombreArchivo);

        StringBuilder contenidoHTML = new StringBuilder();
        contenidoHTML.append("<html><head><title>Patrón " + muestra.getCodigo() + "</title></head><body>");
        contenidoHTML.append("<h1>Patrón " + muestra.getCodigo() + "</h1>");
        contenidoHTML.append("<table border='1'>");

        for (List<Integer> fila : muestra.getMatrix()) {
            contenidoHTML.append("<tr>");
            for (Integer valor : fila) {
                contenidoHTML.append("<td>").append(valor).append("</td>");
            }
            contenidoHTML.append("</tr>");
        }

        contenidoHTML.append("</table></body></html>");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoHTML))) {
            writer.write(contenidoHTML.toString());
        }

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (archivoHTML.exists()) {
                desktop.browse(archivoHTML.toURI());
            }
        }
    }
}