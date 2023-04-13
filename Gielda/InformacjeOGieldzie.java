package Gielda;

import Agenci.Robotnik;

import java.util.HashMap;
import java.util.List;

public class InformacjeOGieldzie {
    Gielda gielda;
    public InformacjeOGieldzie(Gielda gielda) {
        this.gielda = gielda;
    }

    public int getAktualnyDzien() {
        return gielda.getAktualnyDzien();
    }

    private HashMap<Robotnik.Produkty, List<Integer>> czestotliwoscWystepowaniaProduktowWyprodukowanychPrzezRobotnikow;

    public HashMap<Robotnik.Produkty, List<Double>> getHistoriaCen() {
        return gielda.getHistoriaCen();
    }

    public HashMap<Robotnik.Produkty, List<Integer>> getCzestotliwoscWystepowaniaProduktow() {
        return gielda.getCzestotliwoscWystepowaniaProduktow();
    }

    public HashMap<Robotnik.Produkty, List<Integer>> getCzestotliwoscWystepowaniaProduktowWyprodukowanychPrzezRobotnikow() {
        return czestotliwoscWystepowaniaProduktowWyprodukowanychPrzezRobotnikow;
    }

    public int getKara_za_brak_ubran() {
        return gielda.getKara_za_brak_ubran();
    }
}
