package StrategiaDnia;

import Agenci.Robotnik;

public class Pracus implements StrategiaDnia {
    @Override
    public NaukaCzyPraca uczycSieCzyPracowac(Robotnik robotnik) {
        return NaukaCzyPraca.PRACA;
    }
}
