## ShoppingApp

This is an Android shopping application built using Android Jetpack Compose with a Firebase backend. The app follows the MVVM (Model-View-ViewModel) architecture and has a minimum supported Android version of Android 10.

## Features
1) User authentication: The app allows users to create accounts, log in, and log out using Firebase Authentication.
2) Product listing: Users can view a list of products available for purchase.
3) Product details: Users can view detailed information about a specific product, including its price, description, and availability.
4) Shopping cart: Users can add products to their shopping cart and view the items they have selected for purchase.
5) Order placement: Users can place orders for the items in their shopping cart and receive confirmation of their purchase.
6) Order history: Users can view their order history, including details of past purchases.
7) Real-time updates: The app leverages Firebase Realtime Database or Firestore to provide real-time updates for product availability and order status.

## Screenshots

<div align="center">
<div>

</div>
</div>

## Prerequisites
Before running the app, make sure you have the following:

1) Android Studio Flamingo 2022.2.1 or later.
2) Android SDK with a minimum API level of 29 (Android 10).
3) A Firebase project with the following enabled:
    - **Authentication**: Enable Email/Password and Google sign-in methods in the Firebase Authentication section.
    - **Firestore**: Set up Firestore in Native mode for database storage.
    - **Storage**: Enable Firebase Storage for storing user and product-related files (Have to upgrade your plan now to enable this option).



# Accounts and Login
This app has 2 types of logins
1) Users
2) Admin

Create an admin account from firebase console in the format admin.user@gmail.com

## Dependencies
The project uses the following dependencies:

1) Jetpack Compose: A modern UI toolkit for building native Android apps.
2) Firebase: Provides backend services for authentication, real-time updates, and database storage.
3) Coroutines: Provides asynchronous programming capabilities.
4) ViewModel: Part of the Jetpack library that provides a lifecycle-aware container for UI-related data.
5) Navigation: Handles navigation between different screens and features in the app.
6) Hilt: Used for dependency injection.

