package Oferta;

import Agenci.Robotnik;

public abstract class OfertaSprzedazy {
    private final Robotnik.Produkty typProduktuKtoryChceSprzedac;
    private final double liczbaProduktuKtoryChceSprzedac;

    private final int idWystawiajacego;

    public OfertaSprzedazy(Robotnik.Produkty typProduktuKtoryChceSprzedac, double liczbaProduktuKtoryChceSprzedac,
                           int idWystawiajacego) {
        this.typProduktuKtoryChceSprzedac = typProduktuKtoryChceSprzedac;
        this.liczbaProduktuKtoryChceSprzedac = liczbaProduktuKtoryChceSprzedac;
        this.idWystawiajacego = idWystawiajacego;
    }
}
