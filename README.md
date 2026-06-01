# TSW-PROGETTO
Ragazzi questo sarà il nostro spazio di lavoro per il progetto.


👤 PERSONA 1 — Utente + Sicurezza + DB + Validazione


🔹 Requisiti assegnati (testo identico al documento)
Registrazione utente con validazione dei campi tramite espressioni regolari

Login e gestione della sessione

Validazione form con regex e JavaScript

Focus sul campo attivo e placeholder descrittivi

Messaggi di errore inline (no alert)

Cifratura delle password

Prevenzione SQL Injection (PreparedStatement)

DataSource o DriverManager + Connection Pool

Gestione sessioni per il carrello (parte utente)

Pagine di errore personalizzate (404, 500, 403)

Pattern MVC rispettato (Model + Servlet Control)

HTML generato solo da JSP

Fragment JSP per header, footer e menu

Sito responsive (parte form e pagine utente)

🔹 Funzionalità complete assegnate
Registrazione + Login

Profilo utente

Validazione client-side completa

Sicurezza lato server

Configurazione web.xml errori

Struttura DB utenti + ordini (in collaborazione)

👤 PERSONA 2 — Catalogo + Carrello + AJAX + Responsive


🔹 Requisiti assegnati (testo identico al documento)
Catalogo prodotti con visualizzazione dettagliata

Barra di ricerca con AJAX

AJAX: verifica email già presente in fase di registrazione

Carrello: aggiunta, modifica quantità, rimozione

Conferma ordine e svuotamento carrello

Storico ordini effettuati dal cliente

Messaggi di conferma per le azioni dell’utente

Fetch API con JSON per comunicazioni asincrone

Sito responsive (catalogo, carrello, storico)

Pattern MVC rispettato

HTML generato solo da JSP

Fragment JSP (catalogo, navbar, footer)

🔹 Funzionalità complete assegnate
Catalogo + dettaglio prodotto

Carrello completo

Conferma ordine

Storico ordini

AJAX ricerca + email

Responsive design lato catalogo

👤 PERSONA 3 — Area Admin + Filtri + Ordini + CRUD


🔹 Requisiti assegnati (testo identico al documento)
Autenticazione programmata per area admin

Utilizzo dei filtri servlet

CRUD completo prodotti (inserisci/modifica/visualizza/cancella)

Conferma prima di cancellare un prodotto

Visualizzazione ordini complessivi

Filtro ordini per intervallo di date

Filtro ordini per cliente

Prezzo e IVA salvati nella riga d’ordine (integrità storica)

Vincolo d’integrità referenziale (prodotti cancellati negli ordini)

Pagine di errore personalizzate (403 area admin)

Pattern MVC rispettato

HTML generato solo da JSP

Fragment JSP (menu admin)

🔹 Funzionalità complete assegnate
Dashboard admin

CRUD prodotti

Filtri ordini

Sicurezza admin (filtri + autenticazione)

DB prodotti + ordini (parte admin)

<h2>📁 Struttura di lavoro (divisione per persona)</h2>

<pre>
TSW-PROGETTO/
│
├── persona1-utente-sicurezza/
│   ├── servlet/
│   │   ├── RegisterServlet.java
│   │   ├── LoginServlet.java
│   │   ├── SessionManager.java
│   │   └── ErrorHandlerServlet.java
│   ├── model/
│   │   ├── User.java
│   │   ├── UserDAO.java
│   │   └── DBConnection.java
│   ├── jsp/
│   │   ├── login.jsp
│   │   ├── register.jsp
│   │   ├── profilo.jsp
│   │   └── errori/
│   │       ├── 404.jsp
│   │       ├── 500.jsp
│   │       └── 403.jsp
│   └── validation/
│       ├── regex.js
│       └── form-validation.js
│
├── persona2-catalogo-carrello/
│   ├── servlet/
│   │   ├── CatalogoServlet.java
│   │   ├── ProdottoServlet.java
│   │   ├── CarrelloServlet.java
│   │   └── OrdineServlet.java
│   ├── model/
│   │   ├── Prodotto.java
│   │   ├── ProdottoDAO.java
│   │   ├── Carrello.java
│   │   └── OrdineDAO.java
│   ├── jsp/
│   │   ├── catalogo.jsp
│   │   ├── prodotto.jsp
│   │   ├── carrello.jsp
│   │   └── storico-ordini.jsp
│   ├── ajax/
│   │   ├── ricerca.js
│   │   └── email-check.js
│   └── css/
│       └── catalogo.css
│
├── persona3-admin-filtri/
│   ├── servlet/
│   │   ├── AdminLoginServlet.java
│   │   ├── AdminProdottiServlet.java
│   │   ├── AdminOrdiniServlet.java
│   │   └── Filtri/
│   │       ├── AuthFilter.java
│   │       └── LoggingFilter.java
│   ├── model/
│   │   ├── AdminDAO.java
│   │   ├── OrdineAdminDAO.java
│   │   └── ProdottoAdminDAO.java
│   ├── jsp/
│   │   ├── admin-dashboard.jsp
│   │   ├── admin-prodotti.jsp
│   │   ├── admin-ordini.jsp
│   │   └── conferma-eliminazione.jsp
│   ├── fetch/
│   │   ├── admin-prodotti.js
│   │   └── admin-ordini.js
│   └── css/
│       └── admin.css
│
├── shared/
│   ├── fragment/
│   │   ├── header.jsp
│   │   ├── footer.jsp
│   │   └── navbar.jsp
│   ├── css/
│   │   └── global.css
│   ├── js/
│   │   └── utils.js
│   ├── db/
│   │   └── schema.sql
│   └── config/
│       ├── web.xml
│       └── context.xml
│
└── README.md
</pre>

<hr>

<h2>📦 Struttura finale del progetto (versione da consegnare)</h2>

<pre>
TSW-PROGETTO/
│
├── src/
│   └── main/
│       └── java/
│           ├── controller/
│           ├── model/
│           └── filters/
│
├── webapp/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── jsp/
│   ├── css/
│   ├── js/
│   └── fragment/
│
└── shared/
</pre>





