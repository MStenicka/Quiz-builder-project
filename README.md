# Quiz application

## Description

The Quiz application is an interactive web application that allows users to create, manage, and participate in quizzes. It is designed to provide a seamless and user-friendly experience for both quiz creators and participants.

## Features

1. **Quiz Creation:**
   - Registered users can create new quizzes with customizable questions and answer choices.
   - Different question types are supported, including single-choice, multiple-choice, fill-out, and pair-assign.

2. **Quiz Participation:**
   - Participants can join available quizzes and answer the questions.
   - Real-time feedback is provided to participants after submitting their answers.
   - Leaderboards are generated to showcase top quiz performers.

3. **User Management:**
   - User registration and login functionality are implemented with Spring Security and JWT.
   - Automated email notifications are sent to users upon successful registration and password reset.
   - User roles include "USER" and "ADMIN." "USER" can create and take quizzes, while "ADMIN" can manage quizzes and user data.

4. **Code Coverage and Testing:**
   - The project is set up with JaCoCo for code coverage analysis, ensuring robustness and reliability.
   - Unit tests and integration tests are provided for key components of the application.

## Technologies Used

- **Frontend:** (in progress) The frontend of the application will be built with Next.js, a popular React framework that provides server-side rendering and efficient client-side navigation. Thymeleaf templates will be used for server-side rendering of some pages.
- **Backend:** The backend is implemented with Spring Boot, a powerful Java framework, providing a RESTful API for the frontend to interact with the database.
- **Database:** MySQL is used as the database to store quiz-related data, user information, and quiz results.
- **Security:** Spring Security is integrated to handle user authentication and authorization, ensuring secure access to various functionalities.
- **Token-based Authentication:** JSON Web Tokens (JWT) are used for secure and stateless user authentication.

## Getting Started

1. **Prerequisites:**
   - JDK 17+ installed on your machine.
   - MySQL installed and running on your machine.
   - Docker installed for Dockerization of the local database (optional for AWS deployment).

2. **Clone the Repository:**
   git clone ...

3. **Set Environment Variables**

In the backend application, set the environment variables for the database connection (DB_URL, DB_USERNAME, DB_PASSWORD), and any other necessary configurations.

4. **Build and Run the Application**

- For local testing, you can build and run the backend application using Gradle.
- For AWS deployment, follow the provided instructions in the README for creating and deploying the application on Elastic Beanstalk.

5. **Access the Application**

Once the backend application is up and running, you can access the frontend through your browser. The application will be available at http://localhost:8080.

6. **Deployment to AWS**

For AWS deployment, follow the provided instructions in the README for creating an environment on Elastic Beanstalk, setting up the database configuration, and deploying the application.
