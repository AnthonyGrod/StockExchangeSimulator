package StrategiaDnia;

import Agenci.Robotnik;

public class Okresowy implements StrategiaDnia {
    private final int okresowosc_nauki;

    public Okresowy(int okresowosc_nauki) {
        this.okresowosc_nauki = okresowosc_nauki;
    }

    @Override
    public NaukaCzyPraca uczycSieCzyPracowac(Robotnik robotnik) {
        if (robotnik.getInformacjeOGieldzie().getAktualnyDzien() % this.okresowosc_nauki == 0) {
            return NaukaCzyPraca.NAUKA;
        } else {
            return NaukaCzyPraca.PRACA;
        }
    }
}
