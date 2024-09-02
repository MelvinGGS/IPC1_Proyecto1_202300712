package INTERFACES;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenderBotonTabla extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private JButton button;
    private int fila;

    public RenderBotonTabla() {
        button = new JButton();
        button.setText("Ver");
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        fila = row;
        return button;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        fila = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return fila;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Maneja la acción del botón aquí
        System.out.println("Botón en la fila " + fila + " presionado.");
        // Detener la edición
        fireEditingStopped();
    }
}
