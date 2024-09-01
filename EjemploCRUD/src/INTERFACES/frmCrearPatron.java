package INTERFACES;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import MODELS.Patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class frmCrearPatron extends javax.swing.JFrame {

    private File selectedFile;
    private JTextField codigoField;
    private JTextField nombreField;
    private JButton jButton1;
    private JButton jButton2;
    private frmMenuAdministracion menuAdmin;

    public frmCrearPatron(frmMenuAdministracion menuAdmin) {
        this.menuAdmin = menuAdmin;
        initComponents();
        updateTableFromBin(); // Load data from .bin file when the window opens
    }

    private void initComponents() {
        // Inicialización de componentes
        codigoField = new JTextField(15);
        nombreField = new JTextField(15);
        jButton1 = new JButton("Cargar CSV");
        jButton2 = new JButton("Guardar");

        JLabel codigoLabel = new JLabel("Código:");
        JLabel nombreLabel = new JLabel("Nombre:");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        // Configuración del layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(codigoLabel)
                    .addComponent(nombreLabel)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(codigoField)
                    .addComponent(nombreField))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(codigoLabel)
                    .addComponent(codigoField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(nombreField))
                .addComponent(jButton1)
                .addComponent(jButton2)
        );

        pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            try {
                List<List<String>> matrix = readCSV(selectedFile);
                updateTable(matrix);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo CSV: " + e.getMessage());
            }
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        if (selectedFile != null) {
            try {
                List<List<String>> matrix = readCSV(selectedFile);
                String codigo = codigoField.getText().trim();
                String nombre = nombreField.getText().trim();
                saveMatrixToBin(matrix, codigo, nombre);
                JOptionPane.showMessageDialog(this, "Archivo CSV cargado y guardado exitosamente.");
                updateTableFromBin(); // Update table after saving
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo CSV: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo CSV primero.");
        }
    }

    private List<List<String>> readCSV(File file) throws IOException {
        List<List<String>> matrix = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    // Verificar si la primera línea contiene encabezados
                    if (line.toLowerCase().contains("codigo")) {
                        continue; // Ignorar la primera línea si contiene encabezados
                    }
                }
                String[] values = line.split(",");
                List<String> row = new ArrayList<>();
                for (String value : values) {
                    row.add(value.trim());
                }
                matrix.add(row);
            }
        }
        return matrix;
    }

    @SuppressWarnings("unchecked")
    private void saveMatrixToBin(List<List<String>> matrix, String codigo, String nombre) throws IOException {
        List<Patron> patrones = new ArrayList<>();
        File file = new File("patron.bin");

        // Leer patrones existentes si el archivo ya existe
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List<?>) {
                    List<?> list = (List<?>) obj;
                    if (!list.isEmpty() && list.get(0) instanceof Patron) {
                        patrones = (List<Patron>) list;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Convertir la matriz a una cadena CSV
        StringBuilder csvContent = new StringBuilder();
        for (List<String> row : matrix) {
            csvContent.append(String.join(",", row)).append("\n");
        }

        // Agregar el nuevo patrón a la lista
        patrones.add(new Patron(codigo, nombre, csvContent.toString()));

        // Guardar la lista actualizada de patrones en el archivo
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(patrones);
        }
    }

    private void updateTable(List<List<String>> matrix) {
        DefaultTableModel tableModel = (DefaultTableModel) menuAdmin.getJTable3().getModel();
        tableModel.setRowCount(0); // Clear existing rows
        StringBuilder csvContent = new StringBuilder();
        for (List<String> row : matrix) {
            csvContent.append(String.join(",", row)).append("\n");
        }
        tableModel.addRow(new Object[]{null, null, csvContent.toString()});
    }

    @SuppressWarnings("unchecked")
    public void updateTableFromBin() {
        List<Patron> patrones = new ArrayList<>();
        File file = new File("patron.bin");

        // Leer patrones existentes si el archivo ya existe
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List<?>) {
                    List<?> list = (List<?>) obj;
                    if (!list.isEmpty() && list.get(0) instanceof Patron) {
                        patrones = (List<Patron>) list;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        DefaultTableModel tableModel = (DefaultTableModel) menuAdmin.getJTable3().getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (Patron patron : patrones) {
            tableModel.addRow(new Object[]{patron.getCodigo(), patron.getNombre(), patron.getCsvContent()});
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCrearPatron(new frmMenuAdministracion()).setVisible(true);
            }
        });
    }
}