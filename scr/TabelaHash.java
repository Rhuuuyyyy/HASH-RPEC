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
}
