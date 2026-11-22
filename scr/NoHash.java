public class NoHash {
    public char[] chave;
    public int tamanho;
    public int indice;
    public NoHash proximo;

    public NoHash(char[] chave, int tamanho, int indice) {
        this.chave = chave;
        this.tamanho = tamanho;
        this.indice = indice;
        this.proximo = null;
    }
}