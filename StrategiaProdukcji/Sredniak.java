package StrategiaProdukcji;

import Agenci.Robotnik;

public class Sredniak implements StrategiaProdukcji {
    private final int historia_sredniej_produkcji;

    public Sredniak(int historia_sredniej_produkcji) {
        this.historia_sredniej_produkcji = historia_sredniej_produkcji;
    }

    @Override
    public Robotnik.Produkty coProdukowac(Robotnik robotnik) {
        Robotnik.Produkty najbardziejOplacalnyProdukt = null;
        Double sredniaCenaNajbardziejOplacalnegoProduktu = 0.0;
        int aktualnyDzien = robotnik.getInformacjeOGieldzie().getAktualnyDzien();
        int dzienDoPoliczenia = aktualnyDzien - 1;

        Robotnik.Produkty[] tablicaProduktow = Robotnik.stworzTabliceProduktowBezDiamentow();

        // Iterujemy się po wszystkich rodzajach produktow ktore mają zmienne ceny
        for (int i = 0; i < tablicaProduktow.length; i++) {
            dzienDoPoliczenia = aktualnyDzien - 1;
            // Iterujemy się po historia_sredniej_produkcji dni wstecz
            for (int j = 0; j < this.historia_sredniej_produkcji; j++) {
                if (dzienDoPoliczenia < 0) {
                    break;
                }
                if (robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i]).get(dzienDoPoliczenia)
                        > sredniaCenaNajbardziejOplacalnegoProduktu) {
                    sredniaCenaNajbardziejOplacalnegoProduktu =
                            robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i])
                                    .get(dzienDoPoliczenia);
                    najbardziejOplacalnyProdukt = tablicaProduktow[i];
                }
                dzienDoPoliczenia--;
            }
        }
        return najbardziejOplacalnyProdukt;
    }
}

