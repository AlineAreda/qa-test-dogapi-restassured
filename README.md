<div align="center">

<img src="assets/dog-api-logo.png" alt="Dog API" width="180">

# 🐶 Dog API — Automação de Testes de API

Projeto de automação de testes de API desenvolvido em **Java**, utilizando **RestAssured** e **JUnit 5**, com gerenciamento de configurações através do **Owner** e geração de relatórios com **Allure**.

</div>

----------

## 📌 Sobre o projeto

Este projeto foi desenvolvido com o objetivo de automatizar e validar os principais endpoints da **Dog API**, avaliando diferentes cenários funcionais e o contrato das respostas retornadas pela API.

A estratégia de testes contempla:

-   validação de códigos HTTP;

-   validação do formato das respostas;

-   validação do conteúdo JSON;

-   validação da estrutura dos dados;

-   cenários positivos;

-   cenário negativo;

-   testes parametrizados;

-   validação de comportamento;

-   geração de relatório de execução com Allure.


----------

## 🧪 Endpoints testados

Endpoint

Método

Cenários principais

`/api/breeds/list/all`

`GET`

Lista de raças, estrutura das raças e sub-raças

`/api/breed/{breed}/images`

`GET`

Imagens de raças válidas, formato das imagens e raça inexistente

`/api/breeds/image/random`

`GET`

Imagem aleatória, formato da URL e comportamento aleatório

----------

## 🏗️ Arquitetura do projeto

```
ceo-dog-api
│
├── src
│   └── test
│       ├── java
│       │   └── com.ceodog.api
│       │       │
│       │       ├── config
│       │       │   ├── BaseTest.java
│       │       │   └── Configuracoes.java
│       │       │
│       │       ├── specs
│       │       │   └── ResponseSpecs.java
│       │       │
│       │       └── tests
│       │           ├── BreedsListTest.java
│       │           ├── BreedImagesTest.java
│       │           └── RandomImageTest.java
│       │
│       └── resources
│           └── properties
│               └── hml.properties
│
├── assets
│   ├── dog-api-logo.png
│   └── reports
│
├── .gitignore
├── pom.xml
└── README.md
```

### 📂 Organização

`**config**`

Responsável pela configuração base dos testes.

-   `BaseTest.java` centraliza a configuração do RestAssured, incluindo URL, porta e caminho base.

-   `Configuracoes.java` utiliza o Owner para leitura das configurações do ambiente.


`**specs**`

Contém especificações de resposta reutilizáveis.

-   `ResponseSpecs.java` centraliza as validações comuns de respostas de sucesso, como `HTTP 200` e `Content-Type JSON`.


`**tests**`

Contém as suítes de testes organizadas de acordo com os endpoints avaliados.

----------

## 🧰 Tecnologias e dependências

### Principais

-   **Java 17**

-   **Maven**

-   **JUnit Jupiter 5.9.3**

-   **RestAssured 5.4.0**

-   **Owner 1.0.12**

-   **Allure JUnit 5 2.13.8**

-   **Allure Maven 2.10.0**


### Dependências adicionais configuradas

O projeto também possui:

-   `json-schema-validator` — suporte à validação de JSON Schema;

-   `slf4j-simple` — implementação simples de logging;

-   `Jackson Databind` — suporte à serialização/desserialização JSON;

-   `Lombok` — redução de código boilerplate.


Essas dependências estão disponíveis no projeto para suportar possíveis evoluções da automação, embora nem todas sejam necessárias para os cenários atualmente implementados.

----------

## ⚙️ Configuração

As configurações do ambiente estão em:

```
src/test/resources/properties/hml.properties
```

Exemplo:

```
baseUrl=https://dog.ceo
basePath=/api
port=443
maxTimeout=90000
```

O carregamento das propriedades é realizado pelo **Owner**, e a configuração do RestAssured é centralizada no `BaseTest`.

----------

## ▶️ Execução dos testes

### Pré-requisitos

Para executar o projeto, é necessário ter instalado:

-   **Java 17 ou superior**

-   **Maven 3.8 ou superior**

-   acesso à internet para comunicação com a Dog API


### Executar todos os testes

Na raiz do projeto:

```
mvn clean test
```

Esse comando limpa os resultados anteriores e executa todas as classes de teste encontradas pelo Maven.

### Executar sem limpar os resultados anteriores

```
mvn test
```

----------

## 📊 Relatório de testes

O projeto utiliza **Allure** para geração de um relatório visual da execução dos testes.

Após executar:

```
mvn clean test
```

os resultados são armazenados em:

```
target/allure-results
```

Para abrir o relatório:

```
mvn allure:serve
```

