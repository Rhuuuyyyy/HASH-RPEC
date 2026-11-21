public class Avaliacoes {
    public int[] usuario;
    public int[] livro;
    public int[] nota;
    public int quantidade;
    private int capacidade;

    public Avaliacoes(int capacidadeInicial) {
        capacidade = capacidadeInicial;
        usuario = new int[capacidade];
        livro = new int[capacidade];
        nota = new int[capacidade];
        quantidade = 0;
    }

    public void adicionar(int indiceUsuario, int indiceLivro, int notaCentesimos) {
        if (quantidade == capacidade) {
            crescer();
        }
        usuario[quantidade] = indiceUsuario;
        livro[quantidade] = indiceLivro;
        nota[quantidade] = notaCentesimos;
        quantidade++;
    }

    private void crescer() {
        int novaCapacidade = capacidade * 2;
        int[] novoUsuario = new int[novaCapacidade];
        int[] novoLivro = new int[novaCapacidade];
        int[] novaNota = new int[novaCapacidade];
        for (int i = 0; i < quantidade; i++) {
            novoUsuario[i] = usuario[i];
            novoLivro[i] = livro[i];
            novaNota[i] = nota[i];
        }
        usuario = novoUsuario;
        livro = novoLivro;
        nota = novaNota;
        capacidade = novaCapacidade;
    }
}
