# 🐶 Dog API — Automação de Testes de API

Projeto de automação de testes de API desenvolvido em **Java**, utilizando **RestAssured** e **JUnit 5**, com gerenciamento de configurações através do **Owner**, geração de relatórios com **Allure** e execução automatizada através do **GitHub Actions**.

----------

## 📌 Sobre o projeto

Este projeto foi desenvolvido para automatizar e validar os endpoints listados abaixo da **Dog API**, avaliando diferentes cenários funcionais, estrutura dos dados e contrato das respostas retornadas pela API.

A estratégia considera **cobertura, relevância e manutenção**, evitando a criação de testes apenas para aumentar a quantidade de casos automatizados.

### Cobertura

-   validação de códigos HTTP;
    
-   validação do formato das respostas;
    
-   validação do conteúdo JSON;
    
-   validação da estrutura dos dados;
    
-   cenários positivos;
    
-   cenário negativo;
    
-   testes parametrizados;
    
-   validação de comportamento;
    
-   geração de relatório com Allure;
    
-   execução automatizada com GitHub Actions;
    
-   disponibilização dos resultados como artefatos da pipeline.
    

----------

## 🧪 Endpoints testados

Endpoint

Método

Principais cenários

`/api/breeds/list/all`

`GET`

Lista de raças, estrutura, sub-raças e raças conhecidas

`/api/breed/{breed}/images`

`GET`

Imagens de raças válidas, formato, parametrização e raça inexistente

`/api/breeds/image/random`

`GET`

Imagem aleatória, formato da URL e comportamento aleatório

----------

## 🏗️ Arquitetura

```
ceo-dog-api
├── .github
│   └── workflows
│       └── api-tests.yml
├── src
│   └── test
│       ├── java
│       │   └── com
│       │       └── ceodog
│       │           └── api
│       │               ├── config
│       │               │   ├── BaseTest.java
│       │               │   └── Configuracoes.java
│       │               ├── specs
│       │               │   └── ResponseSpecs.java
│       │               └── tests
│       │                   ├── BreedsListTest.java
│       │                   ├── BreedImagesTest.java
│       │                   └── RandomImageTest.java
│       └── resources
│           └── properties
│               └── hml.properties
├── .gitignore
├── pom.xml
└── README.md
```

### `config`

Responsável pela configuração base dos testes.

`BaseTest.java` centraliza URL base, porta e path base do RestAssured.

`Configuracoes.java` utiliza o **Owner** para leitura das configurações do ambiente.

### `specs`

`ResponseSpecs.java` centraliza validações comuns das respostas de sucesso, evitando repetição nos testes.

### `tests`

Contém as suítes organizadas por endpoint:

-   `BreedsListTest.java`
    
-   `BreedImagesTest.java`
    
-   `RandomImageTest.java`
    

### `.github/workflows`

`api-tests.yml` contém a pipeline de CI/CD responsável pela execução dos testes e geração dos relatórios.

----------

## 🧰 Tecnologias

-   Java 17
    
-   Maven
    
-   JUnit Jupiter 5.9.3
    
-   RestAssured 5.4.0
    
-   Owner 1.0.12
    
-   Allure JUnit 5 2.13.8
    
-   Allure Maven 2.10.0
    
-   GitHub Actions
    

Também estão disponíveis dependências para JSON Schema, Jackson, Lombok e logging, permitindo futuras evoluções do projeto.

----------

## ⚙️ Configuração

Arquivo:

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

----------

# ▶️ Execução

## Pré-requisitos

-   Java 17 ou superior
    
-   Maven 3.8 ou superior
    
-   acesso à internet
    

## Clonar

```
git clone (https://github.com/AlineAreda/qa-test-dogapi-restassured.git)
cd ceo-dog-api
```

## Executar todos os testes

```
mvn clean test
```

## Executar sem limpar resultados anteriores

```
mvn test
```

----------

# 📊 Allure

Após a execução:

```
mvn clean test
```

os resultados ficam em:

```
target/allure-results
```

Para abrir o relatório localmente:

```
mvn allure:serve
```

O relatório permite analisar testes aprovados, falhos, ignorados, duração, descrições, severidade e funcionalidades.

----------

# 🔄 GitHub Actions — CI/CD

O projeto possui pipeline de Integração Contínua configurada em:

```
.github/workflows/api-tests.yml
```

A pipeline automatiza a execução dos testes e a geração dos relatórios.

## 🚦 Triggers

-   `push` na `main`;
    
-   Pull Request direcionado para `main`;
    
-   execução manual através de `workflow_dispatch`.
    

### Execução manual

No GitHub:

```
Actions
  ↓
API Tests
  ↓
Run workflow
  ↓
Selecionar branch
  ↓
Run workflow
```



## 📦 Artefatos

São disponibilizados:

```
allure-results
allure-report
```

Os artefatos ficam disponíveis na execução do workflow por 30 dias.

