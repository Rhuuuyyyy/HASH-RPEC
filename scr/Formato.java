public class Formato {

    public static int escreverInteiro(int valor, char[] destino, int inicio) {
        int pos = inicio;
        if (valor < 0) {
            destino[pos] = '-';
            pos++;
            valor = -valor;
        }
        int comeco = pos;
        if (valor == 0) {
            destino[pos] = '0';
            pos++;
        }
        while (valor > 0) {
            destino[pos] = (char) ('0' + valor % 10);
            pos++;
            valor = valor / 10;
        }
        int esquerda = comeco;
        int direita = pos - 1;
        while (esquerda < direita) {
            char troca = destino[esquerda];
            destino[esquerda] = destino[direita];
            destino[direita] = troca;
            esquerda++;
            direita--;
        }
        return pos - inicio;
    }

    private static void escreverComZeros(int valor, int digitos, char[] destino, int inicio) {
        for (int k = digitos - 1; k >= 0; k--) {
            destino[inicio + k] = (char) ('0' + valor % 10);
            valor = valor / 10;
        }
    }

    public static int escreverDecimal(double valor, int casas, char[] destino, int inicio) {
        int potencia = 1;
        for (int k = 0; k < casas; k++) {
            potencia = potencia * 10;
        }
        int inteira = (int) valor;
        int fracao = (int) ((valor - inteira) * potencia + 0.5);
        if (fracao >= potencia) {
            inteira++;
            fracao = fracao - potencia;
        }
        int pos = inicio + escreverInteiro(inteira, destino, inicio);
        if (casas > 0) {
            destino[pos] = '.';
            pos++;
            escreverComZeros(fracao, casas, destino, pos);
            pos = pos + casas;
        }
        return pos - inicio;
    }

    public static int escreverMedia(int somaCentesimos, int n, char[] destino, int inicio) {
        double numerador = somaCentesimos * 200.0 + n;
        int mediaVezes10000 = (int) (numerador / (2.0 * n));
        int pos = inicio + escreverInteiro(mediaVezes10000 / 10000, destino, inicio);
        destino[pos] = '.';
        pos++;
        escreverComZeros(mediaVezes10000 % 10000, 4, destino, pos);
        pos = pos + 4;
        return pos - inicio;
    }

    public static int escreverNota(int centesimos, char[] destino, int inicio) {
        int pos = inicio + escreverInteiro(centesimos / 100, destino, inicio);
        destino[pos] = '.';
        pos++;
        int resto = centesimos % 100;
        if (resto == 0) {
            destino[pos] = '0';
            pos++;
        } else if (resto % 10 == 0) {
            destino[pos] = (char) ('0' + resto / 10);
            pos++;
        } else {
            escreverComZeros(resto, 2, destino, pos);
            pos = pos + 2;
        }
        return pos - inicio;
    }
}
