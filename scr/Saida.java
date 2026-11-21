public class Saida {

    private static char[] numero = new char[48];

    public static void texto(String s) {
        System.out.print(s);
    }

    public static void linha(String s) {
        System.out.println(s);
    }

    public static void novaLinha() {
        System.out.println();
    }

    public static void caracteres(char[] dados, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            System.out.print(dados[i]);
        }
    }

    private static void espacos(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            System.out.print(' ');
        }
    }

    public static void inteiro(int valor) {
        int n = Formato.escreverInteiro(valor, numero, 0);
        caracteres(numero, n);
    }

    public static void decimal(double valor, int casas) {
        int n = Formato.escreverDecimal(valor, casas, numero, 0);
        caracteres(numero, n);
    }

    public static void inteiroAlinhado(int valor, int largura) {
        int n = Formato.escreverInteiro(valor, numero, 0);
        espacos(largura - n);
        caracteres(numero, n);
    }

    public static void decimalAlinhado(double valor, int casas, int largura) {
        int n = Formato.escreverDecimal(valor, casas, numero, 0);
        espacos(largura - n);
        caracteres(numero, n);
    }

    public static void media(int somaCentesimos, int n) {
        int tamanho = Formato.escreverMedia(somaCentesimos, n, numero, 0);
        caracteres(numero, tamanho);
    }

    public static void nota(int centesimos) {
        int tamanho = Formato.escreverNota(centesimos, numero, 0);
        caracteres(numero, tamanho);
    }
}
