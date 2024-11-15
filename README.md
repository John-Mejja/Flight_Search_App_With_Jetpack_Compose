# ✈️ Flight Search App

Flight Search App is an Android Basics with Compose project, that allows users to select airports to view the flight routes.
The app uses Fake Database of airports from Room, which displays the airports to the user. The user searches for the airport and selects it to display the routes.
The user can also save airport route as favorite, which is displayed on the app's home screen.

## 📃 Features

* Search for airport.
* Displays autocomplete suggestions as the user searches for airport.
* Display all the route destinations from the selected airport.
* Save favorite routes to the database.
* Display favorite routes on the home screen.

## 👨🏾‍💻 Technologies Used
* **Room :** The app uses Room to retrieve the airports and store favorite routes.
* **Preference Datastore :** The app uses Preference Datastore to save users search, which is loaded when the app restarts.
* **Dependency Injection :** The app uses manual dependency injection to provide repository to the viewModel.
* **Kotlin Coroutine :** The app uses coroutine to asynchronously execute some task in the background, improving UI responsiveness.
* **Kotlin Flows :** The app uses Flow, a kotlin language feature that searves as a reactive programming framework, to update Favorite Books from the database.
* **Jetpack Compose :** The app uses Jetpack compose, a modern toolkit for building Android UIs, to create the app's UI.
* 
## 🖼️ Screenshots
<img src="/screenshots/image_1.jpg" alt="Flight Search App homepage that displays favorite routes." width="200">
<img src="/screenshots/image_2.jpg" alt="Flight Search App search screen that displays all available airport when there is no query input." width="200">
<img src="/screenshots/image_3.jpg" alt="Flight Search App search screen that displays autocompletion suggestions as the users inputs query." width="200">
<img src="/screenshots/image_4.jpg" alt="Flight Search App search screen that displays all the routes according the user's query." width="200">

## Installation
To install the app, clone the repository and open the project in Android Studio. The app can be run on an emulator or a physical device.

## Acknowledgements
* [Android Basics With Compose](https://developer.android.com/courses/pathways/android-basics-compose-unit-5-pathway-2)
