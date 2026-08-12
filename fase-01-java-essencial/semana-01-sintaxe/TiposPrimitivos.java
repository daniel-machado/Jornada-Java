public class TiposPrimitivos {

    public static void main(String[] args){

        // ====== 1. OVERFLOW ======
        int maximo = Integer.MAX_VALUE;
        System.out.println("Int máximo: " + maximo);
        System.out.println("Máximo + 1: " + (maximo + 1)); // ?

        long grande = 9_000_000_000L; // o L é obrigatório
        System.out.println("Long: " + grande);

        // ======= 2. PONTO FLUTUANTE =======
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));

        // ======= 3. DIVISÃO INTEIRA ======
        System.out.println("7 / 2 = " + (7 / 2));
        System.out.println("7.0 / 2 = " + (7.0 / 2));
        System.out.println("7 % 2 = " + (7 % 2));

        // === 4. VALORES PADRÃO vs null ===
        int primitivo = 0;
        Integer objeto = null;
        System.out.println("primitivo: " + primitivo);
        System.out.println("objeto: " + objeto);

        // === 5. O ENIGMA DO DIA ===
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        System.out.println("127 == 127 ? " + (a == b));
        System.out.println("128 == 128 ? " + (c == d));
    }

}
