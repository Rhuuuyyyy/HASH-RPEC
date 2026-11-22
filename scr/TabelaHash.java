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
                ultimasSondagens = sondagens;
                return atual.indice;
            }
            atual = atual.proximo;
        }
        ultimasSondagens = sondagens;
        return -1;
    }

    public int inserir(char[] chave, int tamanho, int indice) {
        int h = FuncoesHash.calcular(funcao, chave, tamanho);
        int posicao = FuncoesHash.posicao(h, capacidade);
        if (posicoes[posicao] != null) {
            ultimaColisao = 1;
        } else {
            ultimaColisao = 0;
        }
        int sondagens = 0;
        NoHash atual = posicoes[posicao];
        while (atual != null) {
            sondagens++;
            if (Texto.iguais(atual.chave, atual.tamanho, chave, tamanho) == 1) {
                ultimasSondagens = sondagens;
                return atual.indice;
            }
            atual = atual.proximo;
        }
        ultimasSondagens = sondagens;
        NoHash novo = new NoHash(chave, tamanho, indice);
        novo.proximo = posicoes[posicao];
        posicoes[posicao] = novo;
        quantidadeChaves++;
        return indice;
    }

    public int maiorLista() {
        int maior = 0;
        for (int p = 0; p < capacidade; p++) {
            int tamanhoLista = 0;
            NoHash atual = posicoes[p];
            while (atual != null) {
                tamanhoLista++;
                atual = atual.proximo;
            }
            if (tamanhoLista > maior) {
                maior = tamanhoLista;
            }
        }
        return maior;
    }
}
