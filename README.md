# Backend REST API application

This project contains a backend REST API and a Pulumi Dynamic Provider. 
The backend REST API maintains sets of 2 resource types, blogpost & product.
I used MongoDB for the database with MonogoDB instance using Docker  

## Run the app

    gradlew run

# REST API

    base uri: http://localhost:8080

## Resources Types

### `Product`

```
{
    id: number
    name: string
    brand: string
    price: string
    category: string ("Shirt", "Pants" or "TankTop")
 }
 ```

## Endpoints

### `GET /products`

Lists the products.  The response will be a JSON array of `Product` objects.

### `GET /products/ids`

Lists the ids of all of the products.  The response will be a JSON array of numbers.

### `POST /products`

Create a product. you should enter the id of the product (a possitive number) 
The body should be a JSON object with the structure:
 
 ```
 {
    id: number
    name: string
    brand: string
    price: string
    category: string ("Shirt", "Pants" or "TankTop")
 }
 ```

 The response will be a JSON-encoded `Product` object.

### `GET /products/{id}`

Returns the product with the given ID.   The response will be a JSON-encoded `Product` object.

### `PUT /products/{id}`

PUT (update) all the fields of the product with the given ID.  The body should be a JSON object with the given structure:
 
 ```
 {
    name: string
    brand: string
    price: string
    category: string ("Shirt", "Pants" or "TankTop")
 }
 ```

 The response will be a JSON-encoded `Product` object.

### `DELETE /products/{id}`

Delete the product with the given ID.


### `Blogpost`

```
{
    id: number
    name: string
    text: string
    category: string ("Shirt", "Pants" or "TankTop")
    products: array of Products
 }
 ```

## Endpoints

### `GET /blogposts`

Lists the blogposts.  The response will be a JSON array of `Blogpost` objects.

### `GET /blogposts/ids`

Lists the ids of all of the blogposts.  The response will be a JSON array of numbers.

### `POST /blogposts`

Create a blogpost. you should enter the id of the blogpost (a possitive number) 
The body should be a JSON object with the structure:
 
 ```
 {
    id: number
    name: string
    text: string
    category: string ("Shirt", "Pants" or "TankTop")
    products: array of Products
 }
 ```

 The response will be a JSON-encoded `Product` object.

### `GET /products/{id}`

Returns the blogpost with the given ID.   The response will be a JSON-encoded `Blogpost` object.

### `PUT /blogpost/{id}`

PUT (update) all the fields of the blogpost with the given ID.  The body should be a JSON object with the given structure:
 
 ```
 {
    name: string
    text: string
    category: string ("Shirt", "Pants" or "TankTop")
    products: array of Products
 }
 ```

 The response will be a JSON-encoded `Blogpost` object.

### `DELETE /blogposts/{id}`

Delete the blogpost with the given ID.

