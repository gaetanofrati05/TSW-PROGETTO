<%-- Direttiva JSP: Configura il linguaggio della pagina e la codifica dei caratteri per evitare problemi con gli accenti --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Pagina JSP utilizzata come inserimeto dei nuovi prodotti (Admin)--%>
<!DOCTYPE html>

<html lang="it">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Inserimento Nuovo Prodotto - The Royal Rest</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagerror.css">
</head>

<body class="admin-page-bg">

    <main class="admin-wrapper">
        
        <div class="admin-heading-box">
            <h1 class="admin-title-luxury">Area Amministrativa</h1>
            <p class="admin-subtitle-luxury">Inserimento Nuovo Prodotto</p>
        </div>

        <div class="admin-card-luxury">
         
            <form id="formInserisciProdotto" action="${pageContext.request.contextPath}/InserisciProdottoServlet" method="POST" enctype="multipart/form-data">
                
                <div class="admin-form-group">
                    <label class="admin-label">Nome Prodotto</label>
                    
                    <input type="text" id="nomeProdotto"class="admin-input" name="nome" placeholder="Es: Tavoletta in oro 24K" required>
                </div>

                <div id="nomeProdotto-error" class="js-error">Nome del prodotto non valido</div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Stile</label>
                    <input type="text" id="stileProdotto" class="admin-input" name="stile" placeholder="Es: Moderno" required>
                </div>
                
                <div id="stileProdotto-error" class="js-error">Stile del prodotto non valido</div>

                <div class="admin-form-group">
                    <label class="admin-label">Colore</label>
                    <input type="text" id="coloreProdotto" class="admin-input" name="colore" placeholder="Es: Avorio" required>
                </div>
                
                <div id="coloreProdotto-error" class="js-error">Colore del prodotto non valido</div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Dimensioni (cm)</label>
                    <input type="text" id="dimensioniProdotto" class="admin-input" name="dimensioni" placeholder="Es: 36x24x3" required>
                </div>
                
                <div id="dimensioniProdotto-error" class="js-error">Dimensioni del prodotto non valido</div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Prezzo (€)</label>
                    <input type="number" id="prezzoProdotto" step="0.01" min="0" class="admin-input" name="prezzo" placeholder="0.00" required>
                </div>
                
                <div id="prezzoProdotto-error" class="js-error">Prezzo del prodotto non valido</div>    
                
                <div class="admin-form-group">
                    <label class="admin-label">Quantità a magazzino</label>
                    <input type="number" id="quantitaProdotto" min="0" class="admin-input" name="quantita" placeholder="0" required>
                </div> 
                
                <div id="quantitaProdotto-error" class="js-error">Quantità del prodotto non valida</div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Descrizione</label>
                    <textarea name="descrizione"  id="descrizioneProdotto" class="custom-textarea" rows="3" placeholder="Inserisci i dettagli del prodotto..." required></textarea>
                </div>
                
                <div id="descrizioneProdotto-error" class="js-error">Descrizione del prodotto non valida</div>

                <div class="admin-form-group">
                    <label class="admin-label">Immagine</label>
                    <div class="custom-file-wrapper">
                        <label for="immagineProdotto" class="btn-action btn-edit" style="text-align: center; margin-right: 0;">Seleziona Immagine</label>

                        <input type="file" id="immagineProdotto" name="immagine" required class="hidden-file-input" accept="image/jpeg,image/png,image/jpg" onchange="aggiornaNomeFileImmagine(this)">

                        <span id="file-name-display" class="file-name-text">Nessun file selezionato (JPG, JPEG, PNG)</span>
                    </div>
                </div>
                
                <div id="immagineProdotto-error" class="js-error">Immagine del prodotto non valida</div>
                
                <%-- Errore lato Server (Java) --%>
                <% if (request.getAttribute("errore") != null) { %>
                    <div class="error-message">
                        <%= request.getAttribute("errore") %>
                    </div>
                <% } %>
                           
                <button type="submit" class="btn-admin-submit">Salva nel Catalogo</button>
                
            </form>
            
            <div class="admin-redirect-box">
                <a href="${pageContext.request.contextPath}/" class="admin-link">&larr; Torna alla Home</a> &nbsp; &nbsp; &nbsp; &nbsp;
                <a href="${pageContext.request.contextPath}/CatalogoAdminServlet" class="admin-link">Vai al Catalogo &rarr;</a>
            </div>
            
        </div>
        
        <hr>
    </main>
    <script src="${pageContext.request.contextPath}/js/validazioneProdotti.js"></script>
    <script src="${pageContext.request.contextPath}/js/inserisciProdotto.js"></script>
</body>
</html>