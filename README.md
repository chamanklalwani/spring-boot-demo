# Spring Boot Demo Application

[![Build Status](https://travis-ci.org/chamanklalwani/spring-boot-demo.svg?branch=master)](https://travis-ci.org/chamanklalwani/spring-boot-demo)



A spring boot and maven based demo project having REST endpoints to modify a ```.json``` file at a local file system
which acts like a database. Project has all the endpoints for ```CRUD``` operations, which would be
performed on the ```.json``` file. The project has ```Swagger, Mockito and Travis CI ``` integration as well.

## Dependencies
* Java 8
* Spring Boot > 2

## Steps to Setup the Spring Boot Back end app

1. Clone the application
```
git clone https://github.com/chamanklalwani/spring-boot-demo.git
cd spring-boot-demo
```

2. Run the app

You can run the spring boot app by typing the following command
```
mvn spring-boot:run
```
The server will start on port 8080. Type http://localhost:8080/api/v1/hello in browser

## Running unit tests
```
mvn test
```

## Swagger UI for the API can be accessed at
```
http://localhost:8080/swagger-ui.htm
```
