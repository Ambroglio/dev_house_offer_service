# Offer Service

## Presentation

Offer services allows a registered member to create, update or delete (logically) a specific offer for Dev House. For creation and update, Authorization will be checked with JWT via Authentication/Identity Service.
Anyone can see the different offers provided by this service.

## Technical stack

This service is built on Kotlin. The stack relies on :
 - [Spring Boot](https://spring.io/projects/spring-boot): swiss knife allowing to create a REST service quite easily
- [Maven](https://maven.apache.org/): software project management and comprehension tool

## Environment variables

As the Authentication Service, you have to provide a configuration if the default configuration doesn't match your configuration.
The different environement variables to add are the following :
- **POSTGRESQL_ADDON_HOST**, Postgresql host
- **POSTGRESQL_ADDON_DB**, Postgresl database's name
- **POSTGRESQL_ADDON_USER**, Postgresq user
- **POSTGRESQL_ADDON_PASSWORD**, Postgresql password

On Unix, you can set up an environment variable by typing `export VARIABLE=VALUE`

## Development

In postgres :
### `CREATE DATABASE offers`

In a terminal :
### `mvn clean compile install spring-boot:run`

The main branch uses Spring MVC while the webflux branch uses Spring Webflux. However there are some issues with Spring Webflux so it doesn't work properly.
