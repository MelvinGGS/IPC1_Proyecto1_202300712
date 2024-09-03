package INTERFACES;

import ARCHIVOS.ManejadorArchivoBinarioInvestigador;
import ARCHIVOS.ManejadorArchivoBinarioPatron;
import ARCHIVOS.ManejadorArchivoBinarioResultado;
import MODELS.Resultado;
import MODELS.Asignacion;
import MODELS.Patron;
import MODELS.Muestra;

import java.util.ArrayList;

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
        cargarResultados();
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

    private void cargarResultados() {
        ManejadorArchivoBinarioResultado manejadorArchivo = new ManejadorArchivoBinarioResultado();
        ArrayList<Resultado> resultados = manejadorArchivo.obtenerResultados("resultados.bin");

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        for (Resultado resultado : resultados) {
            model.addRow(new Object[]{
                resultado.getNumero(),
                resultado.getCodigoMuestra(),
                resultado.getCodigoPatron(),
                resultado.getFecha(),
                resultado.getHora(),
                resultado.getResultado()
            });
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18));

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
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "No.", "Muestra", "Patrón", "Fecha", "Hora", "Resultado", "Acciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
    
                // Calcular el tamaño de la matriz cuadrada
                int muestraLength = muestraDatos.length;
                int patronLength = patronDatos.length;
    
                // Determinar las dimensiones de las matrices
                int muestraSize = (int) Math.sqrt(muestraLength);
                int patronSize = (int) Math.sqrt(patronLength);
    
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
    
                // Multiplicar matrizM por 3 y guardar en matriz1
                int[][] matriz1 = new int[muestraSize][muestraSize];
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matriz1[i][j] = matrizM[i][j] * 3;
                    }
                }
    
                // Multiplicar matriz1 por 7 y guardar en matriz2
                int[][] matriz2 = new int[muestraSize][muestraSize];
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matriz2[i][j] = matriz1[i][j] * 7;
                    }
                }
    
                // Multiplicar matriz2 por matriz1 y guardar en matriz3
                int[][] matriz3 = new int[muestraSize][muestraSize];
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matriz3[i][j] = 0;
                        for (int k = 0; k < muestraSize; k++) {
                            matriz3[i][j] += matriz2[i][k] * matriz1[k][j];
                        }
                    }
                }
    
                // Realizar la división modular de 2 en matriz3 y guardar en matrizM2
                int[][] matrizM2 = new int[muestraSize][muestraSize];
                for (int i = 0; i < muestraSize; i++) {
                    for (int j = 0; j < muestraSize; j++) {
                        matrizM2[i][j] = matriz3[i][j] % 2;
                    }
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
    
                // Obtener fecha y hora del sistema
                java.util.Date date = new java.util.Date();
                java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.text.SimpleDateFormat sdfTime = new java.text.SimpleDateFormat("HH:mm:ss");
                String fecha = sdfDate.format(date);
                String hora = sdfTime.format(date);
    
                // Mostrar el resultado en jLabel5
                String resultado;
                if (sonIguales) {
                    resultado = "EXITO";
                    jLabel5.setText("Los resultados coinciden con " + muestraSeleccionada.getCodigo());
                    jLabel5.setForeground(new java.awt.Color(0, 128, 0)); // Verde
                } else {
                    resultado = "FALLO";
                    jLabel5.setText("Los resultados no coinciden con " + muestraSeleccionada.getCodigo());
                    jLabel5.setForeground(new java.awt.Color(255, 0, 0)); // Rojo
                }
    
                // Mostrar los datos en la tabla
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                int rowCount = model.getRowCount();
                model.addRow(new Object[]{rowCount + 1, muestraSeleccionada.getCodigo(), patronSeleccionado.getCodigo(), fecha, hora, resultado});
    
                // Crear un objeto Resultado y guardarlo en el archivo binario
                Resultado nuevoResultado = new Resultado(rowCount + 1, muestraSeleccionada.getCodigo(), patronSeleccionado.getCodigo(), resultado);
                ManejadorArchivoBinarioResultado manejadorArchivo = new ManejadorArchivoBinarioResultado();
                manejadorArchivo.agregarResultado("resultados.bin", nuevoResultado);
    
            } catch (NumberFormatException e) {
                jLabel5.setText("Error en los datos de entrada. Asegúrate de que los datos estén correctamente formateados.");
                e.printStackTrace(); // Imprimir la traza de la pila para depuración
            }
        } else {
            jLabel5.setText("No se encontró la muestra o el patrón seleccionados.");
        }
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