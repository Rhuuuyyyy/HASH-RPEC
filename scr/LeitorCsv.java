import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class LeitorCsv {

    private static final int INICIO_CAMPO = 0;
    private static final int CAMPO_SIMPLES = 1;
    private static final int CAMPO_ENTRE_ASPAS = 2;
    private static final int ASPAS_VISTA = 3;

    private static final int TAMANHO_BLOCO = 65536;

    public static final int COLUNA_TITULO = 1;
    public static final int COLUNA_USUARIO = 3;
    public static final int COLUNA_NOME = 4;
    public static final int COLUNA_NOTA = 6;
    public static final int COLUNAS_MINIMAS = 7;

    public CampoTexto titulo;
    public CampoTexto usuario;
    public CampoTexto nome;
    public CampoTexto nota;
    public int quantidadeCampos;

    private Reader leitor;
    private char[] bloco;
    private int tamanhoBloco;
    private int posicaoBloco;
    private int indiceCampo;
    private CampoTexto destino;

    public LeitorCsv(String caminho) throws IOException {
        leitor = new InputStreamReader(new FileInputStream(caminho), "UTF-8");
        bloco = new char[TAMANHO_BLOCO];
        tamanhoBloco = 0;
        posicaoBloco = 0;
        titulo = new CampoTexto(256);
        usuario = new CampoTexto(64);
        nome = new CampoTexto(64);
        nota = new CampoTexto(16);
    }
