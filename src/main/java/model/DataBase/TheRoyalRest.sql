
-- 1. RESET E CREAZIONE DEL DATABASE
DROP DATABASE IF EXISTS TheRoyalRest;
CREATE DATABASE IF NOT EXISTS TheRoyalRest;
USE TheRoyalRest;

-- Tabella: Utente
CREATE TABLE Utente (
    email VARCHAR(255)  PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    dataNascita DATE NOT NULL,
    nazionalita VARCHAR(20) NOT NULL,
    prefisso VARCHAR(10) NOT NULL,        
    cellulare VARCHAR(20) NOT NULL,       
    num_ordinazioni INT DEFAULT 0,
    isAdmin BOOLEAN DEFAULT FALSE
);

-- Tabella: Prodotto
CREATE TABLE Prodotto (
    idProdotto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    stile VARCHAR(10) DEFAULT 'neutro' NOT NULL,
    colore VARCHAR(10) DEFAULT '#ffffff' NOT NULL,
    dimensioni VARCHAR(50) NOT NULL, 
    prezzo FLOAT DEFAULT 0.0 NOT NULL,
    quantita INT DEFAULT 0,               
    descrizione VARCHAR(250) NOT NULL
);



-- Tabella: Ordinazione (Dipende da Utente)
CREATE TABLE Ordinazione (
    idOrdinazione INT AUTO_INCREMENT PRIMARY KEY,
    citta VARCHAR(20) NOT NULL,
    dataOrdinazione DATE NOT NULL,
    importo FLOAT NOT NULL DEFAULT 0,
    indirizzo VARCHAR(20) NOT NULL,
    civico INT NOT NULL,
    cap VARCHAR(5) NOT NULL,
    fk_utente_email VARCHAR(50) NOT NULL,
    FOREIGN KEY (fk_utente_email) REFERENCES Utente(email) ON DELETE RESTRICT
);

-- Tabella: Recensione (Dipende da Prodotto)
CREATE TABLE Recensione (
    idRecensione INT AUTO_INCREMENT PRIMARY KEY,
    data_Recensione DATE NOT NULL,
    Scoring INT DEFAULT 0 NOT NULL CHECK (Scoring >= 0 AND Scoring <= 5),
    descrizione VARCHAR(250) NOT NULL,
    fk_Prodotto_idProdotto INT NOT NULL,
    FOREIGN KEY (fk_Prodotto_idProdotto) REFERENCES Prodotto(idProdotto) ON DELETE CASCADE
);


-- Tabella: Valutazione (Relazione Utente <-> Recensione)
CREATE TABLE Valutazione (
    fk_Valutazione_email VARCHAR(50) NOT NULL,
    fk_Valutazione_idRecensione INT NOT NULL,
    FOREIGN KEY (fk_Valutazione_email) REFERENCES Utente(email) ON DELETE CASCADE,
    FOREIGN KEY (fk_Valutazione_idRecensione) REFERENCES Recensione(idRecensione) ON DELETE CASCADE,
    PRIMARY KEY (fk_Valutazione_email, fk_Valutazione_idRecensione) 
);

-- Tabella: Composizione (Relazione Ordinazione <-> Prodotto)
CREATE TABLE Composizione (
    fk_Composizione_idOrdinazione INT NOT NULL,
    fk_Composizione_idProdotto INT NOT NULL,
    quantita_ordinata INT NOT NULL DEFAULT 1,
    FOREIGN KEY (fk_Composizione_idOrdinazione) REFERENCES Ordinazione(idOrdinazione) ON DELETE CASCADE,
    FOREIGN KEY (fk_Composizione_idProdotto) REFERENCES Prodotto(idProdotto) ON DELETE RESTRICT,
    PRIMARY KEY (fk_Composizione_idOrdinazione, fk_Composizione_idProdotto)
);