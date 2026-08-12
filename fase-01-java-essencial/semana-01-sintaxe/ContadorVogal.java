/**
 * Conta quantas vezes cada vogal aparece num texto.
 *
 * O truque central: "aeiou".indexOf(c) devolve -1 quando c não é vogal,
 * ou a posição (0..4) quando é. Essa posição serve direto como índice do
 * array de contagem — resolvendo, com uma ideia só, tanto a checagem
 * quanto a contagem separada por vogal.
 */
public class ContadorVogal {

    private static final String VOGAIS = "aeiou";

    /**
     * @return array de 5 posições, na ordem a, e, i, o, u
     */
    public static int[] contarVogais(String texto) {
        String limpo = TextoUtils.normalizar(texto);
        int[] contagem = new int[VOGAIS.length()];

        for (int i = 0; i < limpo.length(); i++) {
            int indice = VOGAIS.indexOf(limpo.charAt(i));
            if (indice >= 0) {
                contagem[indice]++;
            }
        }

        return contagem;
    }

    /** Soma total de vogais — derivada da contagem detalhada. */
    public static int totalVogais(String texto) {
        int total = 0;
        for (int quantidade : contarVogais(texto)) {
            total += quantidade;
        }
        return total;
    }

    /** Formata "a: 6 | e: 1 | i: 0 | o: 0 | u: 0" */
    private static String formatar(int[] contagem) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contagem.length; i++) {
            if (i > 0) {
                sb.append(" | ");
            }
            sb.append(VOGAIS.charAt(i)).append(": ").append(contagem[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] casos = {
                "Daniel",
                "Java",
                "A grama é amarga",
                "Programação orientada a objetos",
                ""
        };

        for (String caso : casos) {
            System.out.printf("%-35s %s   (total: %d)%n",
                    "\"" + caso + "\"",
                    formatar(contarVogais(caso)),
                    totalVogais(caso));
        }
    }
}