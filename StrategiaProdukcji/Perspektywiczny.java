package StrategiaProdukcji;

import Agenci.Robotnik;

public class Perspektywiczny implements StrategiaProdukcji {
    private final int historia_perspektywy;

    public Perspektywiczny(int historia_perspektywy) {
        this.historia_perspektywy = historia_perspektywy;
    }

    @Override
    public Robotnik.Produkty coProdukowac(Robotnik robotnik) {
        Robotnik.Produkty najbardziejOplacalnyProdukt = null;
        double najwiekszaRoznica = Double.MIN_VALUE;
        int aktualnyDzien = robotnik.getInformacjeOGieldzie().getAktualnyDzien();
        int wczoraj = aktualnyDzien - 1;
        int dzienHistoriaPerspektywyDniTemu = aktualnyDzien - this.historia_perspektywy;

        Robotnik.Produkty[] tablicaProduktow = Robotnik.stworzTabliceProduktowBezDiamentow();

        if (robotnik.getInformacjeOGieldzie().getAktualnyDzien() - historia_perspektywy < 0) {
            dzienHistoriaPerspektywyDniTemu = 0;
        }

        // Iterujemy się po wszystkich rodzajach produktow ktore mają zmienne ceny
        for (int i = 0; i < tablicaProduktow.length; i++) {
            if (robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i]).get(wczoraj)
                    - robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i])
                    .get(dzienHistoriaPerspektywyDniTemu) > najwiekszaRoznica) {
                najwiekszaRoznica = robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i])
                        .get(wczoraj) - robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i])
                        .get(dzienHistoriaPerspektywyDniTemu);
                najbardziejOplacalnyProdukt = tablicaProduktow[i];
            }
        }
        return najbardziejOplacalnyProdukt;
    }
}

