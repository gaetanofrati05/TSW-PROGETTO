# TSW-PROGETTO
<h2>👤 Gaetano — Utente + Sicurezza + DB + Validazione</h2>

<h3>🔹 Requisiti assegnati </h3>
<ul>
  <li>Registrazione utente con validazione dei campi tramite espressioni regolari</li>
  <li>Login e gestione della sessione</li>
  <li>Validazione form con regex e JavaScript</li>
  <li>Focus sul campo attivo e placeholder descrittivi</li>
  <li>Messaggi di errore inline (no alert)</li>
  <li>Cifratura delle password</li>
  <li>Prevenzione SQL Injection (PreparedStatement)</li>
  <li>DataSource o DriverManager + Connection Pool</li>
  <li>Gestione sessioni per il carrello (parte utente)</li>
  <li>Pagine di errore personalizzate (404, 500, 403)</li>
  <li>Pattern MVC rispettato (Model + Servlet Control)</li>
  <li>HTML generato solo da JSP</li>
  <li>Fragment JSP per header, footer e menu</li>
  <li>Sito responsive (parte form e pagine utente)</li>
  <li>Creazione e gestione delle recensioni</li>
  <li>Configurazione file xml</li>
  <li>Pattern MVC rispettato</li>
</ul>

<h3>🔹 Funzionalità complete assegnate</h3>
<ul>
  <li>Registrazione + Login</li>
  <li>Profilo utente</li>
  <li>Validazione client-side completa</li>
  <li>Sicurezza lato server</li>
  <li>Configurazione web.xml errori</li>
  <li>Struttura DB utenti + ordini (in collaborazione)</li>
</ul>

<hr>

<h2>👤 David — Catalogo + Carrello + AJAX + Responsive</h2>

<h3>🔹 Requisiti assegnati </h3>
<ul>
  <li>Catalogo prodotti con visualizzazione dettagliata</li>
  <li>Barra di ricerca con AJAX</li>
  <li>AJAX: verifica email già presente in fase di registrazione</li>
  <li>Carrello: aggiunta, modifica quantità, rimozione</li>
  <li>Conferma ordine e svuotamento carrello</li>
  <li>Storico ordini effettuati dal cliente</li>
  <li>Messaggi di conferma per le azioni dell’utente</li>
  <li>Fetch API con JSON per comunicazioni asincrone</li>
  <li>Sito responsive (catalogo, carrello, storico)</li>
  <li>Pattern MVC rispettato</li>
  <li>HTML generato solo da JSP</li>
  <li>Fragment JSP (catalogo, navbar, footer)</li>
</ul>

<h3>🔹 Funzionalità complete assegnate</h3>
<ul>
  <li>Catalogo + dettaglio prodotto</li>
  <li>Carrello completo</li>
  <li>Conferma ordine</li>
  <li>Storico ordini</li>
  <li>AJAX ricerca + email</li>
  <li>Responsive design lato catalogo</li>
</ul>

<hr>

<h2>👤 Massimo — Area Admin + Filtri + Ordini + CRUD</h2>

<h3>🔹 Requisiti assegnati</h3>
<ul>
  <li>Autenticazione programmata per area admin</li>
  <li>Utilizzo dei filtri servlet</li>
  <li>CRUD completo prodotti (inserisci/modifica/visualizza/cancella)</li>
  <li>Conferma prima di cancellare un prodotto</li>
  <li>Visualizzazione ordini complessivi</li>
  <li>Filtro ordini per intervallo di date</li>
  <li>Filtro ordini per cliente</li>
  <li>Prezzo e IVA salvati nella riga d’ordine (integrità storica)</li>
  <li>Vincolo d’integrità referenziale (prodotti cancellati negli ordini)</li>
  <li>Pagine di errore personalizzate (403 area admin)</li>
  <li>Pattern MVC rispettato</li>
  <li>HTML generato solo da JSP</li>
  <li>Fragment JSP (menu admin)</li>
</ul>

<h3>🔹 Funzionalità complete assegnate</h3>
<ul>
  <li>Dashboard admin</li>
  <li>CRUD prodotti</li>
  <li>Filtri ordini</li>
  <li>Sicurezza admin (filtri + autenticazione)</li>
  <li>DB prodotti + ordini (parte admin)</li>
</ul>


<h2>📦 Struttura finale del progetto </h2>

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





