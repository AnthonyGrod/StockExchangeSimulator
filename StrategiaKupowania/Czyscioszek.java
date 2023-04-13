package StrategiaKupowania;

import Agenci.Robotnik;

import java.util.HashMap;

public class Czyscioszek implements StrategiaKupowania {

    public HashMap<Robotnik.Produkty, Double> zadbajOJedzenieIUbraniaJakCzyscioszek(Robotnik robotnik) {
        // Dbamy o jedzenie
        HashMap<Robotnik.Produkty, Double> decyzjaCzyscioszka = Robotnik.paryProduktLiczbaBezDiamentow();
        decyzjaCzyscioszka.replace(Robotnik.Produkty.JEDZENIE, 100.0);

        // Teraz musimy zadbać, żeby miał na następny dzień co najmniej 100 ubrań. Musimy najpierw rozstrzygnąć, ile
        // ubrań ma teraz, które będą jeszcze też dobre na jutro, tzn. ile Czyścioszek ma ubrań o wytrzymałości
        // większej równej 2.
        int liczbaUbranDobrychNaJutro = 0;
        for (int i = 0; i < robotnik.getZasobyRobotnika().getUbrania().size(); i++) {
            if (robotnik.getZasobyRobotnika().getUbrania().get(i).getWytrzymalosc() >= 2) {
                liczbaUbranDobrychNaJutro++;
            }
        }
        if (liczbaUbranDobrychNaJutro < 100) {
            decyzjaCzyscioszka.replace(Robotnik.Produkty.UBRANIA, 100.0 - liczbaUbranDobrychNaJutro);
        }
        return decyzjaCzyscioszka;
    }

    @Override
    public HashMap<Robotnik.Produkty, Double> coKupic(Robotnik robotnik) {
        return zadbajOJedzenieIUbraniaJakCzyscioszek(robotnik);
    }
}
