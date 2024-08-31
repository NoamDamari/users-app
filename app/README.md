# User Management App

## Overview

The User Management App is an Android application designed to manage user profiles efficiently. 
It allows users to create, update, and view their profiles, including profile images.
The app leverages modern Android development practices and integrates local database for a seamless user experience.


## Features

- **View Users**: Display a list of all user profiles.
- **Add User**: Register new user profiles with essential details and profile images.
- **Delete User**: Remove existing user profiles from the list.
- **Update User**: Modify user profile details and update profile images.


## Technologies Used

- **MVVM Architecture**: Separates concerns by using the Model-View-ViewModel pattern.
- **Room Database**: Provides a local SQLite database for offline data persistence.
- **LiveData**: Observes and reacts to data changes in real-time.
- **ViewModel**: Manages UI-related data lifecycle-consciously.
- **Retrofit**: Handles API requests for data synchronization.
- **ActivityResultLauncher**: Manages image selection from device storage.


## Installation

1. **Clone the Repository**:

2. **Open in Android Studio**:
   Open Android Studio and select "Open an existing project."
   Navigate to the directory where you cloned the repository and open it.

3. **Build and Run**:
   Click on the "Run" button in Android Studio or use the shortcut Shift + F10 to build and run the app on an emulator or a connected device.

## Usage

1. **View Users:**
    - Launch the app to see a list of all user profiles.

2. **Add User:**
    - Tap the **"Add User"** button to create a new user profile.
    - Enter the necessary details and choose a profile image.

3. **Update User:**
    - tap the menu button (three dots) next to the user's name.
    - Select **"Update"** from the menu options.
    - Select a user from the list to view and modify their profile information.
    - Save your changes by tapping the **"Update User"** button.

4. **Delete User:**
    - tap the menu button (three dots) next to the user's name.
    - Select **"Delete"** from the menu options.
    - A confirmation dialog will appear. Tap **"Yes"** to delete the user or **"No"** to abort the deletion.