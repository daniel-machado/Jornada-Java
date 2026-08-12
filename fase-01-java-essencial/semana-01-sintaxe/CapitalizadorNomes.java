import java.util.Locale;
import java.util.Set;

/**
 * Capitaliza nomes próprios seguindo a convenção brasileira:
 * preposições ficam minúsculas, exceto quando iniciam o nome.
 *
 * "daniel machado barbosa dos santos" -> "Daniel Machado Barbosa dos Santos"
 */
public class CapitalizadorNomes {

    /**
     * Set em vez de uma cadeia de ||: busca em O(1), e adicionar
     * "del", "van", "von" depois custa uma palavra, não uma linha.
     */
    private static final Set<String> PREPOSICOES =
            Set.of("da", "de", "di", "do", "das", "dos", "e");

    public static String capitalizar(String nome) {
        if (nome == null || nome.isBlank()) {
            return "";
        }

        // \s+ trata múltiplos espaços como um separador só,
        // evitando elementos vazios no array.
        String[] palavras = nome.strip().toLowerCase(Locale.ROOT).split("\\s+");

        // StringBuilder, nunca += dentro de loop:
        // concatenação com + é O(n²) porque cria uma String nova a cada volta.
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palavras.length; i++) {
            String palavra = palavras[i];

            // Regra de negócio: a primeira palavra sempre capitaliza,
            // mesmo sendo preposição ("da Silva" -> "Da Silva").
            boolean deveCapitalizar = (i == 0) || !PREPOSICOES.contains(palavra);

            if (deveCapitalizar) {
                resultado.append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1));
            } else {
                resultado.append(palavra);
            }

            // Espaço só entre palavras — mais intencional que
            // adicionar sempre e depois cortar com trim().
            if (i < palavras.length - 1) {
                resultado.append(' ');
            }
        }

        return resultado.toString();
    }

    public static void main(String[] args) {
        String[] casos = {
                "daniel machado barbosa dos santos",
                "JOSÉ DA SILVA",
                "maria   de   souza e lima",
                "da silva",
                "ana",
                "   ",
                ""
        };

        for (String caso : casos) {
            System.out.printf("%-40s -> \"%s\"%n", "\"" + caso + "\"", capitalizar(caso));
        }
    }
}