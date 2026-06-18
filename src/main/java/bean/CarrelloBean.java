package bean;

import java.util.ArrayList;
import java.util.List;

public class CarrelloBean {
private List<ProdottoCarrello> prodotti;
public CarrelloBean() {
	this.prodotti=new ArrayList<>();
  }
public void aggiungiProdotto(ProdottoBean nuovoProdotto, int quantita) {
    
    boolean trovato = false;
    
    // Scorriamo tutti i prodotti già presenti nel carrello, uno per uno per vedere se è già presente
    for (ProdottoCarrello item : prodotti) {
        
        if (item.getProdotto().getIdProdotto() == nuovoProdotto.getIdProdotto()) {
            item.setQuantita(item.getQuantita() + quantita); //se è già presente aumentiamo la quantità
            trovato = true;
            break;
        }
    }
    // Se dopo aver scorso tutta la lista non abbiamo trovato il prodotto,
    // significa che non era ancora nel carrello: lo aggiungiamo come riga nuova
    if (!trovato) {
        prodotti.add(new ProdottoCarrello(nuovoProdotto, quantita));
    }
}
 public void eliminaProdotto(int idProdotto) {
	 prodotti.removeIf(item->item.getProdotto().getIdProdotto()==idProdotto);
 }
 public List<ProdottoCarrello> getElementi(){
	 return prodotti;
 }
 public float getTotale() {
	 float totale = 0;
	 for (ProdottoCarrello item : prodotti) {
		 totale += item.getProdotto().getPrezzo() * item.getQuantita();
	 }
	 return totale;
 }
}
