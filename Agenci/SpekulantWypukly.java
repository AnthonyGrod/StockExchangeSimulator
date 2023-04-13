package Agenci;

import Gielda.InformacjeOGieldzie;
import Zasoby.ZasobyRobotnika;

import java.util.ArrayList;
import java.util.HashMap;

public class SpekulantWypukly extends Spekulant {

    public SpekulantWypukly(int id, ZasobyRobotnika zasoby,
                           InformacjeOGieldzie informacjeOGieldzie) {
        super(id, zasoby, informacjeOGieldzie);
    }

    @Override
    public HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>>
            stworzOfertySpekulantow() {
        // Tworzymy najpierw pustą mapę
        HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>> ofertyKupnaISprzedaży =
                this.inicjalizujofertyKupnaISprzedaży();

        Robotnik.Produkty[] tablicaProduktowBezDiamentow = Robotnik.stworzTabliceProduktowBezDiamentow();
        boolean czyWypukla;
        int aktualnyDzien = this.getInformacjeOGieldzie().getAktualnyDzien();
        ///HashMap<Robotnik.Produkty, Boolean> paryProduktyCzyWypukly = new HashMap<>();
        for (int i = 0; i < tablicaProduktowBezDiamentow.length; i++) {
            if (this.getInformacjeOGieldzie().getAktualnyDzien() < 3) {
                // Nie mamy wystarczająco informacji żeby utworzyć funkcję wklęsłą, więc Spekulant nie robi nic
                // (co jest równoważne ze zwróceniem mapy wypełnionej zerami w stosownych polach
                czyWypukla = false;
            } else {
                // Jeśli mamy wystarczająco dni żeby rozstrzygnąć czy funkcja jest wypukła, to to robimy
                double sredniaCenaSprzedTrzechDni = this.getInformacjeOGieldzie().getHistoriaCen()
                        .get(tablicaProduktowBezDiamentow[i]).get(aktualnyDzien - 3);
                double sredniaCenaSprzedDwochDni = this.getInformacjeOGieldzie().getHistoriaCen()
                        .get(tablicaProduktowBezDiamentow[i]).get(aktualnyDzien - 2);
                double sredniaCenaSprzedJednegoDnia = this.getInformacjeOGieldzie().getHistoriaCen()
                        .get(tablicaProduktowBezDiamentow[i]).get(aktualnyDzien - 1);
                if (sredniaCenaSprzedTrzechDni > sredniaCenaSprzedDwochDni &&
                        sredniaCenaSprzedJednegoDnia > sredniaCenaSprzedDwochDni) {
                    // Wtedy jest wypukła, więc wystawiamy oferte Kupna 100 sztuk danego Produktu za 0.9 średniej ceny
                    // z wczoraj oraz wystawiamy oferę Sprzedaży wszytskich sztuk danego produktu jakie Spekulant ma
                    // po cenie 1.1 ceny średniej danego produktu z wczoraj
                    czyWypukla = true;
                } else {
                    czyWypukla = false;
                }
            }
            if (czyWypukla) {
                double sredniaCenaSprzedJednegoDnia = this.getInformacjeOGieldzie().getHistoriaCen()
                        .get(tablicaProduktowBezDiamentow[i]).get(aktualnyDzien - 1);
                // Ustawiamy liczbe Produktów które Spekulant chce kupić
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.KUPNO).set(0, 100.0);
                // Ustawiamy cenę Produktów które Spekulant chce kupić
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.KUPNO).set(1, sredniaCenaSprzedJednegoDnia * 0.9);
                // Ustawiamy liczbe Produktów które Spekulant chce sprzedać
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.KUPNO).set(0, this.getZasoby()
                                .stworzParyProduktLiczbaZasobu().get(tablicaProduktowBezDiamentow[i]));
                // Ustawiamy cenę Produktów które Spekulant chce sprzedać
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.KUPNO).set(1, sredniaCenaSprzedJednegoDnia * 1.1);
            } else {
                // W przeciwnym wypadku ustawiamy wszystko co nie było zerami na zero (czyli tylko liczby produktów)
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.KUPNO).set(0, 0.0);
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.KUPNO).set(0, 0.0);
            }
        }
        return ofertyKupnaISprzedaży;
    }
}
