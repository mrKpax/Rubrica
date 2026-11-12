package ui;

import model.Persona;

import javax.swing.*;
import java.awt.*;

public class PersonaEditorDialog extends JDialog {
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtIndirizzo;
    private JTextField txtTelefono;
    private JTextField txtEta;

    private boolean salvato = false;
    private Persona persona;

    public PersonaEditorDialog(Frame parent, String titolo, Persona personaEsistente) {
        super(parent, titolo, true); // Modale rispetto alla finestra principale
        this.persona = personaEsistente;

        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 5, 5));

        // Inizializzazione componenti
        add(new JLabel(" Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel(" Cognome:"));
        txtCognome = new JTextField();
        add(txtCognome);

        add(new JLabel(" Indirizzo:"));
        txtIndirizzo = new JTextField();
        add(txtIndirizzo);

        add(new JLabel(" Telefono:"));
        txtTelefono = new JTextField();
        add(txtTelefono);

        add(new JLabel(" Età:"));
        txtEta = new JTextField();
        add(txtEta);

        JButton btnSalva = new JButton("Salva");
        JButton btnAnnulla = new JButton("Annulla");
        add(btnSalva);
        add(btnAnnulla);

        // Se modifica, recupera valori dei campi
        if (this.persona != null) {
            txtNome.setText(persona.getNome());
            txtCognome.setText(persona.getCognome());
            txtIndirizzo.setText(persona.getIndirizzo());
            txtTelefono.setText(persona.getTelefono());
            txtEta.setText(String.valueOf(persona.getEta()));
        }

        // Listener button Salva
        btnSalva.addActionListener(e -> {
            if (validaCampi()) {
                try {
                    String nome = txtNome.getText().trim();
                    String cognome = txtCognome.getText().trim();
                    String indirizzo = txtIndirizzo.getText().trim();
                    String telefono = txtTelefono.getText().trim();
                    int eta = Integer.parseInt(txtEta.getText().trim());

                    if (this.persona == null) {
                        this.persona = new Persona(nome, cognome, indirizzo, telefono, eta);
                    } else {
                        this.persona.setNome(nome);
                        this.persona.setCognome(cognome);
                        this.persona.setIndirizzo(indirizzo);
                        this.persona.setTelefono(telefono);
                        this.persona.setEta(eta);
                    }

                    salvato = true;
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Il campo 'Età' deve essere un numero intero valido.", "Errore di validazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener button Annulla
        btnAnnulla.addActionListener(e -> {
            salvato = false;
            dispose();
        });
    }

    private boolean validaCampi() {
        if (txtNome.getText().trim().isEmpty() || txtCognome.getText().trim().isEmpty() ||
                txtIndirizzo.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty() ||
                txtEta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi devono essere compilati.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isSalvato() {
        return salvato;
    }

    public Persona getPersona() {
        return persona;
    }
}