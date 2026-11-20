Perfume Stock Manager

Perfume Stock Manager is an Android application for managing a perfume collection and tracking perfume decant sales.

The application is built with Kotlin, Jetpack Compose, MVVM architecture, Room 3, StateFlow, and Navigation Compose.

Main Features

* Add new perfumes
* View perfume details
* Edit perfume information
* Delete perfumes with confirmation
* Track bottle volume and remaining perfume stock
* Store perfume information such as concentration, fragrance family, notes, and status
* Support different statuses:
    * Owned
    * Wishlist
    * Want to test
* Persistent local storage using Room database

Additional Feature — Decant Sales Tracking

The additional feature allows the user to record perfume decant sales.

For every decant transaction, the user enters:

* Amount sold in milliliters
* Sale price in KZT

When the transaction is recorded:

1. The entered values are validated.
2. The perfume’s remaining stock is automatically decreased.
3. A new transaction is saved in the local Room database.
4. The transaction appears in the Decant History screen.
5. Total sold volume and total revenue are recalculated automatically.

The decant operation updates both perfume stock and transaction history.

Architecture

The application follows the MVVM architecture.

Compose UI
↓
ViewModel
↓
Repository
↓
DAO
↓
Room
↓
SQLite

The database exposes data using Flow.

The ViewModel collects the data and stores the current application state in StateFlow.

The UI observes this state using collectAsStateWithLifecycle(), so changes in the database are automatically reflected on the screens.

Technologies

* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose
* ViewModel
* StateFlow
* Kotlin Coroutines
* Room 3
* SQLite

Application Screens

The application contains four main screens:

* Perfume List
* Add / Edit Perfume
* Perfume Details
* Decant History

Screenshots

Perfume Details

The perfume details screen shows information about a selected perfume, its current stock, and available actions.

Record Decant

The Record Decant dialog allows the user to enter the amount of perfume sold and its price.

Decant History

The history screen stores all recorded decant transactions and automatically calculates total sold volume and total revenue.

Data Persistence

The application uses Room for local persistent storage.

The following data is stored locally:

* Perfume collection
* Remaining perfume stock
* Decant transaction history

The information remains available after closing and reopening the application.

Validation

The application validates user input before saving data.

Examples:

* Brand and perfume name cannot be empty
* Bottle volume must be greater than zero
* Remaining volume cannot be negative
* Remaining volume cannot exceed bottle volume
* Decant amount must be greater than zero
* Decant amount cannot exceed remaining stock
* Price cannot be negative

Project Structure

com.example.perfumestockmanager
│
├── PerfumeApplication.kt
├── MainActivity.kt
│
├── data
│   ├── PerfumeRepository.kt
│   │
│   └── local
│       ├── PerfumeEntity.kt
│       ├── DecantTransactionEntity.kt
│       ├── PerfumeDao.kt
│       ├── TransactionDao.kt
│       └── PerfumeDatabase.kt
│
└── ui
├── PerfumeUiState.kt
├── PerfumeViewModel.kt
├── PerfumeViewModelFactory.kt
│
├── screens
│   ├── PerfumeListScreen.kt
│   ├── AddEditPerfumeScreen.kt
│   ├── PerfumeDetailScreen.kt
│   └── HistoryScreen.kt
│
└── theme

Running the Application

1. Clone the repository.
2. Open the project in Android Studio.
3. Wait for Gradle synchronization to finish.
4. Start an Android emulator or connect an Android device.
5. Run the app configuration.

Minimum SDK: 24

Additional Feature Demonstration

Example workflow:

Open perfume details
↓
Select Record Decant
↓
Enter amount and price
↓
Record transaction
↓
Remaining stock decreases
↓
Transaction is stored in Room
↓
History and totals update automatically