# AGENTS.md

## Project Context
This is a **Kotlin Multiplatform (KMP)** project targeting **Android** and **iOS**. It serves as a comprehensive template using modern development practices, libraries, and architectural patterns.

## Tech Stack
- **Language**: Kotlin 2.x
- **UI Framework**: Compose Multiplatform (Jetpack Compose for Android/iOS)
- **Dependency Injection**: Koin (using KSP Annotations)
- **Asynchronous**: Kotlin Coroutines & Flow
- **Network**: Ktor Client
- **Local Storage**: DataStore, SecureStorage (Custom/Tink)
- **Navigation**: Manual / Koin-assisted
- **Build System**: Gradle with Kotlin DSL and Version Catalog (`libs.versions.toml`)
- **Testing**: Mokkery (Mocking), Turbine (Flow testing)

## Architecture
The project follows **Clean Architecture** with a strict separation of concerns, organized into the following layers:

### 1. Core Modules
Located in `core/`:
- **`core/domain`**: The pure business logic layer.
    - Contains: UseCases, Domain Models, Repository Interfaces.
    - Dependencies: Pure Kotlin, no Android/iOS specific dependencies.
- **`core/data`**: The implementation layer.
    - Contains: Repository Implementations, API definitions (DTOs), Local Storage, Mappers.
    - Dependencies: `core/domain`, Ktor, DataStore.
- **`core/ui`**: Base UI infrastructure.
    - Contains: `BaseScreen`, `BaseViewModel`, Design System (Theme, Typography, Colors), Common Widgets.
    - Pattern: **MVI (Model-View-Intent)**.

### 2. Feature Modules
(Implied structure for expansion)
- Features should be separated into their own modules or packages (e.g., `feat/auth`) adhering to the clean architecture layers.

### 3. Application Module
Located in `composeApp/`:
- **Android**: `src/androidMain` (Manifest, Activity).
- **iOS**: `src/iosMain` (ViewControllers).
- **Common**: `src/commonMain` (DI initialization, Root Navigation).

## Coding Conventions & Patterns

### Dependency Injection (Koin)
- **Annotations**: Use Koin Annotations (`@Module`, `@Single`, `@Factory`) instead of manual DSL module definitions where possible.
- **Setup**: Modules are aggregated in `Koin.kt`.
  ```kotlin
  @Module
  @ComponentScan
  class FeatureModule