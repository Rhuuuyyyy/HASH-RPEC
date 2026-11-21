public class RegistroUsuario {
    public char[] id;
    public int tamanhoId;
    public char[] nome;
    public int tamanhoNome;
    public int melhorLivro;
    public int notaCentesimos;

    public RegistroUsuario(char[] id, int tamanhoId) {
        this.id = id;
        this.tamanhoId = tamanhoId;
        this.nome = null;
        this.tamanhoNome = 0;
        this.melhorLivro = -1;
        this.notaCentesimos = 0;
    }
}
