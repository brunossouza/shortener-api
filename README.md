# Shortener-api

This is a simple url shortener api built with spring boot (3.0.4) and MongoDB. The api was built for learning purposes
and is not intended for production use.

## Requirements

- Need to run:

  - Java 17 (or higher) -- for compiling and running the application
  - Maven 3.8.1 (or higher) -- for download dependencies and run the application
  - MongoDB 4.4.6 (or higher) -- for running the application with mongodb
  - Git 2.33.0 (or higher) -- for cloning the repository
  - A secret key (to run the application) -- for running the application in hex format (can be generated
    on https://www.allkeysgenerator.com/#encryption - use the **hex** value)
  - Postman (or any other rest client) -- for interacting with the api

- Optional:
  - Docker -- for running the application with docker
  - Docker-compose -- for running the application with docker-compose
  - An IDE (IntelliJ IDEA, Eclipse, VSCode, etc) -- for developing the application
  - A terminal (cmd, powershell, git bash, etc) -- for running the application

## Environment Variables

The following environment variables are required to run the application:

- `MONGODB_URI` - The mongodb connection string
  - example: `mongodb://user:password@localhost:27017`
- `MONGODB_DB` - The mongodb database name (default: `shortener`)
  - example: `shortener`
- `SECRET_KEY` - The secret key used to sign the jwt token (for development can be genereted
  on https://www.allkeysgenerator.com/#encryption - use the **hex** value)
- `JWT_EXPIRATION_TIME` - The jwt token expiration time in milliseconds
  - example: `86400000` (1 day)
- `DOMAIN_URL` - The application domain
  - example: `http://localhost:8080` ou `https://domain.com.br`
- `PORT` - The application port (default: `8080`)

## How to run

The application can be run with **maven**, **docker**, **docker-compose** or build the **.jar** and run it.

### Running with maven

1. Clone the repository
2. Run `mvn clean install -DskipTests` or `mvn clean package -DskipTests` to build the project skipping the tests
3. Set the environment variables (see [Environment Variables](#environment-variables))
4. Run `mvn spring-boot:run` to run the application with maven.
5. Open `http://localhost:8080` in your browser. You can also use the api with postman or any other rest client
6. To run the tests, run `mvn test`,(TODO: add tests)

**Note:** If you are using an IDE, you can also run the application from the IDE. The IDE will automatically set the
environment variables if you set them in the IDE.

- If you are using IntelliJ IDEA, you can set the environment variables in the `Run/Debug Configurations` window.
- If you are using Eclipse, you can set the environment variables in the `Run Configurations` window.
- If you are using VSCode, you can set the environment variables in the `launch.json` file.

### Running with docker

1. Clone the repository
2. Build the image with

```bash
docker build -t shortener-api .
```

3. Run the container with: (see [Environment Variables](#environment-variables))

```bash
docker run -p 8080:8080 \
    -e MONGODB_URI=mongodb://user:password@localhost:27017 \
    -e MONGODB_DB=shortener \
    -e SECRET_KEY=secret-key \
    -e JWT_EXPIRATION_TIME=86400000 \
    -e DOMAIN_URL=http://localhost:8080 \
    -e PORT=8080 \
    --name shortener shortener-api
```

4. Open `http://localhost:8080` in your browser. You can also use the api with postman or any other rest client

### Running with docker-compose

1. Clone the repository
2. Update the environment variables and args in the `docker-compose.yml` file (
   see [Environment Variables](#environment-variables))

example:

```yaml
version: "3.7"
services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    restart: unless-stopped
    environment:
      - SECRET_KEY=<secret-key> # generate a secret key with https://www.allkeysgenerator.com/#encryption - use the hex value
      - MONGODB_URI=mongodb://root:password@db:27017
      - MONGODB_DB=shortener
      - JWT_EXPIRATION_TIME=86400000
      - DOMAIN_URL=http://localhost:8080
      - PORT=8080
    ports:
      - "8080:8080"
    depends_on:
      - db
  db:
    image: mongo
    restart: unless-stopped
    environment:
      - MONGO_INITDB_ROOT_USERNAME=root
      - MONGO_INITDB_ROOT_PASSWORD=password
    ports:
      - "27017:27017"
```

3. Run the following command:

```bash
docker-compose up -d
```

4. Open `http://localhost:8080` in your browser. You can also use the api with postman or any other rest client

### Running with jar

1. Clone the repository
2. Run `mvn clean install -DskipTests` or `mvn clean package -DskipTests` to build the project skipping the tests
3. Set the environment variables (see [Environment Variables](#environment-variables))
4. Run the following command:

```bash
java -jar target/shortener-api-0.0.1-SNAPSHOT.jar
```

5. Open `http://localhost:8080` in your browser. You can also use the api with postman or any other rest client

## Api Documentation

The api documentation is available at [docs](http://localhost:8080/api/swagger-ui/index.html)
