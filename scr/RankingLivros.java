public class RankingLivros {
    public int[] indices;
    public int quantidade;
    private int capacidade;

    public RankingLivros(int capacidade) {
        this.capacidade = capacidade;
        this.indices = new int[capacidade];
        this.quantidade = 0;
    }

    public void construir(BaseDeDados base, int nMinimo) {
        quantidade = 0;
        for (int candidato = 0; candidato < base.quantidadeLivros; candidato++) {
            if (base.livros[candidato].n < nMinimo) {
                continue;
            }
            int posicao;
            if (quantidade < capacidade) {
                posicao = quantidade;
                quantidade++;
            } else if (MelhorLivro.compararLivros(base, candidato, indices[capacidade - 1]) > 0) {
                posicao = capacidade - 1;
            } else {
                continue;
            }
            while (posicao > 0
                   && MelhorLivro.compararLivros(base, candidato, indices[posicao - 1]) > 0) {
                indices[posicao] = indices[posicao - 1];
                posicao--;
            }
            indices[posicao] = candidato;
        }
    }
}
