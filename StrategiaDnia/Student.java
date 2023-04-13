package StrategiaDnia;

import Agenci.Robotnik;

public class Student implements StrategiaDnia {
    private final int zapas;
    private final int okres;

    public Student(int zapas, int okres) {
        this.okres = okres;
        this.zapas = zapas;
    }

    @Override
    public NaukaCzyPraca uczycSieCzyPracowac(Robotnik robotnik) {
        // Obliczamy średnią arytmetyczną cen Jedzenia z ostatnich okres dni
        double sredniaCenaJedzeniaZOkresOstatnichDni = 0.0;
        int zliczanyDzien = robotnik.getInformacjeOGieldzie().getAktualnyDzien() - 1;
        int zliczoneDni = 0;
        for (int i = 0; i < this.okres; i++) {
            if (zliczanyDzien < 0) {
                break;
            }
            zliczoneDni++;
            sredniaCenaJedzeniaZOkresOstatnichDni += robotnik.getInformacjeOGieldzie()
                    .getHistoriaCen().get(Robotnik.Produkty.JEDZENIE).get(zliczanyDzien);
            zliczanyDzien--;
        }
        if (zliczoneDni != 0) {
            sredniaCenaJedzeniaZOkresOstatnichDni = sredniaCenaJedzeniaZOkresOstatnichDni / zliczoneDni;
        }

        // Obliczamy, czy stać Studenta na kupno zapas * 100 sztuk Jedzenia po ustalonej cenie
        double cenaZaZapas100SztukJedzenia = this.zapas * 100 * sredniaCenaJedzeniaZOkresOstatnichDni;
        if (cenaZaZapas100SztukJedzenia > robotnik.getZasobyRobotnika().getLiczbaDiamentow()) {
            return NaukaCzyPraca.PRACA;
        } else {
            return NaukaCzyPraca.NAUKA;
        }
    }
}
