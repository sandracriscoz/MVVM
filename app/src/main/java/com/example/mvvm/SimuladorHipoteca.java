package com.example.mvvm;

public class SimuladorHipoteca {
    public static class Solicitud {
        public double capital;
        public int plazo;

        public Solicitud(double capital, int plazo) {
            this.capital = capital;
            this.plazo = plazo;
        }
    }

    public double calcular(Solicitud solicitud) {
        double inteses = 0;
        try {
            Thread.sleep(10000);
            inteses = 0.01605;
        } catch (InterruptedException e) {}
        return solicitud.capital*inteses/12/(1-Math.pow(1+(inteses/12),-solicitud.plazo*12));
    }
}
