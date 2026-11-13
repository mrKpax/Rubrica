# Rubrica - Progettino per Turing

Applicazione desktop Java Swing sviluppata come prova pratica per il percorso di selezione Turing. Il software gestisce una rubrica di contatti telefonici con autenticazione utente e persistenza dei dati su database relazionale MySQL.

---

## Funzionalità e Requisiti Realizzati

* **Architettura a Layer:** Separazione tra Modello di Dominio (`model`), Interfaccia Grafica Swing (`ui`) e Layer di Accesso ai Dati (`dao`).
* **Autenticazione:** Finestra di login iniziale con verifica delle credenziali tramite la classe `Utente` prima di sbloccare l'applicazione.
* **Interfaccia Avanzata:** Finestra principale dotata di barra degli strumenti (`JToolBar`) e tabella (`JTable`) per la visualizzazione e la gestione dei contatti.
* **Persistenza su Database MySQL:** Connessione JDBC nativa per il salvataggio, la modifica e la rimozione dei contatti in tempo reale.
* **Configurazione Esterna:** Parametrizzazione della connessione al database tramite il file esterno `credenziali_database.properties`.
* **Versionamento:** Codice sorgente tracciato e pubblicato su repository GitHub.

---

## Contenuto del Pacchetto di Consegna

L'archivio del progetto rilasciato (`Rubrica.zip`) contiene:
1. **`Rubrica.jar`** - File eseguibile dell'applicazione.
2. **`schema_database.sql`** - Script DDL per la creazione del database, delle tabelle e del popolamento iniziale.
3. **`credenziali_database.properties`** - File di configurazione per i parametri di connessione al database MySQL.

---

## Guida all'Installazione

1. **Configurazione del Database MySQL:**
   * Assicurarsi di avere un server MySQL attivo sulla propria macchina.
   * Eseguire lo script SQL fornito per creare lo schema e popolare i dati di test[cite: 5]:
     ```bash
     mysql -u root -p < schema_database.sql
     ```

2. **Configurazione delle Credenziali:**
   * Aprire il file `credenziali_database.properties` ed inserire i parametri corretti del proprio server MySQL locale (username, password, porta e host):
     ```properties
     db.host=localhost
     db.port=3306
     db.name=rubrica_db
     db.user=tuo_utente_mysql
     db.password=tua_password_mysql
     ```

3. **Esecuzione del Software:**
   * Eseguire il file JAR tramite doppio click o da terminale:
   ```bash
   java -jar Rubrica.jar
   ```
   * Effettuare il login con le credenziali di default (`admin` / `password123`) per accedere alla rubrica.