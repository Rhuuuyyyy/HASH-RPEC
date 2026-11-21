public class MelhorLivro {

    public static int compararMediaEN(BaseDeDados base, int a, int b) {
        if (a == b) {
            return 0;
        }
        RegistroLivro livroA = base.livros[a];
        RegistroLivro livroB = base.livros[b];
        double esquerda = (double) livroA.somaCentesimos * livroB.n;
        double direita = (double) livroB.somaCentesimos * livroA.n;
        if (esquerda > direita) {
            return 1;
        }
        if (esquerda < direita) {
            return -1;
        }
        if (livroA.n > livroB.n) {
            return 1;
        }
        if (livroA.n < livroB.n) {
            return -1;
        }
        return 0;
    }

    public static int compararLivros(BaseDeDados base, int a, int b) {
        int porMediaEN = compararMediaEN(base, a, b);
        if (porMediaEN != 0 || a == b) {
            return porMediaEN;
        }

        RegistroLivro livroA = base.livros[a];
        RegistroLivro livroB = base.livros[b];
        int ordem = Texto.comparar(livroA.titulo, livroA.tamanhoTitulo,
                                   livroB.titulo, livroB.tamanhoTitulo);
        return -ordem;
    }

    public static void calcularParaTodos(BaseDeDados base) {
        Avaliacoes lista = base.avaliacoes;
        for (int i = 0; i < lista.quantidade; i++) {
            RegistroUsuario usuario = base.usuarios[lista.usuario[i]];
            int livro = lista.livro[i];
            int nota = lista.nota[i];
            if (usuario.melhorLivro == -1) {
                usuario.melhorLivro = livro;
                usuario.notaCentesimos = nota;
            } else if (usuario.melhorLivro == livro) {
                if (nota > usuario.notaCentesimos) {
                    usuario.notaCentesimos = nota;
                }
            } else if (compararLivros(base, livro, usuario.melhorLivro) > 0) {
                usuario.melhorLivro = livro;
                usuario.notaCentesimos = nota;
            }
        }
    }
}
