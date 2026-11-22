import java.io.IOException;

public class Principal {

    public static final int CAPACIDADE_TABELAS = 1000003;

    public static final int FUNCAO_PRINCIPAL = FuncoesHash.FNV1A;

    public static void main(String[] args) throws IOException {

        String caminhoCsv = null;
        String pasta = null;
        String caminhoConsultas = null;
        int posicaoArgumento = 0;
        for (String argumento : args) {
            if (posicaoArgumento == 0) {
                caminhoCsv = argumento;
            } else if (posicaoArgumento == 1) {
                pasta = argumento;
            } else if (posicaoArgumento == 2) {
                caminhoConsultas = argumento;
            }
            posicaoArgumento++;
        }
        if (caminhoCsv == null || pasta == null) {
            Saida.linha("Uso: java Principal <Books_rating.csv> <pasta_de_saida> [user_id.txt]");
            return;
        }

        Cronometro relogio = new Cronometro();
        double tempoLeitura;
        double tempoMelhorLivro;
        double tempoRanking;
        double tempoOrdenacao;
        double tempoGravacao;
        double tempoConsultas = 0;
        double tempoExperimento;

        relogio.iniciar();
        BaseDeDados base = new BaseDeDados(CAPACIDADE_TABELAS, FUNCAO_PRINCIPAL);
        base.carregar(caminhoCsv);
        tempoLeitura = relogio.parar();

        Saida.linha("Contagens da leitura");
        Saida.texto("Linhas de dados lidas: ");
        Saida.inteiro(base.linhasLidas);
        Saida.novaLinha();
        Saida.texto("Linhas validas: ");
        Saida.inteiro(base.linhasValidas);
        Saida.novaLinha();
        Saida.texto("Linhas descartadas (total): ");
        Saida.inteiro(base.descartadas);
        Saida.novaLinha();
        Saida.texto("  sem User_id: ");
        Saida.inteiro(base.semUsuario);
        Saida.novaLinha();
        Saida.texto("  sem Title: ");
        Saida.inteiro(base.semTitulo);
        Saida.novaLinha();
        Saida.texto("  com nota invalida: ");
        Saida.inteiro(base.notaInvalida);
        Saida.novaLinha();
        Saida.texto("  malformadas (menos de 7 colunas): ");
        Saida.inteiro(base.malformadas);
        Saida.novaLinha();
        Saida.texto("Usuarios distintos: ");
        Saida.inteiro(base.quantidadeUsuarios);
        Saida.novaLinha();
        Saida.texto("Livros distintos: ");
        Saida.inteiro(base.quantidadeLivros);
        Saida.novaLinha();

        relogio.iniciar();
        MelhorLivro.calcularParaTodos(base);
        tempoMelhorLivro = relogio.parar();

        relogio.iniciar();
        RankingLivros ranking = new RankingLivros(10);
        ranking.construir(base, 100);
        tempoRanking = relogio.parar();
        Saida.novaLinha();
        Saida.linha("Top 10 livros por media");
        for (int k = 0; k < ranking.quantidade; k++) {
            RegistroLivro livro = base.livros[ranking.indices[k]];
            Saida.inteiroAlinhado(k + 1, 2);
            Saida.texto(". ");
            Saida.media(livro.somaCentesimos, livro.n);
            Saida.texto("  N=");
            Saida.inteiro(livro.n);
            Saida.texto("  ");
            Saida.caracteres(livro.titulo, livro.tamanhoTitulo);
            Saida.novaLinha();
        }

        int[] ordemAparicao = new int[base.quantidadeUsuarios];
        int[] ordemOrdenada = new int[base.quantidadeUsuarios];
        for (int i = 0; i < base.quantidadeUsuarios; i++) {
            ordemAparicao[i] = i;
            ordemOrdenada[i] = i;
        }
        relogio.iniciar();
        Ordenacao.ordenarUsuarios(base, ordemOrdenada, base.quantidadeUsuarios);
        tempoOrdenacao = relogio.parar();

        relogio.iniciar();
        GravadorResultados.gravarUsuarios(base, ordemAparicao, pasta + "/usuario_melhor_livro.csv");
        GravadorResultados.gravarUsuarios(base, ordemOrdenada,
                                          pasta + "/usuario_melhor_livro_ordenado.csv");
        GravadorResultados.gravarRanking(base, ranking, pasta + "/top10_livros.csv");
        tempoGravacao = relogio.parar();

        if (caminhoConsultas != null) {
            Saida.novaLinha();
            Saida.linha("=== Consultas (RF6) ===");
            relogio.iniciar();
            Consultas.executar(base, caminhoConsultas);
            tempoConsultas = relogio.parar();
        }

        Saida.novaLinha();
        Saida.linha("Parte experimental");
        relogio.iniciar();
        Experimento.executar(base);
        tempoExperimento = relogio.parar();

        Saida.novaLinha();
        Saida.linha("Tempo de cada etapa");
        Saida.texto("Leitura e indexacao:      ");
        Saida.decimal(tempoLeitura, 3);
        Saida.novaLinha();
        Saida.texto("Calculo do melhor livro:  ");
        Saida.decimal(tempoMelhorLivro, 3);
        Saida.novaLinha();
        Saida.texto("Ranking top 10:           ");
        Saida.decimal(tempoRanking, 3);
        Saida.novaLinha();
        Saida.texto("Ordenacao (merge sort):   ");
        Saida.decimal(tempoOrdenacao, 3);
        Saida.novaLinha();
        Saida.texto("Gravacao dos arquivos:    ");
        Saida.decimal(tempoGravacao, 3);
        Saida.novaLinha();
        if (caminhoConsultas != null) {
            Saida.texto("Consultas:                ");
            Saida.decimal(tempoConsultas, 3);
            Saida.novaLinha();
        }
        Saida.texto("Experimento (3 x 6 x 2):  ");
        Saida.decimal(tempoExperimento, 3);
        Saida.novaLinha();
    }
}
