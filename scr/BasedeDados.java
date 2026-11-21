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