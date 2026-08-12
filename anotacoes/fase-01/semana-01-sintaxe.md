# Semana 1 — Sintaxe e Tipos

**Período:** 11/08/2026 – 12/08/2026
**Fonte principal:** dev.java/learn — Language Basics
**Contexto:** venho de TypeScript / React Native

---

## Como uso este arquivo

Um bloco por conceito, escrito com minhas palavras.
Se não consigo explicar em 3 frases, não entendi ainda.

---

## Tipos primitivos

**O que é:** Java tem 8 tipos que não são objetos — `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`. Não têm métodos, não podem ser `null`, e cada um ocupa um tamanho fixo na memória. Na prática eu uso quatro: `int`, `long`, `double`, `boolean`.

**Vindo de TS:** JS tem um único `number`, que é float de 64 bits. O `double` do Java é o equivalente direto dele.

**Onde a analogia quebra:** em Java eu escolho o tipo. Para inteiros uso `int`, não `double` — e `int` tem limite de ±2,1 bilhões.

**Armadilha:** overflow silencioso. `Integer.MAX_VALUE + 1` vira o menor negativo possível, sem erro nenhum. O número "dá a volta". É por isso que IDs de banco de dados são `long`, não `int`.

**Se me perguntarem:** primitivo vs wrapper — primitivo não pode ser `null` e tem valor padrão (`int` = 0, `boolean` = false); o wrapper (`Integer`) é objeto e pode ser `null`. Collections só aceitam objetos, então existe `List<Integer>` mas nunca `List<int>`.

---

## Divisão inteira

**O que é:** se os dois operandos são inteiros, o resultado é inteiro. `7 / 2` dá `3`, não `3.5`. O resto é descartado.

**Vindo de TS:** isso não existe em JS — `7 / 2` sempre dá `3.5`.

**Armadilha:** bug silencioso em média, porcentagem ou rateio. Para forçar decimal, basta um dos operandos ser `double`: `7.0 / 2`.

**Descoberta relacionada:** divisão por zero se comporta diferente conforme o tipo.
- `10 / 0` (int) → lança `ArithmeticException`
- `10.0 / 0` (double) → devolve `Infinity`
- `10.0 % 0` (double) → devolve `NaN`

Isso segue o padrão IEEE 754, o mesmo do JavaScript. Ou seja: com `double`, a linguagem **não me protege** — a validação de divisor zero tem que ser minha.

---

## Integer cache (o enigma do 127 vs 128)

**O que é:** `Integer a = 127, b = 127; a == b` → `true`, mas com `128` → `false`.

**Por quê:** o Java mantém um cache de objetos `Integer` de **-128 a 127**. Dentro desse intervalo, o autoboxing devolve sempre a mesma instância. Fora dele, cria objeto novo a cada vez. Como `==` compara **referência** e não valor, dois objetos distintos com o mesmo número dão `false`.

**Detalhes:**
- Não é otimização opcional — está na especificação da linguagem (JLS §5.1.7). Vale também para `boolean`, `byte`, `char` até 127 e `short` no mesmo intervalo. Acima de 127 fica a critério da JVM.
- Só age no **autoboxing**. `Integer a = 127` chama `Integer.valueOf(127)` por baixo, que consulta o cache. `new Integer(127)` (depreciado) força objeto novo e daria `false` mesmo dentro do intervalo.

**A lição real:** `==` compara referência para qualquer objeto. O fato de às vezes funcionar é coincidência de implementação, nunca garantia. **Para objetos, sempre `.equals()`.**

**Se me perguntarem:** essa é a base da pergunta "diferença entre `==` e `.equals()`", que cai em praticamente toda entrevista de Java.

---

## String

**O que é:** classe (não primitivo) e **imutável** — nenhum método altera a String original, todos devolvem uma nova. Java mantém um **String pool**: literais escritos no código são internados numa área especial da memória e reutilizados.

**Vindo de TS:** strings em JS também são imutáveis, então o conceito eu já tinha. `str.toUpperCase()` devolve nova nos dois.

**Onde a analogia quebra:** em JS, `===` compara o conteúdo. Em Java, `==` compara **referência** também para String. Quando dá `true`, é porque os dois apontam para o mesmo objeto do pool — não porque o texto é igual.

