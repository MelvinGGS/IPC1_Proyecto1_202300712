package INTERFACES;

import ARCHIVOS.ManejadorArchivoBinarioInvestigador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import MODELS.Asignacion;
import MODELS.Investigador;
import MODELS.Muestra;
import MODELS.Patron;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;



/**
 *
 * @author sochd
 */
public class frmMenuAdministracion extends javax.swing.JFrame {

    private ChartPanel chartPanel;
    private ArrayList<Asignacion> asignaciones;


    /**
     * Creates new form frmMenuAdministracion
     */
    public frmMenuAdministracion() {
        initComponents();
        ManejadorArchivoBinarioInvestigador manejadorInvestigador = new ManejadorArchivoBinarioInvestigador();
        asignaciones = manejadorInvestigador.obtenerAsignaciones("asignaciones.bin");
        inicializarTablas();
        setLocationRelativeTo(null);
        agregarGrafica();        
        refrescarTabla();
        refrescarTablaMuestras();
        updateTableFromBin();
        cargarCodigosInvestigadores();
        cargarCodigosMuestras();
   
    }

    private void cargarCodigosInvestigadores() {
        ManejadorArchivoBinarioInvestigador manejadorInvestigador = new ManejadorArchivoBinarioInvestigador();
        ArrayList<Investigador> investigadores = manejadorInvestigador.obtenerContenido("investigador.bin");

        jComboBox1.removeAllItems();
        for (Investigador investigador : investigadores) {
            jComboBox1.addItem(investigador.getCodigo());
        }
    }

    private void cargarCodigosMuestras() {
        ManejadorArchivoBinarioInvestigador manejadorInvestigador = new ManejadorArchivoBinarioInvestigador();
        ArrayList<Muestra> muestras = manejadorInvestigador.obtenerMuestras("muestras.bin");

        // Obtener los códigos de las muestras ya asignadas
        Set<String> muestrasAsignadas = new HashSet<>();
        for (Asignacion asignacion : asignaciones) {
            muestrasAsignadas.add(asignacion.getCodigoMuestra());
        }

        jComboBox2.removeAllItems();
        for (Muestra muestra : muestras) {
            if (!muestrasAsignadas.contains(muestra.getCodigo())) {
                jComboBox2.addItem(muestra.getCodigo());
            }
        }
    }

    private void asignarMuestraAInvestigador(String codigoInvestigador, String codigoMuestra) {
        ManejadorArchivoBinarioInvestigador manejadorInvestigador = new ManejadorArchivoBinarioInvestigador();
        ArrayList<Muestra> muestras = manejadorInvestigador.obtenerMuestras("muestras.bin");

        // Crear una nueva asignación
        Asignacion nuevaAsignacion = new Asignacion(codigoInvestigador, codigoMuestra);
        asignaciones.add(nuevaAsignacion);

        // Cambiar el estado de la muestra a "en proceso"
        for (Muestra muestra : muestras) {
            if (muestra.getCodigo().equals(codigoMuestra)) {
                muestra.setEstado("en proceso");
                break;
            }
        }

        // Guardar los cambios
        manejadorInvestigador.guardarAsignaciones("asignaciones.bin", asignaciones);
        manejadorInvestigador.guardarMuestras("muestras.bin", muestras);

        // Imprimir la lista de asignaciones actualizada
        System.out.println("Lista de Asignaciones:");
        for (Asignacion asignacion : asignaciones) {
            System.out.println("Investigador: " + asignacion.getCodigoInvestigador() + ", Muestra: " + asignacion.getCodigoMuestra());
        }

        // Recargar los códigos de las muestras
        cargarCodigosMuestras();
    }


    
    @SuppressWarnings("unchecked")
    private void updateTableFromBin() {
        List<Patron> patrones = new ArrayList<>();
        File file = new File("patron.bin");

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

        DefaultTableModel tableModel = (DefaultTableModel) getJTable3().getModel();
        tableModel.setRowCount(0); // Limpiar las filas existentes

        for (Patron patron : patrones) {
            tableModel.addRow(new Object[]{patron.getCodigo(), patron.getNombre(), patron.getCsvContent()});}
        }

    public void refrescarTablaMuestras() {
        ManejadorArchivoBinarioInvestigador manejador = new ManejadorArchivoBinarioInvestigador();
        List<Muestra> muestras = manejador.obtenerMuestras("muestras.bin");
    
        DefaultTableModel tablaModelo = (DefaultTableModel) jTable2.getModel();
    
        // Limpiar la tabla
        tablaModelo.setRowCount(0);
    
        // Agregar los nuevos datos
        for (Muestra muestra : muestras) {
            tablaModelo.addRow(new Object[]{muestra.getCodigo(), muestra.getDescripcion(), muestra.getEstado(), muestra.getCodigoCSV()});
        }
    
        // Notificar al JTable que los datos han cambiado
        tablaModelo.fireTableDataChanged();
    
        // Forzar el repintado del JScrollPane (opcional)
        jScrollPane2.repaint();
    }


