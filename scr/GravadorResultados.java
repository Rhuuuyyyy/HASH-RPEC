import java.io.IOException;

public class GravadorResultados {

    public static void gravarUsuarios(BaseDeDados base, int[] ordem, String caminho)
            throws IOException {
        GravadorTexto saida = new GravadorTexto(caminho);
        saida.literal("User_id,profileName,Title,media_livro,N_livro,nota_usuario");
        saida.novaLinha();
        for (int k = 0; k < base.quantidadeUsuarios; k++) {
            RegistroUsuario usuario = base.usuarios[ordem[k]];
            RegistroLivro livro = base.livros[usuario.melhorLivro];
            saida.campoTexto(usuario.id, usuario.tamanhoId);
            saida.virgula();
            saida.campoTexto(usuario.nome, usuario.tamanhoNome);
            saida.virgula();
            saida.campoTexto(livro.titulo, livro.tamanhoTitulo);
            saida.virgula();
            saida.media(livro.somaCentesimos, livro.n);
            saida.virgula();
            saida.inteiro(livro.n);
            saida.virgula();
            saida.nota(usuario.notaCentesimos);
            saida.novaLinha();
        }
        saida.fechar();
    }

    public static void gravarRanking(BaseDeDados base, RankingLivros ranking, String caminho)
            throws IOException {
        GravadorTexto saida = new GravadorTexto(caminho);
        saida.literal("posicao,Title,media_livro,N_livro");
        saida.novaLinha();
        for (int k = 0; k < ranking.quantidade; k++) {
            RegistroLivro livro = base.livros[ranking.indices[k]];
            saida.inteiro(k + 1);
            saida.virgula();
            saida.campoTexto(livro.titulo, livro.tamanhoTitulo);
            saida.virgula();
            saida.media(livro.somaCentesimos, livro.n);
            saida.virgula();
            saida.inteiro(livro.n);
            saida.novaLinha();
        }
        saida.fechar();
    }
}
