
# Product Service

The **Product Service** is a core component of the E-commerce platform, responsible for managing product-related operations, including CRUD operations, searching, and filtering products.


## Features

- Features of Products

    - Add, update, and delete products.
    - Fetch product details by ID.
    - Search and filter products by name, category, description and price range or search all products.
    - Integration with **Search Service** via REST APIs.

- Features of Categories
    - Add, update, and delete categories.
    - Fetch category details by ID.
    - Search and filter categories by name or search all categories.


## Technologies Used

- **Java** 17
- **Spring Boot** 3.x
- **Spring Data JPA**
- **PostgreSQL** as the database
- **Docker** for containerization
- **Lombok** for reducing boilerplate code

## Architecture

The microservice follows a **RESTful API** design pattern and communicates with other services via HTTP. 

- **Controller Layer:** Handles HTTP requests.
- **Service Layer:** Contains business logic.
- **Repository Layer:** Interfaces with the database.

## Setup

### Prerequisites

- Docker
- Docker Compose

### Steps

- Build the Docker images:

        docker-compose build

- Start the Docker containers:

        docker-compose up

- Restore the dockerized PostgreSQL database

        docker exec -i product-postgres psql -U postgres -d products < database_dump.sql



## API Reference

### APIs of the **Product**

#### Create product

```http
  POST http://localhost:8084/api/product/create

Example JSON body:

{
    "productName": "Example Product",
    "description": "Example Description",
    "quantity": 50,
    "price": 5599.99,
    "category": {
        "categoryName": "ExampleCategory"
    }
}

Make sure that category that you want to insert the product exists. If not, insert that category from Category controller.
```

#### Delete product

```http
  POST http://localhost:8084/api/product/delete/{productId}
```

#### Update product

```http
  POST http://localhost:8084/api/product/update/{productId}

Example JSON body:

{
    "productName": "Updated Product",
    "description": "Updated Description",
    "quantity": 100,
    "price": 3999.99,
    "category": {
        "categoryName": "ExampleCategory"
    }
}

```

- In order to do GET operations on products, **Search Service** has to be cloned from my repository.

#### Get product by ID

```http
  GET http://localhost:8084/api/product/get/id/{productId}
```
#### Get product by description

```http
  GET http://localhost:8084/api/product/get/description/{productDescription}
```
#### Get product by name

```http
  GET http://localhost:8084/api/product/get/name/{productName}
```
#### Get product by price

```http
  GET http://localhost:8084/api/product/get/price?minPrice=100&maxPrice=1000
```
#### Get all products

```http
  GET http://localhost:8084/api/product/getAll
```
---

### APIs of the **Category**

#### Create category

```http
  POST http://localhost:8084/api/category/create

Example JSON body:

{
    "categoryName": "ExampleCategory"
}
```

#### Delete category

```http
  POST http://localhost:8084/api/category/delete

Example JSON body:

{
    "categoryName": "ExampleCategory"
}
```

#### Update category

```http
  POST http://localhost:8084/api/category/update/{categoryName}

Example JSON body:

{
    "categoryName": "UpdatedCategory"
}
```

#### Get category by name

```http
  GET http://localhost:8084/api/category/get/name/{categoryName}
```
#### Get all categories

```http
  GET http://localhost:8084/api/category/getAll
```



