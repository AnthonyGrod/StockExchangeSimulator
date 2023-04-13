package StrategiaKupowania;

import Agenci.Robotnik;

import java.util.HashMap;

public class Zmechanizowany extends Czyscioszek implements StrategiaKupowania {
    private final double liczba_narzedzi;

    public Zmechanizowany(int liczba_narzedzi) {
        this.liczba_narzedzi = liczba_narzedzi;
    }

    public HashMap<Robotnik.Produkty, Double> zadbajOJedzenieIUbraniaJakZmechanizowany(Robotnik robotnik) {
        HashMap<Robotnik.Produkty, Double> decyzjaZmechanizowanego = super.
                zadbajOJedzenieIUbraniaJakCzyscioszek(robotnik);
        decyzjaZmechanizowanego.replace(Robotnik.Produkty.NARZEDZIA, this.liczba_narzedzi);
        return decyzjaZmechanizowanego;
    }

    @Override
    public HashMap<Robotnik.Produkty, Double> coKupic(Robotnik robotnik) {
        return zadbajOJedzenieIUbraniaJakCzyscioszek(robotnik);
    }
}