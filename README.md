## Book-Service
Book Service REST API

## Author
Arnab Mondal

### This Rest API supports the following endpoints

|   Endpoint      | HTTP Method | Description                          |  Access |                   Comments                     |
|-----------------|-------------|--------------------------------------|---------|------------------------------------------------|
|/books           | GET         | List of all books                    | GENERAL |                                                |
|/books/{book_id} | GET         | Get book by id                       | GENERAL |                                                |
|/books           | POST        | Add a book                           | ADMIN   |                                                |
|/books/{book_id} | DELETE      | Delete a book                        | ADMIN   |                                                |
|/books/{book_id} | PUT         | Update a book                        | GENERAL | Update book Author, Category, Description, etc |
|/books/upload    | POST        | Upload CSV file with a list of books | ADMIN   |                                                |



### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)