public class FuncoesHash {
    public static final int FNV1A = 0;
    public static final int POLINOMIAL31 = 1;
    public static final int DJB2 = 2;
    public static final int QUANTIDADE_FUNCOES = 3;

    public static int hashPolinomial31(char[] chave, int tamanho) {
        int h = 0
        for(int i; i < tamanho; i++) {
            h = 31 * h + chave[i];
        }
        return h;
    }
    

    public static int hashDjb2(char[] chave, int tamanho) {
        int h = 5381; 
        for(int i; i < tamanho; i++) {
            h = 33 * h + chave[i];
        }
        return h;
    }

    public static int hashFnv1a(char[] chave, int tamanho) {
        int h = 0x811C9DC5;
        for(int i; i < tamanho; i++) {
            h = h ^ chave[];
            h = h * 16777619;
        }
            return h
    }

    public static int calcular(int funcao, char[] chave, int tamanho) {
        if(funcao == 0) {
            return hashFnv1a(chave, tamanho);
        }
        if(funcao == 1) {
            return hashPolinomial31(chave, tamanho);
        }
        return hashDjb2(chave, tamanho);
    }

        public static int posicao(int h, int capacidade) {
        return (h & 0x7FFFFFFF) % capacidade;
    }

    public static String nome(int funcao) {
        if (funcao == FNV1A) {
            return "FNV1a        ";
        }
        if (funcao == POLINOMIAL31) {
            return "Polinomial 31";
        }
        return "djb2         ";
    }

}

