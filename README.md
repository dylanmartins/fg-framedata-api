# framedata-api

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
└── build.gradle.kts  # or pom.xml if using Maven