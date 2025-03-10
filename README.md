# Overview

Sample SpringBoot app using **WebFlux** and **GraphQL**.
Simple CRUD style app using GraphQL.

Following along course Section 5: https://www.udemy.com/course/graphql-spring/

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
    --name=sample-app-spring-webflux-graphql \
    ./
```

## Source
- [CustomerRepository](src/main/java/com/github/dkirrane/sample/repository/CustomerRepository.java) - Customer Repository using R2DBC ReactiveCrudRepository.
- [CustomerService](src/main/java/com/github/dkirrane/sample/service/CustomerService.java) - Service for CRUD ops on Customer Repository.
- [CustomerController](src/main/java/com/github/dkirrane/sample/controller/CustomerController.java) - GraphQL API Controller for Customer CRUD operations.
- [Customer](src/main/java/com/github/dkirrane/sample/entity/Customer.java) - Customer Entity clas for the database.
- [CustomerDto](src/main/java/com/github/dkirrane/sample/dto/CustomerDto.java) - Customer Data Transfer Object (DTO) for sending data to and from the GraphQL API.

## GraphiQL
Run the SpringBoot application and use the baked in GraphiQL to run the GraphQL CRUD queries & mutations.

GraphQL queries & variables for testing the CRUD application: 
```graphql
query GetAll {
  customers{
    id,
    name,
    age,
    city,
    type: __typename
  }
}

query GetCustomerById($id: ID!){
  customerById(id: $id){
    id,
    name,
    age,
    city,
    type: __typename
  }
}
  
mutation CreateCustomer($customer: CustomerInput!) {
  createCustomer(customer: $customer) {
    id
    name
    age
    city  
  }
}

mutation UpdateCustomer($id: ID!, $customer: CustomerInput!) {
  updateCustomer(id: $id, customer: $customer) {
    id
    name
    age
    city  
  }
}

mutation DeleteCustomer($id: ID!) {
  deleteCustomer(id: $id){
    id
    status
  }
}
```
Sample variables for above GraphQL mutations
```json
{
  "id": 5,
  "customer": {
    "name": "dessie",
    "age": 42,
    "city": "Tuam"
  }
}
```

![graphiql](images/graphiql.png)
