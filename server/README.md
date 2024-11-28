# Financial API

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![GraphQL](https://img.shields.io/badge/-GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-%230db7ed?style=for-the-badge&logo=Docker&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

This project aims to provide a practical and efficient solution for organizing individual or family finances. With this
application, users will be able to monitor their expenses, income, savings goals, and financial planning in a simple 
and intuitive way.

This project is an API built using Spring Boot, Java, GraphQL and PostgresSQL as the database.

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#Api-Documentation)

## Installation
1. Clone the repository:
```bash
git clone git@github.com:mattmiriani/financial-API.git
```
2. Install dependencies:
```bash
mvn install
```

## Configuration

### Docker
- Run Docker Compose:
```bash
docker compose -f compose.yaml up -d --build
```

### Others
- Install [PostgresSQL](https://www.postgresql.org/download/).
- Create a PostgreSQL user with the username financial and password financial.
- Create a database named financial.

## Usage
1. Start the application
2. The API will be accessible at [GraphQL Panel](http://localhost:8080/graphiql) with a valid token.

## Api Documentation
- Documentation is available on my project page at [Documentation](https://mattmiriani.notion.site/Projeto-Financial-1a0d1e7059344953b407d52891b86ca2?pvs=74).