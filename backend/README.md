## ATeam-Java-NextJS-Quiz-Builder

## backend

## Getting started

To get started with this project, you will need to have the following installed on your local machine:

- JDK 17+

## Technologies

- Spring Boot 3.0.2
- Spring Security
- JSON Web Tokens (JWT)
- BCrypt
- Gradle - Groovy

## Configuration

- You have to set "Environment variables":

  - DB_URL - jdbc:mysql://localhost:3306/name_of_your_database
  - DB_USERNAME - your username in MySqlWorkbench
  - DB_PASSWORD - your password in MySqlWorkbench

- To dockerized localhost DB you have to set your Workbench password and name of database in
  docker-compose.yml in backend folder and run this file.

Clone the repository: `git clone ...`

-> The application will be available at http://localhost:8080

## Deploy app to AWS

- Open CLI (run the following commands):

  - make sure you are in the backend directory `pwd`
  - command to create a JAR file: `./gradlew clean build`
  - command to open Elastic Beanstalk environment in a web browser: `eb console`

- Create a new environment, upload and deploy

  - select "create a new environment"
  - select "web server environment"
  - fill "Application name" (quizBuilder), "Platform" (Java) and submit "create a new environment"
  - choose "Upload your code"
  - choose file "backend-0.0.1-SNAPSHOT.jar" and submit "Create application" (this takes a few minutes)

- Configuration

  - go to "Configuration"
  - push "Edit" Database button
  - fill "Database settings" ("Username", "Password") and submit "Apply"
  - go back to "Configuration"
  - push "Edit" Software button
  - fill "Environment properties":
    - "SPRING_DATASOURCE_URL" = "jdbc:mysql://awseb-e-wtrybwj46m-stack-awsebrdsdatabase-zkfdady72ake.cfyfcgxfgkpt.eu-central-1.rds.amazonaws.com:3306/ebdb"
    - "SPRING_DATASOURCE_USERNAME" = "your_username"
    - "SPRING_DATASOURCE_PASSWORD" = "your_password"
    - "SERVER_PORT" = "5000"

- Test random endpoint in Postman
  - try endpoint with new URL: "http://Quizbuilder-env.eba-rsabv2wf.eu-central-1.elasticbeanstalk.com"
