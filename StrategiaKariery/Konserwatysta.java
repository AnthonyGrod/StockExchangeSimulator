package StrategiaKariery;

import Agenci.Robotnik;

public class Konserwatysta implements StrategiaKariery {

    @Override
    public Robotnik.SciezkaZawodu czyZmieniaKariere(Robotnik robotnik) {
        // Konserwatysta nigdy nie zmienia swojej kariery, wiec zwracamy te sciezke ktorą mial ostatnio
        return robotnik.getAktualnaSciezkaZawodu();
    }
}
