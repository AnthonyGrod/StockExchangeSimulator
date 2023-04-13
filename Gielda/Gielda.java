package Gielda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Agenci.Robotnik;
import Agenci.Robotnik.*;
import Agenci.Spekulant;
import Oferta.OfertaKupnaRobotnika;
import Oferta.OfertaKupnaSpekulanta;
import Oferta.OfertaSprzedazyRobotnika;
import Oferta.OfertaSprzedazySpekulanta;

public class Gielda {
    public enum RobotnikCzySpekulant {
        ROBOTNIK, SPEKULANT
    }
    private final int dlugosc;
    // Mowi ktory dzien gieldy mamy aktualnie. Zaczynamy od pierwszego (lecz mamy tez dane z zerowego)
    private int aktualnyDzien;
    // okresla czy Gielda jest socjalistyczna, kapitalistyczna, czy zrownowazona
    private final String gielda;

    // ArrayLista przechowująca wszystkich żywych Robotników
    private ArrayList<Robotnik> robotnicy;

    // ArrayLista przechowująca wszystkich Spekulantów
    private ArrayList<Spekulant> spekulanci;
    private String typGieldy;
    private double[] srednieCeny;
    // ceny z dnia zerowego wczytywane z wejscia
    private double[] ceny;
    // Zmienna, która pod kluczem Produkty (np. NARZEDZIA lub JEDZENIE) przetrzymuje listę średnich cen
    // danego produktu z kolejnych dni. Jako pierwszy element listy to będzie średnia cena danego produktu z dnia
    // zerowego, drugi element z dnia drugiego itp.
    // Historia średnich cen tyczy się wyłącznie zrealizowanych ofert Spekulantów
    private HashMap<Produkty, List<Double>> historiaCen;

    // Zmienna przechowująca informację o tym ile Produktów danego typu który pojawiło się (w sensie sumarycznej
    // liczby jednostek w ofertach sprzedaży Spekulantów i Robotników) danego dnia. Jesli danego dnia zaden
    // produkt danego typu nie pojawil sie na gieldzie, to wstawiamy w to pole wartość 0.
    private HashMap<Produkty, List<Integer>> czestotliwoscWystepowaniaProduktow;

    // Zmienna przechowujaca informacje o tym ile produktow danego typu ktory pojawil sie w sensie sumarycznej
    // liczby jednostek w ofertach sprzedaży Robotników danego dnia. Jeśli danego dnia żaden produkt danego typu
    // nie pojawił się na giełdzie, to wstawiamy w to pole wartośc 0.
    private HashMap<Produkty, List<Integer>> czestotliwoscWystepowaniaProduktowWyprodukowanychPrzezRobotnikow;

    // Zmienna inicjalizowana z wejścia JSON
    private final int kara_za_brak_ubran;

    private ArrayList<OfertaKupnaRobotnika> ofertyKupnaRobotnika;
    private ArrayList<OfertaSprzedazyRobotnika> ofertySprzedazyRobotnika;
    private ArrayList<OfertaKupnaSpekulanta> ofertyKupnaSpekulanta;
    private ArrayList<OfertaSprzedazySpekulanta> ofertySprzedazySpekulanta;

    // Tu powinny być dane wczytane z JSONa
    public Gielda(int dlugosc, String gielda, int kara_za_brak_ubran) {
        this.dlugosc = dlugosc;
        this.gielda = gielda;
        this.kara_za_brak_ubran = kara_za_brak_ubran;
    }

    public HashMap<Produkty, List<Double>> getHistoriaCen() {
        return historiaCen;
    }

    public HashMap<Produkty, List<Integer>> getCzestotliwoscWystepowaniaProduktow() {
        return czestotliwoscWystepowaniaProduktow;
    }

    public HashMap<Produkty, List<Integer>> getCzestotliwoscWystepowaniaProduktowWyprodukowanychPrzezRobotnikow() {
        return czestotliwoscWystepowaniaProduktowWyprodukowanychPrzezRobotnikow;
    }

    public int getAktualnyDzien() {
        return aktualnyDzien;
    }

    public int getKara_za_brak_ubran() {
        return kara_za_brak_ubran;
    }

    public ArrayList<Robotnik> getRobotnicy() {
        return robotnicy;
    }

    public ArrayList<Spekulant> getSpekulanci() {
        return spekulanci;
    }

    public void setOfertyKupnaRobotnika(ArrayList<OfertaKupnaRobotnika> ofertyKupnaRobotnika) {
        this.ofertyKupnaRobotnika = ofertyKupnaRobotnika;
    }

    public void setOfertyKupnaSpekulanta(ArrayList<OfertaKupnaSpekulanta> ofertyKupnaSpekulanta) {
        this.ofertyKupnaSpekulanta = ofertyKupnaSpekulanta;
    }

    public void setOfertySprzedazyRobotnika(ArrayList<OfertaSprzedazyRobotnika> ofertySprzedazyRobotnika) {
        this.ofertySprzedazyRobotnika = ofertySprzedazyRobotnika;
    }

    public void setOfertySprzedazySpekulanta(ArrayList<OfertaSprzedazySpekulanta> ofertySprzedazySpekulanta) {
        this.ofertySprzedazySpekulanta = ofertySprzedazySpekulanta;
    }
}
