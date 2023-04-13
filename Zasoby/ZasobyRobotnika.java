package Zasoby;

import Agenci.Robotnik;

import java.util.HashMap;
import java.util.List;

public class ZasobyRobotnika {
    // Poniższe Zasoby są tymi niewyprodukwanymi przez posiadającego je Robotnika (tzn. może je używać)
    private double liczbaDiamentow;
    private int liczbaJedzenia;
    private List<Narzedzie> narzedzia;
    private List<ProgramKomputerowy> programyKomputerowe;
    private List<Ubranie> ubrania;


    // Poniższe Zasoby są tymi wyprodukwanymi przez posiadającego je Robotnika (tzn. nie może ich używać)
    private int liczbaWyprodukowanegoJedzenia;
    private List<Narzedzie> wyprodukowaneNarzedzia;
    private List<ProgramKomputerowy> wyprodukowaneProgramyKomputerowe;
    private List<Ubranie> wyprodukowaneUbrania;


    public double getLiczbaDiamentow() {
        return liczbaDiamentow;
    }

    public List<ProgramKomputerowy> getProgramyKomputerowe() {
        return programyKomputerowe;
    }

    public List<Narzedzie> getNarzedzia() {
        return narzedzia;
    }

    public List<Ubranie> getUbrania() {
        return ubrania;
    }

    public double getLiczbaJedzenia() {
        return liczbaJedzenia;
    }

    public double getLiczbaUbran() {
        return this.ubrania.size();
    }

    public double getLiczbaNarzedzi() {
        return this.narzedzia.size();
    }

    public double getLiczbaProgramow() {
        return this.programyKomputerowe.size();
    }

    public int getLiczbaWyprodukowanegoJedzenia() {
        return liczbaWyprodukowanegoJedzenia;
    }

    public List<Narzedzie> getWyprodukowaneNarzedzia() {
        return wyprodukowaneNarzedzia;
    }

    public List<ProgramKomputerowy> getWyprodukowaneProgramyKomputerowe() {
        return wyprodukowaneProgramyKomputerowe;
    }

    public List<Ubranie> getWyprodukowaneUbrania() {
        return wyprodukowaneUbrania;
    }

    public void setLiczbaDiamentow(double liczbaDiamentow) {
        this.liczbaDiamentow = liczbaDiamentow;
    }

    public void setLiczbaJedzenia(int liczbaJedzenia) {
        this.liczbaJedzenia = liczbaJedzenia;
    }

    public void setNarzedzia(List<Narzedzie> narzedzia) {
        this.narzedzia = narzedzia;
    }

    public void setProgramyKomputerowe(List<ProgramKomputerowy> programyKomputerowe) {
        this.programyKomputerowe = programyKomputerowe;
    }

    public void setLiczbaWyprodukowanegoJedzenia(int liczbaWyprodukowanegoJedzenia) {
        this.liczbaWyprodukowanegoJedzenia = liczbaWyprodukowanegoJedzenia;
    }

    public void setWyprodukowaneNarzedzia(List<Narzedzie> wyprodukowaneNarzedzia) {
        this.wyprodukowaneNarzedzia = wyprodukowaneNarzedzia;
    }

    public void setWyprodukowaneUbrania(List<Ubranie> wyprodukowaneUbrania) {
        this.wyprodukowaneUbrania = wyprodukowaneUbrania;
    }

    public void setWyprodukowaneProgramyKomputerowe(List<ProgramKomputerowy> wyprodukowaneProgramyKomputerowe) {
        this.wyprodukowaneProgramyKomputerowe = wyprodukowaneProgramyKomputerowe;
    }

    public void setUbrania(List<Ubranie> ubrania) {
        this.ubrania = ubrania;
    }

    public HashMap<Robotnik.Produkty, Double> stworzParyProduktLiczbaZasobu() {
        HashMap<Robotnik.Produkty, Double> wynik = new HashMap<>();
        wynik.put(Robotnik.Produkty.NARZEDZIA, this.getLiczbaNarzedzi());
        wynik.put(Robotnik.Produkty.PROGRAMYKOMPUTEROWE, this.getLiczbaProgramow());
        wynik.put(Robotnik.Produkty.JEDZENIE, this.getLiczbaJedzenia());
        wynik.put(Robotnik.Produkty.UBRANIA, this.getLiczbaUbran());
        return wynik;
    }
}