        public JTable getJTable3() {
        return jTable3;
    }

    


    // Método para cargar muestras desde un archivo CSV
        private void cargarCSVMuestras(File file) {
            ArrayList<Muestra> muestras = new ArrayList<>();
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
                    if (values.length >= 3) {
                        try {
                            String codigo = values[0].trim();
                            String descripcion = values[1].trim();
                            String estado = "Ingreso"; // Fijar el valor de la tercera columna como "Ingreso"
                            
                            // Unir los valores restantes después de la tercera columna
                            String codigoCSV = String.join(",", Arrays.copyOfRange(values, 2, values.length)).trim();
                            
                            Muestra muestra = new Muestra(null, codigo, descripcion, estado, codigoCSV);
                            muestras.add(muestra);
                        } catch (Exception e) {
                            // Manejar la excepción sin imprimir el mensaje de error
                        }
                    } else {
                        System.err.println("Línea con formato incorrecto: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al leer el archivo CSV: " + e.getMessage());
            }
        
            // Borrar el contenido existente del archivo binario
            ManejadorArchivoBinarioInvestigador manejador = new ManejadorArchivoBinarioInvestigador();
            manejador.borrarContenido("muestras.bin");
        
            // Guardar en el archivo binario
            for (Muestra muestra : muestras) {
                manejador.agregarMuestra("muestras.bin", muestra);
            }
        
            // Actualizar la tabla con las muestras leídas
            actualizarTablaMuestras(muestras);
            
            // Refrescar la tabla
            refrescarTabla();
        }
    
    public void refrescarTabla(){
        ManejadorArchivoBinarioInvestigador archivoBinario = new ManejadorArchivoBinarioInvestigador();
        ArrayList<Investigador> investigador = archivoBinario.obtenerContenido("investigador.bin");
        
        DefaultTableModel tablaModelo = (DefaultTableModel)jTable1.getModel();
        tablaModelo.setRowCount(0);
        for (Investigador invest : investigador) {
            tablaModelo.addRow(new Object[]{invest.getCodigo(), invest.getNombre(), invest.getGenero(), invest.getExperimento()});
        }
        actualizarGrafica();
    }

    private void agregarGrafica() {
        // Create a new panel for the chart
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Generate the chart and add it to the chartPanel
        ManejadorArchivoBinarioInvestigador manejadorArchivo = new ManejadorArchivoBinarioInvestigador();
        JFreeChart barChart = manejadorArchivo.generarGraficaTop3("investigador.bin");
        chartPanel = new ChartPanel(barChart);

        // Set the preferred size of the chart to match jPanel5
        chartPanel.setPreferredSize(new Dimension(jPanel5.getWidth(), jPanel5.getHeight()));
        panel.add(chartPanel, BorderLayout.CENTER);

        // Set jPanel5 layout to BorderLayout and add the chartPanel
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(panel, BorderLayout.CENTER);
        jPanel5.validate();
    }

private void inicializarTablas() {
    DefaultTableModel model1 = new DefaultTableModel(new Object[]{"Código", "Nombre", "Género", "Experimento"}, 0);
    jTable1.setModel(model1);

    DefaultTableModel model2 = new DefaultTableModel(new Object[]{"Código", "Descripción", "Estado", "Acciones"}, 0);
    jTable2.setModel(model2);

    DefaultTableModel model3 = new DefaultTableModel(new Object[]{"Código", "Nombre", "Tipo"}, 0);
    jTable3.setModel(model3);
}


    private void actualizarGrafica() {
        ManejadorArchivoBinarioInvestigador manejadorArchivo = new ManejadorArchivoBinarioInvestigador();
        JFreeChart barChart = manejadorArchivo.generarGraficaTop3("investigador.bin");
        chartPanel.setChart(barChart);
        chartPanel.validate();
    }

