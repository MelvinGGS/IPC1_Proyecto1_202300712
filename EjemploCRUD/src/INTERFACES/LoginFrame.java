package INTERFACES;

import ARCHIVOS.ManejadorArchivoBinarioInvestigador;
import MODELS.Investigador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class LoginFrame extends JFrame {
    private JTextField codigoField;
    private JPasswordField passwordField;
    private ArrayList<Investigador> investigadores;
    private HashSet<String> codigosExistentes;

    public LoginFrame() {
        // Configuración del frame
        setTitle("L O G I N");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Leer investigadores del archivo binario
        ManejadorArchivoBinarioInvestigador manejadorArchivo = new ManejadorArchivoBinarioInvestigador();
        investigadores = manejadorArchivo.obtenerContenido("investigador.bin");
        manejadorArchivo.generarGraficaTop3("investigador.bin");
        codigosExistentes = new HashSet<>();
        for (Investigador inv : investigadores) {
            codigosExistentes.add(inv.getCodigo());
        }

        // Crear componentes
        JLabel titleLabel = new JLabel("IPC QUIMIK");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel codeLabel = new JLabel("Código:");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        codigoField = new JTextField(15);
        codigoField.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));

        // Configurar layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(codeLabel, gbc);

        gbc.gridx = 1;
        add(codigoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Acción del botón de login
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String code = codigoField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (code.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Ambos campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (code.equals("admin") && password.equals("admin")) {
                    new frmMenuAdministracion().setVisible(true);
                    dispose();
                } else if (validarCredenciales(code, password)) {
                    new frmMenuUsuario().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Código o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Hacer visible el frame
        setVisible(true);
    }

    private boolean validarCredenciales(String codigo, String contraseña) {
        for (Investigador investigador : investigadores) {
            if (investigador.getCodigo().equals(codigo) && investigador.getContrasenia().equals(contraseña)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}