As etapas de relatório utilizam `if: always()`, permitindo preservar evidências mesmo quando algum teste falha.

----------

# 🔍 Cenários automatizados

## 1. Lista de raças

### `GET /api/breeds/list/all`

Valida:

-   HTTP 200;
    
-   conteúdo JSON;
    
-   status de sucesso;
    
-   lista não vazia;
    
-   estrutura das raças;
    
-   estrutura das sub-raças;
    
-   raças conhecidas.
    

O teste estrutural percorre as raças retornadas e verifica a estrutura dos dados.

Raças sem sub-raças são consideradas válidas quando retornam uma lista vazia.

----------

## 2. Imagens de uma raça

### `GET /api/breed/{breed}/images`

Valida:

-   raça válida;
    
-   HTTP 200;
    
-   lista de imagens;
    
-   lista não vazia;
    
-   formato das URLs;
    
-   diferentes raças através de parametrização;
    
-   raça inexistente.
    

### Cenário negativo

Para uma raça inexistente:

```
{
  "status": "error",
  "message": "Breed not found (main breed does not exist)",
  "code": 404
}
```

O teste valida HTTP 404 e o contrato do erro.

----------

## 3. Imagem aleatória

### `GET /api/breeds/image/random`

Valida:

-   HTTP 200;
    
-   status de sucesso;
    
-   existência da imagem;
    
-   formato da URL;
    
-   comportamento aleatório.
    

### 🧠 Decisão sobre aleatoriedade

O teste não exige que duas chamadas consecutivas retornem imagens diferentes, pois isso poderia gerar um teste instável.

São realizadas múltiplas chamadas e verificado se existe mais de um resultado distinto.

Assim, o teste avalia o comportamento aleatório sem criar uma regra artificial de diferença obrigatória entre chamadas consecutivas.

----------

# 🎯 Estratégia de testes

A estratégia prioriza **cobertura e relevância**, e não quantidade de testes.

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
      ↓
Execução local
      ↓
CI / Pipeline
      ↓
Relatório Allure
```

----------

# 🧱 Decisões de arquitetura

A arquitetura foi mantida propositalmente enxuta.

O `BaseTest` centraliza configurações comuns do RestAssured e o `ResponseSpecs` evita repetição de especificações de resposta.

Não foi criada uma camada adicional de:

```
Client
Models
Utils
Assertions
```

porque, para o escopo atual de três endpoints, essas abstrações adicionariam complexidade sem benefício proporcional.

A prioridade foi:

-   simplicidade;
    
-   legibilidade;
    
-   baixo acoplamento;
    
-   manutenção;
    
-   reutilização apenas quando existe necessidade real.
    

Novas abstrações podem ser introduzidas conforme a automação cresça.

----------

# 🧹 Versionamento

Arquivos gerados ou específicos do ambiente local não são versionados:

```
.idea/
.allure/
target/
allure-results/
allure-report/
```

A configuração necessária do projeto permanece no `pom.xml` e os resultados de execução podem ser disponibilizados como artefatos da CI.

----------

# 📁 Arquivos gerados

Durante a execução:

```
target/
├── surefire-reports/
├── allure-results/
└── site/
    └── allure-maven-plugin/
```

A pasta `target/` não deve ser versionada.

----------

# 📸 Resultado da execução

Adicionar screenshot do relatório Allure em:

```
assets/reports/allure-report.png
```

E utilizar:

```
![Relatório Allure](assets/reports/allure-report.png)
```

----------

# 🎥 Demonstração

Adicionar aqui um vídeo ou GIF demonstrando:

1.  execução dos testes;
    
2.  resultado no Maven;
    
3.  execução da pipeline;
    
4.  relatório Allure.
    

----------

# 🚀 Possíveis evoluções

-   validação através de JSON Schema;
     
-   maior cobertura de contrato;
    
-   execução paralela;
   
-   integração com ferramentas de gestão de testes;
    
-   métricas de qualidade;
    
-   expansão para novos endpoints.
    

Essas evoluções não fazem parte do escopo atual para manter o projeto proporcional ao desafio.

----------

# 📌 Critérios de qualidade

### Cobertura

Cenários selecionados considerando os comportamentos mais relevantes.

### Manutenibilidade

Abstrações adicionadas somente quando existe necessidade real.

### Legibilidade

Nomes descritivos, `@DisplayName` e `@Description`.

### Reutilização

Especificações comuns centralizadas em `ResponseSpecs`.

### Parametrização

Uso de testes parametrizados para diferentes entradas.

### Cenários negativos

Validação de raça inexistente.

### CI/CD

Execução automatizada através do GitHub Actions.

### Evidências

Resultados disponíveis através do Allure e artefatos da pipeline.

----------

## 👩‍💻 Autora

**Aline Areda**

QA | Automação de Testes | Qualidade de Software

----------

<div align="center">

🐶 **Dog API — Projeto de Automação de Testes**
