package Oferta;

import Agenci.Robotnik;

public abstract class OfertaKupna {

    private final Robotnik.Produkty typProduktuKtoryChceKupic;
    private final double liczbaProduktuKtoryChceKupic;


    private int idWystawiajacego;

    public OfertaKupna(Robotnik.Produkty typProduktuKtoryChceKupic, double liczbaProduktuKtoryChceKupic,
                       int idWystawiajacego) {
        this.typProduktuKtoryChceKupic = typProduktuKtoryChceKupic;
        this.liczbaProduktuKtoryChceKupic = liczbaProduktuKtoryChceKupic;
        this.idWystawiajacego = idWystawiajacego;
    }
}
