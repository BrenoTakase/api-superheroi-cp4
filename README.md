# 🦸 Hero API

API REST desenvolvida em **Java 17 + Spring Boot** para gerenciar **super-heróis** e **equipes de heróis**, com persistência em **SQL Server** via **Spring Data JPA**.

Projeto do **Check Point 2 — Microservices and Web Engineering (2º semestre/2026)**.

## 👥 Integrantes

| Nome | RM |
|---|---|
| _Breno Takase Gomes_ | _RM554799_ |


docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=1q2w3e4R@" \
  -p 1433:1433 --name sqlserver -d mcr.microsoft.com/mssql/server:2022-latest

docker cp src/main/resources/script-sqlserver.sql sqlserver:/tmp/script.sql
docker exec -it sqlserver /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "1q2w3e4R@" -C -i /tmp/script.sql

sqlcmd -S localhost -U sa -P "1q2w3e4R@" -C -Q "CREATE DATABASE api"

mvn clean spring-boot:run

---

## 📁 Organização do projeto

```text
src/main/java/br/com/fiap/hero_api
├── HeroApiApplication.java      # Classe principal
├── controller/                  # Endpoints REST
│   ├── HeroiController.java     # CRUD de heróis (usa Service + DTOs)
│   ├── EquipeController.java    # CRUD de equipes (usa Repository direto)
│   └── PingController.java      # Health check simples
├── dto/                         # Objetos de entrada/saída + Mapper
│   ├── HeroiCreateRequest.java
│   ├── HeroiUpdateRequest.java
│   ├── HeroiResponse.java
│   └── HeroiMapper.java
├── model/                       # Entidades JPA (tabelas)
│   ├── Heroi.java               # tabela "herois"
│   └── Equipe.java              # tabela "equipes"
├── repository/                  # Interfaces Spring Data JPA
│   ├── HeroiRepository.java
│   └── EquipeRepository.java
└── service/
    └── HeroiService.java        # Regras de acesso aos dados de heróis

src/main/resources
├── application.properties       # Configuração base (profile padrão: dev)
├── application-dev.properties   # SQL Server – desenvolvimento
├── application-prd.properties   # SQL Server – produção (somente variáveis de ambiente)
└── script-sqlserver.sql         # Criação do banco, tabelas e dados de exemplo
```

### Modelo de dados

**Tabela `herois`** (entidade `Heroi`)

| Coluna | Tipo | Observação |
|---|---|---|
| `id` | BIGINT IDENTITY | Chave primária |
| `codinome` | VARCHAR(100) | Obrigatório |
| `nome_civil` | VARCHAR(100) | Opcional |
| `poder` | VARCHAR(150) | Obrigatório |
| `universo` | VARCHAR(50) | Obrigatório (ex.: Marvel, DC) |
| `nivel_poder` | INT | Obrigatório, de 1 a 100 |

**Tabela `equipes`** (entidade `Equipe`)

| Coluna | Tipo | Observação |
|---|---|---|
| `id` | BIGINT IDENTITY | Chave primária |
| `nome_equipe` | VARCHAR(100) | Obrigatório |
| `base_operacoes` | VARCHAR(150) | Opcional |

---

## 📋 Pré-requisitos

- Java 17+
- Maven (ou o Maven Wrapper incluso: `mvnw` / `mvnw.cmd`)
- SQL Server (local, remoto, Azure SQL ou container Docker)
- Docker (opcional)

---

## 🗄️ Configuração do SQL Server

### 1. Subir um SQL Server (caso ainda não tenha um)

Se for usar um SQL Server local via Docker:

```sh
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=1q2w3e4R@" \
  -p 1433:1433 --name sqlserver -d mcr.microsoft.com/mssql/server:2022-latest
```

### 2. Criar o banco de dados

O driver do SQL Server **não cria o banco automaticamente**, então o banco precisa existir antes de iniciar a API.
Execute o script [`src/main/resources/script-sqlserver.sql`](src/main/resources/script-sqlserver.sql) no SSMS, Azure Data Studio ou `sqlcmd`. Ele:

- cria o banco `herois_db`;
- cria as tabelas `herois` e `equipes`;
- insere alguns heróis e equipes para teste.

Via `sqlcmd` (no container acima):

```sh
docker cp src/main/resources/script-sqlserver.sql sqlserver:/tmp/script.sql
docker exec -it sqlserver /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "1q2w3e4R@" -C -i /tmp/script.sql
```


sqlcmd -S localhost -U sa -P "1q2w3e4R@" -C -Q "CREATE DATABASE api"

As tabelas também são criadas/atualizadas automaticamente pelo Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Informar a conexão para a API

A conexão é configurada nos arquivos `application-dev.properties` e `application-prd.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://${DB_SERVER_URL:localhost}:${DB_SERVER_PORT:1433};databaseName=${DB_SCHEMA:herois_db};encrypt=false;trustServerCertificate=true
spring.datasource.username=${DB_USER:sa}
spring.datasource.password=${DB_PWD:1q2w3e4R@}
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
```

Os valores são lidos de **variáveis de ambiente**:

| Variável | Descrição | Padrão no profile `dev` |
|---|---|---|
| `DB_SERVER_URL` | Endereço do servidor SQL Server | `localhost` |
| `DB_SERVER_PORT` | Porta do SQL Server | `1433` |
| `DB_SCHEMA` | Nome do banco de dados | `herois_db` |
| `DB_USER` | Usuário do banco | `sa` |
| `DB_PWD` | Senha do banco | `1q2w3e4R@` |
| `SPRING_PROFILES_ACTIVE` | Profile do Spring (`dev` ou `prd`) | `dev` |

- **`dev`**: usa os valores padrão acima caso as variáveis não sejam informadas.
- **`prd`**: exige que **todas** as variáveis sejam informadas.

> Para usar um **SQL Server remoto** (ex.: banco disponibilizado pelo professor ou Azure SQL), basta trocar `DB_SERVER_URL`, `DB_SERVER_PORT`, `DB_SCHEMA`, `DB_USER` e `DB_PWD` pelos dados fornecidos.

**Linux / macOS**

```sh
export DB_SERVER_URL=localhost
export DB_SERVER_PORT=1433
export DB_SCHEMA=herois_db
export DB_USER=sa
export DB_PWD=1q2w3e4R@
export SPRING_PROFILES_ACTIVE=dev
```

**Windows PowerShell**

```powershell
$env:DB_SERVER_URL="localhost"
$env:DB_SERVER_PORT="1433"
$env:DB_SCHEMA="herois_db"
$env:DB_USER="sa"
$env:DB_PWD="1q2w3e4R@"
$env:SPRING_PROFILES_ACTIVE="dev"
```

Um modelo dessas variáveis está em [`.env.example`](.env.example).

---

## 🚀 Executando a aplicação

Com Maven:

```sh
mvn spring-boot:run
```

Com o Maven Wrapper:

```sh
./mvnw spring-boot:run      # Linux / macOS
.\mvnw.cmd spring-boot:run  # Windows
```

A API sobe em **http://localhost:8080** e a **Swagger UI** fica disponível na raiz: **http://localhost:8080/**

Teste rápido:

```sh
curl http://localhost:8080/ping
# pong
```

### Com Docker

```sh
docker build -t hero-api:1.0 .

docker run -p 8080:8080 \
  -e DB_SERVER_URL=host.docker.internal \
  -e DB_SERVER_PORT=1433 \
  -e DB_SCHEMA=herois_db \
  -e DB_USER=sa \
  -e DB_PWD=1q2w3e4R@ \
  -e SPRING_PROFILES_ACTIVE=prd \
  hero-api:1.0
```

> `host.docker.internal` permite que o container acesse o SQL Server rodando na máquina host.

---

## 🔗 Endpoints