**O experimento que provou isso:**
```java
String a = "teste";
String b = "teste";           a == b  → true   (mesmo objeto no pool)
String c = new String("teste"); a == c → false  (força objeto novo no heap)
String d = "tes" + "te";      a == d  → true   (dois literais: o compilador resolve
                                                em tempo de compilação, vira "teste")
String parte = "tes";
String e = parte + "te";      a == e  → false  (tem variável no meio: acontece em
                                                runtime, cria objeto fora do pool)
```
Duas expressões visualmente quase idênticas (`d` e `e`) com resultados opostos. É exatamente por isso que a regra é absoluta: **comparação de String é sempre `.equals()`**.

**Por que ela é imutável (4 motivos):**
1. O String pool só funciona assim — se eu pudesse alterar `"teste"`, alteraria para todo mundo que aponta pra lá.
2. `hashCode` é calculado uma vez e cacheado. É por isso que `HashMap<String, ...>` é rápido — e só é seguro porque o conteúdo nunca muda.
3. Segurança: senhas, URLs, nomes de classe em `Class.forName()`. Se fossem mutáveis, dariam para alterar depois da validação.
4. Thread-safe de graça — objeto imutável não tem race condition.

**Armadilha 1 — concatenação em loop.** Cada `+=` cria uma String nova e copia tudo: é O(n²). Medi com 50.000 iterações no meu M-chip:
- `+=` em loop: ~2000ms
- `StringBuilder`: ~3ms

**Regra mecânica:** `+` para juntar duas ou três coisas fixas; `StringBuilder` sempre que estiver dentro de loop.

**Armadilha 2 — resultado descartado.** `original.toUpperCase();` numa linha sozinha não faz nada. O compilador nem avisa; o IntelliJ marca em cinza.

**Diferenças de método vindo de JS:**

| JavaScript | Java |
|---|---|
| `str.trim()` | `str.strip()` (Unicode-aware; `trim()` é legado) |
| `str[0]` | não existe — `str.charAt(0)` |
| `str.at(-1)` | não existe — `str.charAt(str.length()-1)` |
| `` `olá ${x}` `` | `"olá %s".formatted(x)` ou text block `"""` |
| `str.includes(x)` | `str.contains(x)` |
| `str.split("")` | `str.toCharArray()` |

E `charAt()` devolve um `char` (primitivo), não uma String de um caractere. Em JS não existe tipo char.

**Se me perguntarem:** por que String é imutável → pool, hashCode cacheado, segurança e thread-safety. E `==` vs `.equals()` → referência vs conteúdo.

---

## switch expression

**O que é:** desde o Java 14, `switch` pode ser **expressão** — retorna valor e é atribuível — usando seta em vez de dois-pontos.

```java
String tipo = switch (dia) {
    case SABADO, DOMINGO -> "fim de semana";
    case SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA -> "dia útil";
};
```

**O que a forma nova resolve:**
- **Sem `break`.** A seta não cai no próximo caso (o *fallthrough*, que é bug em 99% dos casos).
- **É expressão**, então atribuo direto o resultado. A forma antiga era comando: eu precisava declarar a variável antes e atribuir dentro de cada caso.
- **Vírgula agrupa casos**, sem o truque de empilhar `case` vazios.
- **Exaustividade verificada.** Com `enum`, se eu esquecer um valor, **não compila** — e por isso posso omitir o `default`. Se alguém adicionar um valor ao enum amanhã, o build quebra e aponta onde.

**Vindo de TS:** a exaustividade é a mesma garantia que eu tinha com union type + checagem `never` no `default`. A diferença é que em Java é nativo, não um truque de tipagem.

**`yield`** é o "return do bloco", quando um caso precisa de mais de uma linha:
```java
case 2 -> {
    boolean bissexto = ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0);
    yield bissexto ? 29 : 28;
}
```

**Armadilha:** com `String` no switch, o `default` é **obrigatório** — String tem infinitos valores possíveis, então não há como ser exaustivo. Só com `enum` (e depois com `sealed`) o compilador consegue garantir cobertura.

---

## var (inferência de tipo)

**O que é:** desde o Java 10, `var` deixa o compilador inferir o tipo pelo lado direito da atribuição.

**Vindo de TS:** parecido com `const`/`let` sem anotação de tipo.

**Onde a analogia quebra — `var` NÃO é `any`.** O tipo é fixado em tempo de compilação e continua estático. `var x = 5; x = "texto";` não compila. Não tem nada de dinâmico.

**Onde não funciona:**
```java
var x;                    // ❌ sem valor, não há o que inferir
var y = null;             // ❌ null não tem tipo
var soma = (a, b) -> a+b; // ❌ lambda não infere
```
E não pode ser atributo de classe nem parâmetro de método.

**Quando uso:** quando o tipo já está escrito na linha.
```java
var repositorio = new LivroRepositoryImpl();   // repetir o tipo seria ruído
for (var livro : livros) { ... }
```

