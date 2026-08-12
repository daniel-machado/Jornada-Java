public class Calculadora {

    record CasoTeste(double a, String operador, double b) { }

    public static double calcular(double a, String operador, double b) {
        if (operador == null) {
            throw new IllegalArgumentException("Operador não pode ser nulo");
        }

        return switch (operador) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "^" -> Math.pow(a, b);
            case "/", "%" -> {
                if (b == 0) {
                    throw new ArithmeticException(
                            "Divisão por zero: %.2f %s 0".formatted(a, operador));
                }
                yield operador.equals("/") ? a / b : a % b;
            }
            default -> throw new IllegalArgumentException(
                    "Operador desconhecido: \"%s\"".formatted(operador));
        };
    }

    public static void main(String[] args) {
        CasoTeste[] casos = {
                new CasoTeste(10, "+", 5),
                new CasoTeste(10, "-", 5),
                new CasoTeste(10, "*", 5),
                new CasoTeste(10, "/", 5),
                new CasoTeste(10, "%", 3),
                new CasoTeste(2, "^", 10),
                new CasoTeste(10, "/", 0),
                new CasoTeste(10, "%", 0),
                new CasoTeste(10, "&", 5)
        };

        for (CasoTeste caso : casos) {
            try {
                double resultado = calcular(caso.a(), caso.operador(), caso.b());
                System.out.printf("%.2f %s %.2f = %.2f%n",
                        caso.a(), caso.operador(), caso.b(), resultado);
            } catch (ArithmeticException | IllegalArgumentException e) {
                System.out.printf("%.2f %s %.2f -> ERRO: %s%n",
                        caso.a(), caso.operador(), caso.b(), e.getMessage());
            }
        }
    }
}