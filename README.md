Internship Connect Portal (Java Swing + MySQL)
Internship Connect is a desktop application that helps students discover and apply for internships and allows companies to post and manage internship opportunities.
The project is built using Java Swing, JDBC, and MySQL.

Features
For Students

Student registration and login.

View all available internships with company name, location, stipend, and deadline.

Search internships by title or location.

Apply to internships and prevent duplicate applications.

Track how many internships they have applied to.

Edit profile details like phone, college, and course.

For Companies

Company registration and login.

Post new internships with title, description, requirements, location, duration, stipend, and deadline.

See a dashboard of all posted internships with application counts.

View all applications for a selected internship with student details.

Edit company profile information (phone, address, website).

General

Clean home screen with separate paths for students and companies.

Simple, original UI using plain Java Swing components.

Data stored in a MySQL database accessed via JDBC.

Technologies Used
Language: Java

GUI: Java Swing

Database: MySQL

Database Access: JDBC (MySQL Connector/J)

Version Control: Git & GitHub

Database Setup
Start MySQL and open a MySQL client (terminal or Workbench).

Create the database and tables:

sql
CREATE DATABASE internship_connect;
USE internship_connect;

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    phone VARCHAR(15),
    college VARCHAR(100),
    course VARCHAR(50)
);

CREATE TABLE companies (
    company_id INT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    website VARCHAR(100)
);

CREATE TABLE internships (
    internship_id INT PRIMARY KEY AUTO_INCREMENT,
    company_id INT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    requirements TEXT,
    location VARCHAR(100),
    duration VARCHAR(50),
    stipend DECIMAL(10,2),
    deadline_date DATE,
    FOREIGN KEY (company_id) REFERENCES companies(company_id)
);

CREATE TABLE applications (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    internship_id INT,
    application_date DATE,
    status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (internship_id) REFERENCES internships(internship_id)
);
Optionally insert some sample data (students, companies, internships) for testing.

Project Structure
text
InternshipConnect/
 ├─ src/
 │   ├─ DatabaseConnection.java
 │   ├─ InternshipConnect.java
 │   ├─ StudentRegister.java
 │   ├─ StudentLogin.java
 │   ├─ StudentDashboard.java
 │   ├─ CompanyRegister.java
 │   ├─ CompanyLogin.java
 │   ├─ CompanyDashboard.java
 │   └─ UIUtils.java
 └─ lib/
     └─ mysql-connector-j-<version>.jar
Configuration
In DatabaseConnection.java, set your MySQL username and password:

java
private static final String URL = "jdbc:mysql://localhost:3306/internship_connect";
private static final String USER = "root";          // or your MySQL user
private static final String PASSWORD = "your_pass"; // your MySQL password
How to Run
From the project root in a terminal:

Compile:

bash
javac -cp "lib/mysql-connector-j-<version>.jar:src" src/*.java
Run:

bash
java -cp "lib/mysql-connector-j-<version>.jar:src" InternshipConnect
(Replace <version> with your actual connector jar name.)

Possible Future Improvements
Password hashing instead of plain text passwords.

Email notifications to students when application status changes.

More advanced filtering (by stipend range, duration, location).

Export applications to CSV for companies.
