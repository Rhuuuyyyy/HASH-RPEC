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

    public void fechar() throws IOException {
        leitor.close();
    }

        private int lerCaractere() throws IOException {
        if (posicaoBloco >= tamanhoBloco) {
            tamanhoBloco = leitor.read(bloco, 0, TAMANHO_BLOCO);
            posicaoBloco = 0;
            if (tamanhoBloco <= 0) {
                tamanhoBloco = 0;
                return -1;
            }
        }
        char c = bloco[posicaoBloco];
        posicaoBloco++;
        return c;
    }

    private CampoTexto escolherDestino(int coluna) {
        if (coluna == COLUNA_TITULO) {
            return titulo;
        }
        if (coluna == COLUNA_USUARIO) {
            return usuario;
        }
        if (coluna == COLUNA_NOME) {
            return nome;
        }
        if (coluna == COLUNA_NOTA) {
            return nota;
        }
        return null;
    }

    private void guardar(char c) {
        if (destino != null) {
            destino.adicionar(c);
        }
    }

    public int proximoRegistro() throws IOException {
        titulo.limpar();
        usuario.limpar();
        nome.limpar();
        nota.limpar();
        indiceCampo = 0;
        destino = escolherDestino(0);
        int estado = INICIO_CAMPO;
        int temConteudo = 0;

        while (true) {
            int lido = lerCaractere();
            if (lido == -1) {
                if (temConteudo == 0) {
                    return 0;
                }
                quantidadeCampos = indiceCampo + 1;
                return 1;
            }
            char c = (char) lido;

            if (estado == CAMPO_ENTRE_ASPAS) {
                temConteudo = 1;
                if (c == '"') {
                    estado = ASPAS_VISTA;
                } else {
                    guardar(c);
                }
            } else if (estado == ASPAS_VISTA && c == '"') {
                guardar('"');
                estado = CAMPO_ENTRE_ASPAS;
            } else if (c == ',') {
                temConteudo = 1;
                indiceCampo++;
                destino = escolherDestino(indiceCampo);
                estado = INICIO_CAMPO;
            } else if (c == '\n') {
                if (temConteudo == 0) {
                    continue;
                }
                quantidadeCampos = indiceCampo + 1;
                return 1;
            } else if (c == '\r') {

            } else if (estado == INICIO_CAMPO && c == '"') {
                temConteudo = 1;
                estado = CAMPO_ENTRE_ASPAS;
            } else {
                temConteudo = 1;
                guardar(c);
                estado = CAMPO_SIMPLES;
            }
        }
    }
}