package bean;

import java.util.ArrayList;
import java.util.List;

public class CarrelloBean {
    private static final float IVA = 0.22f; // 22%

    private List<ProdottoCarrello> prodotti;

    public CarrelloBean() {
        this.prodotti = new ArrayList<>();
    }

    public void aggiungiProdotto(ProdottoBean nuovoProdotto, int quantita) {
        prodotti.stream()
            .filter(item -> item.getProdotto().getIdProdotto() == nuovoProdotto.getIdProdotto())
            .findFirst()
            .ifPresentOrElse(
                item -> item.setQuantita(item.getQuantita() + quantita),
                () -> prodotti.add(new ProdottoCarrello(nuovoProdotto, quantita))
            );
    }

    // Totale netto (senza IVA)
    public float calcolaTotaleNetto() {
        float totaleNetto = 0;
        for (ProdottoCarrello item : prodotti) {
            totaleNetto += item.getProdotto().getPrezzo() * item.getQuantita();
        }
        return totaleNetto;
    }

    // Importo IVA sul totale
    public float calcolaIVA() {
        return calcolaTotaleNetto() * IVA;
    }

    // Totale lordo (netto + IVA)
    public float calcolaTotaleLordo() {
        return calcolaTotaleNetto() + calcolaIVA();
    }

    public void eliminaProdotto(int idProdotto) {
        prodotti.removeIf(item -> item.getProdotto().getIdProdotto() == idProdotto);
    }

    public List<ProdottoCarrello> getElementi() {
        return prodotti;
    }
}