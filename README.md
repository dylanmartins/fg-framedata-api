# FG Framedata API

### Project Structure
```
fg-framedata-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── fgc/
│   │   │           └── framedata_api/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               ├── service/
│   │   │               └── FramedataApiApplication.java
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── db/
│   │           └── db.migration/
│   │               ├── V1__init_schema.sql
│   │               ├── application.properties
│   │               ├── templates/
│   │               ├── static/
│   │               └── application.yaml
│   └── test/
├── Dockerfile
├── docker-compose.yaml
└── pom.xml
```

### Description
This Spring Boot application provides an API for accessing framedata from various fighting games, serving as a guide to help players understand the mechanics and abilities of different characters.

### Features
- **RESTful API**: Provides endpoints to access framedata for different characters and games.
- **Database Integration**: Uses Spring Data JPA for database operations, allowing for easy management of framedata.
- **Docker Support**: Comes with a Dockerfile and docker-compose configuration for easy deployment.
