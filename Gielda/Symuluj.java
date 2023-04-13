package Gielda;

import Agenci.Robotnik;
import Agenci.Spekulant;
import Agenci.SpekulantSredni;
import Oferta.OfertaKupnaRobotnika;
import Oferta.OfertaKupnaSpekulanta;
import Oferta.OfertaSprzedazyRobotnika;
import Oferta.OfertaSprzedazySpekulanta;
import StrategiaDnia.NaukaCzyPraca;
import Zasoby.Narzedzie;
import Zasoby.ProgramKomputerowy;
import Zasoby.Ubranie;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Symuluj {

    private final Gielda gielda;

    public Symuluj(Gielda gielda) {
        this.gielda = gielda;
    }

    // Jeśli funkcja zwróci wartość null, to znaczy, że Robotnik umiera
    public HashMap<Robotnik.Premie, Integer>  obliczWektorProduktywnosciRobotnika(Robotnik robotnik) {
        // Obliczamy PREMIAZANARZEDZIA
        Integer premiaZaNarzedzia = 0;
        for (int i = 0; i < robotnik.getZasobyRobotnika().getLiczbaNarzedzi(); i++) {
            premiaZaNarzedzia += robotnik.getZasobyRobotnika().getNarzedzia().get(i).getPoziomNarzedzia();
        }

        // Obliczamy PREMIAZAPOZIOM
        Integer premiaZaPoziomZawodu = 0;
        if (robotnik.getSciezkiZawodowe().get(robotnik.getAktualnaSciezkaZawodu()) == 1) {
            premiaZaPoziomZawodu = 50;
        } else if (robotnik.getSciezkiZawodowe().get(robotnik.getAktualnaSciezkaZawodu()) == 2) {
            premiaZaPoziomZawodu = 150;
        } else if (robotnik.getSciezkiZawodowe().get(robotnik.getAktualnaSciezkaZawodu()) == 3) {
            premiaZaPoziomZawodu = 300;
        } else {
            premiaZaPoziomZawodu = 300 + 25 *
                    (robotnik.getSciezkiZawodowe().get(robotnik.getAktualnaSciezkaZawodu()) - 3);
        }

        // Obliczamy KARAZAUBRANIE
        Integer karaZaUbranie = 0;
        if (robotnik.getZasobyRobotnika().getLiczbaUbran() < 100) {
            karaZaUbranie = robotnik.getInformacjeOGieldzie().getKara_za_brak_ubran() * (-1);
        }

        // Obliczamy KARAZAJEDZENIE
        Integer karaZaJedzenie = 0;
        if (robotnik.getDniBezJedzenia() == 1) {
            karaZaJedzenie = 100 * (-1);
        } else if (robotnik.getDniBezJedzenia() == 2) {
            karaZaJedzenie = 300 * (-1);
        } else if (robotnik.getDniBezJedzenia() == 3) {
            // Robotnik umiera
            return null;
        }

        // Obliczamy PREMIAZAAKTUALNACIEZKE, czyli bazową wartość produkcji
        Integer premiaZaAktualnaSciezke = 0;
        if (robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.PROGRAMISTA) {
            premiaZaAktualnaSciezke = robotnik.getProduktywnosc()[0];
        } else if ((robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.ROLNIK)) {
            premiaZaAktualnaSciezke = robotnik.getProduktywnosc()[1];
        } else if ((robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.GORNIK)) {
            premiaZaAktualnaSciezke = robotnik.getProduktywnosc()[2];
        } else if ((robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.RZEMIESLNIK)) {
            premiaZaAktualnaSciezke = robotnik.getProduktywnosc()[3];
        } else {
            premiaZaAktualnaSciezke = robotnik.getProduktywnosc()[4];
        }

        // Teraz tworzymy mapę którą mamy zwrócić
        HashMap<Robotnik.Premie, Integer>  wektorProduktywnosciRobotnika = new HashMap<>();
        wektorProduktywnosciRobotnika.put(Robotnik.Premie.PREMIAZANARZEDZIA, premiaZaNarzedzia);
        wektorProduktywnosciRobotnika.put(Robotnik.Premie.PREMIAZAAKTUALNACIEZKE, premiaZaAktualnaSciezke);
        wektorProduktywnosciRobotnika.put(Robotnik.Premie.PREMIAZAPOZIOM, premiaZaPoziomZawodu);
        wektorProduktywnosciRobotnika.put(Robotnik.Premie.KARAZAUBRANIE, karaZaUbranie);
        wektorProduktywnosciRobotnika.put(Robotnik.Premie.KARAZAJEDZENIE, karaZaJedzenie);

        return  wektorProduktywnosciRobotnika;
    }

    // Te funkcję można wywołać pod koniec dnia, gdy Robotnik nie będzie mieć wtedy już jedzenia
    public void wyrzucMartwychRobotnikow() {
        for (int i = 0; i < gielda.getRobotnicy().size(); i++) {
            if (gielda.getRobotnicy().get(i).getDniBezJedzenia() == 3) {
                gielda.getRobotnicy().remove(i);
            }
        }
    }

    // Zwraca pary id, NaukaCzyPraca
    public HashMap<Integer, NaukaCzyPraca> decyzjeNaukaCzyPraca() {
        HashMap<Integer, NaukaCzyPraca> decyzjeRobotnikow = new HashMap<>();
        for (int i = 0; i < gielda.getRobotnicy().size(); i++) {
            decyzjeRobotnikow.put(gielda.getRobotnicy().get(i).getId(), gielda.getRobotnicy().get(i).getStrategiaDnia()
                    .uczycSieCzyPracowac(gielda.getRobotnicy().get(i)));
        }

        return decyzjeRobotnikow;
    }

    // W tej funkcji będziemy dla Robotników uczących się będziemy odpowiednio zmieniać kariere lub podnosić
    // poziom aktualnej kariery i usuwać streak nie jedzenia. Ponadto dla Robotników pracujących będziemy
    // zwiększać zasoby produktów wyprodukowanych przez niego i odpowiednio usuwać ich Narzędzia
    public void przeprowadzNaukeIPraceRobotnikow(HashMap<Integer, NaukaCzyPraca> decyzjeNaukaCzyPraca) {
        for (int i = 0; i < decyzjeNaukaCzyPraca.size(); i++) {
            Robotnik robotnik = gielda.getRobotnicy().get(i);

            // Najpierw zajmiemy się uczącymi się
            if (decyzjeNaukaCzyPraca.get(robotnik.getId()) == NaukaCzyPraca.NAUKA) {
                robotnik.setDniBezJedzenia(0);
                // Sprawdzamy, czy zmienił ścieżkę
                if (robotnik.getStrategiaKariery().czyZmieniaKariere(robotnik) != robotnik.getAktualnaSciezkaZawodu()) {
                    // Jeśli tak, to zmieniamy jego aktualnaSciezkeZawodu
                    robotnik.setAktualnaSciezkaZawodu(robotnik.getStrategiaKariery().czyZmieniaKariere(robotnik));
                } else {
                    // W przeciwnym wypadku po prostu zwiększamy poziom jego aktualnej ścieżki
                    robotnik.podniesPoziomDanegoZawoduOJeden(robotnik.getAktualnaSciezkaZawodu());
                }
            } else {
                // Teraz możemy zająć się pracującymi
                Robotnik.Produkty produktDoProdukcji = robotnik.getStrategiaProdukcji().coProdukowac(robotnik);
                // Jeśli Robotnik ma ujemną produktywność to nic nie produkuje, więc musimy wyifować ten przypadek
                if (produktDoProdukcji != null) {
                    int sumarycznyProcentProdukcji = 0;
                    for (int j : robotnik.getWektorProduktywnosci().values()) {
                        sumarycznyProcentProdukcji += j;
                    }
                    if (produktDoProdukcji == Robotnik.Produkty.DIAMENTY) {
                        robotnik.getZasobyRobotnika().setLiczbaDiamentow(robotnik.getZasobyRobotnika()
                                .getLiczbaDiamentow() + robotnik.getProduktywnosc()[2] * sumarycznyProcentProdukcji);
                    } else if (produktDoProdukcji == Robotnik.Produkty.JEDZENIE) {
                        robotnik.getZasobyRobotnika().setLiczbaWyprodukowanegoJedzenia((int)robotnik.getZasobyRobotnika()
                                .getLiczbaJedzenia() + robotnik.getProduktywnosc()[1] * sumarycznyProcentProdukcji);
                    } else if (produktDoProdukcji == Robotnik.Produkty.PROGRAMYKOMPUTEROWE) {
                        int liczbaProgramowDoWyprodukowania = robotnik.getProduktywnosc()[0] * sumarycznyProcentProdukcji;
                        List<ProgramKomputerowy> listaWyprodukowanychProgramow = new ArrayList<>();
                        // Jeśli Robotnik jest Programistą, to tworzy listaWyprodukowanychProgramow poziomu swojej
                        // ścieżki Programisty.
                        if (robotnik.getAktualnaSciezkaZawodu() == Robotnik.SciezkaZawodu.PROGRAMISTA) {
                            for (int k = 0; k < liczbaProgramowDoWyprodukowania; k++) {
                                listaWyprodukowanychProgramow.add(new ProgramKomputerowy(robotnik
                                        .getSciezkiZawodowe().get(Robotnik.SciezkaZawodu.PROGRAMISTA)));
                            }
                        } else {
                            // W przeciwnym wypadku tworzy ich tyle samo ale o poziomie 1
                            for (int k = 0; k < liczbaProgramowDoWyprodukowania; k++) {
                                listaWyprodukowanychProgramow.add(new ProgramKomputerowy(1));
                            }
                        }
                        robotnik.getZasobyRobotnika().setWyprodukowaneProgramyKomputerowe(listaWyprodukowanychProgramow);
                    } else if (produktDoProdukcji == Robotnik.Produkty.NARZEDZIA) {
                        int liczbaNarzedziDoWyprodukowania = robotnik.getProduktywnosc()[4] * sumarycznyProcentProdukcji;
                        List<Narzedzie> listaWyprodukowanychNarzedzi = new ArrayList<>();
                        int liczbaProgramow = (int) robotnik.getZasobyRobotnika().getLiczbaProgramow();
                        // Produkujemy tyle narzędzi o poziomie programu ile jest tych programow
                        if (liczbaProgramow != 0) {
                            for (int k = 0; k < liczbaProgramow && k < liczbaNarzedziDoWyprodukowania; k++) {
                                listaWyprodukowanychNarzedzi.add(new Narzedzie(robotnik.getZasobyRobotnika()
                                        .getProgramyKomputerowe().get(k).getPoziomProgramu()));
                            }
                        }
                        // Produkujemy pozostałe narzędzia
                        for (int k = 0; k < liczbaNarzedziDoWyprodukowania - liczbaProgramow; k++) {
                            listaWyprodukowanychNarzedzi.add(new Narzedzie(1));
                        }
                        robotnik.getZasobyRobotnika().setWyprodukowaneNarzedzia(listaWyprodukowanychNarzedzi);
                    } else {
                        // W przeciwnym wypadku produkujemy Ubrania
                        int liczbaUbranDoWyprodukowania = robotnik.getProduktywnosc()[3] * sumarycznyProcentProdukcji;
                        List<Ubranie> listaWyprodukowanychUbran = new ArrayList<>();
                        int liczbaProgramow = (int) robotnik.getZasobyRobotnika().getLiczbaProgramow();
                        // Produkujemy tyle ubrań o poziomie programu ile jest tych programow
                        if (liczbaProgramow != 0) {
                            for (int k = 0; k < liczbaProgramow && k < liczbaUbranDoWyprodukowania; k++) {
                                listaWyprodukowanychUbran.add(new Ubranie(robotnik.getZasobyRobotnika()
                                        .getProgramyKomputerowe().get(k).getPoziomProgramu()));
                            }
                        }
                        // Produkujemy pozostałe ubrania
                        for (int k = 0; k < liczbaUbranDoWyprodukowania - liczbaProgramow; k++) {
                            listaWyprodukowanychUbran.add(new Ubranie(1));
                        }
                        robotnik.getZasobyRobotnika().setWyprodukowaneUbrania(listaWyprodukowanychUbran);
                    }
                }
            }
        }
    }

    public void przeprowadzWystawianieOfertSprzedazyIKupnaRobotnikow(HashMap<Integer, NaukaCzyPraca> decyzjeNaukaCzyPraca) {
        // Tworzymy oferty sprzedazy Robotnikow
        ArrayList<OfertaSprzedazyRobotnika> ofertySprzedazyRobotnikow = new ArrayList<>();
        for (int i = 0; i < decyzjeNaukaCzyPraca.size(); i++) {
            Robotnik robotnik = gielda.getRobotnicy().get(i);
            if (decyzjeNaukaCzyPraca.get(robotnik.getId()) == NaukaCzyPraca.PRACA) {
                if (robotnik.getZasobyRobotnika().getLiczbaWyprodukowanegoJedzenia() > 0) {
                    ofertySprzedazyRobotnikow.add(new OfertaSprzedazyRobotnika(Robotnik.Produkty.JEDZENIE,
                            robotnik.getZasobyRobotnika().getLiczbaWyprodukowanegoJedzenia(), robotnik.getId()));
                } else if (robotnik.getZasobyRobotnika().getWyprodukowaneNarzedzia() != null) {
                    ofertySprzedazyRobotnikow.add(new OfertaSprzedazyRobotnika(Robotnik.Produkty.NARZEDZIA,
                            robotnik.getZasobyRobotnika().getWyprodukowaneNarzedzia().size(), robotnik.getId()));
                } else if (robotnik.getZasobyRobotnika().getWyprodukowaneProgramyKomputerowe() != null) {
                    ofertySprzedazyRobotnikow.add(new OfertaSprzedazyRobotnika(Robotnik.Produkty.PROGRAMYKOMPUTEROWE,
                            robotnik.getZasobyRobotnika().getWyprodukowaneProgramyKomputerowe().size(),
                            robotnik.getId()));
                } else if (robotnik.getZasobyRobotnika().getWyprodukowaneUbrania() != null) {
                    ofertySprzedazyRobotnikow.add(new OfertaSprzedazyRobotnika(Robotnik.Produkty.UBRANIA,
                            robotnik.getZasobyRobotnika().getWyprodukowaneUbrania().size(), robotnik.getId()));
                }
            }
        }

        gielda.setOfertySprzedazyRobotnika(ofertySprzedazyRobotnikow);

        // Tworzymy oferty kupna Robotnikow
        ArrayList<OfertaKupnaRobotnika> ofertyKupnaRobotnikow = new ArrayList<>();
        Robotnik.Produkty[] tablicaProduktowBezDiamentow = Robotnik.stworzTabliceProduktowBezDiamentow();
        for (int i = 0; i < decyzjeNaukaCzyPraca.size(); i++) {
            for (int j = 0; j < tablicaProduktowBezDiamentow.length; j++) {
                Robotnik robotnik = gielda.getRobotnicy().get(i);
                Robotnik.Produkty produktDoKupienia = tablicaProduktowBezDiamentow[i];
                double liczbaProduktDoKupienia = robotnik.getStrategiaKupowania().coKupic(robotnik)
                        .get(produktDoKupienia);
                if (liczbaProduktDoKupienia > 0) {
                    ofertyKupnaRobotnikow.add(new OfertaKupnaRobotnika(produktDoKupienia, 100.0,
                            robotnik.getId()));
                }
            }
        }

        gielda.setOfertyKupnaRobotnika(ofertyKupnaRobotnikow);
    }

    public void przeprowadzWystawianieOfertSprzedazyIKupnaSpekulantow() {
        // Tworzymy oferty sprzedazy Spekulantow
        ArrayList<OfertaSprzedazySpekulanta> ofertySprzedazySpekulantow = new ArrayList<>();
        ArrayList<OfertaKupnaSpekulanta> ofertyKupnaSpekulantow = new ArrayList<>();
        Robotnik.Produkty[] tablicaProduktowBezDiamentow = Robotnik.stworzTabliceProduktowBezDiamentow();
        for (int i = 0; i < gielda.getSpekulanci().size(); i++) {
            Spekulant spekulant = gielda.getSpekulanci().get(i);
            HashMap<Robotnik.Produkty, HashMap<SpekulantSredni.sprzedazCzyKupno, ArrayList<Double>>>
                    ofertyKupnaISprzedazy = spekulant.stworzOfertySpekulantow();
            for (int j = 0; j < tablicaProduktowBezDiamentow.length; j++) {
                // Najpierw stworzymy oferty kupna. Sprawdzmy, czy Spekulant chce kupić dany produkt. Analogicznie
                // postępujemy dla Sprzedaży.
                if (ofertyKupnaISprzedazy.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.KUPNO).get(0) > 0) {
                    // Jesli tak, to tworzymy te oferte
                    ofertyKupnaSpekulantow.add(new OfertaKupnaSpekulanta(tablicaProduktowBezDiamentow[i],
                            ofertyKupnaISprzedazy.get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni
                                    .sprzedazCzyKupno.KUPNO).get(0), ofertyKupnaISprzedazy
                            .get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni.sprzedazCzyKupno.KUPNO).get(1),
                            spekulant.getId()));
                }
                if (ofertyKupnaISprzedazy.get(tablicaProduktowBezDiamentow[i])
                        .get(SpekulantSredni.sprzedazCzyKupno.SPRZEDAZ).get(0) > 0) {
                    // Jesli tak, to tworzymy te oferte
                    ofertySprzedazySpekulantow.add(new OfertaSprzedazySpekulanta(tablicaProduktowBezDiamentow[i],
                            ofertyKupnaISprzedazy.get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni
                                    .sprzedazCzyKupno.SPRZEDAZ).get(0), ofertyKupnaISprzedazy
                            .get(tablicaProduktowBezDiamentow[i]).get(SpekulantSredni.sprzedazCzyKupno.SPRZEDAZ).get(1),
                            spekulant.getId()));
                }
            }
        }

        gielda.setOfertyKupnaSpekulanta(ofertyKupnaSpekulantow);
        gielda.setOfertySprzedazySpekulanta(ofertySprzedazySpekulantow);
    }

    public void dopasujOferty() {
        return;
    }
    
}