**Quando NÃO uso:** quando esconde informação.
```java
var resultado = processar();   // resultado do quê? sou obrigado a ir olhar o método
```

**Por que a regra em Java é mais conservadora que em TS:** o TypeScript infere o tipo de retorno das funções, e o editor mostra isso em todo lugar. Em Java, a assinatura do método é a única documentação do tipo — se eu escondo na variável também, quem lê fica sem nenhuma pista sem navegar até a definição.

---

## Arrays

**O que é:** estrutura de **tamanho fixo**, definido na criação e imutável depois. Já vem inicializada com o valor padrão do tipo (`int` = 0, objeto = `null`).

```java
int[] numeros = new int[5];              // [0, 0, 0, 0, 0]
String[] nomes = {"Ana", "Bruno"};
numeros.length                           // CAMPO, não método — sem parênteses
```

**Vindo de TS:** o `Array<T>` do JS é dinâmico — tem `push`, `map`, `filter`. O array Java não tem nada disso. O equivalente real do array JS é a `List<T>` do Java, que eu vou usar em 95% dos casos.

| JavaScript | Java |
|---|---|
| `arr.length` | `arr.length` (campo, sem parênteses) |
| `arr.push(x)` | não existe — tamanho fixo |
| `arr.map(f)` | não existe — `List` + Stream (Fase 2) |
| `arr.slice(i,j)` | `Arrays.copyOfRange(arr, i, j)` |
| `arr.includes(x)` | `Arrays.asList(arr).contains(x)` |
| `arr.sort()` | `Arrays.sort(arr)` (modifica no lugar) |
| `console.log(arr)` | `Arrays.toString(arr)` |

**Armadilha — imprimir array direto:**
```java
System.out.println(numeros);                  // [I@1b6d3586   ← lixo
System.out.println(Arrays.toString(numeros)); // [3, 1, 2]     ← certo
```
Aquele `[I@1b6d3586` é o `toString()` herdado de `Object`: tipo do array + hash em hexadecimal. Array não sobrescreve `toString()`. Para matriz, `Arrays.deepToString()`.

**`Arrays.equals()` existe porque `==` compara referência** — o mesmo padrão do String pool e do Integer cache, agora em terceiro contexto. Três situações diferentes, um princípio só.

**Por que quase não uso na prática:** tamanho fixo e sem métodos de transformação. `List` (Semana 5) resolve os dois. Arrays aparecem principalmente em `String[] args`, em APIs antigas e quando performance bruta importa.

---

# Erros que cometi esta semana

Registrar erro é mais útil que registrar acerto — o erro é o que eu vou repetir.

| Erro | Causa | Como evitar |
|---|---|---|
| Palíndromo falhava com hífen | Removi só espaços (blacklist) em vez de manter só letras (whitelist) | Ao limpar entrada, listar o que **manter**, não o que remover — assim caractere novo nunca escapa |
| Capitalizador usava `+=` dentro de loop | Hábito do JS, onde a penalidade é bem menor | `StringBuilder` sempre que a concatenação estiver dentro de loop |
| `charAt(0)` estourava com string vazia | Assumi que toda palavra teria pelo menos um caractere | Testar sempre os três: vazio, nulo e malformado (espaço duplo) |
| Capitalizador não capitalizava "da Silva" | Não tratei a primeira palavra como exceção à regra de preposição | Regra de negócio precisa de decisão explícita, não de comportamento acidental |
| Máscara de CPF devolvia `""` em erro | Achei que retorno vazio era "seguro" | Falha silenciosa vira bug fantasma — lançar exceção com contexto |
| `"Operador desconhecido %d: \"%s\"".formatted(operador)` | Dois placeholders, um argumento; e `%d` para uma String | Conferir se a quantidade e o tipo dos placeholders batem com os argumentos |
| O erro acima ficou **escondido** | `IllegalFormatConversionException` herda de `IllegalArgumentException`, então meu `catch` engoliu | `catch` largo demais transforma erro de programação em erro de negócio aparente |
| Não protegi `%` contra divisor zero | Só pensei em `/` | Divisor zero afeta divisão **e** módulo — e com `double` a linguagem não avisa |
| Duplicei `normalizar()` em duas classes | Copiei em vez de extrair | Código repetido duas vezes quer virar um lugar só |
| Importei `java.util.Locale` sem usar | Copiei sem pensar | Import não usado é ruído — o IntelliJ marca em cinza |

---

# Dúvidas em aberto

