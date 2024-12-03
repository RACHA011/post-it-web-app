# post-it-web-app

## Description

post-it-web-app is a Spring Boot-based blog application that allows users to post, view, edit, and delete blog articles. It is designed with role-based access control to provide varying levels of functionality for users, including regular users and administrators. The app uses Spring Security for authentication and authorization, Thymeleaf for rendering HTML templates, and H2 for the in-memory database.

---

## Features

- **User Authentication**:
  - Users can register, log in, and manage their profiles.
  - Role-based access control via Spring Security.
  - Admin users can manage all blog posts.

- **Post Management**:
  - Authenticated users can create, edit, and delete their posts.
  - Posts can be filtered and sorted by date (created or updated).
  - Pagination support for efficient post viewing.

- **Admin Features**:
  - Admin users can view, edit, and delete any post.
  - Admins can manage user accounts and roles.

- **Mail Support**:
  - The application supports email sending, ideal for notifications or account management.

---

## Technologies Used

- **Backend**:
  - **Spring Boot 3.3.0**: Java framework for creating the RESTful backend.
  - **Spring Security**: For securing endpoints and implementing authentication and authorization.
  - **Spring Data JPA**: For database interaction.
  - **Spring Boot Starter Mail**: For email functionality.

- **Frontend**:
  - **Thymeleaf**: For rendering HTML views on the server-side.
  - **Bootstrap**: For a responsive and clean UI.

- **Database**:
  - **H2 Database**: In-memory database for easy testing and development.

- **Development Tools**:
  - **Lombok**: To reduce boilerplate code, especially for models and controllers.
  - **Spring Boot DevTools**: For automatic restarts and live reload during development.
  - **JUnit & Spring Security Test**: For testing security features and endpoints.

---

## Getting Started

### Prerequisites

To run this project locally, make sure you have the following installed:

- **Java 17+**.
- **Maven** for dependency management and building the project.
- **IDE** like IntelliJ IDEA or Eclipse (optional).

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/racha011/post-it-web-app.git
   ```

2. Navigate to the project directory:

   ```bash
   cd post-it-web-app
   ```

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

5. Open your browser and go to:

   ```
   http://localhost:8080
   ```

### Configuration

The application uses the default configurations that should work out of the box. However, you can modify the `application.properties` file for custom settings such as the database URL, email configurations, and others.

---

## Usage

### User Roles:

- **Unauthenticated Users**:
  - Can view the list of blog posts.
  - Can register and log in.

- **Authenticated Users**:
  - Can create, edit, and delete their own blog posts.
  - Can view and edit their profile.

- **Admin Users**:
  - Have full access to all blog posts.
  - Can manage users and modify roles.

### Pages:

1. **Home**: Displays a list of blog posts with pagination and sorting options.
2. **Login**: Allows users to log in.
3. **Register**: Allows new users to create an account.
4. **Profile**: Allows users to view and edit their profile.
5. **Add Post**: Authenticated users can create a new blog post.
6. **Admin Panel**: Admin users can view and manage all blog posts.

---

## Dependencies in `pom.xml`

- **Spring Boot Starter Web**: Provides support for creating web applications, including RESTful APIs.
- **Spring Boot Starter Security**: For securing the application with authentication and role-based access.
- **Spring Boot Starter Thymeleaf**: For server-side HTML rendering.
- **H2 Database**: An in-memory database for local development.
- **Spring Boot Starter Mail**: To enable email functionality.
- **Spring Boot Starter Validation**: For validating user inputs.
- **Lombok**: To reduce boilerplate code like getters, setters, and constructors.

---

## Contributing

We welcome contributions! If you'd like to contribute, follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push them to your fork.
4. Submit a pull request for review.

---

## License

This project is licensed under the MIT License.

---

## Acknowledgements

- **Spring Boot**: For simplifying the development of the backend.
- **Thymeleaf**: For making server-side rendering of HTML easy and clean.
- **Bootstrap**: For a responsive and modern design.
