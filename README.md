# Gestão de Espaço Físico

- Sistema de Gestão de Espaço Físico feito para a disciplina de Banco de Dados 1, utilizando as tecnologias: JAVA + Spring Boot + JDBC

- Dependencias utilizadas com o Spring Initialzr: Spring Web, Thymeleaf, PostgreSQL Driver SQL e Spring Data JDBC 

## Requisitos

- Java 21
- Maven
- PostgreSQL

## Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL chamado `gestao_espaco`:

```sql
CREATE DATABASE gestao_espaco;
```

2. Configure as credenciais do banco de dados no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_espaco
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Executando o Projeto

Para executar o projeto, você pode usar o Maven:

```bash
mvn spring-boot:run
```

Ou executar `AtividadeFinalApplication`.

## Acesso ao Sistema

http://localhost:8080


## SQL

Os arquivos `queries.sql`,`data.sql` e `schema.sql` contém DQL, DML e DDL respectivamente.