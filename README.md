# Energy Mix API (Backend)

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

A robust Spring Boot RESTful API core engine for the Energy Mix Dashboard. It fetches real-time data from the UK National Grid API and processes it to calculate the best time window to ecologicly charge an EV.

## Live Application
**[Base URL](https://energy-mix-frontend-icpr.onrender.com)**

## Features
- **Data Aggregation**: Fetches data from the official `api.carbonintensity.org.uk` to get the current power generation mix.
- **Algorithmic Forecasting**: Implements a sliding-window algorithm that analyzes 3-day carbon intensity forecasts to determine the exact timeframe with the lowest ecological impact for a given duration up to 6 hours.
- **Unit Tested**: Core algorithms and API clients are covered by JUnit & Mockito tests to guarantee business logic integrity.

## Technology Stack
- **Language**: Java 17
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito
- **Containerization**: Docker

## API Endpoints

### 1. Get Current Energy Mix
`GET /api/energy-mix`
Returns the real-time percentage breakdown of the UK's energy generation by fuel type.
Test with [Energy Mix](https://energy-mix-backend-b1bt.onrender.com/api/energy-mix)

### 2. Get Optimal Charging Window
`GET /api/charging-window?hours={1-6}`
Calculates and returns the best contiguous timeframe to consume power based on carbon forecasts.
Test with [Charging Window](https://energy-mix-backend-b1bt.onrender.com/api/charging-window?hours=2) (2 hours is the default)

## Local Setup

1. **Clone the repository**.
2. **Run the application via Maven wrapper**:
   ```bash
   ./mvnw spring-boot:run
   ```
3. The server will start on `http://localhost:8081` (unless the `PORT` environment variable is overwritten).

## Cloud Deployment
This application is production-ready and configured with dynamic port binding (`server.port=${PORT:8081}`).

## Check out the frontend Github Repository
**[View Github Frontend Repository](https://github.com/anwidaj/energy-mix-frontend)**
