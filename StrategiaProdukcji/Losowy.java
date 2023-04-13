package StrategiaProdukcji;

import Agenci.Robotnik;

public class Losowy implements StrategiaProdukcji {

    @Override
    public Robotnik.Produkty coProdukowac(Robotnik robotnik) {
        Robotnik.Produkty[] tablicaProduktow = Robotnik.stworzTabliceProduktowZDiamentami();

        int randomIndex = (int) Math.floor(Math.random() * 5);
        return tablicaProduktow[randomIndex];
    }
}
