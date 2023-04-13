package StrategiaKupowania;

import Agenci.Robotnik;

import java.util.HashMap;

public interface StrategiaKupowania {

    public HashMap<Robotnik.Produkty, Double> coKupic(Robotnik robotnik);
}
