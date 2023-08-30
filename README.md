# Cards Application

The Cards Application is a comprehensive end-to-end system encompassing both the backend and front end components. This project serves as an assessment for Logicea Limited.


## Table of Contents

- [Installation and Running the Application ](#installation )
- [Usage](#usage)


## Installation and Running the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory from your IDE.
3. Install the required dependencies using Maven
4. Configure the MySQL database and run it.
5. Run the application using your preferred IDE or the command line.

## Usage
1. Launch the application.
2. Access the frontend interface to create, manage, and interact with cards.
3. Utilize API calls for more advanced interactions, authenticated using JWT.


### Features

1. **User Authentication and Authorization:**
   - Users are identified by their email addresses.
   - Roles: Member or Admin.
   - Authentication using passwords.
   - Members access cards they created.
   - Admins access all cards.

2. **Card Creation and Management:**
   - Create tasks as cards with a name (mandatory), description, and color (optional).
   - Color follows the format: 6 alphanumeric characters prefixed with #.
   - Default status: "To Do".

3. **Card Search and Filtering:**
   - Search cards based on name, color, status, and creation date.
   - Pagination options: page, size, offset, and limit.
   - Sorting options: name, color, status, and creation date.

4. **Single Card Access:**
   - Request details of a specific card.

5. **Card Update and Deletion:**
   - Update name, description, color, and status.
   - Clear description and color fields.
   - Card statuses: To Do, In Progress, and Done.
   - Delete cards.

6. **RESTful Web Service:**
   - Built using Java, Spring Boot, and MySQL.
   - Authenticate with JSON Web Tokens (JWT).
   - JWT included in "Authorization" header for API calls.
   - API documented using Swagger UI.
   - Backend Swagger UI: [http://localhost:8080/swagger-ui/#/](http://localhost:8080/swagger-ui/#/)
   - Frontend Swagger UI: [http://localhost:8081/swagger-ui/#/](http://localhost:8081/swagger-ui/#/)

### Screenshots

1. Login page
![Screenshot 2023-08-31 at 00 55 32](https://github.com/iangithua/logicea_assesment/assets/40959077/b0c2bcac-484c-4056-b736-1b760c4cc4f4)

2. Cards Dashboard
![Screenshot 2023-08-31 at 00 59 17](https://github.com/iangithua/logicea_assesment/assets/40959077/a648bde7-8d46-4a62-a609-8d305d828097)

3. Reduced Page Size and pagination number
![Screenshot 2023-08-31 at 01 01 47](https://github.com/iangithua/logicea_assesment/assets/40959077/54a60b2c-aa05-4afa-9557-b5a1114ecdfb)

4. Filter By name, Value Ian Order By Color for instance 
![Screenshot 2023-08-31 at 01 06 01](https://github.com/iangithua/logicea_assesment/assets/40959077/9a225ac4-8810-4556-a778-987eaa5bff92)

5. Crud Operations: Add new Card
![Screenshot 2023-08-31 at 01 08 39](https://github.com/iangithua/logicea_assesment/assets/40959077/2cac4d78-a556-4fa5-9fc9-f0c54cff9ba0)

6. Update an existing card
![Screenshot 2023-08-31 at 01 09 58](https://github.com/iangithua/logicea_assesment/assets/40959077/85da25e6-191b-42b7-98d2-a738f304313a)

7. Delete an existing card
![Screenshot 2023-08-31 at 01 10 25](https://github.com/iangithua/logicea_assesment/assets/40959077/7bdd0322-114c-4429-bcf4-5388215cf0ce)

8. Admin View access to all cards
![admin_view](https://github.com/iangithua/logicea_assesment/assets/40959077/98314740-1421-441d-807e-3f59e28db57e)


## Notes

- User signup is not part of this assignment; manually populate the database with users.



