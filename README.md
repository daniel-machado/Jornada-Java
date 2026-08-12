# Jornada Java

Repositório de estudos da minha transição de **TypeScript/React Native** para **back-end Java**, com foco em Spring Boot, IA aplicada e AWS.

Estudo público e diário. Cada pasta corresponde a uma fase do plano, e cada commit é uma sessão de estudo real.

---

## Stack em aprendizado

**Linguagem** · Java 21 LTS (Temurin)
**Build** · Maven
**Framework** · Spring Boot
**Persistência** · PostgreSQL, JPA/Hibernate, Flyway
**Testes** · JUnit 5, AssertJ, Mockito, Testcontainers
**IA** · LangChain4j, Amazon Bedrock, RAG com pgvector
**Cloud** · AWS (ECS Fargate, RDS, S3, SQS, Lambda)
**Infra** · Docker, GitHub Actions, Terraform

---

## Progresso

| Fase | Tema | Status      |
|---|---|-------------|
| 0 | Setup do ambiente | ✅          |
| 1 | Java Essencial | 🔄 Semana 2 |
| 2 | Java Moderno (Streams, Optional, Generics) | ⬜          |
| 3 | Ferramentas e Testes | ⬜          |
| 4 | Banco de Dados e SQL | ⬜          |
| 5 | Spring Boot | ⬜          |
| 6 | Docker e CI/CD | ⬜          |
| 7 | AWS para Java | ⬜          |
| 8 | IA Corporativa (LangChain4j + Bedrock) | ⬜          |
| 9 | Arquitetura e Design | ⬜          |
| 10 | Concorrência e System Design | ⬜          |

### Fase 1 — detalhamento

- [x] Semana 1 — Sintaxe, tipos primitivos, String, arrays
- [ ] Semana 2 — OO: classes, encapsulamento, herança, polimorfismo
- [ ] Semana 3 — OO: interfaces, record, enum, sealed, equals/hashCode
- [ ] Semana 4 — Exceções
- [ ] Semanas 5–6 — Collections
- [ ] Semana 7 — Projeto: Sistema de Biblioteca

---

## Estrutura

```
jornada-java/
├── anotacoes/                  # o que aprendi, com minhas palavras
│   └── fase-01/
├── fase-01-java-essencial/
│   ├── semana-01-sintaxe/
│   ├── semana-02-oo-basico/
│   ├── semana-03-oo-avancado/
│   ├── semana-04-excecoes/
│   ├── semana-05-collections/
│   └── projeto-biblioteca/     # projeto que evolui até a Fase 7
└── ...
```

---

## Projetos

### 🚧 Sistema de Biblioteca
Projeto-espinha do plano. Evolui a cada fase:

| Fase | Estado |
|---|---|
| 1 | Java puro, console, persistência em arquivo |
| 3 | Coberto por testes (JUnit + Mockito) |
| 4 | PostgreSQL com JPA e Flyway |
| 5 | API REST com Spring Boot e JWT |
| 6 | Containerizado, com pipeline CI/CD |
| 7 | Rodando em AWS ECS Fargate |

*(mais projetos serão adicionados a partir da Fase 5)*

---

## Como rodar

Requer JDK 21+.

```bash
# Fase 1 — classes soltas, sem build tool
cd fase-01-java-essencial/semana-01-sintaxe
javac TiposPrimitivos.java && java TiposPrimitivos
```

A partir da Fase 3 o projeto passa a usar Maven (`mvn test`, `mvn package`).

---

## Convenção de commits

Segue [Conventional Commits](https://www.conventionalcommits.org/pt-br/):

| Prefixo | Uso |
|---|---|
| `feat:` | código funcional novo |
| `fix:` | correção |
| `refactor:` | mudança sem alterar comportamento |
| `test:` | testes |
| `docs:` | documentação e anotações |
| `chore:` | configuração e dependências |
| `study:` | experimentos e exercícios de estudo |

---

## Anotações

Cada semana tem um arquivo em [`anotacoes/`](./anotacoes) onde registro os conceitos com minhas próprias palavras — incluindo os erros que cometi e o que os causou.

---

*Estudo em andamento. Feedback e sugestões são bem-vindos.*