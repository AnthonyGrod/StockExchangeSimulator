package StrategiaDnia;

import Agenci.Robotnik;

public class Oszczedny implements StrategiaDnia {
    private final int limit_diamentow;

    public Oszczedny(int limit_diamentow) {
        this.limit_diamentow = limit_diamentow;
    }

    @Override
    public NaukaCzyPraca uczycSieCzyPracowac(Robotnik robotnik) {
        if (robotnik.getZasobyRobotnika().getLiczbaDiamentow() > limit_diamentow) {
            return NaukaCzyPraca.NAUKA;
        } else {
            return NaukaCzyPraca.PRACA;
        }
    }
}
