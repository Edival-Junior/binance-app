# Binance App

## Requisitos

- Docker
- Java 17
- Maven

## Como Rodar

1. Clone o repositório
2. Navegue até o diretório do projeto
3. Execute `mvn clean install` para construir o projeto
4. Execute `docker build -t binanceapp -f docker/Dockerfile .` para construir a imagem Docker
5. Execute `docker run -p 8080:8080 binanceapp` para rodar o container

A aplicação estará disponível em `http://localhost:8080`.

## Endpoints

- `GET /api/candles/{symbol}/{interval}` - Obtém dados de candles por símbolo e intervalo

## Documentação

A documentação da API estará disponível em `http://localhost:8080/swagger-ui.html`.
