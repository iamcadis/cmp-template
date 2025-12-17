# Project Rules

## 1. Project Overview
- **Type**: Kotlin Multiplatform (KMP) Mobile Application.
- **Targets**: Android (`androidMain`), iOS (`iosMain`).
- **UI Framework**: Compose Multiplatform (Jetpack Compose).
- **Architecture**: Clean Architecture with MVI (Model-View-Intent).

## 2. Architectural Layers & Dependencies
The project enforces strict separation of concerns. Adhere to these dependency rules:

### Core Layers
1.  **Domain (`core/domain`)**
    -   **Role**: Pure business logic.
    -   **Contains**: Use Cases, Domain Models, Repository Interfaces.
    -   **Rules**:
        -   MUST NOT depend on `core/data` or `core/ui`.
        -   MUST be pure Kotlin (no Android/iOS specific dependencies).
        -   Use `runCatching` for error handling in Use Cases.

2.  **Data (`core/data`)**
    -   **Role**: Data retrieval and persistence.
    -   **Contains**: Repository Implementations, DTOs, API Services, Local Storage (DataStore/SQL), Mappers.
    -   **Rules**:
        -   Implements interfaces from `core/domain`.
        -   Maps DTOs to Domain Models before returning them.
        -   Handles exceptions and maps them to domain errors.

3.  **UI (`core/ui`)**
    -   **Role**: Presentation and common UI components.
    -   **Contains**: Base classes (`BaseViewModel`, `BaseScreen`), Design System (Theme, Type, Color), Common Widgets.
    -   **Rules**:
        -   Depends on `core/domain` (for models/usecases).
        -   MUST NOT depend on `core/data` implementation details.

### Feature Modules
-   Features should be modularized (e.g., `feat/auth`, `feat/home`).
-   Each feature module should contain its own specific UI code and DI setup.

## 3. Coding Patterns

### MVI Pattern (Presentation)
All ViewModels MUST adhere to the MVI contract defined in `com.core.ui.base`:
-   **Inheritance**: Extend `BaseViewModel<State, Action, Effect>`.
-   **State (`ViewState`)**: Must be an immutable `data class`. Default values required.
-   **Actions (`ViewAction`)**: Use `sealed interface` to define user intents (e.g., `LoginClicked`).
-   **Effects (`ViewEffect`)**: Use `sealed interface` for one-off events (e.g., `MapsToDashboard`, `ShowError`).
-   **State Updates**: Use `setState { copy(...) }` to mutate state safely.

### Dependency Injection (Koin)
-   **Mechanism**: Use Koin Annotations (`koin-ksp-compiler`).
-   **Definitions**: Use annotations `@Single`, `@Factory`, or `@Named` instead of manual module definitions where possible.
-   **Modules**: Define generic modules with `@Module` and `@ComponentScan`.
-   **Injection**: Use constructor injection for all classes.

### Concurrency
-   **Coroutines**: Use `viewModelScope` for UI-related coroutines.
-   **Dispatchers**: Inject `DispatcherProvider` (or use a wrapper) instead of hardcoding `Dispatchers.IO` to ensure testability.
-   **Flows**: Use `StateFlow` for UI state and `SharedFlow` (via `Channel`) for Effects.

## 4. Build System & Gradle
-   **Configuration**: Use Kotlin DSL (`.kts`).
-   **Version Catalog**: All dependencies and versions MUST be defined in `gradle/libs.versions.toml`. Do not hardcode versions in build files.
-   **Convention Plugins**: Use the project's custom plugins located in `build-logic`:
    -   `alias(libs.plugins.convention.library)` for Kotlin libraries.
    -   `alias(libs.plugins.convention.compose)` for UI modules.
    -   `alias(libs.plugins.convention.feature)` for feature modules.

## 5. File & Naming Conventions
-   **Packages**: `com.[module].[layer]...` (e.g., `com.core.data.repository`).
-   **Interfaces**: No `I` prefix (e.g., `UserRepository`, not `IUserRepository`).
-   **Implementations**: Suffix with `Impl` (e.g., `UserRepositoryImpl`).
-   **DTOs**: Suffix with `Dto` (e.g., `UserDto`).
-   **Composables**: PascalCase, noun-based (e.g., `LoginScreen`, `PrimaryButton`).

## 6. Testing
-   **Frameworks**: Use `kotlin-test`, `mokkery` (for mocking), and `turbine` (for Flow testing).
-   **Requirement**: Business logic (Use Cases) and ViewModel states must be unit tested.