package StrategiaKupowania;

import Agenci.Robotnik;

import java.util.HashMap;

public class Technofob implements StrategiaKupowania {

    @Override
    public HashMap<Robotnik.Produkty, Double> coKupic(Robotnik robotnik) {
        HashMap<Robotnik.Produkty, Double> decyzja = Robotnik.paryProduktLiczbaBezDiamentow();
        decyzja.replace(Robotnik.Produkty.JEDZENIE, 100.0);
        return decyzja;
    }

}
