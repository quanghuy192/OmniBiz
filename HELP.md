# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.dummy.omni-biz' is invalid and this project uses 'com.dummy.omni_biz' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.3/maven-plugin/build-image.html)
* [Docker Compose Support](https://docs.spring.io/spring-boot/3.5.3/reference/features/dev-services.html#features.dev-services.docker-compose)
* [Spring Session for Spring Data MongoDB](https://docs.spring.io/spring-session/reference/)
* [Rest Repositories](https://docs.spring.io/spring-boot/3.5.3/how-to/data-access.html#howto.data-access.exposing-spring-data-repositories-as-rest)
* [OpenAI](https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html)
* [Spring Web Services](https://docs.spring.io/spring-boot/3.5.3/reference/io/webservices.html)
* [Spring Security](https://docs.spring.io/spring-boot/3.5.3/reference/web/spring-security.html)
* [OAuth2 Client](https://docs.spring.io/spring-boot/3.5.3/reference/web/spring-security.html#web.security.oauth2.client)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.3/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/3.5.3/reference/data/sql.html#data.sql.jdbc)
* [Spring Session for Spring Data Redis](https://docs.spring.io/spring-session/reference/)
* [Flyway Migration](https://docs.spring.io/spring-boot/3.5.3/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [Spring gRPC](https://docs.spring.io/spring-grpc/reference/index.html)
* [Quartz Scheduler](https://docs.spring.io/spring-boot/3.5.3/reference/io/quartz.html)
* [Gateway](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc.html)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.5.3/reference/messaging/kafka.html)
* [Eureka Server](https://docs.spring.io/spring-cloud-netflix/reference/spring-cloud-netflix.html#spring-cloud-eureka-server)
* [Resilience4J](https://docs.spring.io/spring-cloud-circuitbreaker/reference/spring-cloud-circuitbreaker-resilience4j.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Service Registration and Discovery with Eureka and Spring Cloud](https://spring.io/guides/gs/service-registration-and-discovery/)

### Additional Links
These additional references should also help you:

* [Various sample apps using Spring gRPC](https://github.com/spring-projects/spring-grpc/tree/main/samples)

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* mysql: [`mysql:latest`](https://hub.docker.com/_/mysql)

Please review the tags of the used images and set them to the same as you're running in production.

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

