/**
 * Verifica se um texto é palíndromo, ignorando maiúsculas,
 * acentos, espaços e pontuação.
 */
public class Palindromo {

    /**
     * Estratégia de dois ponteiros: um no início, outro no fim,
     * caminhando em direção ao centro.
     *
     * Vantagem sobre inverter a string e comparar: não aloca memória extra
     * e sai na primeira divergência, sem percorrer o resto.
     * É a solução esperada em entrevista.
     */
    public static boolean ehPalindromo(String texto) {
        String limpo = TextoUtils.normalizar(texto);

        int inicio = 0;
        int fim = limpo.length() - 1;

        while (inicio < fim) {
            if (limpo.charAt(inicio) != limpo.charAt(fim)) {
                return false;
            }
            inicio++;
            fim--;
        }

        // Decisão de projeto: string vazia é considerada palíndromo
        // (o loop não executa e nada contradiz a propriedade).
        return true;
    }

    public static void main(String[] args) {
        String[] casos = {
                "arara",
                "árárá",
                "A grama é amarga",
                "Socorram-me, subi no ônibus em Marrocos!",
                "Anotaram a data da maratona",
                "daniel",
                "Java é a melhor linguagem",
                ""
        };

        for (String caso : casos) {
            System.out.printf("%-45s -> %b%n", "\"" + caso + "\"", ehPalindromo(caso));
        }
    }
}