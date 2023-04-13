package Agenci;

import Gielda.InformacjeOGieldzie;
import Zasoby.ZasobyRobotnika;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Spekulant extends Agent {

    private final ZasobyRobotnika zasoby;
    private final InformacjeOGieldzie informacjeOGieldzie;

    public Spekulant(int id, ZasobyRobotnika zasoby, InformacjeOGieldzie informacjeOGieldzie) {
        super(id);
        this.zasoby = zasoby;
        this.informacjeOGieldzie = informacjeOGieldzie;
    }

    public HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>>
            inicjalizujofertyKupnaISprzedaży() {
        HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>> ofertyKupnaISprzedaży
                = new HashMap<>();
        Robotnik.Produkty[] tablicaProduktowBezDiamentow = Robotnik.stworzTabliceProduktowBezDiamentow();
        HashMap<Robotnik.Produkty, Double> paryProduktyLiczbaPosiadana = this.getZasoby()
                .stworzParyProduktLiczbaZasobu();
        for (int i = 0; i < tablicaProduktowBezDiamentow.length; i++) {
            Double liczbaProduktowDoKupienia = 100.0;
            Double cenaKupna = 0.0;
            // Ekstraktujemy informacje o liczbie dotepnego Zasobu Spekulanta danego Produktu
            Double liczbaProduktowDoSprzedania = paryProduktyLiczbaPosiadana.get(tablicaProduktowBezDiamentow[i]);
            Double cenaSprzedaży = 0.0;
            // Tworzymy ArrayListe w ktorej na indeksie 0 będzie liczba jednostek danego Produktu do Sprzedania
            // a na indeksie 1 będzie cena sprzedaży lub kupna za jednostkę danego produktu
            ArrayList<Double> liczbaJednostekOrazCenaSprzedazy = new ArrayList<>();
            liczbaJednostekOrazCenaSprzedazy.add(liczbaProduktowDoSprzedania);
            liczbaJednostekOrazCenaSprzedazy.add(cenaSprzedaży);
            // Postępujemy analogicznie lecz dla kupna
            ArrayList<Double> liczbaJednostekOrazCenaKupna = new ArrayList<>();
            liczbaJednostekOrazCenaKupna.add(liczbaProduktowDoKupienia);
            liczbaJednostekOrazCenaKupna.add(cenaKupna);

            // Tworzymy mapę która będzie mieć klucze SPRZEDAZ lub KUPNO i w zależności od tego będziemy
            // mieć dostęp do ArrayListy z informacjami o sprzedaży lub kupnie.
            HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>> mapaSprzedazyIKupna = new HashMap<>();
            mapaSprzedazyIKupna.put(SpekulantSredni.sprzedazCzyKupno.SPRZEDAZ, liczbaJednostekOrazCenaSprzedazy);
            mapaSprzedazyIKupna.put(SpekulantSredni.sprzedazCzyKupno.KUPNO, liczbaJednostekOrazCenaKupna);

            // Dodajemy te mapę dla danego Produktu do końcowej mapy
            ofertyKupnaISprzedaży.put(tablicaProduktowBezDiamentow[i], mapaSprzedazyIKupna);
        }

        return ofertyKupnaISprzedaży;
    }

    public abstract HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>>
            stworzOfertySpekulantow();

    public ZasobyRobotnika getZasoby() {
        return zasoby;
    }

    public InformacjeOGieldzie getInformacjeOGieldzie() {
        return informacjeOGieldzie;
    }
}