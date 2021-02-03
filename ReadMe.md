# Salary Management Service

Functionality

3 User stories : Upload, Fetch & Crud, please refer to the requirement spec docs for details.

##Assumptions/Special cases
Authentication and other security factors were not considered in this exercise
Detailed error message is not passed down to the caller if any.
EventSourcing/Event Driven Architecture is not implemented as this is written as a simple service though the provision for adopting it is available.
CQRS pattern is implemented at Service layer
Secure credentials are available as Environment variables in all non dev environments.
For Dev: H2-InMemory was used

## Setup/Installation
Open the POM file or project folder using IntelliJ IDEA or similar IDE for Java and run the application (The application will start listening at localhost:8080)

# Swagger API Docs
Once the application starts running, swagger ui can be viewed at http://localhost:8080/swagger-ui/index.html

# Architecture, Design Patterns, OOPS Principles used
Rest Calls, DI, CQRS, Inheritance & Interfaces, Transaction, TDD, Custom Exception Handling 

# Data Flow
RestAPI(DTO) --> Service(Entity/DTO) --> Repository(Entity) --> H2InMemoryDB(Record) and vice version

## Usage
Running the App
You can run the app via IntelliJ or any similar IDE (Standard Springboot-Maven)
Use PostmanCanary and import the attached postman scripts (salary-management-service.postman_collection.json), run the collection under salary-management-service title (6 requests)
Basic health check : http://localhost:8080/actuator/health

# Automated Testing
Running the Test
All the tests (unit test and integration tests) are at ..\salary-management-service\src\test\java\com\gmail\renjithkumar1\salarymanagement
Ensure maven is installed and available at PATH (environment) and run the following command from the project root folder
Run via test class at the IDE or use mvn test

#Deployments
Profile files are available for various environments (all credentials has to be passed via environment variables for non dev)
dev test prod
Please thoroughly review application-prod.yml before publishing to any production environment.

Image Creation
docker build --build-arg ENVIRONMENT=local .
Running in interactive mode
docker run <image_id> -p 8080:8080 -t

# Improvements and Extensions
Addition monitoring alert for failures and downtimes
Exceptions to be notified to L1 support via email or sms
Automate build and deployment management including terraform scripts(CI/CD pipeline) e.g., using Jenkins or bitbucket pipelines
Adding cache data to improve performance
Codes for Securing API
Adding more Clear and concise logs that are easy to read and parse
Selenium or similar UI based tool if the UI is in scope of the project
Create deployment pipeline scripts for Kubernetes cluster deployment

## Contributing
N.A.

## License
N.A.

# Support or Queries
email to: renjithkumar1@gmail.com