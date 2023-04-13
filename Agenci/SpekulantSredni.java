package Agenci;

import Gielda.InformacjeOGieldzie;
import Zasoby.ZasobyRobotnika;

import java.util.ArrayList;
import java.util.HashMap;

public class SpekulantSredni extends Spekulant {
    public enum sprzedazCzyKupno {
        SPRZEDAZ, KUPNO
    }

    private final int historia_spekulanta_sredniego;

    public SpekulantSredni(int id, int historia_spekulanta_sredniego, ZasobyRobotnika zasoby,
                           InformacjeOGieldzie informacjeOGieldzie) {
        super(id, zasoby, informacjeOGieldzie);
        this.historia_spekulanta_sredniego = historia_spekulanta_sredniego;
    }

    @Override
    public HashMap<Robotnik.Produkty, HashMap<sprzedazCzyKupno, ArrayList<Double>>> stworzOfertySpekulantow() {
        // Tworzymy najpierw pustą mapę
        HashMap<Robotnik.Produkty, HashMap<sprzedazCzyKupno, ArrayList<Double>>> ofertyKupnaISprzedaży =
                this.inicjalizujofertyKupnaISprzedaży();
        Robotnik.Produkty[] tablicaProduktowBezDiamentow = Robotnik.stworzTabliceProduktowBezDiamentow();
        HashMap<Robotnik.Produkty, Double> paryProduktyLiczbaPosiadana = this.getZasoby()
                .stworzParyProduktLiczbaZasobu();

        // Obliczamy ceny średnie Kupna i Sprzedaży z ostatnich historia_spekulanta_sredniego dni
        HashMap<Robotnik.Produkty, Double> paryProduktySredniaCena = new HashMap<>();
        int dzienDoPoliczenia = this.getInformacjeOGieldzie().getAktualnyDzien() - 1;
        int policzoneDni = 0;
        for (int i = 0; i < tablicaProduktowBezDiamentow.length; i++) {
            Double sredniaCen = 0.0;
            for (int j = 0; i < this.historia_spekulanta_sredniego; j++) {
                if (dzienDoPoliczenia < 0) {
                    break;
                }
                policzoneDni++;
                sredniaCen += this.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktowBezDiamentow[i])
                        .get(dzienDoPoliczenia);
                dzienDoPoliczenia--;
            }
            if (policzoneDni > 0) {
                sredniaCen = sredniaCen / policzoneDni;
            }
            paryProduktySredniaCena.put(tablicaProduktowBezDiamentow[i], sredniaCen);
            // Ustawiamy średnie Ceny w odpowiednich polach w ofertyKupnaISprzedazy
            Double obliczonaCenaKupna = sredniaCen * 0.9;
            Double obliczonaCenaSprzedazy = sredniaCen * 1.1;
            ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(sprzedazCzyKupno.SPRZEDAZ)
                    .set(1, obliczonaCenaSprzedazy);
            ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(sprzedazCzyKupno.KUPNO)
                    .set(1, obliczonaCenaKupna);
        }

        // Teraz ewentualnie nadpiszemy wybrane ceny, jeśli Spekulant nie ma danego produktu (bo jesli nie ma jakiegos
        // produktu to zamiast tego daje tylko ofertę kupna 5% poniżej tej wartości.)
        for (int i = 0; i < tablicaProduktowBezDiamentow.length; i++) {
            // Jeśli Spekulant nie ma danego produktu to ustawiamy jego pole ze sprzedażą na 0.0 w polach liczba i
            // cena produktu który chce sprzedać, a cenę oferty kupna ustawiamy na 0.95 obliczonej średniej ceny.
            // Liczba danego Produktu, którą chce kupić Spekulant pozostaje bez zmian (czyli dalej jest równa 100.0)
            if (paryProduktyLiczbaPosiadana.get(tablicaProduktowBezDiamentow[i]) == 0) {
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(sprzedazCzyKupno.SPRZEDAZ)
                        .set(1, 0.0);
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(sprzedazCzyKupno.SPRZEDAZ)
                        .set(1, 0.0);
                ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(sprzedazCzyKupno.KUPNO)
                        .set(1, paryProduktySredniaCena.get(tablicaProduktowBezDiamentow[i]) * 0.95);
            }
        }
        return ofertyKupnaISprzedaży;
    }
}