    private void cargarCSV(File file) {
        ArrayList<Investigador> investigadores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Omitir la primera línea (encabezados)
                }
                String[] values = line.split(",");
                if (values.length == 5) {
                    try {
                        int experimento = Integer.parseInt(values[3].trim());
                        Investigador investigador = new Investigador(values[0].trim(), values[1].trim(), values[2].trim(), experimento, values[4].trim());
                        investigadores.add(investigador);
                    } catch (NumberFormatException e) {
                        // Manejar la excepción sin imprimir el mensaje de error
                    }
                } else {
                    System.err.println("Línea con formato incorrecto: " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // Borrar el contenido existente del archivo binario
        ManejadorArchivoBinarioInvestigador manejador = new ManejadorArchivoBinarioInvestigador();
        manejador.borrarContenido("investigador.bin");
    
        // Guardar en el archivo binario
        for (Investigador investigador : investigadores) {
            manejador.agregarContenido("investigador.bin", investigador);
        }
    
        // Actualizar la tabla
        actualizarTabla(investigadores);
        
        // Refresh the chart
        refrescarTabla();
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Genero", "Experimientos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setText("Crear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setText("Cargar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setText("Eliminar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton2)
                        .addGap(52, 52, 52)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3)
                            .addComponent(jButton2)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
        );

        jTabbedPane1.addTab("INVESTIGADORES", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Estado", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
        }

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton5.setText("Crear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton6.setText("Cargar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5)
                        .addComponent(jButton6))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("MUESTRAS", jPanel2);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Investigador");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("Muestra");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton7.setText("Asignar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(131, 131, 131)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(87, 87, 87)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(427, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jButton7)
                .addContainerGap(279, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ASIGNACIÓN", jPanel3);

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton8.setText("Crear");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton9.setText("Cargar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton10.setText("Eliminar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton9)
                            .addComponent(jButton8))
                        .addGap(18, 18, 18)
                        .addComponent(jButton10))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PATRONES", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1087, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmCrearInvestigador vistaCrearInvestigador = new frmCrearInvestigador(this);
        vistaCrearInvestigador.setLocationRelativeTo(null);
        vistaCrearInvestigador.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        frmActualizarInvestigador vistaActualizarInvestigador = new frmActualizarInvestigador(this);
        vistaActualizarInvestigador.setLocationRelativeTo(null);
        vistaActualizarInvestigador.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        frmEliminarInvestigador vistaEliminarInvestigador = new frmEliminarInvestigador(this);
        vistaEliminarInvestigador.setLocationRelativeTo(null);
        vistaEliminarInvestigador.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        frmCrearMuestra vistaCrearMuestra = new frmCrearMuestra(this);
        vistaCrearMuestra.setLocationRelativeTo(null);
        vistaCrearMuestra.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        JFileChooser fileChooser2 = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser2.setFileFilter(filter);
        int result = fileChooser2.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser2.getSelectedFile();
            cargarCSVMuestras(selectedFile);
        }        
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String codigoInvestigador = jComboBox1.getSelectedItem().toString();
        String codigoMuestra = jComboBox2.getSelectedItem().toString();
        asignarMuestraAInvestigador(codigoInvestigador, codigoMuestra);       
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        frmCrearPatron vistaCrearPatron = new frmCrearPatron(this);
        vistaCrearPatron.setLocationRelativeTo(null);
        vistaCrearPatron.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Seleccionar archivo CSV");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));

    int seleccion = fileChooser.showOpenDialog(this);

    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivoSeleccionado = fileChooser.getSelectedFile();

        // Leer el archivo CSV y guardar los datos en un archivo binario
        List<Patron> patrones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
            String linea;
            // Omitir la primera línea (encabezados)
            br.readLine();
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    // Crear un nuevo objeto Patron y agregarlo a la lista
                    Patron patron = new Patron(datos[0], datos[1], datos[2]);
                    patrones.add(patron);
                } else {
                    JOptionPane.showMessageDialog(this, "El archivo CSV tiene un formato incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Guardar los datos en el archivo binario
            guardarDatosBinario("patron.bin", patrones);
            
            // Mostrar los datos en jTable3
            cargarDatosEnTabla("patron.bin");
            
            JOptionPane.showMessageDialog(this, "Datos cargados correctamente en la tabla.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_jButton9ActionPerformed

    private void guardarDatosBinario(String ruta_archivo, List<Patron> patrones) {
    try (FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
         ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo)) {
        salidaObjeto.writeObject(patrones);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al guardar el archivo binario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

@SuppressWarnings("unchecked")
private void cargarDatosEnTabla(String ruta_archivo) {
    DefaultTableModel modelo = (DefaultTableModel) jTable3.getModel();
    modelo.setRowCount(0);  // Limpiar la tabla antes de agregar los nuevos datos
    
    try (FileInputStream entradaArchivo = new FileInputStream(ruta_archivo);
         ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo)) {
        List<Patron> patrones = (List<Patron>) entradaObjeto.readObject();
        
        for (Patron patron : patrones) {
            modelo.addRow(new Object[]{patron.getCodigo(), patron.getNombre(), patron.getCsvContent()});
        }
    } catch (IOException | ClassNotFoundException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar los datos en la tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        frmEliminarPatron vistaEliminarPatron = new frmEliminarPatron();
        vistaEliminarPatron.setLocationRelativeTo(null);
        vistaEliminarPatron.setVisible(true);        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            cargarCSV(selectedFile);
        }
    }

    private void actualizarTabla(ArrayList<Investigador> investigadores) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpiar la tabla
        for (Investigador investigador : investigadores) {
            model.addRow(new Object[]{investigador.getCodigo(), investigador.getNombre(), investigador.getGenero(), investigador.getExperimento()});
        }
    }

    public void actualizarTablaMuestras(List<Muestra> muestras) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Limpiar la tabla
        for (Muestra muestra : muestras) {
            model.addRow(new Object[]{muestra.getCodigo(), muestra.getDescripcion(), muestra.getEstado(), muestra.getCodigoCSV()});
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMenuAdministracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMenuAdministracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMenuAdministracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMenuAdministracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMenuAdministracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
