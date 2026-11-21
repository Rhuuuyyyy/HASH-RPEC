public class Texto {

    public static int iguais(char[] a, int tamanhoA, char[] b, int tamanhoB) {
        if (tamanhoA != tamanhoB) {
            return 0;
        }
        for (int i = 0; i < tamanhoA; i++) {
            if (a[i] != b[i]) {
                return 0;
            }
        }
        return 1;
    }

    public static int comparar(char[] a, int tamanhoA, char[] b, int tamanhoB) {
        int limite = tamanhoA;
        if (tamanhoB < limite) {
            limite = tamanhoB;
        }
        for (int i = 0; i < limite; i++) {
            if (a[i] < b[i]) {
                return -1;
            }
            if (a[i] > b[i]) {
                return 1;
            }
        }
        if (tamanhoA < tamanhoB) {
            return -1;
        }
        if (tamanhoA > tamanhoB) {
            return 1;
        }
        return 0;
    }

    public static char[] copiar(char[] origem, int tamanho) {
        char[] copia = new char[tamanho];
        for (int i = 0; i < tamanho; i++) {
            copia[i] = origem[i];
        }
        return copia;
    }

    public static int converterNota(char[] campo, int tamanho) {
        int i = 0;
        int inteira = 0;
        int digitosInteiros = 0;
        while (i < tamanho && campo[i] >= '0' && campo[i] <= '9') {
            if (digitosInteiros < 3) {
                inteira = inteira * 10 + (campo[i] - '0');
            }
            digitosInteiros++;
            i++;
        }
        if (digitosInteiros == 0 || digitosInteiros > 3) {
            return -1;
        }
        int centesimos = inteira * 100;
        if (i < tamanho) {
            if (campo[i] != '.') {
                return -1;
            }
            i++;
            int digitosFracao = 0;
            while (i < tamanho && campo[i] >= '0' && campo[i] <= '9') {
                if (digitosFracao == 0) {
                    centesimos = centesimos + (campo[i] - '0') * 10;
                } else if (digitosFracao == 1) {
                    centesimos = centesimos + (campo[i] - '0');
                }
                digitosFracao++;
                i++;
            }
            if (digitosFracao == 0 || digitosFracao > 2 || i < tamanho) {
                return -1;
            }
        }
        if (centesimos < 100 || centesimos > 500) {
            return -1;
        }
        return centesimos;
    }
}
