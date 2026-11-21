import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class GravadorTexto {
    private static final int TAMANHO_BLOCO = 65536;

    private Writer escritor;
    private char[] bloco;
    private int usados;
    private char[] numero;

    public GravadorTexto(String caminho) throws IOException {
        escritor = new OutputStreamWriter(new FileOutputStream(caminho), "UTF-8");
        bloco = new char[TAMANHO_BLOCO];
        usados = 0;
        numero = new char[48];
    }

    private void descarregar() throws IOException {
        escritor.write(bloco, 0, usados);
        usados = 0;
    }

    public void caractere(char c) throws IOException {
        if (usados == TAMANHO_BLOCO) {
            descarregar();
        }
        bloco[usados] = c;
        usados++;
    }

    public void literal(String texto) throws IOException {
        descarregar();
        escritor.write(texto);
    }

    public void novaLinha() throws IOException {
        caractere('\n');
    }

    public void virgula() throws IOException {
        caractere(',');
    }

    public void campoTexto(char[] dados, int tamanho) throws IOException {
        int precisaAspas = 0;
        for (int i = 0; i < tamanho; i++) {
            char c = dados[i];
            if (c == ',' || c == '"' || c == '\n' || c == '\r') {
                precisaAspas = 1;
            }
        }
        if (precisaAspas == 1) {
            caractere('"');
            for (int i = 0; i < tamanho; i++) {
                if (dados[i] == '"') {
                    caractere('"');
                }
                caractere(dados[i]);
            }
            caractere('"');
        } else {
            for (int i = 0; i < tamanho; i++) {
                caractere(dados[i]);
            }
        }
    }

    private void gravarNumero(int tamanho) throws IOException {
        for (int i = 0; i < tamanho; i++) {
            caractere(numero[i]);
        }
    }

    public void inteiro(int valor) throws IOException {
        gravarNumero(Formato.escreverInteiro(valor, numero, 0));
    }

    public void media(int somaCentesimos, int n) throws IOException {
        gravarNumero(Formato.escreverMedia(somaCentesimos, n, numero, 0));
    }

    public void nota(int centesimos) throws IOException {
        gravarNumero(Formato.escreverNota(centesimos, numero, 0));
    }

    public void fechar() throws IOException {
        descarregar();
        escritor.close();
    }
}
