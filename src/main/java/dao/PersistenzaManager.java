// Vecchio DAO per persistenza su file di testo
// Attualmente inutilizzato in favore di DbManager

package dao;

import model.Persona;
import model.Rubrica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class PersistenzaManager {
    private static final String NOME_FILE = "informazioni.txt";

    // All'avvio carica i dati dal file
    public static void caricaDaFile(Rubrica rubrica) {
        File file = new File(NOME_FILE);
        if (!file.exists()) {
            // Se il file non esiste, non fa nulla
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                if (!linea.isEmpty()) {
                    String[] parti = linea.split(";");
                    if (parti.length == 5) {
                        try {
                            String nome = parti[0];
                            String cognome = parti[1];
                            String indirizzo = parti[2];
                            String telefono = parti[3];
                            int eta = Integer.parseInt(parti[4].trim());

                            Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
                            rubrica.aggiungiPersona(p);
                        } catch (NumberFormatException e) {
                            System.err.println("Errore di parsing dell'età nel file: " + parti[4]);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File informazioni.txt non trovato: " + e.getMessage());
        }
    }

    // Salva la lista di persone sul file
    public static void salvaSuFile(Rubrica rubrica) {
        File file = new File(NOME_FILE);
        try (PrintStream ps = new PrintStream(file)) {
            for (Persona p : rubrica.getContatti()) {
                // Sfrutta il metodo toString() sovrascritto
                ps.println(p.toString());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Impossibile scrivere sul file informazioni.txt: " + e.getMessage());
        }
    }
}