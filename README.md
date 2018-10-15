# Spring Boot Demo Application

A spring boot and maven based demo project having REST endpoints to modify a ```.json``` file at a local file system
which acts like a database. Project has all the endpoints for ```CRUD``` operations, which would
performed on the ```.json``` file.

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