### Resultado da execução

> 📸 **Adicionar aqui o screenshot do relatório Allure.**

Quando o arquivo estiver salvo em:

```
assets/reports/allure-report.png
```

utilize:

```
![Relatório Allure](assets/reports/allure-report.png)
```

----------

## 🎥 Demonstração da execução

Abaixo será disponibilizado um vídeo demonstrando a execução da suíte de testes e a geração dos resultados.

> 🎬 **Adicionar aqui o vídeo ou GIF da execução.**

Para uma demonstração diretamente no GitHub, pode ser utilizado um GIF curto ou um vídeo anexado ao repositório.

----------

# 🔍 Cenários automatizados

## 1. Lista de raças

### `GET /api/breeds/list/all`

São realizados testes para verificar:

-   retorno com sucesso;

-   status HTTP esperado;

-   conteúdo JSON;

-   existência de raças;

-   estrutura das raças;

-   estrutura das sub-raças;

-   presença de raças conhecidas.


O teste estrutural percorre todas as raças retornadas pela API e verifica se cada raça possui uma estrutura válida.

----------

## 2. Imagens de uma raça

### `GET /api/breed/{breed}/images`

São realizados testes para verificar:

-   consulta de uma raça válida;

-   retorno de imagens;

-   lista de imagens não vazia;

-   formato dos dados retornados;

-   diferentes raças através de teste parametrizado;

-   tratamento de uma raça inexistente.


### Cenário negativo

Para uma raça inexistente, a API retorna:

```
{
  "status": "error",
  "message": "Breed not found (main breed does not exist)",
  "code": 404
}
```

O teste valida o contrato completo do erro, incluindo:

-   HTTP `404`;

-   `Content-Type` JSON;

-   `status = error`;

-   mensagem retornada;

-   código `404`.


----------

## 3. Imagem aleatória

### `GET /api/breeds/image/random`

São realizados testes para verificar:

-   retorno com sucesso;

-   existência da imagem;

-   formato da URL;

-   utilização de HTTPS;

-   comportamento aleatório através de múltiplas chamadas.


### 🧠 Decisão sobre o teste de aleatoriedade

O teste não considera que duas chamadas consecutivas obrigatoriamente precisam retornar imagens diferentes.

Isso poderia gerar um **teste instável (flaky)**, pois é perfeitamente possível que um endpoint aleatório retorne o mesmo resultado duas vezes.

Por isso, são realizadas múltiplas chamadas e verificado se existe mais de um resultado distinto entre elas.

A validação busca evidenciar o comportamento de aleatoriedade sem criar uma regra artificial de que **toda chamada deve obrigatoriamente retornar um valor diferente da anterior**.

----------

# 🎯 Estratégia de testes

A estratégia foi construída considerando **cobertura e relevância dos cenários**, e não simplesmente a quantidade de testes.

A cobertura contempla:

```
Cenário positivo
       ↓
Contrato da resposta
       ↓
Estrutura dos dados
       ↓
Parametrização
       ↓
Cenário negativo
       ↓
Validação comportamental
```

A intenção é garantir que os principais comportamentos dos três endpoints estejam cobertos, mantendo a automação simples e de fácil manutenção.

----------

## 🧱 Decisões de arquitetura

A arquitetura foi mantida propositalmente enxuta.

O `BaseTest` centraliza as configurações comuns do RestAssured e o `ResponseSpecs` evita a repetição de especificações de resposta.

Não foi criada uma camada adicional de `Client`, `Models`, `Utils` ou classes específicas de assertions porque, para o escopo atual de apenas três endpoints, isso adicionaria complexidade sem um benefício proporcional.

Novas abstrações podem ser introduzidas conforme a automação cresça e surjam necessidades reais de reutilização ou complexidade.

----------

## 📁 Estrutura de arquivos gerados

Os arquivos gerados durante a execução ficam dentro de:

```
target/
```

Essa pasta não deve ser versionada no Git.

----------

## 🚀 Possíveis evoluções

Como próximos passos, o projeto poderia evoluir com:

-   execução em pipeline CI/CD;

-   integração com GitHub Actions;

-   validação através de JSON Schema;

-   configuração de diferentes ambientes;

-   maior cobertura de cenários de contrato;

-   execução paralela dos testes;

-   publicação automática do relatório Allure.


Essas evoluções não foram adicionadas ao escopo atual para manter o projeto proporcional ao desafio proposto.

----------

## 👩‍💻 Autora

**Aline Oliveira**

QA | Automação de Testes | Qualidade de Software

----------

<div align="center">

🐶 **Dog API — Projeto de Automação de Testes**

</div>