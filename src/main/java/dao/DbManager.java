package dao;

import model.Persona;
import model.Rubrica;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbManager {
    private static Properties caricaProprieta() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("credenziali_database.properties")) {
            props.load(input);
        } catch (IOException e) {
            System.err.println("Impossibile leggere credenziali_database.properties: " + e.getMessage());
        }
        return props;
    }

    private static Connection getConnection() throws SQLException {
        Properties props = caricaProprieta();
        String host = props.getProperty("db.host", "localhost");
        String port = props.getProperty("db.port", "3306");
        String dbName = props.getProperty("db.name", "rubrica_db");
        String user = props.getProperty("db.user", "root");
        String pass = props.getProperty("db.password", "");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(url, user, pass);
    }

    // All'avvio carica tutte le persone dal DB
    public static void caricaDaDb(Rubrica rubrica) {
        String query = "SELECT nome, cognome, indirizzo, telefono, eta FROM persone";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String indirizzo = rs.getString("indirizzo");
                String telefono = rs.getString("telefono");
                int eta = rs.getInt("eta");

                Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
                rubrica.aggiungiPersona(p);
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione o lettura dal Database MySQL: " + e.getMessage());
        }
    }

    // Inserisce nuova persona nel DB
    public static void inserisciPersona(Persona p) {
        String query = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setString(3, p.getIndirizzo());
            pstmt.setString(4, p.getTelefono());
            pstmt.setInt(5, p.getEta());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore inserimento persona su DB: " + e.getMessage());
        }
    }

    // Aggiorna persona esistente ripulendo la tabella e riscrivendo i contatti aggiornati
    public static void sincronizzaDb(Rubrica rubrica) {
        String truncateQuery = "TRUNCATE TABLE persone";
        String insertQuery = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Svuota tabella
            stmt.executeUpdate(truncateQuery);

            // Reinserisce tutti i contatti aggiornati presenti nella rubrica in memoria
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                for (Persona p : rubrica.getContatti()) {
                    pstmt.setString(1, p.getNome());
                    pstmt.setString(2, p.getCognome());
                    pstmt.setString(3, p.getIndirizzo());
                    pstmt.setString(4, p.getTelefono());
                    pstmt.setInt(5, p.getEta());
                    pstmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la sincronizzazione del Database: " + e.getMessage());
        }
    }
}