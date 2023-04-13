package StrategiaProdukcji;

import Agenci.Robotnik;

public class Krotkowzroczny implements StrategiaProdukcji {

    @Override
    public Robotnik.Produkty coProdukowac(Robotnik robotnik) {
        Robotnik.Produkty najbardziejOplacalnyTowar = null;
        double najwyzszaSredniaCena = 0;
        Robotnik.Produkty[] tablicaProduktow = Robotnik.stworzTabliceProduktowBezDiamentow();
        // iterujemy się po wszystkich cenach z dnia poprzedniego (czyli aktualnyDzien - 1)
        for (int i = 0; i < 4; i++) {
            if (robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i])
                    .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1) > najwyzszaSredniaCena) {
                najwyzszaSredniaCena = robotnik.getInformacjeOGieldzie().getHistoriaCen().get(tablicaProduktow[i])
                        .get(robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1);
                najbardziejOplacalnyTowar = tablicaProduktow[i];
            }
        }
        return najbardziejOplacalnyTowar;
    }
}
