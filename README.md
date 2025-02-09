# Rate Limiter with Spring Boot and Redis

This project implements a rate limiting system using Spring Boot and Redis. The goal is to control the number of requests a user can make within a specified time frame.

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

Ensure that both Docker and Docker Compose are installed and functioning correctly on your machine.

## Configuration

### `docker-compose.yml` File

The project uses Docker Compose to orchestrate the application and Redis services.


### `application.properties` File
The application settings are defined in the application.properties file:
```
spring.application.name=rate-limiter
spring.redis.host=${SPRING_REDIS_HOST}
spring.redis.port=${SPRING_REDIS_PORT}
```

### `.env/app.env` File
Add your environments like:

```
SPRING_REDIS_HOST=redis
SPRING_REDIS_PORT="6379"
REDIS_PASSWORD="mypass"
```


### Running the Application
To start the application along with Redis, execute the following command in the project's root directory:

``` bash
docker-compose up --build
```

This command will build the application's image and start the services defined in the docker-compose.yml file.

### Testing the Application
Once initialized, the application will be available at http://localhost:8080.
You can test the rate limiting by making requests to the defined endpoints, like `/notifications` and observing the responses according to the configured limiting rules.

### Troubleshooting
Connection Error with Redis

If the application encounters errors when attempting to connect to Redis, ensure that the Redis service is running and accessible.
You can check the service logs with the following command:

``` bash
docker-compose logs
```

Verify that Redis is accepting connections on the correct port and that the application is configured to connect to the correct host and port.
