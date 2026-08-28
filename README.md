# Project Architecture & Technical Choices

This document summarizes the architecture, patterns, and libraries applied to the Android Recruitment Test App.

## 1. Architecture Overview
The project follows **Clean Architecture** principles and is organized into a **multi-module** structure to ensure separation of concerns, scalability, and testability.

### Module Structure
- **`:domain`**: The core layer containing business logic. It is independent of any framework.
  - **Models**: Domain entities (e.g., `Album`).
  - **UseCases**: Encapsulate specific business rules (e.g., `GetAlbumsUseCase`, `ToggleFavoriteUseCase`).
  - **Repository Interfaces**: Defined here, implemented in the data layer.
- **`:data`**: Handles data retrieval and persistence.
  - **Repository Implementations**: Coordinates between network and local data sources.
  - **Network**: Retrofit service and DTOs.
  - **Local**: Room database, DAOs, and Entities.
  - **Mappers**: Convert between DTOs/Entities and Domain Models.
- **`:app`**: The presentation layer.
  - **UI**: Jetpack Compose screens and components.
  - **ViewModels**: Manage UI state and interact with UseCases (MVVM).
  - **DI**: Hilt modules for dependency injection.
  - **Navigation**: Jetpack Navigation Compose with structured routes and custom transitions.

## 2. Design Patterns
- **MVVM (Model-View-ViewModel)**: Decouples the UI from the business logic.
- **UDF (Unidirectional Data Flow)**: The UI observes a single state stream (via `StateFlow`) and sends actions/events back to the ViewModel, ensuring a predictable data flow.
- **Repository Pattern**: Abstracts data sources from the rest of the application.
- **Mapper Pattern**: Ensures that domain models remain pure and independent of data source schemas.
- **Dependency Injection**: Promotes loose coupling and facilitates testing.
- **Singleton Pattern**: Used via Hilt for database, network clients, and repositories.

## 3. DDD (Domain-Driven Design) Principles
The project adopts core **DDD** concepts to maintain a clear boundary between business logic and technical implementation:
- **Domain Layer**: The `:domain` module is the "heart" of the software, containing entities (`Album`) and business rules.
- **Use Cases**: Act as application services that orchestrate the flow of data to and from the domain entities.
- **Repository Interfaces**: Define the contract for data operations, keeping the domain agnostic of the persistence mechanism.
- **Separation of Concerns**: Each layer has a specific responsibility, preventing "leaky abstractions".

## 4. Libraries

### Core / UI / Navigation
- **Jetpack Compose**: Modern declarative UI framework.
- **Jetpack Navigation Compose**: Handles screen navigation and transitions with a structured route system.
- **Spark UI**: Adevinta's design system library for consistent UI components.
- **Coil**: Lightweight image loading library for Android.
- **Paging 3**: Efficiently handles large lists of albums with built-in loading and error states.

### Networking & Persistence
- **Retrofit & OkHttp**: Industry standard for REST API communication.
- **Kotlinx Serialization**: Type-safe JSON parsing.
- **Room**: Abstraction layer over SQLite for robust database access.

### Asynchrony & Reactivity
- **Kotlin Coroutines**: For non-blocking background tasks.
- **Kotlin Flow**: Reactive data streams from database/network to UI.

### Dependency Injection
- **Hilt**: Simplifies Dagger DI boilerplate and manages component lifecycles.

### Logging
- **Timber**: A powerful logging library built on top of the native `Log` class.
    - **Automatic Tagging**: Automatically infers the tag based on the calling class, reducing boilerplate and potential for manual tagging errors.
    - **Tree API**: Allows "planting" different behavior for different build types (e.g., logging to Logcat in debug, but sending to a crash reporting service or staying silent in release).
    - **Security**: Prevents leaking sensitive information in production by easily disabling logging in release builds.
    - **Formatting**: Provides cleaner syntax and built-in support for string formatting and logging exceptions.

### Testing
- **JUnit 4**, **MockK**, **Turbine**, and **Coroutines Test**.

## 5. Performance & Optimizations
- **Offline First**: Data is fetched from network and cached in Room; the UI observes the database.
- **Image Caching**: Handled by Coil.
- **Memory Leak Detection**: LeakCanary is used in debug builds.

## 6. Future Evolutions
- **Navigation 3**: Adopt the next generation of Jetpack Navigation for improved flexibility and better decoupling of navigation logic from the UI.
- **MVI (Model-View-Intent)**: Further centralize state changes through explicit intents.
- **Modularization by Feature**: Split the app into feature modules (e.g., `:feature:album-list`) to improve build times and team autonomy.
- **UI & Automation Testing**: Implement comprehensive Compose UI tests and end-to-end automation tests using Hilt and MockWebServer to ensure long-term stability.
- **Deep Linking**: Enhanced integration with external app links.
