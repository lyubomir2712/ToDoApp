# ToDo Application

A full-featured ToDo application built with Spring Boot featuring user authentication and a beautiful wooden notepad design.

## Features

- **User Authentication**: Register and login functionality with secure password encryption
- **Task Management**: Create, read, update, and delete tasks
- **Task Status**: Mark tasks as completed or incomplete
- **Filter Tasks**: View all tasks, only completed, or only incomplete tasks
- **Wooden Notepad Design**: Beautiful UI with a paper-like notepad aesthetic
- **H2 Database**: In-memory database for easy development and testing

## Technologies Used

- **Backend**: Spring Boot 3.5.7
- **Security**: Spring Security with BCrypt password encoding
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Java Version**: 25

## Running the Application

### Option 1: Using Maven Wrapper
```bash
./mvnw spring-boot:run
```

### Option 2: Using Java
```bash
./mvnw clean package -DskipTests
java -jar target/ToDo-0.0.1-SNAPSHOT.jar
```

### Option 3: Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Run the `ToDoApplication` main class

## Accessing the Application

Once the application is running, open your browser and navigate to:
- **Main Application**: http://localhost:8080
- **H2 Console** (for database inspection): http://localhost:8080/h2-console

### H2 Console Settings
- **JDBC URL**: `jdbc:h2:mem:tododb`
- **Username**: `sa`
- **Password**: (leave empty)

## Application Structure

```
src/main/java/com/ToDo/ToDo/
├── config/
│   └── SecurityConfig.java          # Security configuration
├── controller/
│   ├── AuthController.java          # Login and registration
│   └── TaskController.java          # Task CRUD operations
├── model/
│   ├── User.java                    # User entity
│   └── Task.java                    # Task entity
├── repository/
│   ├── UserRepository.java          # User data access
│   └── TaskRepository.java          # Task data access
├── service/
│   ├── UserService.java             # User business logic
│   └── TaskService.java             # Task business logic
└── ToDoApplication.java             # Main application class

src/main/resources/
├── templates/
│   ├── login.html                   # Login page
│   ├── register.html                # Registration page
│   └── tasks.html                   # Task management page
├── static/css/
│   └── style.css                    # Wooden notepad styling
└── application.properties           # Application configuration
```

## How to Use

### 1. Register an Account
1. Navigate to http://localhost:8080
2. You'll be redirected to the login page
3. Click on "Register here"
4. Fill in the registration form with:
   - Username (3-50 characters)
   - Email (valid email format)
   - Password (minimum 6 characters)
5. Click "Register"

### 2. Login
1. Enter your username and password
2. Click "Login"
3. You'll be redirected to your tasks page

### 3. Manage Tasks
- **Add a Task**: Fill in the title (required) and description (optional), then click "Add Task"
- **Mark Complete/Incomplete**: Click the button to toggle task completion status
- **Edit a Task**: Click "Edit", modify the details, then click "Save Changes"
- **Delete a Task**: Click "Delete" (confirmation required)
- **Filter Tasks**: Use the filter buttons to view All, Incomplete, or Completed tasks

### 4. Logout
Click the "Logout" button in the top right corner

## API Endpoints

### Authentication
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /register` - Registration page
- `POST /register` - Process registration
- `POST /logout` - Logout

### Tasks
- `GET /tasks` - View all tasks (with optional filter parameter)
- `POST /tasks/create` - Create a new task
- `POST /tasks/{id}/toggle` - Toggle task completion
- `POST /tasks/{id}/update` - Update task details
- `POST /tasks/{id}/delete` - Delete a task

## Security Features

- Passwords are encrypted using BCrypt
- CSRF protection enabled (except for H2 console)
- User authentication required for all task operations
- Users can only access their own tasks

## Database Schema

### Users Table
- `id` (Primary Key)
- `username` (Unique, 3-50 characters)
- `email` (Unique)
- `password` (Encrypted)

### Tasks Table
- `id` (Primary Key)
- `title` (Required)
- `description` (Optional, max 1000 characters)
- `completed` (Boolean)
- `created_at` (Timestamp)
- `updated_at` (Timestamp)
- `user_id` (Foreign Key to Users)

## Design Features

The application features a beautiful wooden notepad design with:
- Paper-like background texture
- Horizontal lines mimicking notebook paper
- Red vertical margin line
- Wooden border effect
- Hover effects on interactive elements
- Responsive design for mobile devices

## Future Enhancements

Potential improvements for the application:
- Persistent database (MySQL, PostgreSQL)
- Task categories/tags
- Due dates and reminders
- Task priorities
- Search functionality
- User profile management
- Password reset functionality
- Task sharing between users
