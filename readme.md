# Live Concert Service

## Table of Contents

- [Introduction](#introduction)
- [Technologies](#technologies)
- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

Provide a brief overview of the project. What does it do? Why was it created?

## Technologies

List of technologies/libraries used in the project.

- Spring Boot
- Java
- Docker
- Postgres

## Features

- User Authentication
- Filter Live Concert
- Booking Ticket

## Getting Started

Instructions for setting up the project locally.

### Prerequisites

List any software or tools that need to be installed.

- Java JDK
- Maven
- Docker

### Database Design

```mermaid
erDiagram
    Account {
        id INT PK
        username VARCHAR
        email VARCHAR
        password VARCHAR
        created_on DATETIME
        created_by VARCHAR
        updated_on DATETIME
        updated_by VARCHAR
    }

    Concert {
        id INT PK
        name VARCHAR
        date_time DATETIME
        description VARCHAR
        total_tickets INT
        total_tickets_sold INT
        start_selling_on DATETIMETZ
        finish_selling_on DATETIMETZ
        created_on DATETIMETZ
        created_by VARCHAR
        updated_on DATETIMETZ
        updated_by VARCHAR
    }

    Ticket_Order {
        id INT PK
        concert_id INT FK
        account_id INT FK
        quantity INT
        created_on DATETIME
        created_by VARCHAR
        updated_on DATETIME
        updated_by VARCHAR
    }

   
    Account ||--o| Ticket_Order : placed
    Concert ||--o| Ticket_Order : has

```

### Installation

1. Clone the repository.
   ```sh
   git clone
2. Run Docker Compose.
   ```sh
   docker compose up -d
   
