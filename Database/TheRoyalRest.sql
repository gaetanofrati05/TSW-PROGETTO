
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
    stile VARCHAR(10) DEFAULT 'neutro' NOT NULL,
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
 'img/prodotti/sainttropez_azure.jpg'),

-- Kyoto (confermato)
('Kyoto Zen Harmony', 'Kyoto', '#c2b280', '42x38 cm', 4310.00, 8,
 'Minimalismo giapponese e chiusura silenziosa: un rituale di pace che inizia… sedendosi.',
 'img/prodotti/kyoto_zen.jpg'),

-- St. Moritz (nuova versione riscaldata)
('The Thermal Embrace', 'St. Moritz', '#f8f8ff', '45x40 cm', 7390.00, 4,
 'Perché l’unico brivido ammesso è quello dello champagne.',
 'img/prodotti/stmoritz_warmth.jpg'),


('king of the desert', 'Dubai', '#f8f8ff', '45x40 cm', 7390.00, 4,
 'il soffice tocco del deserto ma senza sabbia ',
 'img/prodotti/stmoritz_warmth.jpg'),

-- Imperial Roma (nuova creazione)
('Imperial Aurea Maxima', 'Roma Imperiale', '#d4af37', '46x40 cm', 7120.00, 5,
 'Una seduta degna di un imperatore: marmo satinato, dettagli dorati e un comfort che fa tremare persino il Senato.',
 'img/prodotti/imperial_aurea.jpg');
 -- 1. INSERIMENTO DI 10 UTENTI DI LUSSO (CON PASSWORD SEMPLICI)
INSERT INTO Utente (email, password_hash, nome, cognome, dataNascita, nazionalita, prefisso, cellulare, num_ordinazioni, isAdmin)
VALUES
('conte.vittorio@luxe.it', 'pass123', 'Vittorio', 'Sforza', '1965-03-14', 'Italiana', '+39', '3351122334', 4, FALSE),
('lady.alexandra@royal.uk', 'secret456', 'Alexandra', 'Windsor', '1982-07-21', 'Inglese', '+44', '7712345678', 6, FALSE),
('sheikh.mansoor@dubai.ae', 'dubai789', 'Mansoor', 'Al-Maktoum', '1990-11-05', 'Emira', '+971', '501234567', 12, FALSE),
('monaco.charlotte@royal.mc', 'monaco2026', 'Charlotte', 'Grimaldi', '1988-08-03', 'Monegasca', '+377', '607123456', 3, FALSE),
('arch.fujimoto@tokyo.jp', 'zenpass', 'Kenzo', 'Fujimoto', '1974-05-19', 'Giapponese', '+81', '9012345678', 5, FALSE),
('baronessa.elena@vienna.at', 'elena99', 'Elena', 'Von Bismarck', '1971-12-01', 'Austriaca', '+43', '6641234567', 2, FALSE),
('oligarh.igor@moscow.ru', 'igorvip', 'Igor', 'Ivanov', '1979-02-25', 'Russa', '+7', '9161234567', 8, FALSE),
('marta.fashion@milano.it', 'marta20', 'Marta', 'Donati', '1995-04-10', 'Italiana', '+39', '3487654321', 7, FALSE),
('ceo.elon@silicon.com', 'tesla123', 'Elon', 'Marshall', '1985-09-30', 'Americana', '+1', '5551234567', 10, FALSE),
('dott.ramazzotti@clinica.ch', 'salute77', 'Giovanni', 'Ramazzotti', '1960-01-15', 'Svizzera', '+41', '791234567', 4, FALSE);


-- 2. INSERIMENTO DELLE RECENSIONI DI LUSSO IN DATE E SCORING DIVERSI
-- (Assumendo che gli ID dei tuoi prodotti vadano da 1 a 5)

INSERT INTO Recensione (data_Recensione, Scoring, descrizione, fk_Prodotto_idProdotto, fk_utente_email)
VALUES
-- Prodotto 1: Saint‑Tropez Azure Breeze
('2026-01-15', 5, 'La profumazione marina è sublime, sembra di essere costantemente ormeggiati a Pampelonne. Un capolavoro per il mio yacht.', 1, 'conte.vittorio@luxe.it'),
('2026-02-20', 4, 'Ottimo sistema idrico, l''azzurro si sposa divinamente con i marmi della mia cabina armatoriale. Unica nota, la spedizione a Monaco ha tardato un giorno.', 1, 'monaco.charlotte@royal.mc'),

-- Prodotto 2: Kyoto Zen Harmony
('2026-03-05', 5, 'La chiusura silenziosa è una poesia geometrica. Il minimalismo che cercavo per la mia dépendance a Kyoto.', 2, 'arch.fujimoto@tokyo.jp'),
('2026-03-28', 3, 'Il design è splendido, ma le dimensioni sono leggermente ridotte per gli standard della mia villa a Vienna. Comunque un buon prodotto.', 2, 'baronessa.elena@vienna.at'),

-- Prodotto 3: The Thermal Embrace (St. Moritz)
('2026-01-02', 5, 'Riscaldamento impeccabile per lo chalet a St. Moritz. Addio brividi mattutini, ora è un vero abbraccio termico. Superbo.', 3, 'lady.alexandra@royal.uk'),
('2026-04-12', 5, 'Installato nella mia dimora sulle Alpi svizzere. La tecnologia di regolazione della temperatura è fantascientifica.', 3, 'dott.ramazzotti@clinica.ch'),
('2026-05-18', 4, 'Molto confortevole, anche se avrei preferito una finitura in platino spazzolato anziché bianco neve. Riscalda magnificamente.', 3, 'ceo.elon@silicon.com'),

-- Prodotto 4: King of the desert (Dubai)
('2026-02-14', 5, 'Finalmente il comfort del deserto senza il fastidio della sabbia. Splendido, ne ho ordinati altri quattro per l''attico di Doha.', 4, 'sheikh.mansoor@dubai.ae'),
('2026-05-01', 5, 'Una morbidezza indescrivibile, la pelle utilizzata è di una qualità strabiliante. Si adatta perfettamente al lusso esotico.', 4, 'oligarh.igor@moscow.ru'),

-- Prodotto 5: Imperial Aurea Maxima (Roma Imperiale)
('2026-04-22', 5, 'Il dettaglio dorato al centro è pura maestosità. Gli ospiti della mia sfilata a Milano sono rimasti estasiati da questo pezzo d''arte.', 5, 'marta.fashion@milano.it'),
('2026-06-05', 5, 'Marmo satinato impeccabile, una seduta regale che trasforma il bagno in una sala del trono del Senato Romano. Eccellente.', 5, 'conte.vittorio@luxe.it');
