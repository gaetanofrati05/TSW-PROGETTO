<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pannello Admin - The Royal Rest</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>

<body class="admin-page-bg">

    <main class="admin-wrapper">
        
        <div class="admin-heading-box">
            <h1 class="admin-title-luxury">Area Amministrativa</h1>
            <p class="admin-subtitle-luxury">Inserimento Nuovo Prodotto</p>
        </div>

        <div class="admin-card-luxury">
         
            <form action="${pageContext.request.contextPath}/InserisciProdottoServlet" method="POST">
                
                <div class="admin-form-group">
                    <label class="admin-label">Nome Prodotto</label>
                    <input type="text" class="admin-input" name="nome" placeholder="Es: Cuscino in Memory Foam" required>
                </div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Stile</label>
                    <input type="text" class="admin-input" name="stile" placeholder="Es: Moderno, Classico" required>
                </div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Colore</label>
                    <input type="text" class="admin-input" name="colore" placeholder="Es: Bianco Seta" required>
                </div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Dimensioni</label>
                    <input type="text" class="admin-input" name="dimensioni" placeholder="Es: 50x80 cm" required>
                </div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Prezzo (€)</label>
                    <input type="number" step="0.01" min="0" class="admin-input" name="prezzo" placeholder="0.00" required>
                </div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Quantità a magazzino</label>
                    <input type="number" min="0" class="admin-input" name="quantita" placeholder="0" required>
                </div>
                
                <div class="admin-form-group">
                    <label class="admin-label">Descrizione</label>
                    <textarea name="descrizione" class="custom-textarea" rows="3" placeholder="Inserisci i dettagli del prodotto..." required></textarea>
                </div>
                
                <button type="submit" class="btn-admin-submit">Salva nel Catalogo</button>
                
            </form>
            
            <div class="admin-redirect-box">
                <a href="${pageContext.request.contextPath}/" class="admin-link">&larr; Torna alla Home</a>
            </div>
            
        </div>
        
    </main>

</body>
</html>