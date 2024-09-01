package INTERFACES;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import MODELS.Muestra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class frmCrearMuestra extends javax.swing.JFrame {

    private File selectedFile;
    private JTextField codigoField;
    private JTextField descripcionField;
    private JTextField estadoField;
    private JButton jButton1;
    private JButton jButton2;

    public frmCrearMuestra() {
        initComponents();
    }
    
    public frmCrearMuestra(frmMenuAdministracion menuAdmin) {
        initComponents();
    }

    private void initComponents() {
        // Inicialización de componentes
        codigoField = new JTextField(15);
        descripcionField = new JTextField(15);
        estadoField = new JTextField(15);
        jButton1 = new JButton("Cargar CSV");
        jButton2 = new JButton("Guardar");

        JLabel codigoLabel = new JLabel("Código:");
        JLabel descripcionLabel = new JLabel("Descripción:");
        JLabel estadoLabel = new JLabel("Estado:");

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
                    .addComponent(descripcionLabel)
                    .addComponent(estadoLabel)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(codigoField)
                    .addComponent(descripcionField)
                    .addComponent(estadoField))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(codigoLabel)
                    .addComponent(codigoField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(descripcionLabel)
                    .addComponent(descripcionField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(estadoLabel)
                    .addComponent(estadoField))
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
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        if (selectedFile != null) {
            try {
                List<List<String>> matrix = readCSV(selectedFile);
                String codigo = codigoField.getText().trim();
                String descripcion = descripcionField.getText().trim();
                String estado = estadoField.getText().trim();
                saveMatrixToBin(matrix, codigo, descripcion, estado);
                JOptionPane.showMessageDialog(this, "Archivo CSV cargado y guardado exitosamente.");

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
    private void saveMatrixToBin(List<List<String>> matrix, String codigo, String descripcion, String estado) throws IOException {
        List<Muestra> muestras = new ArrayList<>();
        File file = new File("muestras.bin");

        // Leer muestras existentes si el archivo ya existe
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List<?>) {
                    List<?> list = (List<?>) obj;
                    if (!list.isEmpty() && list.get(0) instanceof Muestra) {
                        muestras = (List<Muestra>) list;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Convertir la matriz de cadenas a una matriz de enteros
        List<List<Integer>> intMatrix = new ArrayList<>();
        for (List<String> row : matrix) {
            List<Integer> intRow = new ArrayList<>();
            for (String value : row) {
                try {
                    intRow.add(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    // Manejar el caso en que el valor no sea un número
                    intRow.add(0); // O cualquier valor predeterminado
                }
            }
            intMatrix.add(intRow);
        }

        // Convertir la matriz a una cadena CSV
        StringBuilder csvContent = new StringBuilder();
        for (List<String> row : matrix) {
            csvContent.append(String.join(",", row)).append("\n");
        }

        // Agregar la nueva muestra a la lista
        muestras.add(new Muestra(intMatrix, codigo, descripcion, estado, csvContent.toString()));

        // Guardar la lista actualizada de muestras en el archivo
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(muestras);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCrearMuestra(new frmMenuAdministracion()).setVisible(true);
            }
        });
    }
}
