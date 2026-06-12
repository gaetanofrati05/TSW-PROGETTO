package bean;

import java.util.ArrayList;
import java.util.List;

public class CarrelloBean {
private List<ProdottoCarrello> prodotti;
public CarrelloBean() {
	this.prodotti=new ArrayList<>();
  }
 public void aggiungiProdotto(ProdottoBean nuovoProdotto, int quantita) {
	 prodotti.stream()
	 .filter(item->item.getProdotto().getIdProdotto()==nuovoProdotto.getIdProdotto())
	 .findFirst()
	 .ifPresentOrElse(
			 item->item.setQuantita(item.getQuantita()+quantita),
	         ()->prodotti.add(new ProdottoCarrello(nuovoProdotto, quantita))
	         );
  }
 public void eliminaProdotto(int idProdotto) {
	 prodotti.removeIf(item->item.getProdotto().getIdProdotto()==idProdotto);
 }
 public List<ProdottoCarrello> getElementi(){
	 return prodotti;
 }
}
