# ProfMeet

A JavaFX desktop application for managing professor office hours and course scheduling at San Jose State University.

## Features

- **Course Management**: Add and view course information including course codes, names, and section numbers
- **Office Hours Scheduling**: Create and edit professor office hours with semester, year, and day information
- **Time Slot Management**: Organize and view available time slots for office hours
- **Search Functionality**: Search through office hours schedules
- **Modern UI**: Clean, intuitive interface built with JavaFX

## Technology Stack

- **Java 23** with JavaFX 17
- **Maven** for dependency management
- **FXML** for UI design
- **CSV** data storage

## Getting Started

### Prerequisites

- Java 23 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd ProfMeet
```

2. Build the project:
```bash
mvn clean compile
```

3. Run the application:
```bash
mvn javafx:run
```

## Project Structure

```
src/main/java/s25/cs151/application/
├── Controller/          # MVC Controllers
├── Model/              # Data models (Course, OfficeHour, TimeSlot)
├── View/               # FXML UI files
└── Main.java           # Application entry point

data/                   # CSV data files
├── course_info.csv
├── office_hour_schedule.csv
└── time_slots.csv
```

## Architecture

The application follows the **Model-View-Controller (MVC)** pattern with **polymorphism**:

- **Abstract Parent Class**: `NavigationController`
- **Concrete Child Classes**: Various controllers extending the navigation functionality
- **Models**: Represent data entities (Course, OfficeHour, TimeSlot)
- **Views**: FXML files defining the user interface

## Contributing

This project was developed as part of CS151 coursework at San Jose State University.

## License

This project is for educational purposes.
