package StrategiaKariery;

import Agenci.Robotnik;

import java.util.HashMap;
import java.util.List;

public class Rewolucjonista implements StrategiaKariery {

    @Override
    public Robotnik.SciezkaZawodu czyZmieniaKariere(Robotnik robotnik) {
        int modulo = Math.max(1, robotnik.getId() % 17);
        // Chcemy rozstrzygnąć, który produkt pojawiał się najczęściej przez ostatnie modulo dni
        Robotnik.Produkty[] tablicaProduktow = Robotnik.stworzTabliceProduktowBezDiamentow();

        // Poniższa zmienna będzie przetrzymywać informacje o sumarycznym występowaniu danego produktu przez
        // ostatnie modulo dni
        HashMap<Robotnik.Produkty, Integer> wystepowaniePoszczegolnychProduktow = new HashMap<>();
        // Tworzymy mapę z wartościami 0
        for (int i = 0; i < tablicaProduktow.length; i++) {
            wystepowaniePoszczegolnychProduktow.put(tablicaProduktow[i], 0);
        }

        // Ekstraktujemy informacje o występowaniu danych Produktów w przeszłości
        HashMap<Robotnik.Produkty, List<Integer>> daneOWystepowaniu = robotnik.getInformacjeOGieldzie()
                .getCzestotliwoscWystepowaniaProduktow();

        // Zliczamy występowanie przez ostatnie modulo dni dla poszczególnych produktów
        int aktualnyDzien = robotnik.getInformacjeOGieldzie().getAktualnyDzien();
        int dzienDoPoliczenia = aktualnyDzien - 1;
        // Iterujemy się po wszystkich produktach z tablicy tablicaProduktow
        for (int i = 0; i < tablicaProduktow.length; i++) {
            dzienDoPoliczenia = aktualnyDzien - 1;
            // Iterujemy się po modulo dni wstecz
            for (int j = 0; j < modulo; j++) {
                if (dzienDoPoliczenia < 0) {
                    break;
                }
                // Podnosimy wartość w polu danego produktu w mapie
                wystepowaniePoszczegolnychProduktow.replace(tablicaProduktow[i], wystepowaniePoszczegolnychProduktow
                        .get(tablicaProduktow[i]) + daneOWystepowaniu.get(tablicaProduktow[i]).get(dzienDoPoliczenia));
                dzienDoPoliczenia--;
            }
        }
        // Rozstrzygamy który produkt pojawił się największą liczbę razy
        Robotnik.Produkty najczesciejPojawiajacySieProdukt = null;
        Integer ileRazyPojawilSieNajczesciejPowtarzajacySieProdukt = 0;
        for (int i = 0; i < tablicaProduktow.length; i++) {
            if (wystepowaniePoszczegolnychProduktow.get(tablicaProduktow[i])
                    > ileRazyPojawilSieNajczesciejPowtarzajacySieProdukt) {
                ileRazyPojawilSieNajczesciejPowtarzajacySieProdukt = wystepowaniePoszczegolnychProduktow
                        .get(tablicaProduktow[i]);
                najczesciejPojawiajacySieProdukt = tablicaProduktow[i];
            }
        }
        // Teraz szukamy zawodu, który produkuje najczesciejPojawiajacySieProdukt
        HashMap<Robotnik.Produkty, Robotnik.SciezkaZawodu> pary = Robotnik.paryProduktRobotnik();

        return pary.get(najczesciejPojawiajacySieProdukt);
    }
}
