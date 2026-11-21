public class Ordenacao {

    public static int compararUsuarios(BaseDeDados base, int a, int b) {
        RegistroUsuario usuarioA = base.usuarios[a];
        RegistroUsuario usuarioB = base.usuarios[b];

        int porLivro = MelhorLivro.compararMediaEN(base, usuarioA.melhorLivro,
                                                   usuarioB.melhorLivro);
        if (porLivro > 0) {
            return -1;
        }
        if (porLivro < 0) {
            return 1;
        }

        return Texto.comparar(usuarioA.id, usuarioA.tamanhoId, usuarioB.id, usuarioB.tamanhoId);
    }

    public static void ordenarUsuarios(BaseDeDados base, int[] ordem, int quantidade) {
        int[] auxiliar = new int[quantidade];
        mesclarOrdenar(base, ordem, auxiliar, 0, quantidade);
    }

    private static void mesclarOrdenar(BaseDeDados base, int[] ordem, int[] auxiliar,
                                       int inicio, int fim) {
        if (fim - inicio < 2) {
            return;
        }
        int meio = inicio + (fim - inicio) / 2;
        mesclarOrdenar(base, ordem, auxiliar, inicio, meio);
        mesclarOrdenar(base, ordem, auxiliar, meio, fim);

        int esquerda = inicio;
        int direita = meio;
        int destino = inicio;
        while (esquerda < meio && direita < fim) {

            if (compararUsuarios(base, ordem[esquerda], ordem[direita]) <= 0) {
                auxiliar[destino] = ordem[esquerda];
                esquerda++;
            } else {
                auxiliar[destino] = ordem[direita];
                direita++;
            }
            destino++;
        }
        while (esquerda < meio) {
            auxiliar[destino] = ordem[esquerda];
            esquerda++;
            destino++;
        }
        while (direita < fim) {
            auxiliar[destino] = ordem[direita];
            direita++;
            destino++;
        }
        for (int i = inicio; i < fim; i++) {
            ordem[i] = auxiliar[i];
        }
    }
}
