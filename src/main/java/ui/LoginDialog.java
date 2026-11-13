package ui;

import model.Utente;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private boolean loginRiuscito = false;

    // Utente predefinito per il test
    private Utente utenteRegistrato = new Utente("admin", "password123");

    public LoginDialog(Frame parent) {
        super(parent, "Login Rubrica", true);
        setSize(300, 180);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 8, 8));

        add(new JLabel(" Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel(" Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("LOGIN");
        JButton btnAnnulla = new JButton("Annulla");

        add(btnLogin);
        add(btnAnnulla);

        // Azione Login
        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();

            if (utenteRegistrato.verificaCredenziali(user, pass)) {
                loginRiuscito = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Login errato! Credenziali non valide.",
                        "Errore di autenticazione",
                        JOptionPane.ERROR_MESSAGE
                );
                txtPassword.setText("");
            }
        });

        // Azione Annulla (chiude l'applicazione)
        btnAnnulla.addActionListener(e -> {
            System.exit(0);
        });
    }

    public boolean isLoginRiuscito() {
        return loginRiuscito;
    }
}