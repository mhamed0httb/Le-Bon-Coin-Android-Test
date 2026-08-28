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

## 4. Libraries & Justification

### Core / UI
- **Jetpack Compose**: Modern declarative UI framework. Chosen for its productivity and ability to create reactive UIs easily.
- **Spark UI**: Adevinta's design system library, used for consistent UI components.
- **Coil**: An image loading library for Android backed by Kotlin Coroutines. It is lightweight and integrates seamlessly with Compose.
- **Paging 3**: Used to handle large lists of albums efficiently, providing built-in support for loading states and error handling.

### Networking & Persistence
- **Retrofit & OkHttp**: The industry standard for REST API communication.
- **Kotlinx Serialization**: A modern, type-safe way to handle JSON parsing.
- **Room**: A persistence library that provides an abstraction layer over SQLite, allowing for robust database access with compile-time checks.

### Asynchrony & Reactivity
- **Kotlin Coroutines**: For managing background tasks in a non-blocking way.
- **Kotlin Flow**: Used for reactive data streams from the database and network to the UI.

### Dependency Injection
- **Hilt**: A standardized way to incorporate Dagger DI into Android apps. It simplifies boilerplate and manages component lifecycles automatically.

### Testing
- **JUnit 4**: Standard testing framework.
- **MockK**: A mocking library specifically designed for Kotlin.
- **Turbine**: A small library for testing Kotlin Flow.
- **Coroutines Test**: Utilities for testing asynchronous code.

## 4. Performance & Optimizations
- **Offline First**: Data is first fetched from the network and saved to Room. The UI observes the local database, ensuring the app works offline.
- **Image Caching**: Handled by Coil to reduce network usage and improve scroll performance.
- **Memory Leak Detection**: **LeakCanary** is integrated in debug builds to catch potential memory leaks early.

## 5. Future Evolutions
- **MVI (Model-View-Intent)**: Transition from MVVM+UDF to a full MVI architecture to further centralize state changes and simplify event handling.
- **Modularization by Feature**: Currently modularized by layer. As the app grows, splitting by feature (e.g., `:feature:album-list`, `:feature:album-details`) would further improve build times and team autonomy.
- **Deep Linking**: Implement navigation via URIs for better integration with external apps/notifications.
- **Enhanced Caching**: Implement a more sophisticated TTL-based caching strategy for the network data.
- **UI Testing**: Add more Compose UI tests and end-to-end tests using Hilt and MockWebServer.
