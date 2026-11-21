public class Cronometro {
    private double inicio;

    public void iniciar() {
        inicio = System.nanoTime();
    }

    public double parar() {
        double fim = System.nanoTime();
        return (fim - inicio) / 1000000.0;
    }
}
