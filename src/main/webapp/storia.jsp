<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>The Royal Rest — La Nostra Storia</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/componenti.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/storia.css">
</head>
<body>

  <jsp:include page="/fragments/navbar.jsp" />

  <header class="storia-hero">
    <span class="storia-eyebrow">Est. 1885 · Ginevra, Svizzera</span>
    <h1>La Nostra Storia</h1>
    <p class="storia-intro">
      Un'ossessione tramandata. Una firma che non conosce compromessi.
    </p>
  </header>

  <section class="storia-blocco">
    <span class="storia-eyebrow">Capitolo I</span>
    <h2>Noah Roy</h2>

    <p>
      Nel 1885, in una piccola bottega di rue du Rhône, Ginevra, un giovane di
      ventitré anni di nome Noah Roy accese per la prima volta il suo lume da lavoro.
      Non aveva capitali, non aveva clienti, non aveva fama. Aveva soltanto due cose:
      le mani e una convinzione incrollabile che ogni oggetto, anche il più umile,
      meritasse di essere realizzato alla perfezione.
    </p>

    <p>
      I vicini lo chiamavano <em>l'ossessionato</em>. Non a torto. Noah era capace di
      lavorare quarantotto ore consecutive su una singola commessa, spendendo talvolta
      più in materiali e tempo di quanto avrebbe mai fatturato al cliente. Per anni
      rimase in perdita. Per anni incassò sorrisi di commiserazione dai negozianti
      della sua strada. Ma la città, lentamente, imparò a riconoscere il suo lavoro
      dalla qualità silenziosa che emanava: una precisione quasi morale, una cura che
      si sentiva prima ancora di essere vista.
    </p>

    <p>
      Con il passare dei decenni, la bottega di Noah Roy divenne un pellegrinaggio
      per chi cercava l'eccellenza senza scorciatoie. Non cresceva — Noah aveva ormai
      smesso di volerlo. Ogni pezzo richiedeva il suo tempo, e il suo tempo
      non era in vendita.
    </p>
  </section>

  <section class="storia-citazione">
    <blockquote>
      La perfezione non si negozia,<br>si pretende.
    </blockquote>
    <cite>— Noah Roy, Ginevra, 1885</cite>
  </section>

  <section class="storia-blocco storia-blocco--grigio">
    <div class="storia-grid">
      <div>
        <span class="storia-eyebrow">Capitolo II</span>
        <h2>Logan Roy</h2>

        <p>
          Nell'autunno del 1973, Noah Roy spense il suo lume per l'ultima volta.
          Lasciò in eredità al nipote Logan una bottega di sei metri quadri, due dipendenti
          fedeli, e il peso silenzioso di un'intera vita dedicata alla perfezione.
        </p>

        <p>
          Logan aveva venticinque anni. Era cresciuto su quel bancone, aveva imparato
          a distinguere i materiali al tatto, a capire quando qualcosa era
          <em>abbastanza buono</em> e quando era davvero perfetto. Ma aveva anche
          frequentato l'Università di Ginevra, conseguendo una laurea in Business
          Administration con lode — un dettaglio che Noah aveva sempre guardato
          con affettuosa diffidenza.
        </p>

        <p>
          In meno di un decennio, Logan Roy fece ciò che sembrava impossibile: trasformò
          la bottega di Noah in una multinazionale del lusso. Non rinunciò a nulla — né
          alla qualità artigianale né alla visione commerciale. Aprì showroom a Parigi,
          Tokyo, New York. Brevettò tecniche che Noah aveva sviluppato in silenzio per
          cinquant'anni. Portò il marchio nei salotti dell'élite mondiale.
        </p>

        <p>
          Eppure, ancora oggi, circa la metà dei proventi dell'azienda — una cifra che
          fa impallidire i consulenti finanziari e innervosire gli azionisti più impazienti
          — viene reinvestita in ricerca e sviluppo. Una scelta che nessun MBA
          giustificherebbe, e che Logan Roy non ha mai sentito il bisogno di spiegare.
        </p>

        <p class="storia-evidenza">
          <em>"Era quello che faceva mio zio,"</em> ha risposto una volta, a chi gli chiedeva
          il perché. <em>"Lui lo chiamava rispetto per il mestiere. Io lo chiamo strategia."</em>
        </p>
      </div>

      <figure class="storia-foto">
        <img
          src="${pageContext.request.contextPath}/img/logan.jpg"
          alt="Logan Roy, Presidente di The Royal Rest">
        <figcaption>
          <strong>Logan Roy</strong>
          <span>Presidente & CEO · The Royal Rest</span>
          <span>Dal 1973</span>
        </figcaption>
      </figure>
    </div>
  </section>

  <footer class="storia-chiusura">
    <p>
      Centoquaranta anni dopo, quella bottega di rue du Rhône vive ancora —
      in ogni pezzo che porta il nostro nome.
    </p>
    <span class="storia-eyebrow">The Royal Rest · Est. 1885</span>
  </footer>

  <jsp:include page="/fragments/footer.jsp" />

</body>
</html>
