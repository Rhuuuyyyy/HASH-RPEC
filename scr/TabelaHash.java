public class TabelaHash {
    private NoHash[] posicoes;
    private int capacidade;
    private int funcao;

    public int quantidadeChaves;
    public int ultimasSondagens;
    public int ultimaColisao;

    public TabelaHash(int capacidade, int funcao) {
        this.capacidade = capacidade;
        this.funcao = funcao;
        this.posicoes = new NoHash[capacidade];
        this.quantidadeChaves = 0;
        this.ultimasSondagens = 0;
        this.ultimaColisao = 0;
    }

    public int buscar(char[] chave, int tamanho) {
        int h = FuncoesHash.calcular(funcao, chave, tamanho);
        int posicao = FuncoesHash.posicao(h, capacidade);
        int sondagens = 0;
        NoHash atual = posicoes[posicao];
        while(atual != null) {
            sondagens++;
            if(Texto.iguais(atual.chave, atual.tamanho, chave, tamanho) == 1) {
                ultimas sondagens = sondagens;
                return atual.indice;
            }
            atual = atual.proximo;
        }
        ultimasSondagens = sondagens;
        return -1;
    }
}
