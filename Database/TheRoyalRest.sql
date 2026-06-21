
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

CREATE TABLE Prodotto (
    idProdotto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    stile VARCHAR(30) DEFAULT 'neutro' NOT NULL,
    colore VARCHAR(10) DEFAULT '#ffffff' NOT NULL,
    dimensioni VARCHAR(50) NOT NULL, 
    prezzo FLOAT DEFAULT 0.0 NOT NULL,
    quantita INT DEFAULT 0,                
    descrizione VARCHAR(250) NOT NULL,
    immagine VARCHAR(255) NOT NULL 
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
    statoOrdinazione VARCHAR(20) NOT NULL,
    fk_utente_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (fk_utente_email) REFERENCES Utente(email) ON DELETE RESTRICT
);

-- Tabella: Recensione (Dipende da Prodotto)
CREATE TABLE Recensione (
    idRecensione INT AUTO_INCREMENT PRIMARY KEY,
    data_Recensione DATE NOT NULL,
    Scoring INT DEFAULT 0 NOT NULL CHECK (Scoring >= 0 AND Scoring <= 5),
    descrizione VARCHAR(250) NOT NULL,
    fk_Prodotto_idProdotto INT NOT NULL,
    fk_utente_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (fk_Prodotto_idProdotto) REFERENCES Prodotto(idProdotto) ON DELETE CASCADE,
    FOREIGN KEY (fk_utente_email) REFERENCES Utente(email) ON DELETE CASCADE
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

INSERT INTO Prodotto (nome, stile, colore, dimensioni, prezzo, quantita, descrizione, immagine)
VALUES
-- Saint-Tropez (confermato)
('Saint‑Tropez Azure Breeze', 'Saint-Tropez', '#87ceeb', '43x37 cm', 6220.00, 6,
 'Raffreddamento controllato e profumazione marina: l’esperienza balneare più esclusiva dello yacth.',
 'img/prodotti/sainttropez.png'),

-- Kyoto (confermato)
('Kyoto Zen Harmony', 'Kyoto', '#c2b280', '42x38 cm', 4310.00, 8,
 'Minimalismo giapponese e chiusura silenziosa: un rituale di pace che inizia… sedendosi.',
 'img/prodotti/kyoto_zen.png'),

-- St. Moritz (nuova versione riscaldata)
('The Thermal Embrace', 'St. Moritz', '#f8f8ff', '45x40 cm', 7390.00, 4,
 'Tavoletta riscaldata: perché l’unico brivido ammesso è quello dello champagne.',
 'img/prodotti/stmoritz.png'),


('King of The Desert', 'Dubai', '#f8f8ff', '45x40 cm', 7390.00, 4,
 'Il tocco vellutato del deserto ma senza sabbia ',
 'img/prodotti/dubai.jpeg'),

-- Imperial Roma (nuova creazione)
('Imperial Aurea Maxima', 'Roma Imperiale', '#d4af37', '46x40 cm', 7120.00, 5,
 'Una seduta degna di un imperatore: marmo satinato, dettagli dorati e un comfort che fa tremare persino il Senato.',
 'img/prodotti/roma.jpeg');

INSERT INTO Utente (email, password_hash, nome, cognome, dataNascita, nazionalita, prefisso, cellulare, num_ordinazioni, isAdmin)
VALUES
('elon.musk@x.com', sha2('provaprova',256), 'Elon', 'Musk', '1971-06-28', 'Americana', '+1', '5559876543', 15, FALSE),
('donald.trump@usa.gov', sha2('provaprova',256), 'Donald', 'Trump', '1946-06-14', 'Americana', '+1', '5552024024', 20, FALSE),
('jannik.sinner@atp.com', sha2('provaprova',256), 'Jannik', 'Sinner', '2001-08-16', 'Italiana', '+39', '3409988776', 9, FALSE),
('kanye.west@yeezy.com', sha2('provaprova',256), 'Kanye', 'West', '1977-06-08', 'Americana', '+1', '5557770001', 12, FALSE);

INSERT INTO Recensione (data_Recensione, Scoring, descrizione, fk_Prodotto_idProdotto, fk_utente_email)
VALUES
('2026-06-26', 5,
 'È così morbida che sembra fatta di nuvole del deserto. Kanye approved.',
 4, 'kanye.west@yeezy.com'),

('2026-06-27', 5,
 'Dopo una finale ATP, questo è il vero recovery. Altro che crioterapia.',
 4, 'jannik.sinner@atp.com');

INSERT INTO Recensione (data_Recensione, Scoring, descrizione, fk_Prodotto_idProdotto, fk_utente_email)
VALUES
-- Saint‑Tropez Azure Breeze (Prodotto 1)
('2026-06-20', 5,
 'Ho provato toilette in orbita, su jet privati e su razzi. Ma questa… questa è pura aerodinamica francese. Profuma meglio di Marte.',
 1, 'elon.musk@x.com'),
('2026-06-21', 5,
 'Saint‑Tropez è fantastica, tutti lo sanno. Ma questa tavoletta è ancora meglio. Nessuno ha mai visto niente del genere, credimi.',
 1, 'donald.trump@usa.gov'),

-- Kyoto Zen Harmony (Prodotto 2)
('2026-06-22', 5,
 'Dopo un allenamento di 5 ore, sedersi qui è come meditare sul Monte Fuji. Zero rumore, zero stress. Solo zen.',
 2, 'jannik.sinner@atp.com'),
('2026-06-23', 5,
 'È così minimalista che potrei metterla in un museo. Anzi, forse lo farò. L’arte non ha limiti, nemmeno in bagno.',
 2, 'kanye.west@yeezy.com'),

-- The Thermal Embrace (Prodotto 3)
('2026-06-24', 5,
 'Il riscaldamento è così potente che potrei usarlo per scongelare un razzo. Perfetto per i miei chalet in mezzo al nulla.',
 3, 'elon.musk@x.com'),
('2026-06-25', 5,
 'St. Moritz è lusso. Io sono lusso. Questa tavoletta è lusso. Tutto torna.',
 3, 'donald.trump@usa.gov'),

-- King of the Desert (Prodotto 4)
('2026-06-26', 5,
 'È così morbida che sembra fatta di nuvole del deserto. Kanye approved.',
 4, 'kanye.west@yeezy.com'),
('2026-06-27', 5,
 'Dopo una finale ATP, questo è il vero recovery. Altro che crioterapia.',
 4, 'jannik.sinner@atp.com'),

-- Imperial Aurea Maxima (Prodotto 5)
('2026-06-28', 5,
 'Sembra un trono romano. E io sui troni mi sento a casa. Fantastica.',
 5, 'donald.trump@usa.gov'),
('2026-06-29', 5,
 'Il marmo dorato riflette la mia aura. È praticamente un’opera d’arte collaborativa: io + Roma.',
 5, 'kanye.west@yeezy.com');


