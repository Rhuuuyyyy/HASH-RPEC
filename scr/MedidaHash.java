public class MedidaHash {
    public static final int CAPACIDADE = 1000003;

    public int colisoes;
    public double totalSondagensSucesso;
    public double totalSondagensFalha;
    public int piorCaso;
    public double tempoInsercaoMs;
    public double tempoBuscaMs;
    public int maiorLista;
    public int errosDeBusca;

    public void medir(int funcao, char[][] chaves, int[] tamanhos, int n, char[] areaFalha) {
        TabelaHash tabela = new TabelaHash(CAPACIDADE, funcao);
        colisoes = 0;
        piorCaso = 0;
        totalSondagensSucesso = 0;
        totalSondagensFalha = 0;
        errosDeBusca = 0;
        Cronometro relogio = new Cronometro();

        relogio.iniciar();
        for (int i = 0; i < n; i++) {
            tabela.inserir(chaves[i], tamanhos[i], i);
            colisoes = colisoes + tabela.ultimaColisao;
            if (tabela.ultimasSondagens > piorCaso) {
                piorCaso = tabela.ultimasSondagens;
            }
        }
        tempoInsercaoMs = relogio.parar();

        relogio.iniciar();
        for (int i = 0; i < n; i++) {
            int achado = tabela.buscar(chaves[i], tamanhos[i]);
            totalSondagensSucesso = totalSondagensSucesso + tabela.ultimasSondagens;
            if (tabela.ultimasSondagens > piorCaso) {
                piorCaso = tabela.ultimasSondagens;
            }
            if (achado != i) {
                errosDeBusca++;
            }
        }
        tempoBuscaMs = relogio.parar();

        for (int i = 0; i < n; i++) {
            int tamanho = tamanhos[i];
            for (int k = 0; k < tamanho; k++) {
                areaFalha[k] = chaves[i][k];
            }
            areaFalha[tamanho] = (char) 1;
            int achado = tabela.buscar(areaFalha, tamanho + 1);
            totalSondagensFalha = totalSondagensFalha + tabela.ultimasSondagens;
            if (tabela.ultimasSondagens > piorCaso) {
                piorCaso = tabela.ultimasSondagens;
            }
            if (achado != -1) {
                errosDeBusca++;
            }
        }

        maiorLista = tabela.maiorLista();
    }
}
