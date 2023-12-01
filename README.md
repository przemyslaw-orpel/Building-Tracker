# Building-Tracker

WebApp for real-time monitoring of employees in buildings. 
Management of buildings, building levels, rooms, employees, and users with a CRUD (Create, Read, Update, Delete) functionality. The main functionality is the ability to check whether the employee assigned to a given room is currently present. It can also simulate employees' entry/exit to a building's floor


![Screenshot](https://github.com/przemyslaw-orpel/Building-Tracker/blob/main/src/main/resources/static/img/app_view.jpg?raw=true)
## Design pattern
+ MVC

## Tech Stack
+ Java 17
+ SpringBoot 3.0.12 
+ Spring Secuirty
+ Database H2
+ Thymeleaf
+ JPA


## Installation

The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies
    
## Usage
Run the project through the IDE and head out to http://localhost:8080

If you want run project without IDE run this command in the command line (project directory):
```
mvn spring-boot:run
```
