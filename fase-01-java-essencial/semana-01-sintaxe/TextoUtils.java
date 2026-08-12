import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Utilitários de texto reutilizados pelos exercícios da Semana 1.
 *
 * É uma classe utilitária: só tem métodos estáticos e não deve ser instanciada.
 * O equivalente mais próximo, vindo de TypeScript, é um módulo com funções
 * exportadas — mas em Java toda função precisa morar dentro de uma classe.
 */
public final class TextoUtils {

    /** Casa qualquer caractere fora da tabela ASCII (sobras da decomposição de acentos). */
    private static final Pattern NAO_ASCII = Pattern.compile("[^\\p{ASCII}]");

    /** Casa tudo que não é letra minúscula ou dígito. */
    private static final Pattern RUIDO = Pattern.compile("[^a-z0-9]");

    /**
     * Construtor privado: impede `new TextoUtils()`.
     * Sem ele, o Java geraria um construtor público automático.
     */
    private TextoUtils() {
        throw new AssertionError("Classe utilitária não deve ser instanciada");
    }

    /**
     * Remove acentos preservando espaços e pontuação.
     * "José da Silva" -> "Jose da Silva"
     *
     * Como funciona: NFD decompõe "é" em "e" + marca de acento (dois caracteres),
     * e depois removemos tudo que não é ASCII — sobrando só o "e".
     */
    public static String semAcentos(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não pode ser nulo");
        }
        String decomposto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return NAO_ASCII.matcher(decomposto).replaceAll("");
    }

    /**
     * Reduz o texto ao seu conteúdo comparável: minúsculas, sem acento,
     * sem espaços e sem pontuação.
     * "A grama é amarga!" -> "agramaeamarga"
     *
     * Usa whitelist (mantém o que interessa) em vez de blacklist (remove o que
     * incomoda) — assim pontuação nova nunca escapa.
     */
    public static String normalizar(String texto) {
        String semAcento = semAcentos(texto).toLowerCase(Locale.ROOT);
        return RUIDO.matcher(semAcento).replaceAll("");
    }

    /**
     * Mantém apenas os dígitos de uma string.
     * " 345.543.321-34 " -> "34554332134"
     */
    public static String apenasDigitos(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não pode ser nulo");
        }
        return texto.replaceAll("\\D", "");   // \D = qualquer não-dígito
    }
}