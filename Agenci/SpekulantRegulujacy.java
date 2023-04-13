package Agenci;

import Gielda.InformacjeOGieldzie;
import Zasoby.ZasobyRobotnika;

import java.util.ArrayList;
import java.util.HashMap;

public class SpekulantRegulujacy extends Spekulant {

    public SpekulantRegulujacy(int id, ZasobyRobotnika zasoby,
                            InformacjeOGieldzie informacjeOGieldzie) {
        super(id, zasoby, informacjeOGieldzie);
    }

    @Override
    public HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>>
            stworzOfertySpekulantow() {
        HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>> ofertyKupnaISprzedaży =
                this.inicjalizujofertyKupnaISprzedaży();
        Robotnik.Produkty[] tablicaProduktowBezDiamentow = Robotnik.stworzTabliceProduktowBezDiamentow();

        for (int i = 0; i < tablicaProduktowBezDiamentow.length; i++) {
            double sredniaCenaProduktuZPoprzedniegDnia = this.getInformacjeOGieldzie().getHistoriaCen()
                    .get(tablicaProduktowBezDiamentow[i]).get(this.getInformacjeOGieldzie().getAktualnyDzien() - 1);
            // Zmienna p_i to o liczba Produktów danego rodzaju wystawiona do sprzedaży przez robotników w turze i
            // W naszym przypadku zmienna i to number dnia poprzedniego
            double p_i = this.getInformacjeOGieldzie()
                    .getCzestotliwoscWystepowaniaProduktowWyprodukowanychPrzezRobotnikow()
                    .get(tablicaProduktowBezDiamentow[i]).get(this.getInformacjeOGieldzie().getAktualnyDzien() - 1);
            sredniaCenaProduktuZPoprzedniegDnia = sredniaCenaProduktuZPoprzedniegDnia * (Math.max(p_i, 1));
            double cenaKupna = sredniaCenaProduktuZPoprzedniegDnia * 0.9;
            double cenaSprzedazy = sredniaCenaProduktuZPoprzedniegDnia * 1.1;
            ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni.sprzedazCzyKupno.KUPNO)
                    .set(0, 100.0);
            ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni.sprzedazCzyKupno.KUPNO)
                    .set(1, cenaKupna);
            ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni.sprzedazCzyKupno.SPRZEDAZ)
                    .set(0, this.getZasoby().stworzParyProduktLiczbaZasobu().get(tablicaProduktowBezDiamentow[i]));
            ofertyKupnaISprzedaży.get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni.sprzedazCzyKupno.SPRZEDAZ)
                    .set(1, cenaSprzedazy);
        }

        return ofertyKupnaISprzedaży;
    }
}

