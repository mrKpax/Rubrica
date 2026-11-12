package ui;

import dao.PersistenzaManager;
import model.Persona;
import model.Rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private Rubrica rubrica;
    private JTable table;
    private DefaultTableModel tableModel;

    public MainFrame() {
        super("Rubrica Telefonica");
        this.rubrica = new Rubrica();

        // Caricamento iniziale da file
        PersistenzaManager.caricaDaFile(rubrica);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Tabella con nome, cognome, telefono
        String[] colonne = {"Nome", "Cognome", "Telefono"};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Blocca modifica diretta da tabella
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button inferiori
        JPanel panelBottoni = new JPanel();
        JButton btnNuovo = new JButton("Nuovo");
        JButton btnModifica = new JButton("Modifica");
        JButton btnElimina = new JButton("Elimina");

        panelBottoni.add(btnNuovo);
        panelBottoni.add(btnModifica);
        panelBottoni.add(btnElimina);
        add(panelBottoni, BorderLayout.SOUTH);

        // Azione button Nuovo
        btnNuovo.addActionListener(e -> {
            PersonaEditorDialog dialog = new PersonaEditorDialog(this, "Nuova Persona", null);
            dialog.setVisible(true);
            if (dialog.isSalvato()) {
                rubrica.aggiungiPersona(dialog.getPersona());
                PersistenzaManager.salvaSuFile(rubrica); // Salvataggio automatico
                aggiornaTabella();
            }
        });

        // Azione button Modifica
        btnModifica.addActionListener(e -> {
            int rigaSelezionata = table.getSelectedRow();
            if (rigaSelezionata == -1) {
                JOptionPane.showMessageDialog(this, "Per modificare è necessario prima selezionare una persona.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Persona p = rubrica.getContatti().get(rigaSelezionata);
            PersonaEditorDialog dialog = new PersonaEditorDialog(this, "Modifica Persona", p);
            dialog.setVisible(true);
            if (dialog.isSalvato()) {
                rubrica.aggiornaPersona(rigaSelezionata, dialog.getPersona());
                PersistenzaManager.salvaSuFile(rubrica); // Salvataggio automatico
                aggiornaTabella();
            }
        });

        // Azione button Elimina
        btnElimina.addActionListener(e -> {
            int rigaSelezionata = table.getSelectedRow();
            if (rigaSelezionata == -1) {
                JOptionPane.showMessageDialog(this, "Per eliminare è necessario prima selezionare una persona.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Persona p = rubrica.getContatti().get(rigaSelezionata);
            int conferma = JOptionPane.showConfirmDialog(
                    this,
                    "Eliminare la persona " + p.getNome() + " " + p.getCognome() + "?",
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION
            );

            if (conferma == JOptionPane.YES_OPTION) {
                rubrica.rimuoviPersona(rigaSelezionata);
                PersistenzaManager.salvaSuFile(rubrica); // Salvataggio automatico
                aggiornaTabella();
            }
        });

        // Popolamento iniziale tabella con dati da file
        aggiornaTabella();
    }

    public void aggiornaTabella() {
        tableModel.setRowCount(0); // Pulizia tabella
        for (Persona p : rubrica.getContatti()) {
            Object[] riga = {p.getNome(), p.getCognome(), p.getTelefono()};
            tableModel.addRow(riga);
        }
    }

    public Rubrica getRubrica() {
        return rubrica;
    }
}