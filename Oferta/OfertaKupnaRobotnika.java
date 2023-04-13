package Oferta;

import Agenci.Robotnik;

public class OfertaKupnaRobotnika extends OfertaKupna {

    public OfertaKupnaRobotnika(Robotnik.Produkty typProduktuKtoryChceKupic, double liczbaProduktuKtoryChceKupic,
                                int idWystawiajacego) {
        super(typProduktuKtoryChceKupic, liczbaProduktuKtoryChceKupic, idWystawiajacego);
    }
}
