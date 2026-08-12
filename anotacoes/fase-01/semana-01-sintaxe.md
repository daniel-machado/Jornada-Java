# Semana 1 — Sintaxe e Tipos

**Período:** 11/08/2026 – __/08/2026
**Fonte principal:** dev.java/learn — Language Basics

---

## Como uso este arquivo

Um bloco por conceito. Escrevo **com minhas palavras**, não copio.
Se não consigo explicar em 3 frases, não entendi ainda.

Template:

```
## [Conceito]

**O que é:**
**Vindo de TS:**
**Onde a analogia quebra:**
**Armadilha:**
**Se me perguntarem na entrevista:**
```

---

## Tipos primitivos

**O que é:** Java tem 8 tipos que não são objetos — `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`. Não têm métodos, não podem ser `null`, e cada um ocupa um tamanho fixo na memória.

**Vindo de TS:** JS tem um único `number` (float 64 bits). O `double` do Java é o equivalente direto dele.

**Onde a analogia quebra:** na prática eu vou usar `int` para inteiros, não `double`. E `int` tem limite: ±2,1 bilhões.

**Armadilha:** overflow silencioso. `Integer.MAX_VALUE + 1` vira negativo sem erro nenhum. Por isso IDs de banco são `long`, não `int`.

**Se me perguntarem:** primitivo vs wrapper — primitivo não pode ser null e tem valor padrão (`int` = 0); wrapper (`Integer`) pode ser null. Collections só aceitam objetos, então é `List<Integer>`, nunca `List<int>`.

---

## Divisão inteira

**O que é:** se os dois operandos são inteiros, o resultado é inteiro. `7 / 2` dá `3`, não `3.5`.

**Vindo de TS:** isso não existe em JS — `7 / 2` sempre dá `3.5`.

**Armadilha:** bug silencioso em cálculo de média, porcentagem ou rateio. Para forçar decimal, basta um operando ser `double`: `7.0 / 2`.

---

## Integer cache (o enigma do 127 vs 128)

**O que é:** `Integer a = 127, b = 127; a == b` → `true`, mas com `128` → `false`.

**Por quê:** o Java mantém um cache de objetos `Integer` de **-128 a 127**. Dentro desse intervalo, o autoboxing devolve sempre a mesma instância. Fora dele, cria um objeto novo a cada vez. Como `==` compara **referência** e não valor, dois objetos distintos com o mesmo número dão `false`.

**Detalhes que descobri depois:**
- Não é otimização opcional — está na especificação da linguagem (JLS §5.1.7). Também vale para `boolean`, `byte`, `char` até 127 e `short` no mesmo intervalo.
- Só age no **autoboxing**. `Integer a = 127` chama `Integer.valueOf(127)`, que consulta o cache. `new Integer(127)` força objeto novo e daria `false` mesmo dentro do intervalo.

**A lição real:** `==` compara referência para qualquer objeto. O fato de às vezes funcionar é coincidência de implementação, não garantia. **Para objetos, sempre `.equals()`.**

**Se me perguntarem:** essa é a base da pergunta "diferença entre `==` e `.equals()`" — que cai em praticamente toda entrevista de Java.

---

## String

**O que é:**

**Vindo de TS:**

**Onde a analogia quebra:**

**Armadilha:**

**Se me perguntarem:**

---

## switch expression

**O que é:**

**Vindo de TS:**

**Armadilha:**

---

## var (inferência de tipo)

**O que é:**

**Vindo de TS:**

**Quando NÃO usar:**

---

## Arrays

**O que é:**

**Vindo de TS:**

**Por que quase não uso na prática:**

---

# Erros que cometi esta semana

Registrar erro é mais útil que registrar acerto — o erro é o que eu vou repetir.

| Erro | Causa | Como evitar |
|---|---|---|
| | | |

---

# Dúvidas em aberto

- [ ]

---

# Checkpoint da semana

- [x] Explico por que `127 == 127` é true e `128 == 128` é false
- [ ] Explico por que String é imutável
- [ ] Escrevo `switch` expression sem consultar
- [ ] Fiz o validador de CPF sozinho