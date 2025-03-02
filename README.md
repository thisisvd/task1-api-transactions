<h1 align="center">
  <br>
  Task 1: API Integration with Transactions
  <br>
</h1>

<p align="center">
 <a href="#introduction">Project Overview</a> •
 <a href="#setup-instructions">Setup Instructions</a> •
 <a href="#bonus-features-implemented">Bonus features implemented</a> •
 <a href="#source-code">APK build instructions</a> •
 <a href="#source-code">Source Code</a> •
 <a href="#released-apk">Released Apk</a> •
</p>

## Project Overview


## Setup Instructions

 - Clone the project on your system.
```bash
git clone https://github.com/thisisvd/task1-api-transactions.git
```
- Sync the project -> Clean Project -> then build it and run it on your appropiate android device.

## APK build instructions
 - Select the `build varient` (debug).
 - Select `Build` in top menu -> then `Build Apk Bundle(s) / Apk(s)` -> then `Build Apk`.

## Bonus features implemented
- ✅ `Dark Mode:` Added dark mode, which will automatically change according to the device theme.
- ✅ `Offline Mode (using Room Database):` Implemented a ROOM database that stores the transactions and provides transactions when the network is unavailable.
- ✅ `Search/Filter functionality:` Implemented a search functionality when entering a string that will filter the transaction list based on description, amount, date, and category.
  
## Source Code
- `Tech-stack used:` Java, DI(Hilt), Retrofit, Security Crypto, Biometric, ROOM, Timber etc. 
- Clean architecture with dependency injection was used using Hilt modules followed by MVVM(Model-View-Viewmodel). Below is the project structure of the application flow.
```
com.example.task1_api_transactions
│── application
│   └── BaseApplication
│── core
│   ├── NetworkUtils
│   ├── Resource
│   └── Utils
│── data
│   ├── local
│   │   ├── room
│   │   │   ├── TransactionDao
│   │   │   └── TransactionDatabase
│   │   └── EncryptedSharedPreference
│   ├── network
│   │   ├── Api
│   │   ├── ApiHelper
│   │   ├── ApiImpl
│   │   └── MainRepository
│── di
│   ├── DatabaseModule
│   └── NetworkModule
│── domain
│   ├── Transactions
│   ├── User
│   └── UserResponse
│── ui
│   ├── adapter
│   │   └── TransactionsAdapter
│   ├── login
│   │   └── LoginFragment
│   ├── transactions
│   │   └── TransactionsFragment
│   ├── viewmodel
│   │   └── MainViewModel
│   └── MainActivity
│── utils
│   ├── Constants
│   └── CoroutineHelper
│── res
```

## Released Apk
- Released Application path can be found [here!](https://github.com/thisisvd/task1-api-transactions/tree/master/app/release).
