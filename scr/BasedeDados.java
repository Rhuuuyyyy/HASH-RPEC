import java.io.IOException;

public class BaseDeDados {

    public TabelaHash tabelaLivros;
    public TabelaHash tabelaUsuarios;

    public RegistroLivro[] livros;
    public int quantidadeLivros;
    public RegistroUsuario[] usuarios;
    public int quantidadeUsuarios;
    private int capacidadeLivros;
    private int capacidadeUsuarios;

    public Avaliacoes avaliacoes;

    public int linhasLidas;
    public int linhasValidas;
    public int descartadas;
    public int semUsuario;
    public int semTitulo;
    public int notaInvalida;
    public int malformadas;

    public BaseDeDados(int capacidadeTabelas, int funcaoHash) {
        tabelaLivros = new TabelaHash(capacidadeTabelas, funcaoHash);
        tabelaUsuarios = new TabelaHash(capacidadeTabelas, funcaoHash);
        capacidadeLivros = 1024;
        capacidadeUsuarios = 1024;
        livros = new RegistroLivro[capacidadeLivros];
        usuarios = new RegistroUsuario[capacidadeUsuarios];
        quantidadeLivros = 0;
        quantidadeUsuarios = 0;
        avaliacoes = new Avaliacoes(1024);
    }

    public void carregar(String caminhoCsv) throws IOException {
        LeitorCsv leitor = new LeitorCsv(caminhoCsv);
        leitor.proximoRegistro();
        while (leitor.proximoRegistro() == 1) {
            linhasLidas++;
            processarRegistro(leitor);
        }
        leitor.fechar();
    }

    private void processarRegistro(LeitorCsv leitor) {
        if (leitor.quantidadeCampos < LeitorCsv.COLUNAS_MINIMAS) {
            malformadas++;
            descartadas++;
            return;
        }
        int notaCentesimos = Texto.converterNota(leitor.nota.dados, leitor.nota.tamanho);
        int defeito = 0;
        if (leitor.usuario.tamanho == 0) {
            semUsuario++;
            defeito = 1;
        }
        if (leitor.titulo.tamanho == 0) {
            semTitulo++;
            defeito = 1;
        }
        if (notaCentesimos < 0) {
            notaInvalida++;
            defeito = 1;
        }
        if (defeito == 1) {
            descartadas++;
            return;
        }
        linhasValidas++;

        int indiceLivro = tabelaLivros.buscar(leitor.titulo.dados, leitor.titulo.tamanho);
        if (indiceLivro == -1) {
            indiceLivro = quantidadeLivros;
            char[] copiaTitulo = Texto.copiar(leitor.titulo.dados, leitor.titulo.tamanho);
            tabelaLivros.inserir(copiaTitulo, leitor.titulo.tamanho, indiceLivro);
            adicionarLivro(new RegistroLivro(copiaTitulo, leitor.titulo.tamanho));
        }
        RegistroLivro livro = livros[indiceLivro];
        livro.somaCentesimos = livro.somaCentesimos + notaCentesimos;
        livro.n++;

        int indiceUsuario = tabelaUsuarios.buscar(leitor.usuario.dados, leitor.usuario.tamanho);
        if (indiceUsuario == -1) {
            indiceUsuario = quantidadeUsuarios;
            char[] copiaId = Texto.copiar(leitor.usuario.dados, leitor.usuario.tamanho);
            tabelaUsuarios.inserir(copiaId, leitor.usuario.tamanho, indiceUsuario);
            adicionarUsuario(new RegistroUsuario(copiaId, leitor.usuario.tamanho));
        }
        RegistroUsuario usuario = usuarios[indiceUsuario];
        if (usuario.tamanhoNome == 0 && leitor.nome.tamanho > 0) {
            usuario.nome = Texto.copiar(leitor.nome.dados, leitor.nome.tamanho);
            usuario.tamanhoNome = leitor.nome.tamanho;
        }

        avaliacoes.adicionar(indiceUsuario, indiceLivro, notaCentesimos);
    }

    private void adicionarLivro(RegistroLivro novo) {
        if (quantidadeLivros == capacidadeLivros) {
            int novaCapacidade = capacidadeLivros * 2;
            RegistroLivro[] maior = new RegistroLivro[novaCapacidade];
            for (int i = 0; i < quantidadeLivros; i++) {
                maior[i] = livros[i];
            }
            livros = maior;
            capacidadeLivros = novaCapacidade;
        }
        livros[quantidadeLivros] = novo;
        quantidadeLivros++;
    }

    private void adicionarUsuario(RegistroUsuario novo) {
        if (quantidadeUsuarios == capacidadeUsuarios) {
            int novaCapacidade = capacidadeUsuarios * 2;
            RegistroUsuario[] maior = new RegistroUsuario[novaCapacidade];
            for (int i = 0; i < quantidadeUsuarios; i++) {
                maior[i] = usuarios[i];
            }
            usuarios = maior;
            capacidadeUsuarios = novaCapacidade;
        }
        usuarios[quantidadeUsuarios] = novo;
        quantidadeUsuarios++;
    }
}
