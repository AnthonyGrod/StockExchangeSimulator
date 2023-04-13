package StrategiaProdukcji;

import Agenci.Robotnik;

import java.util.HashMap;

public class Chciwy implements StrategiaProdukcji {

    // Zwraca null jeśli robotnik nic nie wyprodukuje
    @Override
    public Robotnik.Produkty coProdukowac(Robotnik robotnik) {
        double zyskZaUbrania = 0;
        double zyskZaNarzedzia = 0;
        double zyskZaJedzenie = 0;
        double zyskZaProgramy = 0;
        double zyskZaDiamenty = 0;

        double premiaZaNarzedzia = robotnik.getWektorProduktywnosci().get(Robotnik.Premie.PREMIAZANARZEDZIA);
        double kara = robotnik.getWektorProduktywnosci().get(Robotnik.Premie.KARAZAJEDZENIE) +
                robotnik.getWektorProduktywnosci().get(Robotnik.Premie.KARAZAUBRANIE);
        double premiaZaZawod = robotnik.getWektorProduktywnosci().get(Robotnik.Premie.PREMIAZAPOZIOM);
        double premiaBezZawodu = premiaZaNarzedzia - kara;
        double premiaZZawodem = premiaZaZawod + premiaBezZawodu;

        double produkcjaBazowaProgramow = robotnik.getProduktywnosc()[0];
        double produkcjaBazowaJedzenia = robotnik.getProduktywnosc()[1];
        double produkcjaBazowaDiamentow = robotnik.getProduktywnosc()[2];
        double produkcjaBazowaUbran = robotnik.getProduktywnosc()[3];
        double produkcjaBazowaNarzedzi = robotnik.getProduktywnosc()[4];

        // Obliczamy potencjalny zysk za dany towar
        if (robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.GORNIK) {
            zyskZaDiamenty = (premiaZZawodem + produkcjaBazowaDiamentow) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.DIAMENTY)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        } else {
            zyskZaDiamenty = (premiaBezZawodu + produkcjaBazowaDiamentow) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.DIAMENTY)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        }

        if (robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.ROLNIK) {
            zyskZaJedzenie = (premiaZZawodem + produkcjaBazowaJedzenia) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.JEDZENIE)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        } else {
            zyskZaJedzenie = (premiaBezZawodu + produkcjaBazowaJedzenia) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.JEDZENIE)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        }

        if (robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.RZEMIESLNIK) {
            zyskZaUbrania = (premiaZZawodem + produkcjaBazowaUbran) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.UBRANIA)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        } else {
            zyskZaUbrania = (premiaBezZawodu + produkcjaBazowaUbran) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.UBRANIA)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        }

        if (robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.INZYNIER) {
            zyskZaNarzedzia = (premiaZZawodem + produkcjaBazowaNarzedzi) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.NARZEDZIA)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        } else {
            zyskZaNarzedzia = (premiaBezZawodu + produkcjaBazowaNarzedzi) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.NARZEDZIA)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        }

        if (robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.PROGRAMISTA) {
            zyskZaProgramy = (premiaZZawodem + produkcjaBazowaProgramow) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.PROGRAMYKOMPUTEROWE)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        } else {
            zyskZaNarzedzia = (premiaBezZawodu + produkcjaBazowaProgramow) * 100
                    * robotnik.getInformacjeOGieldzie().getHistoriaCen().get(Robotnik.Produkty.PROGRAMYKOMPUTEROWE)
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
        }

        // Rozstrzygamy ktory towar przyniesie najwiekszy zysk
        HashMap<Robotnik.Produkty, Double> zyskZaPoszczegolnyTowar = new HashMap<>();
        Robotnik.Produkty[] tablicaProduktow = Robotnik.stworzTabliceProduktowZDiamentami();

        zyskZaPoszczegolnyTowar.put(Robotnik.Produkty.JEDZENIE, zyskZaJedzenie);
        zyskZaPoszczegolnyTowar.put(Robotnik.Produkty.NARZEDZIA, zyskZaNarzedzia);
        zyskZaPoszczegolnyTowar.put(Robotnik.Produkty.PROGRAMYKOMPUTEROWE, zyskZaProgramy);
        zyskZaPoszczegolnyTowar.put(Robotnik.Produkty.UBRANIA, zyskZaUbrania);
        zyskZaPoszczegolnyTowar.put(Robotnik.Produkty.DIAMENTY, zyskZaDiamenty);
        double zysk = 0;
        Robotnik.Produkty produktNajbardziejOplacalny = null;
        for (int i = 0; i < zyskZaPoszczegolnyTowar.size(); i++) {
            if (zyskZaPoszczegolnyTowar.get(tablicaProduktow[i]) > zysk) {
                zysk = zyskZaPoszczegolnyTowar.get(tablicaProduktow[i]);
                produktNajbardziejOplacalny = tablicaProduktow[i];
            }
        }
        // Jesli najwiekszy zysk jest wiekszy od zera to zwracamy ten produkt ktory ten zysk wygeneruje
        if (zysk > 0) {
            return produktNajbardziejOplacalny;
        }
        // W przeciwnym wypadku zwracamy null bo Robotnik nic nie wyprodukuje
        return null;
    }
}
