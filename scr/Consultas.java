import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Consultas {

    public static int executar(BaseDeDados base, String caminho) throws IOException {
        Reader leitor = new InputStreamReader(new FileInputStream(caminho), "UTF-8");
        CampoTexto linha = new CampoTexto(64);
        int consultas = 0;
        int lido = leitor.read();
        while (lido != -1) {
            char c = (char) lido;
            if (c == '\n') {
                if (linha.tamanho > 0) {
                    consultar(base, linha);
                    consultas++;
                }
                linha.limpar();
            } else if (c != '\r' && c != '\uFEFF') {

                linha.adicionar(c);
            }
            lido = leitor.read();
        }
        if (linha.tamanho > 0) {
            consultar(base, linha);
            consultas++;
        }
        leitor.close();
        return consultas;
    }

    private static void consultar(BaseDeDados base, CampoTexto id) {
        Saida.texto("Consulta ");
        Saida.caracteres(id.dados, id.tamanho);
        Saida.novaLinha();
        int indice = base.tabelaUsuarios.buscar(id.dados, id.tamanho);
        if (indice == -1) {
            Saida.linha("  usuario nao encontrado");
            return;
        }
        RegistroUsuario usuario = base.usuarios[indice];
        RegistroLivro livro = base.livros[usuario.melhorLivro];
        Saida.texto("  profileName: ");
        if (usuario.tamanhoNome == 0) {
            Saida.texto("(vazio)");
        } else {
            Saida.caracteres(usuario.nome, usuario.tamanhoNome);
        }
        Saida.novaLinha();
        Saida.texto("  melhor livro: ");
        Saida.caracteres(livro.titulo, livro.tamanhoTitulo);
        Saida.novaLinha();
        Saida.texto("  media_livro: ");
        Saida.media(livro.somaCentesimos, livro.n);
        Saida.texto("  N_livro: ");
        Saida.inteiro(livro.n);
        Saida.texto("  nota_usuario: ");
        Saida.nota(usuario.notaCentesimos);
        Saida.novaLinha();
    }
}
