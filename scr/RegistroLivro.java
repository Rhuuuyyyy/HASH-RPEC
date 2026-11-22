public class RegistroLivro {
    public char[] titulo;
    public int tamanhoTitulo;
    public int somaCentesimos;
    public int n;

    public RegistroLivro(char[] titulo, int tamanhoTitulo) {
        this.titulo = titulo;
        this.tamanhoTitulo = tamanhoTitulo;
        this.somaCentesimos = 0;
        this.n = 0;
    }
}