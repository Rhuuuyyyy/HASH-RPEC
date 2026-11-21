public class Experimento {
    public static final int RODADAS = 3;

    public static void executar(BaseDeDados base) {
        int totalUsuarios = base.quantidadeUsuarios;
        char[][] chavesUsuarios = new char[totalUsuarios][];
        int[] tamanhosUsuarios = new int[totalUsuarios];
        for (int i = 0; i < totalUsuarios; i++) {
            chavesUsuarios[i] = base.usuarios[i].id;
            tamanhosUsuarios[i] = base.usuarios[i].tamanhoId;
        }
        executarConjunto("USUARIOS (User_id distintos)", chavesUsuarios, tamanhosUsuarios,
                         totalUsuarios);

        int totalLivros = base.quantidadeLivros;
        char[][] chavesLivros = new char[totalLivros][];
        int[] tamanhosLivros = new int[totalLivros];
        for (int i = 0; i < totalLivros; i++) {
            chavesLivros[i] = base.livros[i].titulo;
            tamanhosLivros[i] = base.livros[i].tamanhoTitulo;
        }
        executarConjunto("TITULOS (Title distintos)", chavesLivros, tamanhosLivros, totalLivros);
    }

    private static void executarConjunto(String nome, char[][] chaves, int[] tamanhos, int n) {
        Saida.novaLinha();
        Saida.texto("Experimento - ");
        Saida.texto(nome);
        Saida.texto(" - chaves: ");
        Saida.inteiro(n);
        Saida.texto(" - capacidade: ");
        Saida.inteiro(MedidaHash.CAPACIDADE);
        Saida.novaLinha();
        Saida.linha("Funcao        | Colisoes | Sond.sucesso | Sond.falha | Pior caso | Ins.(ms)   | Busca(ms)");

        int maiorChave = 0;
        for (int i = 0; i < n; i++) {
            if (tamanhos[i] > maiorChave) {
                maiorChave = tamanhos[i];
            }
        }
        char[] areaFalha = new char[maiorChave + 1];
        MedidaHash medida = new MedidaHash();

        for (int funcao = 0; funcao < FuncoesHash.QUANTIDADE_FUNCOES; funcao++) {
            int colisoes = 0;
            int pior = 0;
            double totalSucesso = 0;
            double totalFalha = 0;
            double somaInsercao = 0;
            double somaBusca = 0;
            int conferido = 1;
            for (int rodada = 0; rodada < RODADAS; rodada++) {
                medida.medir(funcao, chaves, tamanhos, n, areaFalha);
                somaInsercao = somaInsercao + medida.tempoInsercaoMs;
                somaBusca = somaBusca + medida.tempoBuscaMs;
                if (rodada == 0) {
                    colisoes = medida.colisoes;
                    pior = medida.piorCaso;
                    totalSucesso = medida.totalSondagensSucesso;
                    totalFalha = medida.totalSondagensFalha;
                } else if (colisoes != medida.colisoes || pior != medida.piorCaso
                           || totalSucesso != medida.totalSondagensSucesso
                           || totalFalha != medida.totalSondagensFalha) {
                    conferido = 0;
                }
                if (medida.maiorLista != medida.piorCaso || medida.errosDeBusca != 0) {
                    conferido = 0;
                }
            }
            Saida.texto(FuncoesHash.nome(funcao));
            Saida.texto(" | ");
            Saida.inteiroAlinhado(colisoes, 8);
            Saida.texto(" | ");
            Saida.decimalAlinhado(totalSucesso / n, 4, 12);
            Saida.texto(" | ");
            Saida.decimalAlinhado(totalFalha / n, 4, 10);
            Saida.texto(" | ");
            Saida.inteiroAlinhado(pior, 9);
            Saida.texto(" | ");
            Saida.decimalAlinhado(somaInsercao / RODADAS, 3, 10);
            Saida.texto(" | ");
            Saida.decimalAlinhado(somaBusca / RODADAS, 3, 9);
            if (conferido == 0) {
                Saida.texto("   [ALERTA: conferencia interna falhou]");
            }
            Saida.novaLinha();
        }
        Saida.linha("(tempos = media de 3 rodadas; conferencias internas: pior caso = maior lista, buscas corretas, contagens iguais entre rodadas)");
    }
}
