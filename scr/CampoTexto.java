public class CampoTexto {
    public char[] dados;
    public int tamanho;
    private int capacidade;

    public CampoTexto(int capacidadeInicial) {
        capacidade = capacidadeInicial;
        dados = new char[capacidade];
        tamanho = 0;
    }

    public void limpar() {
        tamanho = 0;
    }

    public void adicionar(char c) {
        if (tamanho == capacidade) {
            int novaCapacidade = capacidade * 2;
            char[] novos = new char[novaCapacidade];
            for (int i = 0; i < tamanho; i++) {
                novos[i] = dados[i];
            }
            dados = novos;
            capacidade = novaCapacidade;
        }
        dados[tamanho] = c;
        tamanho++;
    }
}
