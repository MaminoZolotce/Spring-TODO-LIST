# Spring-TODO-LIST
for intership
Todo List App
A task management web application built with Spring MVC (without Spring Boot), demonstrating manual configuration of the Spring IoC container, Hibernate ORM, and PostgreSQL integration.
Tech Stack
LayerTechnologyLanguageJava 21FrameworkSpring MVC 7.0.6ORMHibernate 7.3.1 + Jakarta Persistence 3.2DatabasePostgreSQL 15ViewJSP + JSTLConnection PoolApache Commons DBCP2BuildMaven (WAR packaging)ServerApache Tomcat 10.1ContainerizationDocker + Docker Compose
Features

Add new tasks
Delete tasks
Toggle task status between Active and Done
Filter tasks by status (All / Active / Done)
Live counters for active and completed tasks

Project Structure
src/main/java/com/maminozolotce/
├── config/
│   ├── ApplicationInitializer.java   # Replaces web.xml (WebApplicationInitializer)
│   ├── DataBaseConfig.java           # DataSource, Hibernate, TransactionManager beans
│   └── WebConfig.java                # Spring MVC configuration, ViewResolver
├── controllers/
│   └── CommonController.java         # All HTTP request handlers
├── dao/
│   └── RecordDao.java                # Database layer (EntityManager)
├── entity/
│   ├── Record.java                   # Task JPA entity (@Table = "records")
│   ├── RecordStatus.java             # Enum: ACTIVE | DONE
│   └── DTO/
│       └── RecordContainerDto.java   # Wraps task list + counters
└── service/
    └── RecordService.java            # Business logic, filtering, @Transactional
Running with Docker
Prerequisites

Docker
Docker Compose

Steps
bash# 1. Clone the repository
git clone https://github.com/MaminoZolotce/Spring-TODO-LIST.git
cd Spring-TODO-LIST

# 2. Start PostgreSQL and the application
docker-compose up --build

# 3. Open in browser
http://localhost:8080/to-do-list-app
To stop:
bashdocker-compose down
To stop and remove the database volume:
bashdocker-compose down -v
Running Locally (without Docker)
Prerequisites

Java 21+
Maven 3.8+
PostgreSQL running on port 6543
Apache Tomcat 10.1+

Database
Make sure PostgreSQL is running and a database named postgres exists:
sqlCREATE DATABASE postgres;
Configuration
src/main/resources/application.properties:
propertiesdb.url=${DB_URL:jdbc:postgresql://localhost:6543/postgres}
db.username=${DB_USERNAME:postgres}
db.password=${DB_PASSWORD:12345678}
Build and Deploy
bashmvn clean package -DskipTests
# Copy target/to-do-list-app.war to $TOMCAT_HOME/webapps/
Then open: http://localhost:8080/to-do-list-app