Base URL: `http://localhost:8080/api/v1`

### Heróis — `/api/v1/herois`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `GET` | `/herois` | Lista todos os heróis | `200` |
| `GET` | `/herois?universo=Marvel` | Lista heróis de um universo | `200` |
| `GET` | `/herois/{id}` | Busca um herói pelo id | `200` / `404` |
| `POST` | `/herois` | Cadastra um herói | `201` / `400` |
| `PUT` | `/herois/{id}` | Altera um herói | `200` / `400` / `404` |
| `DELETE` | `/herois/{id}` | Exclui um herói | `204` / `404` |

Corpo do `POST` / `PUT`:

```json
{
  "codinome": "Batman",
  "nomeCivil": "Bruce Wayne",
  "poder": "Intelecto, artes marciais e tecnologia",
  "universo": "DC",
  "nivelPoder": 80
}
```

Validações: `codinome` (3–100 caracteres), `poder` e `universo` obrigatórios, `nivelPoder` entre 1 e 100. Dados inválidos retornam `400 Bad Request`.

### Equipes — `/api/v1/equipes`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `GET` | `/equipes` | Lista todas as equipes | `200` |
| `GET` | `/equipes/{id}` | Busca uma equipe pelo id | `200` / `404` |
| `POST` | `/equipes` | Cadastra uma equipe | `201` |
| `PUT` | `/equipes/{id}` | Altera uma equipe | `200` / `404` |
| `DELETE` | `/equipes/{id}` | Exclui uma equipe | `204` / `404` |

Corpo do `POST` / `PUT`:

```json
{
  "nome": "X-Men",
  "base": "Instituto Xavier - Westchester"
}
```

### Outros

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/ping` | Verifica se a API está no ar (`pong`) |
| `GET` | `/` | Swagger UI (documentação interativa) |

---

## 🧪 Roteiro de teste (cURL)

```sh
# 1. Listar heróis (consulta no SQL Server)
curl http://localhost:8080/api/v1/herois

# 2. Cadastrar um herói (INSERT no SQL Server)
curl -X POST http://localhost:8080/api/v1/herois \
  -H "Content-Type: application/json" \
  -d '{"codinome":"Batman","nomeCivil":"Bruce Wayne","poder":"Intelecto e tecnologia","universo":"DC","nivelPoder":80}'

# 3. Buscar pelo id retornado
curl http://localhost:8080/api/v1/herois/5

# 4. Filtrar por universo
curl "http://localhost:8080/api/v1/herois?universo=DC"

# 5. Alterar o herói (UPDATE)
curl -X PUT http://localhost:8080/api/v1/herois/5 \
  -H "Content-Type: application/json" \
  -d '{"codinome":"Batman","nomeCivil":"Bruce Wayne","poder":"Intelecto, artes marciais e tecnologia","universo":"DC","nivelPoder":85}'

# 6. Excluir o herói (DELETE)
curl -X DELETE http://localhost:8080/api/v1/herois/5

# 7. Testar validação (deve retornar 400)
curl -X POST http://localhost:8080/api/v1/herois \
  -H "Content-Type: application/json" \
  -d '{"codinome":"X","poder":"","universo":"DC","nivelPoder":500}'

# 8. Equipes
curl http://localhost:8080/api/v1/equipes
curl -X POST http://localhost:8080/api/v1/equipes \
  -H "Content-Type: application/json" \
  -d '{"nome":"X-Men","base":"Instituto Xavier - Westchester"}'
```

### Conferindo direto no banco

Para comprovar que as operações foram gravadas no SQL Server:

```sql
USE herois_db;
SELECT * FROM herois;
SELECT * FROM equipes;
```

Com `spring.jpa.show-sql=true`, os comandos SQL executados pelo Hibernate também aparecem no console da aplicação.

---

## 🔒 Segurança

Não versione credenciais reais. Use um arquivo `.env` local (já incluído no `.gitignore`) com base no `.env.example`.
