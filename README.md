
# bookstore-service

This service is to manage the CRUD operations on Books for the online book store application. It also provides the checkout facility.

## API Reference

#### Get List of Books

```http
  GET /api/v1/books
```

#### listAllBooks()

It returns all the available books.

#### Sample JSON Response

```
[
    {
        "id": 1,
        "name": "War and Peace",
        "description": "About War and Peace",
        "author": "Tolstoy, Leo",
        "type": "HISTORIC",
        "price": 12.7,
        "isbn": "dbb68e1d-69c8-413a-a4cc-80e791001d12"
    },
    {
        "id": 2,
        "name": "Northanger Abbey",
        "description": "About Penguin",
        "author": "Austen, Jane",
        "type": "FICTION",
        "price": 18.2,
        "isbn": "1009496e-610b-4e31-9e80-6a50659e57dc"
    }
]
```

#### Get Book

```http
  GET /api/v1/books/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. id of Book to fetch |

#### listBookById(id)

Takes book id and returns the available book Details.

#### Sample JSON Response

```
{
    "id": 1,
    "name": "War and Peace",
    "description": "About War and Peace",
    "author": "Tolstoy, Leo",
    "type": "HISTORIC",
    "price": 12.7,
    "isbn": "dbb68e1d-69c8-413a-a4cc-80e791001d12"
}
```

#### Add Book

```http
  POST /api/v1/books
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `book`      | `Book` | **Required**. data to create a Book record|

#### addBook(book)

Takes book data and returns the created Book Details.

#### Sample JSON Request

```
{
    "name": "War and Peace",
    "description": "About War and Peace",
    "author": "Tolstoy, Leo",
    "type": "HISTORIC",
    "price": 12.7
}
```

#### Sample JSON Response

```
{
    "id": 1,
    "name": "War and Peace",
    "description": "About War and Peace",
    "author": "Tolstoy, Leo",
    "type": "HISTORIC",
    "price": 12.7,
    "isbn": "dbb68e1d-69c8-413a-a4cc-80e791001d12"
}
```

#### Update Book

```http
  PUT /api/v1/books
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `book`      | `Book` | **Required**. data to create a Book record|

#### updateBook(book)

Takes book data and returns the updated Book Details.

#### Sample JSON Request

```
{
    "id": 1,
    "name": "War and Peace",
    "description": "About War and Peace",
    "author": "Tolstoy, Leo",
    "type": "HISTORIC",
    "price": 12.7,
    "isbn": "dbb68e1d-69c8-413a-a4cc-80e791001d12"
}
```

#### Sample JSON Response

```
{
    "id": 1,
    "name": "War and Peace",
    "description": "About War and Peace",
    "author": "Tolstoy, Leo",
    "type": "HISTORIC",
    "price": 12.7,
    "isbn": "dbb68e1d-69c8-413a-a4cc-80e791001d12"
}
```

#### Delete Book

```http
  DELETE /api/v1/books/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. id of Book to delete a book record|

#### removeBook(id)

Takes book id and remove the Book Details.

#### Checkout Books

```http
  POST /api/v1/checkout
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `request`      | `CheckoutRequest` | **Required**. books data to checkout the books and provide total amount|

#### checkout(request)

Takes checkout request data and returns the calculated final amount as checkout response.

#### Sample JSON Request

```
{
    "books": [
        {
            "id": 1,
            "name": "War and Peace",
            "description": "About War and Peace",
            "author": "Tolstoy, Leo",
            "type": "HISTORIC",
            "price": 12.7,
            "isbn": "dbb68e1d-69c8-413a-a4cc-80e791001d12"
        },
        {
            "id": 2,
            "name": "Northanger Abbey",
            "description": "About Penguin",
            "author": "Austen, Jane",
            "type": "FICTION",
            "price": 18.2,
            "isbn": "1009496e-610b-4e31-9e80-6a50659e57dc"
        }
    ],
    "promoCode": "FICTION10"
}
```

#### Sample JSON Response

```
{
    "totalAmount": 29.08
}
```


## Documentation

| Service Name |  link |
| :-------- | :------- | 
| `bookstore-service`      | [swagger-ui](http://localhost:8080/swagger-ui/index.html) |



## Run Locally

Clone the project

```bash
  git clone https://github.com/erabhinavrana/bookstore-service
```

Go to the project directory

```bash
  cd bookstore-service
```

Start the server

```bash
  ./mvnw spring-boot:run
```


## Appendix

Docker Image: abhinavrana/bsv1-bookstore-service:0.0.1-SNAPSHOT

