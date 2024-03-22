# Payment System

## Description

This project consists of three services: two REST APIs (`data-api` and `business-api`) and a Spring Boot project with a scheduled task (`cron-app`). The project structure can be visualized using the following diagram:

![Project Diagram](./diagram.svg)

### Data API

The Data API provides CRUD operations for entities such as Payment, BankTransaction, and BankAccount.

#### BankAccount

BankAccount offers fully implemented CRUD operations at the service layer. It serves as a mock entity demonstrating money transfer operations after the creation of a new BankTransaction.

#### Payment

Payment represents an entity containing information necessary for creating BankTransactions. Upon creating a Payment, the first BankTransaction is automatically generated.

#### BankTransaction

BankTransaction holds information about money transfers and is essential for identifying payments requiring new transactions. BankTransaction has two statuses: ACTIVE and CANCELED. Initially, upon successful creation, it assumes the ACTIVE status. When a BankTransaction is rolled back post a successful money transfer, its status changes to CANCELED. Insufficient funds prevent the creation or rollback of BankTransactions.

### Cron App

The Cron App periodically checks payments requiring new transactions and generates new transactions every minute.

## How to Run

Ensure you have `docker-compose` installed and that the following ports are available: 5432, 8080, 8081, 5005-5007.

1. Clone the repository.
2. Create an `.env` file in the root directory:

    ```shell
    cp .env_sample .env
    ```

3. Run the project:

    ```shell
    docker-compose up --build
    ```

## How to Test

To test the endpoints, you can import the Postman collection from [this link](https://www.postman.com/noct2000/workspace/public-workspace/collection/20171165-f97e96f5-1599-4d3a-85d3-8e139af22904?action=share&creator=20171165&active-environment=20171165-12770c54-46ec-424f-9351-1390726ebec8).

## Swagger
Also, you can explore documentation for data-api by following link http://localhost:8080/swagger-ui/index.html#
