# **Todo App UI**

Todo API to store tasks using Keycloak as authentication and authorization provider.

## **Author**

Jorge Alvarez <alvarez.jeap@gmail.com>

## **Requirements***

- Java 11+
- Maven 3.6+
- Docker 19+

## **Playground**

- Add alias IP `192.168.99.1` to loopback inteface

- Start backing services

```sh
./scripts/bash/run-backing-services.sh
```

- Init database configuration

```sh
./scripts/bash/init-database.sh
```

- Compile

```sh
mvn clean package
```

- Run application

```sh
java -jar target/todo-app-ui.jar
```

- Open [http://192.168.99.1:5100](http://192.168.99.1:5100)
