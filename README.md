# Virtual Library Management System

A Java console application for managing a virtual library. This system allows users to manage books, users, and borrowing operations with a user-friendly interface.

## Features

### Book Management
- Add new books with details (title, author, publication year, genre)
- Search books by title, author, or genre
- Remove books from the library
- Rate books (0 to 5 stars, with 0.5 increments)
- View all available and borrowed books

### User Management
- Register new users with unique IDs
- View user list and their borrowing history
- Track current borrowed books for each user

### Borrowing System
- Borrow books with user and book ID verification
- Return books
- View current borrowings
- Track borrowing history

### ID System
- User IDs format: U001, U002, etc.
- Book IDs format: L001, L002, etc.

## Getting Started

### Prerequisites
- Java JDK 11 or higher
- Maven (optional)

### Installation

1. Clone the repository:
```bash
git clone [repository-url]
cd Bibliotheque-Java
```

2. Compile the project:
```bash
# Using Maven
mvn clean compile

# Or using Java compiler
cd src/main/java
javac com/library/Main.java
```

3. Run the application:
```bash
# Using Maven
mvn exec:java -Dexec.mainClass="com.library.Main"

# Or using Java
java com.library.Main
```

## Usage

### Main Menu Options
1. Add a book
2. Search books
3. Borrow a book
4. Return a book
5. Display statistics
6. Delete a book
7. Add a user
8. Rate a book
9. List users
10. List all books
0. Exit

### Example Operations

#### Adding a User
```
=== Add a User ===
Name: John Doe
Email: john@email.com
User added successfully! ID: U001
```

#### Adding a Book
```
=== Add a Book ===
Title: The Great Gatsby
Author: F. Scott Fitzgerald
Publication Year: 1925
Genre: Novel
Book added successfully! ID: L001
```

#### Borrowing a Book
```
=== Borrow a Book ===
User ID (ex: U001): U001
Book ID (ex: L001): L001
Book borrowed successfully!
```

#### Rating a Book
```
=== Rate a Book ===
Book ID (ex: L001): L001
Rating (0-5, in 0.5 increments): 4.5
Rating added successfully!
```

## Project Structure

```
src/main/java/com/library/
├── Main.java                    # Application entry point
├── ApplicationBibliotheque.java # Main application logic
├── model/
│   ├── Livre.java              # Book class
│   └── Utilisateur.java        # User class
├── service/
│   └── ServiceBibliotheque.java # Business logic
└── utils/
    └── GenerateurId.java        # ID generator utility
```

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Project developed as part of a Java programming course
- Implements best practices in Java programming
- Features a modular and maintainable code structure
