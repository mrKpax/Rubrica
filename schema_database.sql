-- Creazione database
CREATE DATABASE IF NOT EXISTS rubrica_db;
USE rubrica_db;

-- Tabella per gestione persone
CREATE TABLE IF NOT EXISTS persone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    indirizzo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    eta INT NOT NULL
    );

-- Tabella per gestione degli utenti
CREATE TABLE IF NOT EXISTS utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

-- Inserimento utente di default per login (admin / password123)
INSERT INTO utenti (username, password)
VALUES ('admin', 'password123')
ON DUPLICATE KEY UPDATE username=username;

-- Inserimento di contatti preesistenti (Seed data per facilitare i test del recruiter)
INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES
('Steve', 'Jobs', 'via Cupertino 13', '0612344', 56),
('Bill', 'Gates', 'via Redmond 10', '06688989', 60),
('Babbo', 'Natale', 'via del Polo Nord', '00000111', 99)
ON DUPLICATE KEY UPDATE id=id;