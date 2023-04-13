package StrategiaDnia;

import Agenci.Robotnik;

public class Rozkladowy implements StrategiaDnia {

    @Override
    public NaukaCzyPraca uczycSieCzyPracowac(Robotnik robotnik) {
        double prawdopodobienstwo = 1 + (robotnik.getInformacjeOGieldzie().getAktualnyDzien() + 3);
        double losowaLiczba = Math.random();
        if (losowaLiczba <= prawdopodobienstwo) {
            return NaukaCzyPraca.NAUKA;
        } else {
            return NaukaCzyPraca.PRACA;
        }
    }
}
