/**
 * Aplica a máscara de CPF: 12345678901 -> 123.456.789-01
 *
 * Aceita entrada suja (com pontos, traços ou espaços), porque extrai
 * apenas os dígitos antes de formatar.
 */
public class MascaraCpf {

    private static final int TAMANHO_CPF = 11;

    public static String mascarar(String cpf) {
        String digitos = TextoUtils.apenasDigitos(cpf);

        if (digitos.length() != TAMANHO_CPF) {
            // Decisão de projeto: falhar alto em vez de devolver "".
            // Retorno vazio esconde o erro e vira bug fantasma lá na frente.
            // A mensagem carrega contexto: o que veio e quantos dígitos tinha.
            throw new IllegalArgumentException(
                    "CPF deve ter %d dígitos, recebeu %d: \"%s\""
                            .formatted(TAMANHO_CPF, digitos.length(), cpf));
        }

        return "%s.%s.%s-%s".formatted(
                digitos.substring(0, 3),
                digitos.substring(3, 6),
                digitos.substring(6, 9),
                digitos.substring(9, 11));
    }

    /** Remove a máscara: 123.456.789-01 -> 12345678901 */
    public static String desmascarar(String cpf) {
        return TextoUtils.apenasDigitos(cpf);
    }

    public static void main(String[] args) {
        String[] validos = {
                "34554332134",
                "345.543.321-34",
                " 345 543 321 34 "
        };

        for (String caso : validos) {
            System.out.printf("%-22s -> %s%n", "\"" + caso + "\"", mascarar(caso));
        }

        // Demonstra o comportamento em caso inválido
        String[] invalidos = { "123", "3455433213412345", "abc" };

        for (String caso : invalidos) {
            try {
                mascarar(caso);
            } catch (IllegalArgumentException e) {
                System.out.println("Rejeitado -> " + e.getMessage());
            }
        }
    }
}