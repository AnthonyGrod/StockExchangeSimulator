package StrategiaKupowania;

import Agenci.Robotnik;

import java.util.HashMap;

public class Gadzeciarz extends Zmechanizowany implements StrategiaKupowania {

    public Gadzeciarz(int liczba_narzedzi) {
        super(liczba_narzedzi);
    }

    @Override
    public HashMap<Robotnik.Produkty, Double> coKupic(Robotnik robotnik) {
        HashMap<Robotnik.Produkty, Double> decyzjaGadzeciarza = super.
                zadbajOJedzenieIUbraniaJakZmechanizowany(robotnik);
        decyzjaGadzeciarza.replace(Robotnik.Produkty.PROGRAMYKOMPUTEROWE,
                robotnik.getLiczbaWyprodukowanychProduktowWDanejRundzie());
        return decyzjaGadzeciarza;
    }
}
