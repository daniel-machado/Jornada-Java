import java.util.Arrays;

public class SintaxeModerna {

    enum DiaSemana { SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO }

    static String classificar(DiaSemana dia) {
        return switch (dia) {
            case SABADO, DOMINGO -> "fim de semana";
            case SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA -> "dia útil";
        };
    }

    static int diasNoMes(int mes, int ano) {
        return switch (mes) {
            case 2 -> {
                boolean bissexto = ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0);
                yield bissexto ? 29 : 28;
            }
            case 4, 6, 9, 11 -> 30;
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            default -> throw new IllegalArgumentException("Mês inválido: " + mes);
        };
    }

    public static void main(String[] args) {

        // === SWITCH ===
        for (var dia : DiaSemana.values()) {
            System.out.printf("%-9s -> %s%n", dia, classificar(dia));
        }

        System.out.println("Fev/2024: " + diasNoMes(2, 2024) + " dias");
        System.out.println("Fev/2025: " + diasNoMes(2, 2025) + " dias");
        System.out.println("Fev/2100: " + diasNoMes(2, 2100) + " dias");  // atenção

        // === VAR ===
        var mensagem = "usando var";
        var numero = 42;
        System.out.println(mensagem + " / " + numero);

        // === ARRAYS ===
        int[] numeros = {5, 3, 9, 1, 7};

        System.out.println("toString padrão: " + numeros);
        System.out.println("Arrays.toString: " + Arrays.toString(numeros));

        Arrays.sort(numeros);
        System.out.println("Ordenado: " + Arrays.toString(numeros));

        System.out.println("length: " + numeros.length);
        System.out.println("Fatia [1,4): " + Arrays.toString(Arrays.copyOfRange(numeros, 1, 4)));

        int[] copia = Arrays.copyOf(numeros, numeros.length);
        System.out.println("copia == numeros ? " + (copia == numeros));
        System.out.println("Arrays.equals ?    " + Arrays.equals(copia, numeros));

        // Matriz
        int[][] matriz = {{1, 2}, {3, 4}};
        System.out.println("Matriz: " + Arrays.deepToString(matriz));
    }
}