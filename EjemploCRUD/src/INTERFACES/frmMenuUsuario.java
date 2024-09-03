    package INTERFACES;

    import ARCHIVOS.ManejadorArchivoBinarioInvestigador;
    import ARCHIVOS.ManejadorArchivoBinarioPatron;
    import ARCHIVOS.ManejadorArchivoBinarioResultado;
    import MODELS.Resultado;
    import MODELS.Asignacion;
    import MODELS.Patron;
    import MODELS.Muestra;

    import javax.swing.JButton;
    import javax.swing.table.TableCellRenderer;
    import javax.swing.table.TableCellEditor;
    import java.awt.Component;
    import javax.swing.AbstractCellEditor;
    import javax.swing.JTable;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.awt.Desktop;
    import java.io.File;
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
                    resultado.getResultado(),
                    "Generar HTML"
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
                    false, false, false, false, false, false, true
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jTable2.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
            jTable2.getColumn("Acciones").setCellEditor(new ButtonEditor(new JButton()));

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
            );

            jTabbedPane1.addTab("RESULTADOS", jPanel2);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1)
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
        
                    // Llamar a generarArchivoHTML con las matrices obtenidas
                    generarArchivoHTML(codigoMuestra, codigoPatron, matrizM, matriz1, matriz2, matriz3, matrizM2, matrizP);
        
                } catch (NumberFormatException e) {
                    jLabel5.setText("Error en los datos de entrada. Asegúrate de que los datos estén correctamente formateados.");
                    e.printStackTrace(); // Imprimir la traza de la pila para depuración
                }
            } else {
                jLabel5.setText("No se encontró la muestra o el patrón seleccionados.");
            }
        }

        public void generarArchivoHTML(String codigoMuestra, String codigoPatron, int[][] matrizM, int[][] matriz1, int[][] matriz2, int[][] matriz3, int[][] matrizM2, int[][] matrizP) {
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body>");
            htmlContent.append("<h1>ALGORITMO PARA ANÁLISIS DE EXPERIMENTOS</h1>");
            htmlContent.append("<h2>Código Muestra: ").append(codigoMuestra).append("</h2>");
            htmlContent.append("<h2>Código Patrón: ").append(codigoPatron).append("</h2>");
        
            // Mostrar los valores de las matrices en el HTML
            htmlContent.append("<h3>Matriz Muestra:</h3>");
            htmlContent.append("<table border='1'>");
            for (int[] row : matrizM) {
                htmlContent.append("<tr>");
                for (int cell : row) {
                    htmlContent.append("<td>").append(cell).append("</td>");
                }
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
        
            htmlContent.append("<h3>Matriz 1, Muestra multiplicada por 3:</h3>");
            htmlContent.append("<table border='1'>");
            for (int[] row : matriz1) {
                htmlContent.append("<tr>");
                for (int cell : row) {
                    htmlContent.append("<td>").append(cell).append("</td>");
                }
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
        
            htmlContent.append("<h3>Matriz 2, Matriz 1 multiplicada por 7:</h3>");
            htmlContent.append("<table border='1'>");
            for (int[] row : matriz2) {
                htmlContent.append("<tr>");
                for (int cell : row) {
                    htmlContent.append("<td>").append(cell).append("</td>");
                }
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
        
            htmlContent.append("<h3>Matriz 3, multiplicacion de Matriz 1 por Matriz 2:</h3>");
            htmlContent.append("<table border='1'>");
            for (int[] row : matriz3) {
                htmlContent.append("<tr>");
                for (int cell : row) {
                    htmlContent.append("<td>").append(cell).append("</td>");
                }
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
        
            htmlContent.append("<h3>Matriz Modulo 2, division modular de la matriz 3 entre 2:</h3>");
            htmlContent.append("<table border='1'>");
            for (int[] row : matrizM2) {
                htmlContent.append("<tr>");
                for (int cell : row) {
                    htmlContent.append("<td>").append(cell).append("</td>");
                }
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
        
            htmlContent.append("<h3>Matriz Patron:</h3>");
            htmlContent.append("<table border='1'>");
            for (int[] row : matrizP) {
                htmlContent.append("<tr>");
                for (int cell : row) {
                    htmlContent.append("<td>").append(cell).append("</td>");
                }
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
        
            htmlContent.append("</body></html>");
        
            // Obtener fecha y hora del sistema para el nombre del archivo
            java.util.Date date = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("ddMMyyyy-HHmmss");
            String fechaHora = sdf.format(date);
            String nombreArchivo = "Analisis" + fechaHora + "_" + codigoMuestra + "_" + codigoPatron + ".html";
        
            // Guardar el archivo HTML y abrirlo
            try (FileWriter fileWriter = new FileWriter(nombreArchivo)) {
                fileWriter.write(htmlContent.toString());
                fileWriter.flush();
                Desktop.getDesktop().open(new File(nombreArchivo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        

        public class ButtonRenderer extends JButton implements TableCellRenderer {
            public ButtonRenderer() {
                setOpaque(true);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                setText((value == null) ? "" : value.toString());
                return this;
            }
        }

        public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
            private JButton button;
            private String codigoMuestra;
            private String codigoPatron;
        
            public ButtonEditor(JButton button) {
                this.button = button;
                button.addActionListener(this);
            }
        
            @Override
            public Object getCellEditorValue() {
                return new String();
            }
        
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                this.codigoMuestra = (String) table.getValueAt(row, 1);
                this.codigoPatron = (String) table.getValueAt(row, 2);
                button.setText("Generar HTML");
                return button;
            }
        
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos reales de la muestra y el patrón
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
                    String[] muestraDatos = muestraSeleccionada.getCodigoCSV().split("[,;\\r\\n]+");
                    String[] patronDatos = patronSeleccionado.getCsvContent().split("[,;\\r\\n]+");
            
                    int muestraSize = (int) Math.sqrt(muestraDatos.length);
                    int patronSize = (int) Math.sqrt(patronDatos.length);
            
                    int[][] matrizM = new int[muestraSize][muestraSize];
                    int[][] matrizP = new int[patronSize][patronSize];
            
                    for (int i = 0; i < muestraSize; i++) {
                        for (int j = 0; j < muestraSize; j++) {
                            matrizM[i][j] = Integer.parseInt(muestraDatos[i * muestraSize + j]);
                        }
                    }
            
                    for (int i = 0; i < patronSize; i++) {
                        for (int j = 0; j < patronSize; j++) {
                            matrizP[i][j] = Integer.parseInt(patronDatos[i * patronSize + j]);
                        }
                    }
            
                    // Llamar a generarArchivoHTML con las matrices obtenidas
                    generarArchivoHTML(codigoMuestra, codigoPatron, matrizM, matrizP, matrizM, matrizM, matrizM, matrizP);
                }
            
                fireEditingStopped();
            }
        }

        // Variables declaration - do not modify
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
        // End of variables declaration
    }
