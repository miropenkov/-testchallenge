# Spring Boot Product Ordering Project

This is a sample Java / Maven / Spring Boot application that is used as a microservice for produt orderin.

## How to Run 

This application is packaged as a jar. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=local target/testchallenge.jar
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=local"
```
* Check the stdout to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2021-02-17 13:29:55,956  INFO  [o.h.e.t.j.p.i.JtaPlatformInitiator] SID= CID= [main] HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2021-02-17 13:29:56,538  WARN  [o.s.b.a.o.j.JpaBaseConfiguration$JpaWebConfiguration] SID= CID= [main] spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2021-02-17 13:29:56,659  INFO  [t.store.TestChallengeApplication] SID= CID= [main] Started TestChallengeApplication in 9.572 seconds (JVM running for 10.232)
```

## About the Service

The service is just a simple product ordering REST service. It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in some of controles ```testchallenge.store.controller.impl.CategoryController, testchallenge.store.controller.impl.OrderController  , testchallenge.store.controller.impl.ProductController``` on **port 8080**. (see below)

More interestingly, you can start calling some of the operational endpoints (see full list below) like ```/product``` or  ```/products```  or  ```/categories``` (these are available on **port 8080**)

You can use this sample service to create a DB-backed RESTful service. 
 
Here is what this little application demonstrates: 

* Full integration with the **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container (tomcat 8): No need to install a container separately on the host just run using the ``java -jar`` command
* Writing a RESTful service using annotation: supports JSON request / response; simply use desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations. 
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* All APIs are "self-documented" by Swagger2 using annotations 

Here are some endpoints you can call:

### Get information about product and categories, etc.

```
http://localhost:8080/product
http://localhost:8080/products
http://localhost:8080/categories
```

### Create a product resource

```
POST /rest/product/
Accept: application/json
Content-Type: application/json

{
"name" : "dev-kit",
"category": "Software",
"description" : "Development Kit",
"quantity" : 2
}


Location header: http://localhost:8080/rest/product/
RESPONSE: HTTP 200 
Content: product resource 
```

### Retrieve a paginated list of products

```
Location header: http://localhost:8080/rest/product/list?direction=DESC&orderBy=name&page=1&pageSize=5

Response: HTTP 200
Content: paginated list 
```

### Update a product resource

```
PUT /product/2/product
Accept: application/json
Content-Type: application/json
{
"name" : "dev-kit Z1",
"category": "Software",
"description" : "Development Kit Z1",
"quantity" : 3
}

Location header: http://localhost:8080/rest/product/2/product
RESPONSE: HTTP 200 
Content: product resource 

```
### Delete a product resource

```
DELETE /product/2/product

Location header: http://localhost:8080/rest/product/2/product
RESPONSE: HTTP 200 (No Content)

```
### View a product resource

```
GET /product/2/product

Location header: http://localhost:8080/rest/product/2/product
RESPONSE: HTTP 200 
Content: product resource 
```

### Update a product resource

```
PUT /rest/products/2/order/5
Accept: application/json
Content-Type: application/json
{
}

Location header: http://localhost:8080 /rest/products/2/order/5
RESPONSE: HTTP 200 
Content: product resource 



```
### Retrieve a list of categories

```
GET /categories

Location header: http://localhost:8080/rest/categories
RESPONSE: HTTP 200 
Content: list of categories with number of availabale products



```
# About Product Ordering

For the database creation used Liquibase. It configuration and initialized data are inside /src/resources/db/changelog/db.changelog-1.0.yaml 
For the REST endpoints configuration is used openapi and it configuration  are inside the /src/resources/swager/rest-ui.yaml     
The java immplementation are residet in following folders 

**/src/java/main/testchallenge/store/configuration** Contains rest template configuration and fix about datetime format configuration.

**/src/java/main/testchallenge/store/controler/impl** Contains the spring boot REST controlers CategoryControler,OrderControler,ProductControler.

**/src/java/main/testchallenge/store/domain/entity** 	Contains Product and Category entity.

**/src/java/main/testchallenge/store/exception** 	Contains HTTP Exceptions .

**/src/java/main/testchallenge/store/mapper** Contains Product mapper between REST and entities classes.

**/src/java/main/testchallenge/store/repository** Contains the repositories for product and category.

**/src/java/main/testchallenge/store** Conatins the test classes.


### To view your H2 in-memory datbase

The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8090/h2-console. Default username is 'sa' with a 'test' password. Make sure you disable this in your production profiles. For more, see https://goo.gl/U8m62X

# Running the project with MySQL or Postrgresql 

This project uses an in-memory database so that you don't have to install a database in order to run it. 
However, converting it to run with another relational database such as MySQL or PostgreSQL is very easy. 
Since the project uses Spring Data and the Repository pattern, it's even fairly easy to back the same service with MongoDB. 

### Uncomment the corresponding configuration lines of application-local.yml and application-test.yml profiles: 

```
---
#    datasource:
#        url: jdbc:postgresql://localhost:5432/testchallenge
#        username: <your_postgre_username>
#        password: <your_postgre_password>

#    datasource:
#        driverClassName: com.mysql.jdbc.Driver
#        url: jdbc:mysql://<your_mysql_host_or_ip>/testchallenge
#        username: <your_mysql_username>
#        password: <your_mysql_password>
#
#    jpa:
#        hibernate:
#            dialect: org.hibernate.dialect.MySQLInnoDBDialect



