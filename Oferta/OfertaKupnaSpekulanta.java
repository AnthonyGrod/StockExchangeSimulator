package Oferta;

import Agenci.Robotnik;

public class OfertaKupnaSpekulanta extends OfertaKupna {

    private final double cenaKupna;

    public OfertaKupnaSpekulanta(Robotnik.Produkty typProduktuKtoryChceKupic, double liczbaProduktuKtoryChceKupic,
                                 double cenaKupna, int idWystawiajacego) {
        super(typProduktuKtoryChceKupic, liczbaProduktuKtoryChceKupic, idWystawiajacego);
        this.cenaKupna = cenaKupna;
    }
}
