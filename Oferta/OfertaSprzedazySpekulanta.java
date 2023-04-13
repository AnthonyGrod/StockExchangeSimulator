package Oferta;

import Agenci.Robotnik;

public class OfertaSprzedazySpekulanta extends OfertaSprzedazy {

    private final double cenaSprzedazySpekulanta;

    public OfertaSprzedazySpekulanta(Robotnik.Produkty typProduktuKtoryChceSprzedac,
                                     double liczbaProduktuKtoryChceSprzedac, double cenaSprzedazySpekulanta, int id) {
        super(typProduktuKtoryChceSprzedac, liczbaProduktuKtoryChceSprzedac, id);
        this.cenaSprzedazySpekulanta = cenaSprzedazySpekulanta;
    }
}