- [ ] O que exatamente `Normalizer.Form.NFD` faz na representação do caractere? (sei o efeito, não sei o mecanismo Unicode)
- [ ] Quando `Pattern.compile` em constante realmente faz diferença mensurável?
- [ ] Se String é imutável, como o `StringBuilder` funciona por dentro? (array de char redimensionável?)
- [ ] Por que `String` tem pool mas outros objetos imutáveis não?

---

# Checkpoint da semana

- [x] Explico por que `127 == 127` é true e `128 == 128` é false
- [x] Explico por que String é imutável (pool, hashCode, segurança, thread-safety)
- [x] Explico a diferença entre `==` e `.equals()` em três contextos (String, Integer, array)
- [x] Escrevo `switch` expression sem consultar
- [x] Sei quando usar `StringBuilder` em vez de `+`
- [x] Sei por que `var` não é `any`
- [ ] **Fiz o validador de CPF sozinho** — fiz só a *máscara*, não a validação com dígito verificador. Pendente.

---

# Perguntas e respostas
1. String imutável — 
A resposta mais forte: o String pool só funciona porque ela é imutável. Se "teste" pudesse ser alterado, você alteraria para todo mundo que aponta para aquele objeto no pool — código que nem sabe da existência do seu.
Um segundo motivo, útil para amarrar com a Semana 5: hashCode é calculado uma vez e guardado. É por isso que HashMap<String, ...> é rápido — e só é seguro porque o conteúdo nunca muda. Se mudasse, a chave "se perderia" dentro do mapa.
Guarde um dos dois. Se lembrar dos dois, melhor.

2. Array — Por que System.out.println(meuArray) imprime aquele lixo tipo [I@1b6d3586?
Só um ajuste de nome: é Arrays.toString(), com s. Arrays é a classe utilitária (como o TextoUtils que a gente fez); Array sozinho é outra coisa. Detalhe pequeno, mas em prova escrita conta.
Versão polida:
Array não sobrescreve toString(), então herda o de Object, que imprime tipo + hash em hexadecimal. Para ver o conteúdo, Arrays.toString() — ou Arrays.deepToString() para matriz.

3. Switch — Qual a diferença prática entre o switch com seta e o antigo com break?
"Não corre risco de esquecer o break" está certo, e é a resposta que a maioria dá. Mas você deixou de fora o ganho maior:
O switch com seta é uma expressão — ele retorna um valor. O antigo era comando: você declarava a variável antes e atribuía dentro de cada caso.

String tipo = switch (dia) { ... };   // só a forma nova permite isso

E o terceiro ganho, que é o mais valioso: com enum, o compilador verifica exaustividade. Se faltar um caso, não compila. Por isso você pode omitir o default. É a mesma segurança que você tinha em TS com union type.

Versão polida:
A seta elimina o fallthrough (esquecer break), permite usar switch como expressão que retorna valor, e com enum o compilador garante que todos os casos foram cobertos — se alguém adicionar um valor ao enum, o build quebra.


4. Concatenação — Você tem um loop que roda 10 mil vezes montando um texto. Por que não usar +=?
Você disse O(n). É O(n²).
E aqui a razão importa mais que a letra. Você mesmo escreveu a explicação certa na frase seguinte — "cria um objeto a cada iteração" — mas não fechou o raciocínio:
A cada +=, o Java cria uma String nova e copia todo o conteúdo anterior. Na iteração 1 copia 1 caractere, na 2 copia 2, na 5000 copia 5000. Somando tudo: n²/2 operações de cópia.
Se fosse só "cria um objeto por iteração", seria O(n) — caro, mas linear. É a cópia acumulada que torna quadrático. Foi por isso que seu benchmark deu 2000ms contra 3ms: não é 2x mais lento, é centenas de vezes.
E o "código fica verboso" não é motivo — na verdade += é menos verboso que StringBuilder. O motivo é só performance.

Versão polida:
Como String é imutável, cada += cria um objeto novo e copia todo o conteúdo acumulado. Isso torna o loop O(n²). StringBuilder mantém um buffer mutável e só materializa a String no final, ficando O(n).

# O que levo para a Semana 2

O padrão que mais se repetiu: **`==` compara referência**. Apareceu em String, em Integer e em array, com explicações diferentes por fora e a mesma causa por dentro. Na Semana 3, quando eu implementar `equals()` e `hashCode()` nas minhas próprias classes, esse fio se fecha.

O segundo padrão: **falhar alto e com contexto**. Retorno vazio, `catch` largo e mensagem genérica esconderam bugs meus três vezes numa única semana.