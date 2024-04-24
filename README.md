<p align = "center"> 
<img src="https://www.freepnglogos.com/uploads/android-logo-png/android-logo-android-icon-logo-vector-eps-download-24.png"  width ="100" height="100" >
</p>
<div align="center">
 <h2> Android-MVVM-clean-architecture </h2>
</div>


This project allows users to search GitHub repositories and display JSON data in a WebView. It implements caching mechanisms for efficient data retrieval and management.

## Table of Contents
1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
    - [Clone the Repository](#clone-the-repository)
    - [Open in Android Studio](#open-in-android-studio)
    - [Build and Run](#build-and-run)
3. [Features](#features)
4. [Technologies Used](#technologies-used)

## Introduction

The project uses Kotlin as a development language and follows the MVVM (Model-View-ViewModel) design pattern. It utilizes Coroutines for asynchronous programming, Retrofit for making network requests, and Room Database for caching data. The main features include searching GitHub repositories, displaying JSON data in a WebView, taking screenshots of the preview screen, and implementing caching mechanisms.

## Getting Started

### Clone the Repository

To get started with the project, clone the repository to your local machine:

```bash
git clone https://github.com/amitbiswas1992/AndroidDemo.git
```
### Open in Android Studio

Open the project in Android Studio:

- Launch Android Studio.
- Choose "Open an existing Android Studio project".
- Navigate to the directory where you cloned the repository and select the project.

### Build and Run

Build and run the project in Android Studio:

- After Gradle sync is successful, you can build and run the project on your preferred emulator or connected device by clicking on the green "Run" button in the toolbar.


### Programming Language
- Kotlin

### Design Pattern
- MVVM
- Singleton

## Technologies Used
- Room DB
- ViewModel
- LiveData
- ViewBinding
- Retrofit
- Coroutines

## Features

#### Search GitHub Repository
- Users can search for GitHub repositories using keywords.

#### Display JSON Data in WebView
- JSON data retrieved from the GitHub API is displayed in a WebView.

#### Caching Mechanism
- If the same keyword is searched within 10 minutes, the app retrieves data from the Room database instead of making an API call.

#### Automatic Screenshot Generation
- Upon retrieving data from the WebView, the app automatically takes a screenshot and stores it in the device cache directory.

#### Cache Data Management
- Cache data older than 30 minutes is automatically deleted to maintain efficiency and manage storage space.
