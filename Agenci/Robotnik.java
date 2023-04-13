package Agenci;

import StrategiaDnia.StrategiaDnia;
import StrategiaKariery.StrategiaKariery;
import StrategiaKupowania.StrategiaKupowania;
import StrategiaProdukcji.StrategiaProdukcji;
import Zasoby.ZasobyRobotnika;
import Gielda.InformacjeOGieldzie;

import java.util.HashMap;

public class Robotnik extends Agent {
    public enum SciezkaZawodu {
        GORNIK, ROLNIK, RZEMIESLNIK, PROGRAMISTA, INZYNIER
    }
    public enum Premie {
        PREMIAZANARZEDZIA, PREMIAZAPOZIOM, KARAZAUBRANIE, KARAZAJEDZENIE, PREMIAZAAKTUALNACIEZKE
    }
    public enum Produkty {
        NARZEDZIA, PROGRAMYKOMPUTEROWE, UBRANIA, JEDZENIE, DIAMENTY
    }

    // Do wczytania z pliku JSON
    private String kariera;
    // Bazowy wektor produktywności to ta liczba czytana z wejścia (np 100). Do wczytania z pliku JSON
    private final Integer[] produktywnosc;
    private HashMap<Premie, Integer> wektorProduktywnosci;

    // Pary SciezkaZawodu, poziom tej ścieżki
    private HashMap<SciezkaZawodu, Integer> sciezkiZawodowe;

    private final StrategiaDnia strategiaDnia;
    private final StrategiaKariery strategiaKariery;
    private final StrategiaKupowania strategiaKupowania;
    private final StrategiaProdukcji strategiaProdukcji;
    private ZasobyRobotnika zasobyRobotnika;
    private SciezkaZawodu aktualnaSciezkaZawodu;
    private InformacjeOGieldzie informacjeOGieldzie;

    // To trzeba ustawiać odrazu po wyprodukowaniu przez Robotników na początku dnia
    private Double liczbaWyprodukowanychProduktowWDanejRundzie;

    private int dniBezJedzenia;

    // Tu powinny być dane wczytane z JSONA
    public Robotnik(int id, Integer[] produktywnosc, StrategiaDnia strategiaDnia, StrategiaKariery strategiaKariery,
                    StrategiaKupowania strategiaKupowania, StrategiaProdukcji strategiaProdukcji) {
        super(id);
        this.produktywnosc = produktywnosc;
        this.strategiaDnia = strategiaDnia;
        this.strategiaKariery = strategiaKariery;
        this.strategiaKupowania = strategiaKupowania;
        this.strategiaProdukcji = strategiaProdukcji;
    }

    static public Produkty[] stworzTabliceProduktowZDiamentami() {
        Produkty[] tablicaProduktow = new Produkty[5];
        tablicaProduktow[4] = Robotnik.Produkty.JEDZENIE;
        tablicaProduktow[3] = Robotnik.Produkty.UBRANIA;
        tablicaProduktow[2] = Robotnik.Produkty.NARZEDZIA;
        tablicaProduktow[1] = Robotnik.Produkty.DIAMENTY;
        tablicaProduktow[0] = Robotnik.Produkty.PROGRAMYKOMPUTEROWE;
        return tablicaProduktow;
    }

    static public Produkty[] stworzTabliceProduktowBezDiamentow() {
        Produkty[] tablicaProduktow = new Produkty[4];
        tablicaProduktow[3] = Robotnik.Produkty.JEDZENIE;
        tablicaProduktow[2] = Robotnik.Produkty.UBRANIA;
        tablicaProduktow[1] = Robotnik.Produkty.NARZEDZIA;
        tablicaProduktow[0] = Robotnik.Produkty.PROGRAMYKOMPUTEROWE;
        return tablicaProduktow;
    }

    // Pod indeksem 0 jest produkcja bazowa Programow, indeksem 1 Jezdenia, indeksem 2 Diamentow, indeksem 3
    // Ubran, indeksem 4 Narzedzi
    public Integer[] getProduktywnosc() {
        return produktywnosc;
    }

    public HashMap<SciezkaZawodu, Integer> getSciezkiZawodowe() {
        return sciezkiZawodowe;
    }

    public SciezkaZawodu getAktualnaSciezkaZawodu() {
        return aktualnaSciezkaZawodu;
    }

    public HashMap<Premie, Integer> getWektorProduktywnosci() {
        return wektorProduktywnosci;
    }

    public int getDniBezJedzenia() {
        return dniBezJedzenia;
    }

    public void setDniBezJedzenia(int dniBezJedzenia) {
        this.dniBezJedzenia = dniBezJedzenia;
    }

    public ZasobyRobotnika getZasobyRobotnika() {
        return zasobyRobotnika;
    }

    public InformacjeOGieldzie getInformacjeOGieldzie() {
        return informacjeOGieldzie;
    }

    public Double getLiczbaWyprodukowanychProduktowWDanejRundzie() {
        return liczbaWyprodukowanychProduktowWDanejRundzie;
    }

    public StrategiaDnia getStrategiaDnia() {
        return strategiaDnia;
    }

    public StrategiaKariery getStrategiaKariery() {
        return strategiaKariery;
    }

    public StrategiaKupowania getStrategiaKupowania() {
        return strategiaKupowania;
    }

    public StrategiaProdukcji getStrategiaProdukcji() {
        return strategiaProdukcji;
    }

    public void setAktualnaSciezkaZawodu(SciezkaZawodu aktualnaSciezkaZawodu) {
        this.aktualnaSciezkaZawodu = aktualnaSciezkaZawodu;
    }

    public void podniesPoziomDanegoZawoduOJeden(SciezkaZawodu sciezka) {
        this.sciezkiZawodowe.replace(sciezka, this.sciezkiZawodowe.get(sciezka) + 1);
    }

    static public HashMap<SciezkaZawodu, Produkty> paryRobotnikProdukt() {
        HashMap<SciezkaZawodu, Produkty> pary = new HashMap<>();
        pary.put(SciezkaZawodu.GORNIK, Produkty.DIAMENTY);
        pary.put(SciezkaZawodu.PROGRAMISTA, Produkty.PROGRAMYKOMPUTEROWE);
        pary.put(SciezkaZawodu.INZYNIER, Produkty.NARZEDZIA);
        pary.put(SciezkaZawodu.ROLNIK, Produkty.JEDZENIE);
        pary.put(SciezkaZawodu.RZEMIESLNIK, Produkty.UBRANIA);
        return pary;
    }

    static public HashMap<Produkty, SciezkaZawodu> paryProduktRobotnik() {
        HashMap<Produkty, SciezkaZawodu> pary = new HashMap<>();
        pary.put(Produkty.DIAMENTY, SciezkaZawodu.GORNIK);
        pary.put(Produkty.PROGRAMYKOMPUTEROWE, SciezkaZawodu.PROGRAMISTA);
        pary.put(Produkty.NARZEDZIA, SciezkaZawodu.INZYNIER);
        pary.put(Produkty.JEDZENIE, SciezkaZawodu.ROLNIK);
        pary.put(Produkty.UBRANIA, SciezkaZawodu.RZEMIESLNIK);
        return pary;
    }

    static public HashMap<Produkty, Double> paryProduktLiczbaBezDiamentow() {
        HashMap<Produkty, Double> pary = new HashMap<>();
        pary.put(Produkty.PROGRAMYKOMPUTEROWE, 0.0);
        pary.put(Produkty.NARZEDZIA, 0.0);
        pary.put(Produkty.JEDZENIE, 0.0);
        pary.put(Produkty.UBRANIA, 0.0);
        return pary;
    }
}

