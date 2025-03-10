# sample-app-spring-webflux-graphql

Sample SpringBoot app using WebFlux and GraphQL

Following along course https://www.udemy.com/course/graphql-spring/

## Setup (using Spring CLI)

Using [Spring Boot CLI](https://docs.spring.io/spring-boot/cli/index.html) instead of [Spring Initializr](https://start.spring.io/)

```bash
sdk install springboot
spring help init

To list all the capabilities of the service:
spring init --list

# Create the project
cd sample-app-spring-webflux-graphql
spring init \
    --dependencies=actuator,webflux,graphql,lombok,data-r2dbc,h2,validation,devtools \
    --build=maven \
    --group-id=com.github.dkirrane \
    --artifact-id=sample-app-spring-webflux-graphql \
    ./sample-app-spring-webflux-graphql
```
