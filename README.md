🎥 Video Rental Store
This project implements a video rental store system using Spring Boot, providing functionalities to manage film inventory, handle rentals, and calculate late fees.

📚 Features
Add, retrieve, and list films
Rent films with dynamic pricing based on film type
Return films and calculate late fees
Simple API design for easy integration

🚀 Getting Started
These instructions will help you set up and run the project on your local machine for development and testing purposes.

📋 Prerequisites
Ensure you have the following installed:

Java JDK 17: Required to run the project.
Gradle: The build tool used for dependency management and running tasks.

⚙️ Installation
Clone this repository:

bash
Copy code
git clone https://angie27manrique@bitbucket.org/chimeralabs/rental-video-store.git

Navigate to the project directory:

bash
Copy code
cd video-rental-store
Build the project:

bash
./gradlew clean build

▶️ Running the Application
Start the application:

bash
./gradlew bootRun
Once the application is running, it will be available at:
http://localhost:8080

🔄 Running the Tests
You can run all unit tests using:

bash
./gradlew test

📡 API Endpoints
The application provides a REST API to interact with the video rental store. Below is a list of the available endpoints:

🎬 Films
Add a new film
POST /api/films
Request Body Example:

{
    "title": "Spider Man 2",
    "type": "REGULAR"
}

Get a film by ID
GET /api/films/{id}

Get all films
GET /api/films

🎥 Rentals
Rent films
POST /api/rentals/rent
Request Body Example:

[
  {
    "film": {
      "id": 2,
      "title": "Matrix 11",
      "type": "NEW_RELEASE"
    },
    "rentalDays": 2
  },
  {
    "film": {
      "id": 3,
      "title": "Spider Man",
      "type": "REGULAR"
    },
    "rentalDays": 5
  }
]


Return films
POST /api/rentals/return
Request Body Example:

[
  {
    "id": 1,
    "film": {
      "id": 2,
      "title": "The Matrix",
      "type": "NEW_RELEASE"
    },
    "rentalDays": 2,
    "extraDays": 1
  },
  {
    "id": 2,
    "film": {
      "id": 3,
      "title": "Spider-Man",
      "type": "REGULAR"
    },
    "rentalDays": 5,
    "extraDays": 2
  }
]


Get all rentals
GET /api/rentals

🛠️ Built With
Spring Boot - Backend framework
Gradle - Build automation tool
JUnit 5 - Testing framework

📄 License
This project is licensed under the MIT License - see the LICENSE.md file for details.