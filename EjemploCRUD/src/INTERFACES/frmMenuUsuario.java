package INTERFACES;

import ARCHIVOS.ManejadorArchivoBinarioInvestigador;
import ARCHIVOS.ManejadorArchivoBinarioPatron;
import MODELS.Asignacion;
import MODELS.Patron;
import MODELS.Muestra;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

public class frmMenuUsuario extends javax.swing.JFrame {

    private String codigoInvestigador;
    private ArrayList<Muestra> muestras;
    private ArrayList<Patron> patrones;

    public frmMenuUsuario(String codigoInvestigador) {
        this.codigoInvestigador = codigoInvestigador;
        initComponents();
        setLocationRelativeTo(null);
        cargarDatosInvestigador();
    }

    private void cargarDatosInvestigador() {
        jLabelinicios.setText("Investigador: " + codigoInvestigador);
        cargarMuestrasAsignadas();
        cargarPatronesDisponibles();
    }

    private void cargarMuestrasAsignadas() {
        ManejadorArchivoBinarioInvestigador manejador = new ManejadorArchivoBinarioInvestigador();
        ArrayList<Asignacion> asignaciones = manejador.obtenerAsignaciones("asignaciones.bin");
        muestras = manejador.obtenerMuestras("muestras.bin");
        jComboBox2.removeAllItems();
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getCodigoInvestigador().equals(codigoInvestigador)) {
                jComboBox2.addItem(asignacion.getCodigoMuestra());
            }
        }
    }

    private void cargarPatronesDisponibles() {
        ManejadorArchivoBinarioPatron manejador = new ManejadorArchivoBinarioPatron();
        patrones = manejador.obtenerPatrones("patron.bin");
        jComboBox1.removeAllItems();
        for (Patron patron : patrones) {
            jComboBox1.addItem(patron.getCodigo());
        }
    }

    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelinicios = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel2.setText("Patrón a analizar");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel3.setText("Muestra");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 18));

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 18));
        jButton7.setText("Analizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel4.setText("RESULTADO");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24));

        jLabelinicios.setFont(new java.awt.Font("Segoe UI", 0, 18));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabelinicios))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(177, 177, 177))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabelinicios)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel2)
                        .addGap(145, 145, 145)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(72, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jButton7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("ANALISIS", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "No.", "Muestra", "Patrón", "Datos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
            jTable2.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("RESULTADOS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 835, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }


    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        // Obtener los valores seleccionados de los JComboBox
        String codigoMuestra = (String) jComboBox2.getSelectedItem();
        String codigoPatron = (String) jComboBox1.getSelectedItem();
    
        // Buscar la muestra y el patrón correspondientes
        Muestra muestraSeleccionada = null;
        Patron patronSeleccionado = null;
    
        for (Muestra muestra : muestras) {
            if (muestra.getCodigo().equals(codigoMuestra)) {
                muestraSeleccionada = muestra;
                break;
            }
        }
    
        for (Patron patron : patrones) {
            if (patron.getCodigo().equals(codigoPatron)) {
                patronSeleccionado = patron;
                break;
            }
        }
    
        if (muestraSeleccionada != null && patronSeleccionado != null) {
            try {
                // Obtener los datos CSV
                String muestraCSV = muestraSeleccionada.getCodigoCSV();
                String patronCSV = patronSeleccionado.getCsvContent();
    
                // Limpiar y dividir los datos en función del delimitador y saltos de línea
                String[] muestraDatos = muestraCSV.split("[,;\\r\\n]+");
                String[] patronDatos = patronCSV.split("[,;\\r\\n]+");
    
                // Imprimir los datos para depuración
                System.out.println("Datos de Muestra: " + Arrays.toString(muestraDatos));
                System.out.println("Datos de Patrón: " + Arrays.toString(patronDatos));
    
                // Calcular el tamaño de la matriz cuadrada
                int muestraLength = muestraDatos.length;
                int patronLength = patronDatos.length;
    
                // Determinar las dimensiones de las matrices
                int muestraSize = (int) Math.sqrt(muestraLength);
                int patronSize = (int) Math.sqrt(patronLength);
    
                // Imprimir tamaños calculados
                System.out.println("Tamaño de la matriz Muestra: " + muestraSize);
                System.out.println("Tamaño de la matriz Patrón: " + patronSize);
    
                // Crear las matrices
                int[][] matrizM = new int[muestraSize][muestraSize];
                int[][] matrizP = new int[patronSize][patronSize];
    
                // Llenar la matrizM
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        int index = i * muestraSize + j;
                        matrizM[i][j] = Integer.parseInt(muestraDatos[index]);
                    }
                }
    
                // Llenar la matrizP
                for (int i = 0; i < patronSize; i++) {
                    for (int j = 0; j < patronSize; j++) {
                        int index = i * patronSize + j;
                        matrizP[i][j] = Integer.parseInt(patronDatos[index]);
                    }
                }
    
                // Imprimir las matrices para depuración
                System.out.println("Matriz M:");
                for (int[] row : matrizM) {
                    System.out.println(Arrays.toString(row));
                }
    
                System.out.println("Matriz P:");
                for (int[] row : matrizP) {
                    System.out.println(Arrays.toString(row));
                }
    
                // Multiplicar matrizM por 3 y guardar en matriz1
                int[][] matriz1 = new int[muestraSize][muestraSize];
                System.out.println("Multiplicación de matrizM por 3:");
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matriz1[i][j] = matrizM[i][j] * 3;
                        System.out.print(matriz1[i][j] + " ");
                    }
                    System.out.println();
                }
    
                // Multiplicar matriz1 por 7 y guardar en matriz2
                int[][] matriz2 = new int[muestraSize][muestraSize];
                System.out.println("Multiplicación de matriz1 por 7:");
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matriz2[i][j] = matrizM[i][j] * 7;
                        System.out.print(matriz2[i][j] + " ");
                    }
                    System.out.println();
                }
    
                // Multiplicar matriz2 por matriz1 y guardar en matriz3
                int[][] matriz3 = new int[muestraSize][muestraSize];
                System.out.println("Multiplicación de matriz2 por matriz1:");
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matriz3[i][j] = 0;
                        for (int k = 0; k < muestraSize; k++) {
                            matriz3[i][j] += matriz2[i][k] * matriz1[k][j];
                        }
                        System.out.print(matriz3[i][j] + " ");
                    }
                    System.out.println();
                }
    
                // Realizar la división modular de 2 en matriz3 y guardar en matrizM2
                int[][] matrizM2 = new int[muestraSize][muestraSize];
                System.out.println("División modular de 2 en matriz3:");
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matrizM2[i][j] = matriz3[i][j] % 2;
                        System.out.print(matrizM2[i][j] + " ");
                    }
                    System.out.println();
                }
    
                // Imprimir matrizM2 para depuración
                System.out.println("Matriz M2:");
                for (int[] row : matrizM2) {
                    System.out.println(Arrays.toString(row));
                }
    
                // Comparar matrizM2 con matrizP
                boolean sonIguales = true;
                for (int i = 0; i < Math.min(muestraSize, patronSize); i++) {
                    for (int j = 0; j < Math.min(muestraSize, patronSize); j++) {
                        if (matrizM2[i][j] != matrizP[i][j]) {
                            sonIguales = false;
                            break;
                        }
                    }
                    if (!sonIguales) {
                        break;
                    }
                }
    
                // Mostrar el resultado en jLabel5
                if (sonIguales) {
                    System.out.println("Resultado: EXITO");
                    jLabel5.setText("EXITO");
                } else {
                    System.out.println("Resultado: FALLO");
                    jLabel5.setText("FALLO");
                }
    
                // Mostrar los datos en la tabla
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                model.setRowCount(0); // Limpiar la tabla
    
                // Agregar datos de la muestra
                for (int i = 0; i < muestraSize; i++) {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < muestraSize; j++) {
                        row.append(muestraDatos[i * muestraSize + j]);
                        if (j < muestraSize - 1) {
                            row.append(",");
                        }
                    }
                    model.addRow(new Object[]{i + 1, muestraSeleccionada.getCodigo(), "", row.toString()});
                }
    
                // Agregar datos del patrón
                for (int i = 0; i < patronSize; i++) {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < patronSize; j++) {
                        row.append(patronDatos[i * patronSize + j]);
                        if (j < patronSize - 1) {
                            row.append(",");
                        }
                    }
                    model.addRow(new Object[]{i + 1 + muestraSize, "", patronSeleccionado.getCodigo(), row.toString()});
                }
            } catch (NumberFormatException e) {
                jLabel5.setText("Error en los datos de entrada. Asegúrate de que los datos estén correctamente formateados.");
                e.printStackTrace(); // Imprimir la traza de la pila para depuración
            }
        } else {
            jLabel5.setText("No se encontró la muestra o el patrón seleccionados.");
        }
    }
    
    
    
    
    


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMenuUsuario("INV123").setVisible(true);
            }
        });
    }

    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelinicios;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
}
