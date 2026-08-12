public class Strings {
    public static void main(String[] args) {

        // === 1. A ARMADILHA DO == ===
        String a = "teste";
        String b = "teste";
        String c = new String("teste");
        String d = "tes" + "te";              // concatenação de literais
        String parte = "tes";
        String e = parte + "te";              // concatenação em runtime

        System.out.println("a == b: " + (a == b));
        System.out.println("a == c: " + (a == c));
        System.out.println("a == d: " + (a == d));
        System.out.println("a == e: " + (a == e));   // surpresa aqui
        System.out.println("a.equals(e): " + a.equals(e));

        // === 2. IMUTABILIDADE ===
        String original = "java";
        original.toUpperCase();               // resultado descartado!
        System.out.println("original: " + original);
        String maiuscula = original.toUpperCase();
        System.out.println("maiuscula: " + maiuscula);

        // === 3. MÉTODOS ESSENCIAIS ===
        String frase = "  Java Elite é caro demais  ";
        System.out.println("[" + frase.strip() + "]");
        System.out.println("length: " + frase.length());
        System.out.println("upper: " + frase.strip().toUpperCase());
        System.out.println("contains 'caro': " + frase.contains("caro"));
        System.out.println("replace: " + frase.strip().replace("caro", "grátis"));
        System.out.println("charAt(2): " + frase.charAt(2));
        System.out.println("substring: " + frase.strip().substring(0, 4));
        System.out.println("indexOf: " + frase.indexOf("Elite"));
        System.out.println("isBlank: " + "   ".isBlank());

        String[] palavras = frase.strip().split(" ");
        System.out.println("split length: " + palavras.length);
        System.out.println("join: " + String.join("-", palavras));

        // === 4. INTERPOLAÇÃO ===
        String nome = "Daniel";
        int idade = 30;
        System.out.println("Olá %s, você tem %d anos".formatted(nome, idade));

        // === 5. TEXT BLOCK (o template literal do Java) ===
        String json = """
                {
                  "nome": "%s",
                  "linguagem": "Java"
                }""".formatted(nome);
        System.out.println(json);

        // === 6. STRINGBUILDER — o experimento de performance ===
        long inicio = System.currentTimeMillis();
        String lento = "";
        for (int i = 0; i < 50_000; i++) {
            lento += i;                       // cria objeto novo TODA vez
        }
        long tempoLento = System.currentTimeMillis() - inicio;

        inicio = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50_000; i++) {
            sb.append(i);
        }
        String rapido = sb.toString();
        long tempoRapido = System.currentTimeMillis() - inicio;

        System.out.println("Concatenação com +  : " + tempoLento + "ms");
        System.out.println("Com StringBuilder   : " + tempoRapido + "ms");
    }
}