# Projeto XPTO - API de Cidades
### Este projeto é uma API RESTful desenvolvida com Spring Boot para gerenciar informações de cidades. Ele permite carregar dados de cidades a partir de um arquivo CSV, realizar consultas e filtros, e gerenciar cidades individualmente.

Tecnologias Utilizadas
* Spring Boot: Framework para desenvolvimento rápido de aplicações Java.
* Spring Data JPA: Camada de persistência de dados.
* H2 Database: Banco de dados em memória para desenvolvimento e testes.
* OpenCSV: Biblioteca para manipulação de arquivos CSV.
* Springdoc OpenAPI (Swagger): Para documentação e teste de APIs.
* Lombok: Para reduzir código boilerplate.
* Maven: Gerenciador de dependências e construção.


### Configuração do Ambiente

Requisitos:

* Java 17 ou superior
* Maven 3.6 ou superior
* IDE de sua preferência (IntelliJ IDEA, Eclipse, etc.)
* Configuração do Banco de Dados H2:

O projeto utiliza o H2 Database em modo memória para facilitar o desenvolvimento e testes.
A URL de conexão é:

* jdbc:h2:mem:testdb.
* usuário:sa
* senha:password.

Caso necessite configurar o acesso ao console do H2 adicione a seguinte propriedade no arquivo application.properties
spring.h2.console.enabled=true

Acesse o console pelo endereço: http://localhost:8080/h2-console/

### Execução da Aplicação:

Clone o repositório.
Navegue até o diretório do projeto.
Execute o comando mvn spring-boot:run para iniciar a aplicação.
Ou execute a aplicação diretamente pela sua IDE.
Endpoints da API
A documentação completa da API pode ser acessada através do Swagger UI:

http://localhost:8080/swagger-ui/index.html.


Aqui estão alguns dos principais endpoints:

## Endpoints da API

* **`POST /cities/load`**: Carrega dados de cidades a partir de um arquivo CSV.
    * Requer um arquivo CSV no formato `multipart/form-data`.
* **`GET /cities/capitals`**: Retorna a lista de cidades que são capitais, ordenadas por nome.
* **`GET /cities/states/min-max`**: Retorna o estado com a maior e menor quantidade de cidades.
* **`GET /cities/count-by-state`**: Retorna a quantidade de cidades por estado.
* **`GET /cities/id/{ibgeId}`**: Retorna os dados de uma cidade com base no ID do IBGE.
* **`GET /cities/state/{state}`**: Retorna a lista de cidades de um determinado estado.
* **`POST /cities`**: Adiciona uma nova cidade.
    * Requer um objeto JSON representando a cidade no corpo da requisição.
* **`DELETE /cities/{id}`**: Remove uma cidade com base no ID.
* **`GET /cities/filter/{column}/{value}`**: Filtra cidades com base em uma coluna e valor.
* **`GET /cities/distinct-count/{column}`**: Retorna a quantidade de registros distintos de uma determinada coluna.
* **`GET /cities/total-count`**: Retorna a quantidade total de cidades.
* **`GET /cities/farthest`**: Retorna as cidades mais distantes entre si.
