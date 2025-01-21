# Tech Challenge - Pós-Tech SOAT - FIAP

Este é o projeto desenvolvido durante a fase I do curso de pós-graduação em arquitetura de software da FIAP - 2024.

Membro do grupo: Igor Veras Lima - RM360611

## Descrição

Este código se trata de um sistema monolítico encarregado de gerenciar a criação de pedidos em um restaurante. Ele processa os pedidos, administra os pagamentos e envia as solicitações para a cozinha assim que o pagamento é confirmado. 

## Tecnologias Utilizadas
![Java](https://img.shields.io/badge/java_21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)
![Spring](https://img.shields.io/badge/spring_3-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![Postgres](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## Arquitetura

O projeto segue a Arquitetura Hexagonal (Ports and Adapters), permitindo maior flexibilidade e facilidade de manutenção.

![Imagem da arquitetura hexagonal](./assets/hex-ports-adapters.svg)

## Problema

Há uma lanchonete de bairro que está se expandindo devido seu grande
sucesso. Porém, com a expansão e sem um sistema de controle de pedidos, o
atendimento aos clientes pode ser caótico e confuso. Por exemplo, imagine que
um cliente faça um pedido complexo, como um hambúrguer personalizado com
ingredientes específicos, acompanhado de batatas fritas e uma bebida. O
atendente pode anotar o pedido em um papel e entregá-lo à cozinha, mas não
há garantia de que o pedido será preparado corretamente.
Sem um sistema de controle de pedidos, pode haver confusão entre os
atendentes e a cozinha, resultando em atrasos na preparação e entrega dos
pedidos. Os pedidos podem ser perdidos, mal interpretados ou esquecidos,
levando à insatisfação dos clientes e a perda de negócios.
Em resumo, um sistema de controle de pedidos é essencial para garantir
que a lanchonete possa atender os clientes de maneira eficiente, gerenciando
seus pedidos e estoques de forma adequada. Sem ele, expandir a lanchonete
pode acabar não dando certo, resultando em clientes insatisfeitos e impactando
os negócios de forma negativa.
Para solucionar o problema, a lanchonete irá investir em um sistema de
autoatendimento de fast food, que é composto por uma série de dispositivos e
interfaces que permitem aos clientes selecionar e fazer pedidos sem precisar
interagir com um atendente.

## Desenvolvimento

- **[Java 21](https://docs.oracle.com/en/java/javase/21/)**: Documentação do Java 21.
- **[Gradle 8.11.1+](https://docs.gradle.org/current/userguide/userguide.html)**: Documentação do Gradle.
- **[Docker](https://docs.docker.com/?_gl=1*v1gqy4*_gcl_au*MTM4MjU0MTI3Ni4xNzM3NDg2MzY2*_ga*MzMxMDkxMTA1LjE3Mzc0MTQ5OTI.*_ga_XJWPQMJYHQ*MTczNzQ4NjI1MC4zLjEuMTczNzQ4NjM2Ni41OS4wLjA.)**: Documentação do Docker.
- **[Docker Compose](https://docs.docker.com/compose/)**: Documentação do Docker Compose.

## Execução

Este projeto utiliza um arquivo docker-compose.yml para subir o container da aplicação e suas dependências (como o banco de dados PostgreSQL).

Passos para execução:

1. Criação do arquivo `.env` (opcional, se desejar customizar variáveis de ambiente):
Antes de executar o `docker-compose`, você pode criar um arquivo `.env` na raiz do projeto para definir as variáveis de ambiente utilizadas. O arquivo segue o formato abaixo:

```properties
POSTGRES_URL=jdbc:postgresql://db:5432/techchallenge
POSTGRES_DB=techchallenge
POSTGRES_USERNAME=postgres
POSTGRES_PASSWORD=123
```

⚠️ Observação: Caso o arquivo `.env` não esteja presente, as variáveis padrão definidas no `docker-compose.yml` serão utilizadas.

2. Com o arquivo `docker-compose.yml` configurado e o `.env` (opcional) pronto, execute o comando: `docker compose up`

Este comando irá:

Construir a imagem da aplicação usando o `Dockerfile`.
Configurar e iniciar o banco de dados PostgreSQL.
Inicializar a aplicação configurada para rodar na porta 8080.

Com tudo ocorrendo bem, os seguintes logs serão visualizados:

Build da imagem da aplicação utilizando o Dockerfile:


![Imagem do build da app](./assets/build-docker.png)

Criação do volume e containers:

![Imagem da criacao do volume e containers](./assets/containers.png)

Banco inicializado com sucesso:


![Imagem do banco inicializado](./assets/db-container.png)

Aplição inicializada e rodando:

![aplicacao rodando](./assets/application-running.png)


3. Após a execução do comando, você pode verificar os containers ativos com o comando: `docker container ls`

O resultado esperado será semelhante ao seguinte:

![Comando docker container ls](./assets/container-ls.png)

4. A aplicação estará disponível no endereço: http://localhost:8080

Com esses passos, a aplicação estará configurada e funcionando para receber chamadas! 🎉

⚠️ Observação: Este projeto utiliza o Flyway para gerenciamento de migrações no banco de dados. Isso significa que, ao iniciar a aplicação, todas as tabelas necessárias serão criadas automaticamente com base nos scripts de migração localizados no diretório classpath: `resources/db/migration` Dessa forma, não é necessário executar nenhum comando manual para configuração inicial do banco de dados.


## Criação de pedidos/ Fake Checkout

Para criar um pedido, siga os passos abaixo:

1. Criar um produto

Primeiro, faça uma requisição ao endpoint `/product` para cadastrar um produto. O payload da requisição deve seguir o formato abaixo:

```json
{
  "name": "string",
  "price": 0,
  "description": "string",
  "category": "LANCHE",
  "images": [
    {
      "url": "string"
    }
  ]
}
```

Após a criação do produto, o sistema retornará o `id` do produto cadastrado, que será necessário para criar o pedido.

2. Criar um pedido

Com o `id` do produto em mãos, faça uma requisição ao endpoint `/order` utilizando o seguinte payload:

```json
{
  "customerId": 0,
  "waitingTimeInMinutes": 0,
  "items": [
    {
      "productId": 0,
      "quantity": 0
    }
  ]
}
```

Essa requisição criará um pedido no sistema com o status inicial de RECEBIDO, indicando que ele foi registrado com sucesso e está pronto para ser processado pela equipe da cozinha.

## Documentação/Swagger

Link para acessar ao swagger após subir a aplicação:

```bash
http://localhost:8080/swagger-ui.html
```

## MIRO - Event Storming

![Event Storming](./assets/event_storming.png)

O miro contendo o event storming está disponível em:
[Event Storming](https://miro.com)